package data_objects.globals;

import data_objects.FaultyVersion;

public class FaultyProjectGlobals {
	/**	must be set for every faulty version */
	
	public static int methodsCount = 0;
	public static int failuresCount = 0;
	public static int passedTestsCount = 0;
	public static int testsCount = 0;
	
	public static void init(FaultyVersion faultyVersion) {
		FaultyProjectGlobals.methodsCount = faultyVersion.getMethodsCount();
		FaultyProjectGlobals.failuresCount = faultyVersion.getFailures().length;
		FaultyProjectGlobals.passedTestsCount = faultyVersion.getPassedTCs().length;
		FaultyProjectGlobals.testsCount = FaultyProjectGlobals.failuresCount + FaultyProjectGlobals.passedTestsCount;
	}
}
