package serie2.grupo2.testFw;

import annotations.SetUp;
import annotations.TearDown;
import annotations.UnitTest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestAdapter<T> implements Test {

    private final String _name;
    private final TestSuite _suite;
    private Method _setUp = null;
    private Method _tearDown = null;

    public TestAdapter(String name, final T source) {

        _name = name;

        List<Method> tests = new ArrayList<Method>();
        List<TestCase> cases = new ArrayList<TestCase>();

        for (Method m : source.getClass().getMethods()) {
            if (m.isAnnotationPresent(SetUp.class)) {
                _setUp = m;
            } else if (m.isAnnotationPresent(TearDown.class)) {
                _tearDown = m;
            } else if (m.isAnnotationPresent(UnitTest.class)) {
                tests.add(m);
            }
        }

        if (_setUp != null && _tearDown != null && tests.size() > 0) {

            for (final Method testMethod : tests) {

                cases.add(new TestCase(
                        (String) ((UnitTest) testMethod.getAnnotation(UnitTest.class)).value()) {

                    @Override
                    public void runTest() {
                        try {
                            testMethod.invoke(source);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(TestAdapter.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            if (ex.getCause().getClass().equals(AssertionFailedError.class)) {
                                throw new AssertionFailedError(ex.getCause().getMessage());
                            } else if (ex.getCause().getClass().equals(RuntimeException.class)) {
                                throw new RuntimeException(ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public final void setup() {
                        try {
                            _setUp.invoke(source);
                        } catch (IllegalAccessException ex) {
                        } catch (InvocationTargetException ex) {
                            if (ex.getCause().getClass().equals(AssertionFailedError.class)) {
                                throw new AssertionFailedError(ex.getMessage());
                            } else if (ex.getCause().getClass().equals(RuntimeException.class)) {
                                throw new RuntimeException(ex.getMessage());
                            }

                        }
                    }

                    @Override
                    public final void tearDown() {
                        try {
                            _tearDown.invoke(source);
                        } catch (IllegalAccessException ex) {
                        } catch (InvocationTargetException ex) {
                            if (ex.getCause().getClass().equals(AssertionFailedError.class)) {
                                throw new AssertionFailedError(ex.getMessage());
                            } else if (ex.getCause().getClass().equals(RuntimeException.class)) {
                                throw new RuntimeException(ex.getMessage());
                            }

                        }
                    }
                });
            }
        }
        Test[] dest = new Test[cases.size()];
        int curr = 0;
        for (TestCase t : cases) {
            dest[curr++] = t;
        }

        _suite = new TestSuite(name, dest);
    }

    public String getName() {
        return _name;
    }

    public int countTestCases() {
        return _suite.countTestCases();
    }

    public void run(TestResult result) {
        _suite.run(result);
    }
}
