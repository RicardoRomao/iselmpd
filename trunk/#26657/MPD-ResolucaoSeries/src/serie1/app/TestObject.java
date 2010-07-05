package serie1.app;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;

public class TestObject {

    public Integer prop1 = 3;
    public String prop2 = "test";
    public Boolean prop3 = false;

    @VisibleProperty(name = "prop1", kind=PropertyKind.Simple)
    public int getProp1() { return prop1; }
    @VisibleProperty(name = "prop1", kind=PropertyKind.Simple)
    public void setProp1(Integer value) { prop1 = value; }

    @VisibleProperty(name = "prop2", kind=PropertyKind.Simple)
    public String getProp2() { return prop2; }
    @VisibleProperty(name = "prop2", kind=PropertyKind.Simple)
    public void dsetProp2(String value) { prop2 = value; }

    @VisibleProperty(name = "prop3", kind=PropertyKind.Simple)
    public boolean getProp3() { return prop3; }
    @VisibleProperty(name = "prop3", kind=PropertyKind.Simple)
    public void setProp3(Boolean value) { prop3 = value;}
}
