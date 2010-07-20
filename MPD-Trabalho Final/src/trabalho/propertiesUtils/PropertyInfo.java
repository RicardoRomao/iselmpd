package trabalho.propertiesUtils;

import annotations.VisibleProperty.PropertyKind;
import java.lang.reflect.Method;

public class PropertyInfo {

    private final Object _value;
    private final PropertyKind _kind;
    private final Method _hookMethod;


    public PropertyInfo(Object value, PropertyKind kind){
        _value = value;
        _kind = kind;
        _hookMethod = null;
    }

    public PropertyInfo(Method method, PropertyKind kind){
        _value = null;
        _kind = kind;
        _hookMethod = method;
    }

    public Object getValue() {
        return _value;
    }

    public PropertyKind getKind() {
        return _kind;
    }

    public Method getMethod() {
        return _hookMethod;
    }

}
