package serie2.grupo2.testFw;

public interface ITestResultListener {

    public void notifyTestStart(Test t);
    public void notifyTestEnd(Test t);
    public void notifyTestFailure(TestFailure f);
    public void notifyTestError(TestFailure f);
    
}
