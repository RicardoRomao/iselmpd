package trabalho.propertySetter;

import annotations.VisibleProperty;
import exceptions.GenericPropSetException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import trabalho.domainObjects.DomainObject;
import trabalho.propertiesUtils.PropertyKind;

public class GenericPropertySetter<T> implements IPropertySetter<T> {

    private final String _propName;
    private final Class<?> _propClass;

    public GenericPropertySetter(String propName, Class<?> propClass) {
        _propName = propName;
        _propClass = propClass;
    }

    public void setValue(T obj, Object newValue) {

        try {
            Method[] methods = obj.getClass().getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(VisibleProperty.class)
                        && m.getReturnType().equals(void.class)) {
                    VisibleProperty v = m.getAnnotation(VisibleProperty.class);
                    if (v.name().equals(_propName)) {
                        System.out.println("Invoking method " + m.getName());
                        if (v.kind() == PropertyKind.Simple) {
                            if (_propClass == String.class) {
                                m.invoke(obj, newValue);
                            } else {
                                Method valueMethod;
                                valueMethod = _propClass.getMethod("valueOf", String.class);
                                m.invoke(obj, valueMethod.invoke(obj, newValue));
                            }
                        } else {
                            m.invoke(obj, (DomainObject)newValue);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException ex) {
            throw new GenericPropSetException();
        } catch (SecurityException ex) {
            throw new GenericPropSetException();
        } catch (IllegalAccessException ex) {
            throw new GenericPropSetException();
        } catch (InvocationTargetException ex) {
            throw new GenericPropSetException();
        }
    }
}
