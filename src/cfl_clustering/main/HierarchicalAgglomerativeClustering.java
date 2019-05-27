package cfl_clustering.main;

import java.util.ArrayList;
import java.util.List;

import ch.usi.inf.sape.hac.HierarchicalAgglomerativeClusterer;
import ch.usi.inf.sape.hac.agglomeration.AgglomerationMethod;
import ch.usi.inf.sape.hac.agglomeration.AverageLinkage;
import ch.usi.inf.sape.hac.dendrogram.Dendrogram;
import ch.usi.inf.sape.hac.dendrogram.DendrogramBuilder;
import ch.usi.inf.sape.hac.experiment.Experiment;
import data_objects.Cluster;
import cfl_clustering.repselection.KNNToCenterSelection;
import cfl_clustering.repselection.RepresentativeSelectionStrategy;
import hac.experiment.custom.AverageCenterCalculation;
import hac.experiment.custom.CustomDissimilarityMeasure;
import hac.experiment.custom.FailureClusteringExperiment;
import hac.experiment.custom.ICenterCalculation;
import hac.experiment.custom.JaccardDistance;
import data_objects.TestCase;
import main.HACPrioritizationBase;
import utils.PrintUtils;

public class HierarchicalAgglomerativeClustering extends HACPrioritizationBase{
	
	
	public HierarchicalAgglomerativeClustering(TestCase[] failures,
			TestCase[] passedTCs) {
		super(failures, passedTCs);
	}
	private Dendrogram performHAC(CustomDissimilarityMeasure dissimilarityMeasure) {
		Experiment experiment = new FailureClusteringExperiment(failures);
		AgglomerationMethod agglomerationMethod = new AverageLinkage();
		DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(experiment.getNumberOfObservations());
		HierarchicalAgglomerativeClusterer clusterer = new HierarchicalAgglomerativeClusterer(experiment, dissimilarityMeasure, agglomerationMethod);
		clusterer.cluster(dendrogramBuilder);
		return dendrogramBuilder.getDendrogram();
	}
	@Override
	public void prioritizeFailures() {
		ICenterCalculation centerCalc = new AverageCenterCalculation();
		CustomDissimilarityMeasure dissimilarityMeasure = new JaccardDistance(centerCalc);
		Dendrogram dendrogram = performHAC(dissimilarityMeasure);
		
		ClusterBuilder cb = new ClusterBuilder(dendrogram.getRoot(), passedTCs, failures, sbflConfig);
		List<Cluster> clusters = cb.getClustersOfCuttingLevel();
		Refinement refinement = new Refinement(cb.getPassedTCsCluster(), sbflConfig);
		System.out.println("TRACE: Number of clusters before refinement step is " + clusters.size());
		clusters = refinement.refineClusters(clusters);
		System.out.println("TRACE: Number of clusters after refinement step is " + clusters.size());
		
		clusters = clusterPrioritization.prioritizeClusters(clusters);
		
		System.out.println("TRACE: The failure clustering strategy created the following clusters");
		PrintUtils.dumpClusters(clusters);
		
		computeRepresentativesOfClusters(clusters, new KNNToCenterSelection(), dissimilarityMeasure);
		prioritizedFailures = getRepresentativesOfClusters(clusters);
	}
	/**
	 * Returns the representatives of the passed prioritized clusters.
	 * The representatives of the clusters must have been already computed before.
	 */
	private List<TestCase> getRepresentativesOfClusters(List<Cluster> clusters){
		List<TestCase> prioritizedFailures = new ArrayList<TestCase>();
		for (Cluster c: clusters) {
			prioritizedFailures.add(c.getRepresentative());
		}
		return prioritizedFailures;
	}
	/**
	 * calls the computeRepresentative method for each cluster.
	 */
	private void computeRepresentativesOfClusters(List<Cluster> clusters, RepresentativeSelectionStrategy representativeSelection, CustomDissimilarityMeasure dissimilarityMeasure) {
		for (Cluster c: clusters) {
			c.computeRepresentative(representativeSelection, dissimilarityMeasure);
		}
	}
}
