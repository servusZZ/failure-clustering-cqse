package main;

import cfl_clustering.main.HACFactory;
import data_import.FaultyVersionReaderDummy;
import data_objects.FaultyVersion;
import data_objects.globals.FaultyProjectGlobals;
import utils.PrintUtils;

public class Main {
	public static void main(String[] args) {
		System.out.println("TRACE: Read dummy input...");
		FaultyVersion faultyVersion = FaultyVersionReaderDummy.readFaultyVersion();
		FaultyProjectGlobals.init(faultyVersion);
		
		System.out.println("TRACE: Perform failure clustering...");
		HACPrioritizationBase failureClustering = HACFactory.createHACStrategy(faultyVersion.getFailures(), faultyVersion.getPassedTCs());
		failureClustering.prioritizeFailures();
		
		System.out.println("INFO: Failures should be inspected by developers in the following order:");
		System.out.println(PrintUtils.printTestCasesList(failureClustering.prioritizedFailures));
		System.out.println("TRACE: Program finished successfully!");
	}
}
