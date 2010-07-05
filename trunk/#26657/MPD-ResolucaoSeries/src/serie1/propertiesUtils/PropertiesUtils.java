package serie1.propertiesUtils;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import exceptions.PropertiesUtilsException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

public class PropertiesUtils {

    public static Map<String, Object> getvisibleProperties(Object obj) {

        String fieldName;
        Object fieldValue;
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

        String fieldName;
        Object fieldValue;
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
}
