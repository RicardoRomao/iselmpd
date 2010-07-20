package trabalho.propertiesViewer;

import java.awt.event.ActionListener;
import trabalho.propertySetter.IPropertySetter;

public interface IPropertiesViewer<T> {

    public void addPropertySetter(String propName, IPropertySetter<T> setter);
    public void addUpdateListener(ActionListener listener);

}
