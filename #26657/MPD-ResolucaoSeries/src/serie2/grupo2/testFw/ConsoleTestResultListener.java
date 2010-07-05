package serie2.grupo2.testFw;

public class ConsoleTestResultListener implements ITestResultListener{

    public void notifyTestStart(Test t) {
        System.out.println(t.getName() + " has started....");
    }

    public void notifyTestEnd(Test t) {
        System.out.println(t.getName() + " done!");
    }

    public void notifyTestFailure(TestFailure f) {
        System.out.println(f.fFailedTest.getName() +
                " failed with the following message:\n" +
                f.fThrownException.getLocalizedMessage());
    }

    public void notifyTestError(TestFailure f) {
        System.out.println(f.fFailedTest.getName() + 
                " resulted in an error with the following message:\n" +
                f.fThrownException.getLocalizedMessage());
    }

}
