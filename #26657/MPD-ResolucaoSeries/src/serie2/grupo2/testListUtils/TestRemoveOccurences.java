package serie2.grupo2.testListUtils;


import serie2.grupo2.testFw.Assert;
import serie2.grupo2.testFw.TestCase;
import serie2.grupo2.utils.ListUtils;

public class TestRemoveOccurences extends TestCase {

    public TestRemoveOccurences() {
        super("Test static method ListUtils::removeOccurrences");
    }

    public void runTest() {

        // Run test
        ListUtils.removeOccurrences(itens, 9);
        Integer[] expectedResult = {7, 8, 6, 1, 2, 6, 1, 6, 5, 4, 6, 5, 6, 7};
        for (int i = 0; i < expectedResult.length; i++) {
            Assert.assertEquals(itens.get(i), expectedResult[i]);
        }

    }
}
