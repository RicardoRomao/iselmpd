package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import trabalho.propertiesUtils.PropertyKind;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VisibleProperty {
    String name();
    PropertyKind kind();
    boolean isAutoGenerated();
}
