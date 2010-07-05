
import serie1.propertiesUtils.PropertiesUtils;
import serie1.app.TestObject;
import java.util.Hashtable;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

public class PropertiesUtilsTester {

    private TestObject testObj;

    public PropertiesUtilsTester() {
        testObj = new TestObject();
    }

    @Test
    public void TestVisibleProperties(){
        Map<String,Object> result = new Hashtable<String,Object>();
        result.put("prop1", (Object)3);
        result.put("prop2", (Object)"test");
        result.put("prop3", (Object)false);
        Map<String,Object> ret = PropertiesUtils.getvisibleProperties(testObj);
        Assert.assertTrue(result.equals(ret));
    }
}