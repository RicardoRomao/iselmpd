package trabalho.propertiesViewer;

import bookSource.FormLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import trabalho.propertySetter.IPropertySetter;

public abstract class AbstractPropertiesForm<T> extends JComponent {

    class PropertyMembers implements ActionListener {

        private final JTextField _field;
        private final JComboBox _combo;
        private IPropertySetter<T> _setter;

        public PropertyMembers(JTextField field) {
            _field = field;
            _combo = null;
        }

        public PropertyMembers(JComboBox combo) {
            _combo = combo;
            _field = null;
        }

        public void editEnable(IPropertySetter<T> setter) {
            if (_field != null) _field.setEnabled(true);
            if (_combo != null) _combo.setEnabled(true);
            _setter = setter;
        }

        public void actionPerformed(ActionEvent e) {
            _setter.setValue(
                _obj,
                _field != null ? _field.getText() : _combo.getSelectedItem()
            );
        }
    }

    final JComponent _propsForm;
    final T _obj;
    final Hashtable<String, PropertyMembers> _properties;

    public AbstractPropertiesForm(T t){
        _obj = t;
        _properties = new Hashtable<String, PropertyMembers>();
        _propsForm = new JPanel(new FormLayout());
        setLayout(new BorderLayout());
        add(_propsForm,BorderLayout.CENTER);
    }

    public abstract void doPopulatePropsForm();

    protected JTextField getTextField(String value) {
        JTextField _field = new JTextField(value);
        _field.setName(value);
        _field.setColumns(15);
        _field.setEnabled(false);
        return _field;
    }

    protected JLabel getLabel(String value) {
        JLabel _label = new JLabel(value);
        _label.setSize(20, 1);
        return _label;
    }
}
