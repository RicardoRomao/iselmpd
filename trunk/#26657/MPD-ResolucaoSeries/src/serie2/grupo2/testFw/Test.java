package serie2.grupo2.testFw;

public interface Test {
	/**
	 * Gets the name of a TestCase
	 * @return the name of the TestCase
	 */
	public String getName();
	/**
	 * Counts the number of test cases that will be run by this test.
	 */
	public int countTestCases();
	/**
	 * Runs a test and collects its result in a TestResult instance.
	 */
	public void run(TestResult result);
}
