package serie1.propertiesViewer;

import java.awt.event.ActionListener;
import serie1.propertySetter.IPropertySetter;

public interface IPropertiesViewer<T> {

    public void addPropertySetter(String propName, IPropertySetter<T> setter);
    public void addUpdateListener(ActionListener listener);

}
