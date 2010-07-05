package serie2.grupo2.testFw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TestCase implements Test {

    protected List<Integer> itens = new ArrayList<Integer>();
    /**
     * the name of the test case
     */
    private String fName;

    /**
     * Constructs a test case with the given name.
     */
    public TestCase(String name) {
        fName = name;
    }

    /**
     * Gets the name of a TestCase
     * @return the name of the TestCase
     */
    public String getName() {
        return fName;
    }

    /**
     * Counts the number of test cases executed by run(TestResult result).
     */
    @Override
    public int countTestCases() {
        return 1;
    }

    /**
     * Runs a test and collects its result in a TestResult instance.
     */
    @Override
    public void run(TestResult result) {
        try {
            this.setup();
            result.startTest(this);
            this.runTest();
            result.endTest(this);

        } catch (AssertionFailedError e) {
            result.addFailure(this, e);
        } catch (RuntimeException e) {
            result.addError(this, e);
            throw e;
        } finally {
            this.tearDown();
        }
    }

    /**
     * Override to run the test and assert its state.
     */
    public abstract void runTest();

    /**
     * Returns a string representation of the test case
     */
    @Override
    public String toString() {
        return getName() + "(" + getClass().getName() + ")";
    }

    public void setup() {
        Integer[] arr = {7, 8, 6, 1, 2, 9, 6, 9, 9, 1, 6, 5, 4, 9, 6, 5, 9, 6, 7};
        itens.addAll(Arrays.asList(arr));
    }

    public void tearDown() {
        itens.clear();
        itens = null;
    }
}
