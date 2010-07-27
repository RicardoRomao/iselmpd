package trabalho.propertiesViewer;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import trabalho.propertiesUtils.PropertiesUtils;
import trabalho.propertiesUtils.PropertyInfo;
import trabalho.listModel.SimpleListViewModel;
import trabalho.listView.ListView;
import trabalho.propertiesUtils.PropertyKind;

import utils.Utils;

public class PropertiesViewer<T> extends AbstractPropertiesForm<T> {

    public PropertiesViewer(T t) {
        super(t);
    }

    public void doPopulatePropsForm() {
        Map<String, PropertyInfo> props = PropertiesUtils.getVisiblePropertiesWithKind(_obj);
        JTextField _propField;
        JLabel _propLabel;

        String key;
        PropertyInfo value;
        PropertyKind kind;

        for (Map.Entry<String, PropertyInfo> entry : props.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            kind = value.getKind();
            _propLabel = getLabel(key);
            _propsForm.add(_propLabel);

            if (kind == PropertyKind.Simple) {
                _propField = getTextField(value.getValue() == null ? "" : value.getValue().toString());
                //_properties.put(key, new PropertyMembers(_propField));
                _propsForm.add(_propField);
            } else if (kind == PropertyKind.Complex) {
                _propsForm.add(getDetailsButton(key,value.getMethod()));
            } else if (kind == PropertyKind.List) {
                _propsForm.add(getListButton(key,value.getMethod()));
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
                Utils.launchFrame(_propView, title);
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
                Utils.launchDialog(view, true, title);
            }
        });
        return listButton;
    }
}
