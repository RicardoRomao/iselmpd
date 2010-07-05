package serie2.grupo2.testFw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * A <code>TestResult</code> collects the results of executing
 * a test case. It is an instance of the Collecting Parameter pattern.
 * The test framework distinguishes between <i>failures</i> and <i>errors</i>.
 * A failure is anticipated and checked for with assertions. Errors are
 * unanticipated problems like an {@link ArrayIndexOutOfBoundsException}.
 *
 * @see Test
 */
public class TestResult extends Object {
	protected List<TestFailure> fFailures;
	protected List<TestFailure> fErrors;
    private List<ITestResultListener> tListeners;
	protected int fRunTests;
	private boolean fStop;
	
	public TestResult() {
		fFailures= new ArrayList<TestFailure>();
		fErrors= new ArrayList<TestFailure>();
		fRunTests= 0;
		fStop= false;
	}

    public synchronized void addTestResultListener(ITestResultListener l) {
        if (tListeners == null)
            tListeners = new ArrayList<ITestResultListener>();
        tListeners.add(l);
    }

	/**
	 * Adds an error to the list of errors. The passed in exception
	 * caused the error.
	 */
	public synchronized void addError(Test test, Throwable t) {
		TestFailure tf = new TestFailure(test,t);
        fErrors.add(tf);
        for (ITestResultListener l : tListeners)
            l.notifyTestError(tf);
	}
	/**
	 * Adds a failure to the list of failures. The passed in exception
	 * caused the failure.
	 */
	public synchronized void addFailure(Test test, AssertionFailedError t) {
		TestFailure tf = new TestFailure(test,t);
        fFailures.add(tf);
        for (ITestResultListener l : tListeners)
            l.notifyTestFailure(tf);
	}
	/**
	 * Informs the result that a test was completed.
	 */
	public void endTest(Test test) {
        for (ITestResultListener l : tListeners)
            l.notifyTestEnd(test);
	}
	/**
	 * Gets the number of detected errors.
	 */
	public synchronized int errorCount() {
		return fErrors.size();
	}
	/**
	 * Returns an Enumeration for the errors
	 */
	public synchronized Enumeration<TestFailure> errors() {
		return Collections.enumeration(fErrors);
	}
	/**
	 * Gets the number of detected failures.
	 */
	public synchronized int failureCount() {
		return fFailures.size();
	}
	/**
	 * Returns an Enumeration for the failures
	 */
	public synchronized Enumeration<TestFailure> failures() {
		return Collections.enumeration(fFailures);
	}
	/**
	 * Gets the number of run tests.
	 */
	public synchronized int runCount() {
		return fRunTests;
	}
	/**
	 * Checks whether the test run should stop
	 */
	public synchronized boolean shouldStop() {
		return fStop;
	}
	/**
	 * Informs the result that a test will be started.
	 */
	public void startTest(Test test) {
		final int count= test.countTestCases();
		synchronized(this) {
			fRunTests+= count;
		}
        for (ITestResultListener l : tListeners)
            l.notifyTestStart(test);
	}
	/**
	 * Marks that the test run should stop.
	 */
	public synchronized void stop() {
		fStop= true;
	}
	/**
	 * Returns whether the entire test was successful or not.
	 */
	public synchronized boolean wasSuccessful() {
		return failureCount() == 0 && errorCount() == 0;
	}
}