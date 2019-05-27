package data_objects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import data_objects.globals.FaultyProjectGlobals;
import cfl_clustering.repselection.RepresentativeSelectionStrategy;
import hac.experiment.custom.CustomDissimilarityMeasure;
import cfl_clustering.sbfl.SBFLConfiguration;
import utils.PrintUtils;
import data_objects.TestCase;

public class Cluster implements Comparable<Cluster>{
	/**	(sorted) list of methodIDs which are most suspicious **/
	private Set<Integer> suspiciousSet;
	private TestCase[] failedTCs;
	/**	values for Ncf, Nuf, Ncs for each method **/
	private DStarTerms[] methodDStarTerms;
	/**	the representative TC of the cluster, stores the result of the last call of computeRepresentative()*/
	private TestCase representative;
	/**	D* suspiciousness value for each method	 **/
	Map<Integer, Double> methodDStarSusp = new HashMap<Integer, Double>();
	private SBFLConfiguration sbflConfig;
	
	public Cluster(TestCase[] failedTCs, DStarTerms[] passedMethodDStarTerms,
			SBFLConfiguration sbflConfig) {
		this.failedTCs = failedTCs;
		this.sbflConfig = sbflConfig;
		initMethodDStarTerms(passedMethodDStarTerms);
		updateSupsiciousSet(failedTCs);
		representative = null;
	}
	private void initMethodDStarTerms(DStarTerms[] passedMethodDStarTerms) {
		methodDStarTerms = new DStarTerms[FaultyProjectGlobals.methodsCount];
		for (int i = 0; i < FaultyProjectGlobals.methodsCount; i++) {
			methodDStarTerms[i] = passedMethodDStarTerms[i].clone();
		}
	}
	public void dump() {
		System.out.println("Cluster  " + toString());
		System.out.println("    D4 Values       [" + PrintUtils.printMapValuesOrdered(methodDStarSusp) + "]");
		System.out.println("    Suspicious Set  " + suspiciousSet.toString());
	}
	/**
	 * Returns the name of the Cluster as concatenation of the contained Failures.
	 */
	@Override
	public String toString() {
		return ("[" + Arrays.toString(failedTCs) + "]");
	}
	/**
	 * Computes the representative of the Cluster. Depends on a representative selection strategy and
	 * the distance computation of two Test Cases.
	 */
	public TestCase computeRepresentative(RepresentativeSelectionStrategy strat, CustomDissimilarityMeasure dis) {
		representative = strat.selectRepresentative(this, dis);
		return representative;
	}
	/**
	 * Computes the center of the Cluster, given some Distance metric.
	 * returns the 'coordinates' (i.e. the method coverage array) of the center.
	 * Center = The point for which the sum to all points in the cluster is minimal
	 */
	public double[] getCenter(CustomDissimilarityMeasure dis) {
		return dis.computeCenter(failedTCs);
	}
	/**
	 * calculates the suspicious set.
	 * @param newFailedTCs
	 */
	private void updateSupsiciousSet(TestCase[] newFailedTCs) {
		if (newFailedTCs == null || newFailedTCs.length == 0) {
			System.err.println("New Failed TCs are null or empty. No reason to update the susp Set.");
			return;
		}
		for (int i = 0; i < FaultyProjectGlobals.methodsCount; i++) {
			// Performance verbesserung: 
			//		(derzeit noch nicht benötigt, da Cluster nur erstellt aber nie geupdatet wird)
			//		updateTermValues soll boolean zurückgeben, welcher signalisiert ob sich ein Wert geändert hat
			//		methodDStarSusp speichern und nur neu berechnen, falls sich ein TermValue geändert hat.
			methodDStarTerms[i].updateTermValues(newFailedTCs);
			methodDStarSusp.put(i, methodDStarTerms[i].getD4Suspiciousness());
		}
		suspiciousSet = retrieveMostSuspiciousSet(methodDStarSusp);
		return;
	}
	/**
	 * Sorts the methodDStarSusp Map and returns only the "most suspicious" elements, defined by the threshold.
	 */
	private Set<Integer> retrieveMostSuspiciousSet(Map<Integer, Double> methodDStarSusp) {
		return sbflConfig.computeMostSuspiciousSet(methodDStarSusp);
	}
	public Set<Integer> getSuspiciousSet() {
		return suspiciousSet;
	}
	public TestCase[] getFailedTCs() {
		return failedTCs;
	}
	/** 
	 * The representative TC of the cluster, stores the result of the last call of computeRepresentative().<br>
	 * CAUTION: computeRepresentative() has to be called at least once before calling this method.
	 */
	public TestCase getRepresentative() {
		if (representative == null) {
			System.err.println("ERROR: getRepresentative method of cluster is called before the representative was computed by method computeRepresentative().");
		}
		return representative;
	}
	/**
	 * Natural ordering of clusters by their size (number of contained failing test cases)
	 * Note: This class has a natural ordering that is inconsistent with equals
	 */
	@Override
	public int compareTo(Cluster o) {
		if (o == null) {
			return 1;
		}
		if (this.getFailedTCs().length > o.getFailedTCs().length) {
			return 1;
		} else if (this.getFailedTCs().length == o.getFailedTCs().length) {
			return 0;
		}
		return -1;
	}
}
