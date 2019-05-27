package cfl_clustering.cluster_prioritization;

import java.util.List;

import data_objects.Cluster;
import cfl_clustering.sbfl.SBFLConfiguration;

public abstract class ClusterPrioritizationBase {
	SBFLConfiguration sbflConfig;
	
	public ClusterPrioritizationBase(SBFLConfiguration sbflConfig) {
		this.sbflConfig = sbflConfig;
	}
	/**
	 * prioritizes the passed clusters, so that the representative of the first cluster
	 * in the list is the first Failure that should be fixed.<br>
	 * NOTE: This method changes the passed list of clusters.
	 */
	public abstract List<Cluster> prioritizeClusters(List<Cluster> clusters);
}
