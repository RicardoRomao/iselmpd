package trabalho.propertiesUtils;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import exceptions.PropertiesUtilsException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import trabalho.misc.IPropertyFilter;

public class PropertiesUtils {

    public static Map<String, Object> getvisibleProperties(Object obj) {
        Map<String, Object> ret = new Hashtable<String, Object>();
        Method[] methods = obj.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getParameterTypes().length == 0 && method.isAnnotationPresent(VisibleProperty.class)) {
                try {
                    ret.put(method.getAnnotation(VisibleProperty.class).name(), method.invoke(obj));
                } catch (IllegalAccessException ex) {
                    throw new PropertiesUtilsException();
                } catch (InvocationTargetException ex) {
                    throw new PropertiesUtilsException();
                }
            }
        }
        return ret;
    }

    public static Map<String, PropertyInfo> getVisiblePropertiesWithKind(Object obj) {
        Map<String, PropertyInfo> ret = new Hashtable<String, PropertyInfo>();
        Method[] methods = obj.getClass().getDeclaredMethods();
        PropertyKind kind;

        for (Method method : methods) {
            if (method.getParameterTypes().length == 0 && method.isAnnotationPresent(VisibleProperty.class)) {
                kind = method.getAnnotation(VisibleProperty.class).kind();
                if (kind == PropertyKind.Simple) {
                    try {
                        ret.put(method.getAnnotation(
                            VisibleProperty.class).name(),
                            new PropertyInfo(
                                method.invoke(obj),
                                kind
                            )
                        );
                    } catch (IllegalAccessException ex) {
                        throw new PropertiesUtilsException();
                    } catch (InvocationTargetException ex) {
                        throw new PropertiesUtilsException();
                    }
                }
                else
                    ret.put(method.getAnnotation(
                        VisibleProperty.class).name(),
                        new PropertyInfo(
                            method,
                            kind
                        )
                    );
            }
        }
        return ret;
    }

    public static Map<String, Method> getVisibleProperties(Object obj, IPropertyFilter filter) {
        Map<String, Method> ret = new Hashtable<String, Method>();
        Method[] methods = obj.getClass().getDeclaredMethods();
        VisibleProperty prop;

        for (Method method : methods) {
            if (method.getParameterTypes().length == 0 && method.isAnnotationPresent(VisibleProperty.class)) {
            	prop = method.getAnnotation(VisibleProperty.class);
                if (filter == null || (filter != null && filter.accept(prop)))
                {
                	ret.put(prop.name(), method);
                }
            }
        }
        return ret;
    }
}
