package serie2.grupo2.testFw;

import java.io.PrintStream;

/**
 * @uml.dependency   supplier="testFw.TestResult"
 */
public class TestRunner {

    private PrintStream printer;

    /**
     * Constructs a TestRunner.
     */
    public TestRunner() {
        this(System.out);
    }

    /**
     * Constructs a TestRunner using the given stream for all the output
     */
    public TestRunner(PrintStream printer) {
        this.printer = printer;
    }

    /**
     * Runs a test and collects its results on result argument.
     */
    public TestResult doRun(Test test, TestResult result) {
        long startTime = System.currentTimeMillis();
        test.run(result);
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;
        printer.println(String.format("Time: %d ms", runTime));
        return result;
    }

    /**
     * Runs a test and collects its results.
     * This method can be used to start a test run
     * from your program.
     * <pre>
     * public static void main (String[] args) {
     *    (new TestRunner()).run(makeTest());
     * }
     * </pre>
     */
    public TestResult doRun(Test test) {
        return doRun(test, new TestResult());

        // Quest�o 1 > aplicar o padr�o Template Method
        // TestSuite usa o Composite
        // TestResult usa Listeners.

    }
}
