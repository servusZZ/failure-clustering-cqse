package cfl_clustering.main;

import java.util.ArrayList;
import java.util.List;

import cfl_clustering.cluster_prioritization.ClusterPrioritizationDissimilarGreatestFirst;

import cfl_clustering.sbfl.JaccardSBFLConfiguration;
import cfl_clustering.sbfl.OverlapConfiguration;
import cfl_clustering.sbfl.SBFLConfiguration;
import data_objects.TestCase;
import main.HACPrioritizationBase;

public class HACFactory {
	
	/**
	 * TODO: (Nils)
	 * 		- Kapitel 6 Parameter-Optimierung meiner MA lesen und gegebenfalls Parameter auf den Kontext des Kundenprojektes anpassen
	 * 		- Parameter SIMILARITY_THRESHOLD kann z.B. erhöht werden, falls mehr (kleinere) Cluster gebildet werden sollten.
	 * 		- auch werden durch Verwenden der JaccardSBFLConfiguration mehr (kleinere) Cluster gebildet als durch die OverlapConfiguration
	 * 		- MOST_SUSP_THRESHOLD würde ich erst mal so lassen
	 */
	public static HACPrioritizationBase createHACStrategy(TestCase[] failures, TestCase[] passedTCs) {
		HACPrioritizationBase strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		SBFLConfiguration sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		/**	a greater value means that 2 clusters are similar. */
		sbflConfig.SIMILARITY_THRESHOLD = 0.70;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap strict");
		return strategy;
	}
	
	public static List<HACPrioritizationBase> createHACStrategies(TestCase[] failures, TestCase[] passedTCs){
		List<HACPrioritizationBase> strategies = new ArrayList<HACPrioritizationBase>();
		
		strategies.addAll(createStrategiesWithOverlapConfig(failures, passedTCs));
		strategies.addAll(createStrategiesWithJaccardConfig(failures, passedTCs));
		
		return strategies;
	}
	
	private static List<HACPrioritizationBase> createStrategiesWithJaccardConfig(TestCase[] failures, TestCase[] passedTCs){
		List<HACPrioritizationBase> jaccardStrategies = new ArrayList<HACPrioritizationBase>();
		
		// medium, 
		HACPrioritizationBase strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		SBFLConfiguration sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		/**	a greater value means that 2 clusters are similar.<br>
		 * 3 of 5 overlap elements are not considered as similar, 6 of 9 are similar */
		sbflConfig.SIMILARITY_THRESHOLD = 0.60;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard medium");
		jaccardStrategies.add(strategy);
		
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.70;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard strict");
		jaccardStrategies.add(strategy);
		
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.85;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard veryStrict");
		jaccardStrategies.add(strategy);
		
		// soft, clusters are more easier seen as similar
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.30;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard soft");
		jaccardStrategies.add(strategy);
		
		// very soft, clusters are more easier seen as similar
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.20;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard very soft");
		jaccardStrategies.add(strategy);
		
		// bigger suspicious set, no max value
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = Integer.MAX_VALUE;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.60;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard SuspSetNoUpperbound");
		jaccardStrategies.add(strategy);
		
		// bigger suspicious set, no max value
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new JaccardSBFLConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = Integer.MAX_VALUE;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.30;
		sbflConfig.SIMILARITY_THRESHOLD = 0.30;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Jaccard SuspSetNoUpperbound biggerSuspSet soft");
		jaccardStrategies.add(strategy);
		
		return jaccardStrategies;
	}
	private static List<HACPrioritizationBase> createStrategiesWithOverlapConfig(TestCase[] failures, TestCase[] passedTCs){
		List<HACPrioritizationBase> overlapStrategies = new ArrayList<HACPrioritizationBase>();
		
		// medium
		HACPrioritizationBase strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		SBFLConfiguration sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		/**	a greater value means that 2 clusters are similar.<br>
		 * 3 of 5 overlap elements are not considered as similar, 6 of 9 are similar */
		sbflConfig.SIMILARITY_THRESHOLD = 0.60;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap medium");
		overlapStrategies.add(strategy);
		
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.70;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap strict");
		overlapStrategies.add(strategy);
		
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.85;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap veryStrict");
		overlapStrategies.add(strategy);
		
		// soft, clusters are more easier seen as similar
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.30;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap soft");
		overlapStrategies.add(strategy);
		
		// soft, clusters are more easier seen as similar
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = 12;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.20;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap very soft");
		overlapStrategies.add(strategy);
		
		// bigger suspicious set, no max value
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = Integer.MAX_VALUE;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.20;
		sbflConfig.SIMILARITY_THRESHOLD = 0.60;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap SuspSetNoUpperbound");
		overlapStrategies.add(strategy);
		
		// bigger suspicious set, no max value
		strategy = new HierarchicalAgglomerativeClustering(failures, passedTCs);
		sbflConfig = new OverlapConfiguration();
		sbflConfig.MOST_SUSP_MAX_COUNT = Integer.MAX_VALUE;
		sbflConfig.MOST_SUSP_MIN_COUNT = 4;
		sbflConfig.MOST_SUSP_THRESHOLD = 0.30;
		sbflConfig.SIMILARITY_THRESHOLD = 0.30;
		strategy.setSbflConfig(sbflConfig);
		strategy.setClusterPrioritization(new ClusterPrioritizationDissimilarGreatestFirst(sbflConfig));
		strategy.setStrategyName("HAC Overlap SuspSetNoUpperbound biggerSuspSet soft");
		overlapStrategies.add(strategy);

		return overlapStrategies;
	}
}
