package trabalho.propertiesViewer;

import java.util.Map;
import trabalho.propertiesUtils.PropertiesUtils;
import trabalho.propertySetter.GenericPropertySetter;
import trabalho.propertySetter.IPropertySetter;

public class PropertiesEditor<T> extends PropertiesViewer<T>{

    public PropertiesEditor(T t){
        super(t);

        Map<String,Object> _props = PropertiesUtils.getvisibleProperties(t);
        String key;
        Object value;
        IPropertySetter<T> genericPropSetter;

        for (Map.Entry _entry : _props.entrySet())
        {
            key = _entry.getKey().toString();
            value = _entry.getValue();
            Class valClass = value.getClass();
            genericPropSetter = new GenericPropertySetter<T>(key,valClass);
            addPropertySetter(key, genericPropSetter);
        }
    }
}
