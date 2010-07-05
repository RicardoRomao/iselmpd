package serie2.grupo2.testListUtils;

import java.util.Enumeration;
import serie2.grupo2.testFw.ITestResultListener;
import serie2.grupo2.testFw.ListTestResultListener;
import serie2.grupo2.testFw.TestAdapter;

import serie2.grupo2.testFw.TestFailure;
import serie2.grupo2.testFw.TestResult;
import serie2.grupo2.testFw.TestRunner;
import serie2.grupo2.testFw.TestSuite;

public class App {

	public static void main(String[] args) throws InterruptedException {

		TestRemoveOccurences test1 = new TestRemoveOccurences();
		TestRemoveRepetitions test2 = new TestRemoveRepetitions();
        TestSuite suite = new TestSuite("My Test Suite",test1,test2);

		TestRunner runner = new TestRunner();
		TestResult res = new TestResult();
		ITestResultListener tListener = new ListTestResultListener();
        res.addTestResultListener(tListener);

        TestAdapter<TestListUtils> adap =
                new TestAdapter<TestListUtils>(
                    "My Test Adapter",
                    new TestListUtils()
                );

        runner.doRun(adap, res);

        //runner.doRun(suite, res);
        //runner.doRun(test1, res);
		//runner.doRun(test2, res);
		//printResults(res);
	}
	public static void printResults(TestResult res){
		if(res.failureCount() == 0 && res.errorCount() == 0){
			System.out.println(String.format("\nOK (%d tests)", res.runCount()));
			return;
		}
		if(res.failureCount() != 0 ){
			System.out.println(String.format("\nThere was %d failure%s:", 
					res.failureCount(), 
					res.failureCount() > 1? "s:": ":"));
			int i = 1;
			for(Enumeration<TestFailure> iter = res.failures(); iter.hasMoreElements();){
				System.out.println(i++ + ") " + iter.nextElement()); 
			}
		}
		if(res.errorCount() != 0 ){
			System.out.println(String.format("\nThere was %d error%s:", 
					res.errorCount(), 
					res.errorCount() > 1? "s:": ":"));
			int i = 1;
			for(Enumeration<TestFailure> iter = res.errors(); iter.hasMoreElements();){
				System.out.println(i++ + ") " + iter.nextElement()); 
			}
		}
		System.out.println(String.format("\nTests run: %d,  Failures: %d,  Errors: %d", 
				res.runCount(),
				res.failureCount(),
				res.errorCount()));
	}
}
