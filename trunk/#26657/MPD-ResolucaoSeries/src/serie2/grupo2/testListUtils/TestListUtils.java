package serie2.grupo2.testListUtils;

import annotations.SetUp;
import annotations.TearDown;
import annotations.UnitTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import serie2.grupo2.testFw.Assert;
import serie2.grupo2.utils.ListUtils;

public class TestListUtils {

    private List<Integer> itens;
    private Integer[] arr = {7, 8, 6, 1, 2, 9, 6, 9, 9, 1, 6, 5, 4, 9, 6, 5, 9, 6, 7};

    @SetUp
    public void iniItens() {
        itens = new ArrayList<Integer>();
        itens.addAll(Arrays.asList(arr));
    }

    @TearDown
    public void freeItens() {
        itens.clear();
        itens = null;
    }

    @UnitTest("Test static method ListUtils.removeOccurrences")
    public void testRemoveOccurrences() {
        ListUtils.removeOccurrences(itens, 9);
        Integer[] expectedResult = {7, 8, 6, 1, 2, 6, 1, 6, 5, 4, 6, 5, 6, 7};
        for (int i = 0; i < expectedResult.length; i++) {
            Assert.assertEquals(itens.get(i), expectedResult[i]);
        }
    }

    @UnitTest("Test static method ListUtils.removeRepetitions")
    public void testRemoveRepetitions() {
        ListUtils.removeRepetitions(itens);
        Integer[] expectedResult = {7, 8, 6, 1, 2, 9, 5, 4};
        for (int i = 0; i < expectedResult.length; i++) {
            Assert.assertEquals(itens.get(i), expectedResult[i]);
        }
    }
}
