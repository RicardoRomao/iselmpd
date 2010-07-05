package serie2.grupo2.testListUtils;


import serie2.grupo2.testFw.Assert;
import serie2.grupo2.testFw.TestCase;
import serie2.grupo2.utils.ListUtils;

public class TestRemoveRepetitions extends TestCase {

    public TestRemoveRepetitions() {
        super("Test static method ListUtils::removeRepetitions");
    }

    public void runTest() {

        // Run test
        ListUtils.removeRepetitions(itens);
        Integer[] expectedResult = {7, 8, 6, 1, 2, 9, 5, 4};
        for (int i = 0; i < expectedResult.length; i++) {
            Assert.assertEquals(itens.get(i), expectedResult[i]);
        }
    }
}
