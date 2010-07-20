package trabalho.propertySetter;

import annotations.VisibleProperty;
import exceptions.GenericPropSetException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericPropertySetter<T> implements IPropertySetter<T> {

    private final String _propName;
    private final Class<?> _propClass;

    public GenericPropertySetter(String propName, Class<?> propClass) {
        _propName = propName;
        _propClass = propClass;

    }

    public void setValue(T obj, String newValue) {

        try {

            Method[] methods = obj.getClass().getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(VisibleProperty.class)
                        && m.getReturnType().equals(void.class)) {
                    VisibleProperty v = m.getAnnotation(VisibleProperty.class);
                    if (v.name().equals(_propName)) {
                        System.out.println("Invoking method " + m.getName());
                        if (_propClass == String.class) {
                            m.invoke(obj, newValue);
                        } else {
                            Method valueMethod;
                            valueMethod = _propClass.getMethod("valueOf", String.class);
                            m.invoke(obj, valueMethod.invoke(obj, newValue));
                        }
                    }
                }
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(GenericPropertySetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(GenericPropertySetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            throw new GenericPropSetException();
        } catch (InvocationTargetException ex) {
            throw new GenericPropSetException();
        }
    }
}
