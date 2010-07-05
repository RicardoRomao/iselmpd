package serie1.propertiesViewer;

import annotations.VisibleProperty.PropertyKind;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serie1.propertySetter.IPropertySetter;
import exceptions.PropertiesViewerException;
import serie1.propertiesUtils.PropertiesUtils;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import serie1.propertiesUtils.PropertyInfo;
import serie1.swingUtils.Utils;
import serie2.grupo1.model.SimpleListViewModel;
import serie2.grupo1.view.ListView;

public class PropertiesViewer<T> extends AbstractPropertiesViewer<T> {

    public PropertiesViewer(T t) {
        super(t);
    }

    public void doPopulatePropsForm() {
        Map<String, PropertyInfo> props = PropertiesUtils.getVisiblePropertiesWithKind(_obj);
        JTextField _propField;
        JLabel _propLabel;

        String key;
        PropertyKind kind;

        for (Map.Entry<String, PropertyInfo> _entry : props.entrySet()) {
            key = _entry.getKey();
            kind = _entry.getValue().getKind();
            _propLabel = customLabel(key);
            _propsForm.add(_propLabel);

            if (kind == PropertyKind.Simple) {
                _propField = customTextField(_entry.getValue().getValue().toString());
                _properties.put(key, new PropertyMembers(_propField));
                _propsForm.add(_propField);
            } else if (kind == PropertyKind.Complex) {
                _propsForm.add(getDetailsButton(key,_entry.getValue().getMethod()));
            } else if (kind == PropertyKind.List) {
                _propsForm.add(getListButton(key,_entry.getValue().getMethod()));
            }

        }
    }

    public JButton getDetailsButton(final String title, final Method hook) {
        JButton detailsButton = new JButton("Details");

        detailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PropertiesViewer<Object> _propView = null;

                try {
                    _propView = new PropertiesViewer<Object>(hook.invoke(_obj));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PropertiesViewer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(PropertiesViewer.class.getName()).log(Level.SEVERE, null, ex);
                }

                JFrame frame = new JFrame(title);
                frame.add(_propView);
                frame.setVisible(true);
                frame.pack();
            }
        });
        return detailsButton;
    }

    public JButton getListButton(final String title, final Method hook) {
        JButton listButton = new JButton("List");

        listButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SimpleListViewModel<Object> listModel = new SimpleListViewModel<Object>();
                try {
                    listModel.addAll((Collection<Object>)hook.invoke(_obj));

                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PropertiesViewer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(PropertiesViewer.class.getName()).log(Level.SEVERE, null, ex);
                }

                ListView view = new ListView(listModel);
                Utils.launchDialog(view, true, "Customers");
            }
        });
        return listButton;
    }

    private JTextField customTextField(String value) {
        JTextField _field = new JTextField(value);
        _field.setName(value);
        _field.setColumns(15);
        _field.setEnabled(false);
        return _field;
    }

    private JLabel customLabel(String value) {
        JLabel _label = new JLabel(value);
        _label.setSize(20, 1);
        return _label;
    }
}
