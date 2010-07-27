package trabalho.propertiesViewer;

import bookSource.FormLayout;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractPropertiesForm<T> extends JComponent {

    final JComponent _propsForm;
    final T _obj;

    public AbstractPropertiesForm(T t){
        _obj = t;
        _propsForm = new JPanel(new FormLayout());
        setLayout(new BorderLayout());
        add(_propsForm,BorderLayout.CENTER);
        doPopulatePropsForm();
    }

    public abstract void doPopulatePropsForm();

    protected JTextField getTextField(Object value) {
        JTextField _field = new JTextField(value.toString());
        _field.setName(value.toString());
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
