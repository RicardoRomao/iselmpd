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

    final JButton _updateBtn;
    final JComponent _propsForm;
    final T _obj;
    final Hashtable<String, PropertyMembers> _properties;

    public AbstractPropertiesViewer(T t){
        _obj = t;
        _properties = new Hashtable<String, PropertyMembers>();
        _updateBtn = new JButton("Update");
        _updateBtn.setVisible(false);
        _propsForm = new JPanel(new FormLayout());
        setLayout(new BorderLayout());
        doPopulatePropsForm();
        add(_propsForm,BorderLayout.CENTER);
        add(_updateBtn,BorderLayout.SOUTH);
    }

    public abstract void doPopulatePropsForm();

    public void addUpdateListener(ActionListener listener) {
        _updateBtn.setVisible(true);
        _updateBtn.addActionListener(listener);
    }

    public void addPropertySetter(String propName, IPropertySetter<T> setter) {
        try {
            _properties.get(propName).editEnable(setter);
        } catch (NullPointerException ex) {
            throw new PropertiesViewerException();
        }
    }

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
