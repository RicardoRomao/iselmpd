package trabalho.propertiesViewer;

import bookSource.FormLayout;
import exceptions.PropertiesViewerException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import trabalho.propertySetter.IPropertySetter;

public abstract class AbstractPropertiesViewer<T> extends JComponent
        implements IPropertiesViewer<T> {

    final JComponent _propsForm;
    final T _obj;
    final Hashtable<String, PropertyMembers> _properties;

    public AbstractPropertiesViewer(T t){
        _obj = t;
        _properties = new Hashtable<String, PropertyMembers>();
        _propsForm = new JPanel(new FormLayout());
        setLayout(new BorderLayout());
        doPopulatePropsForm();
        add(_propsForm,BorderLayout.CENTER);
    }

    public abstract void doPopulatePropsForm();

    class PropertyMembers implements ActionListener {

        private final JTextField _field;
        private IPropertySetter<T> _setter;

        public PropertyMembers(JTextField field) {
            _field = field;
        }

        public void editEnable(IPropertySetter<T> setter) {
            _field.setEnabled(true);
            _setter = setter;
            addUpdateListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            _setter.setValue( _obj, _field.getText());
        }
    }
}
