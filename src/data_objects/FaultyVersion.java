package data_objects;

public class FaultyVersion {
	private TestCase[] failures;
	private TestCase[] passedTCs;
	private String faultyVersionId;
	private int methodsCount;
	
	public FaultyVersion(TestCase[] failures, TestCase[] passedTCs,
			String faultyVersionId, int methodsCount) {
		this.failures = failures;
		this.passedTCs = passedTCs;
		this.faultyVersionId = faultyVersionId;
		this.methodsCount = methodsCount;
	}
	public String getFaultyVersionId() {
		return faultyVersionId;
	}
	/**	empty Constructor and getters and setters needed for serialization */
	public FaultyVersion() { }
	public TestCase[] getFailures() {
		return failures;
	}
	public void setFailures(TestCase[] failures) {
		this.failures = failures;
	}
	public TestCase[] getPassedTCs() {
		return passedTCs;
	}
	public void setPassedTCs(TestCase[] passedTCs) {
		this.passedTCs = passedTCs;
	}
	public int getMethodsCount() {
		return methodsCount;
	}
}
