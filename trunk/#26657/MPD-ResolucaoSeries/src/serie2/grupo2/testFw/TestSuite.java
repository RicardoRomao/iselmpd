package serie2.grupo2.testFw;

import java.util.ArrayList;
import java.util.List;

public class TestSuite implements Test {

    private final String _tName;
    private final List<Test> _testCases;

    public TestSuite(String name, Test... cases) {
        _tName = name;
        _testCases = new ArrayList<Test>();
        for (Test t : cases) {
            _testCases.add(t);
        }
    }

    public String getName() {
        return _tName;
    }

    public int countTestCases() {
        return _testCases.size();
    }

    public void run(TestResult result) {
        result.startTest(this);
        for (Test t : _testCases) {
            t.run(result);
        }
        result.endTest(this);
    }
}
