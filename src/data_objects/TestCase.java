package data_objects;

import java.util.HashSet;
import java.util.Set;

import data_objects.globals.FaultyProjectGlobals;

public class TestCase implements Comparable<TestCase>{
	public String name;
	public boolean passed;
	public Boolean[] coverage;
	public int[] numericCoverage;
	public Set<Integer> coveredMethods;
	
	public TestCase(String name, boolean passed, Boolean[] coverage) {
		this.name = name;
		this.passed = passed;
		this.coverage = coverage;
		this.coveredMethods = initCoveredMethods();
		this.numericCoverage = initNumericCoverage();
	}
	public TestCase(String name, boolean passed, Set<Integer> coveredMethods) {
		this.name = name;
		this.passed = passed;
		this.coveredMethods = coveredMethods;
		this.coverage = null;
		this.numericCoverage = null;
	}
	public void initBooleanAndNumericCoverage() {
		if (coveredMethods != null) {
			coverage = initBooleanCoverage();
			numericCoverage = initNumericCoverage();
		}
	}
	/**
	 * Fills the coveredMethods set based on the boolean coverage.
	 */
	private Set<Integer> initCoveredMethods(){
		Set<Integer> coveredMethods = new HashSet<Integer>();
		for (int i = 0; i < coverage.length; i++) {
			if (coverage[i]) {
				coveredMethods.add(i);
			}
		}
		return coveredMethods;
	}
	/**
	 * Fills the numeric coverage based on the boolean coverage. 
	 */
	private int[] initNumericCoverage() {
		int[] numericCoverage = new int[FaultyProjectGlobals.methodsCount];
		for (int i = 0; i < FaultyProjectGlobals.methodsCount; i++) {
			if(coverage[i]) {
				numericCoverage[i]++;
			}
		}
		return numericCoverage;
	}
	/**
	 * Fills the boolean coverage based on the coveredMethods set.
	 */
	private Boolean[] initBooleanCoverage() {
		Boolean[] coverage = new Boolean[FaultyProjectGlobals.methodsCount];
		for (int i = 0; i < FaultyProjectGlobals.methodsCount; i++) {
			if (coveredMethods.contains(i)) {
				coverage[i] = true;
			} else {
				coverage[i] = false;
			}
		}
		
		return coverage;
	}
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(TestCase tc) {
		if (tc == null) {
			return 1;
		}
		if (coveredMethods.size() > tc.coveredMethods.size()) {
			return 1;
		}
		if (coveredMethods.size() == tc.coveredMethods.size()) {
			return 0;
		}
		return -1;
	}
}
