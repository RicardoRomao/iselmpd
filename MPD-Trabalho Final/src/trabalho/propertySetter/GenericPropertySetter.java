package trabalho.propertySetter;

import annotations.PropertyValidator;
import annotations.VisibleProperty;
import exceptions.DomainObjectValidatorException;
import exceptions.GenericPropSetException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import trabalho.propertiesUtils.PropertyKind;

public class GenericPropertySetter<T> implements IPropertySetter<T> {

    private final String _propName;
    private final Class<?> _propClass;

    public GenericPropertySetter(String propName, Class<?> propClass) {
        _propName = propName;
        _propClass = propClass;
    }

    private Collection<Method> getPropValidators(Class source, String propName, Class paramType) {
        Collection<Method> validators = new LinkedList<Method>();
        Method[] methods = source.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length == 1 &&
                    method.getParameterTypes()[0].equals(paramType) &&
                    method.isAnnotationPresent(PropertyValidator.class) &&
                    method.getAnnotation(PropertyValidator.class).name().equals(propName))
               validators.add(method);
        }
        return validators;
    }

    public void setValue(T obj, Object newValue) throws DomainObjectValidatorException {

        try {
            Method[] methods = obj.getClass().getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(VisibleProperty.class)
                        && m.getReturnType().equals(void.class)) {
                    VisibleProperty annot = m.getAnnotation(VisibleProperty.class);

                    if (annot.name().equals(_propName)) {

                        Collection<Method> validators =
                                getPropValidators(obj.getClass(), _propName, _propClass);
                        for(Method validator : validators) {
                                validator.invoke(obj,newValue);
                        }

                        System.out.println("Invoking method " + m.getName());
                        if (annot.kind() == PropertyKind.Simple) {
                            if (_propClass == String.class) {
                                m.invoke(obj, newValue);
                            } else {
                                Method valueMethod;
                                valueMethod = _propClass.getMethod("valueOf", String.class);
                                m.invoke(obj, valueMethod.invoke(obj, newValue));
                            }
                        } else {
                            m.invoke(obj, newValue);
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
            if (ex.getTargetException().getClass().equals(DomainObjectValidatorException.class))
                throw new DomainObjectValidatorException(ex.getCause().getMessage());
            throw new GenericPropSetException();
        }
    }
}
