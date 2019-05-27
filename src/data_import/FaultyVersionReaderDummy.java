package data_import;

import data_objects.FaultyVersion;
import data_objects.TestCase;
import data_objects.globals.FaultyProjectGlobals;

/**
 * TODO: (Nils)	Import schreiben, welcher aus den Kundendaten eine FaultyVersion erzeugt
 */
public class FaultyVersionReaderDummy {
	public static FaultyVersion readFaultyVersion() {
		return createDummyVersion1();
		/**
		 * TODO: (Nils) eigene dummy faultyVersion initialisieren und damit mal debuggen
		 * 			Problem beim testen/debuggen: Man hat kein Orakel, also man kann die Cluster und repräsentativen Failures,
		 * 			welche das failure-clustering ausgeben soll, nicht vorhersagen (manuell durchrechnen wäre sehr aufwendig).
		 * 			Man kann jedoch zumindest Vermutungen aufstellen: Failures mit sehr ähnlicher Coverage sollten womöglich
		 * 			im gleichen Cluster landen (siehe dummyVersion1).
		 */
	}
	/**
	 * 	Creates a faultyVersion with the following coverage matrix
	 * 	Test-A-Fail     1   0   1   0   1   0   1   0   F
		Test-B-Fail     1   0   1   0   1   0   1   0   F
		Test-C-Fail     0   1   0   1   0   1   1   1   F
		Test-D-Fail     0   0   0   0   0   0   1   1   F
		Test-E-Fail     0   1   0   1   1   1   0   0   F
		Test-F-Fail     0   1   1   1   1   1   0   0   F
		Test-G-Success  0   0   0   0   1   1   1   1   S
		Test-H-Success  1   0   1   0   0   0   0   1   S
		Test-I-Success  1   1   0   1   0   1   1   1   S
		Test-J-Success  0   0   0   1   1   1   0   1   S
		Test-K-Success  0   1   1   0   0   1   0   0   S
		Test-L-Success  1   0   1   1   1   1   0   0   S
		Test-M-Success  1   1   1   0   1   1   1   1   S
		Test-N-Success  0   0   0   1   0   0   1   1   S
	 */
	private static FaultyVersion createDummyVersion1() {
		/**	zur Initialisierung der Coverage arrays in der TestCase Klasse wird auf
		 * 	FaultyProjectGlobals.methodsCount zugegriffen	*/
		FaultyProjectGlobals.methodsCount = 8;
		
		TestCase[] failures = new TestCase[6];
		failures[0] = new TestCase("Test-A-Fail", false, new Boolean[] {true, false, true, false, true, false, true, false});
		failures[1] = new TestCase("Test-B-Fail", false, new Boolean[] {true, false, true, false, true, false, true, false});
		failures[2] = new TestCase("Test-C-Fail", false, new Boolean[] {false, true, false, true, false, true, true, true});
		failures[3] = new TestCase("Test-D-Fail", false, new Boolean[] {false, false, false, false, false, false, true, true});
		failures[4] = new TestCase("Test-E-Fail", false, new Boolean[] {false, true, false, true, true, true, false, false});
		failures[5] = new TestCase("Test-F-Fail", false, new Boolean[] {false, true, true, true, true, true, false, false});
		
		TestCase[] passedTCs = new TestCase[8];
		passedTCs[0] = new TestCase("Test-G-Success", true, new Boolean[] {false, false, false, false, true, true, true, true});
		passedTCs[1] = new TestCase("Test-H-Success", true, new Boolean[] {true, false, true, false, false, false, false, true});
		passedTCs[2] = new TestCase("Test-I-Success", true, new Boolean[] {true, true, false, true, false, true, true, true});
		passedTCs[3] = new TestCase("Test-J-Success", true, new Boolean[] {false, false, false, true, true, true, false, true});
		passedTCs[4] = new TestCase("Test-K-Success", true, new Boolean[] {false, true, true, false, false, true, false, false});
		passedTCs[5] = new TestCase("Test-L-Success", true, new Boolean[] {true, false, true, true, true, true, false, false});
		passedTCs[6] = new TestCase("Test-M-Success", true, new Boolean[] {true, true, true, false, true, true, true, true});
		passedTCs[7] = new TestCase("Test-N-Success", true, new Boolean[] {false, false, false, true, false, false, true, true});
		return new FaultyVersion(failures, passedTCs, "dummyVersion-1", 8);
	}
}
