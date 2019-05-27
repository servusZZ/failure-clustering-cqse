package main;

import java.util.List;

import cfl_clustering.cluster_prioritization.ClusterPrioritizationBase;
import cfl_clustering.sbfl.SBFLConfiguration;
import data_objects.TestCase;

public abstract class HACPrioritizationBase{
	protected TestCase[] failures;
	protected TestCase[] passedTCs;
	protected List<TestCase> prioritizedFailures;
	protected String strategyName = "StrategyBaseClass";
	
	protected SBFLConfiguration sbflConfig;
	protected ClusterPrioritizationBase clusterPrioritization;
	
	public HACPrioritizationBase(TestCase[] failures,TestCase[] passedTCs) {
		this.failures = failures;
		this.passedTCs = passedTCs;
	}

	/**
	 * Returns a prioritized order of all failures in which they should be investigated according
	 * to the concrete prioritization strategy.
	 */
	public abstract void prioritizeFailures();
	
	public void setSbflConfig(SBFLConfiguration sbflConfig) {
		this.sbflConfig = sbflConfig;
	}
	public void setClusterPrioritization(ClusterPrioritizationBase clusterPrioritization) {
		this.clusterPrioritization = clusterPrioritization;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
}
