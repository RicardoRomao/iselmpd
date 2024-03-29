package trabalho.propertiesViewer;

import exceptions.AbstractEditablePropsFormException;
import exceptions.DomainObjectValidatorException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import trabalho.comboBoxModel.ComboBoxModelAdapter;
import trabalho.dataMappers.IDataMapper;
import trabalho.dataMappers.registry.MapperRegistry;
import trabalho.propertiesUtils.PropertiesUtils;
import trabalho.propertiesUtils.PropertyInfo;
import trabalho.propertiesUtils.PropertyKind;
import trabalho.propertySetter.GenericPropertySetter;
import trabalho.propertySetter.IPropertySetter;
import utils.Utils;

public abstract class AbstractEditablePropertiesForm<T> extends AbstractPropertiesForm<T> {
   
    private Map<String, PropertyMembers> _properties;
    private JButton _submitBtn;
    private Collection<ActionListener> _settersObservingSubmit;
    private Collection<ActionListener> _postSubmitObservers;

    public abstract String getSubmitBtnText();

    interface IPropertyValue {
        Object getValue();
        void setEnabled(boolean enable);
    }

    class PropertyMembers implements ActionListener {

        private final IPropertyValue _value;
        private final String _name;
        private IPropertySetter<T> _setter;

        public PropertyMembers(IPropertyValue value, String name) {
            _value = value;
            _name = name;
        }

        public void editEnable(IPropertySetter<T> setter) {
            _value.setEnabled(true);
            _setter = setter;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                _setter.setValue(_obj,_value.getValue());
            } catch (DomainObjectValidatorException v) {
                Utils.showError(
                        "Error setting property '" + _name + "'.\n" +
                        v.getMessage() +
                        "\nNo changes where made to property."
                        , "Validator error"
                );
            }
        }
    }

    public AbstractEditablePropertiesForm(T t) { 
        super(t);
    }

    public void doPopulatePropsForm() {
        Map<String, PropertyInfo> props = PropertiesUtils.getVisiblePropertiesWithKind(_obj);
        _properties = new Hashtable<String, PropertyMembers>();

        String key;
        PropertyKind kind;
        PropertyInfo value;

        for (Map.Entry<String, PropertyInfo> entry : props.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            kind = value.getKind();

            if ((kind == PropertyKind.Simple || kind == PropertyKind.Complex)
                    && !value.isAutoGenerated()) {

                _propsForm.add(getLabel(key));

                if (kind == PropertyKind.Simple) {
                    final JTextField propField =
                            getTextField(
                                value.getValue() == null ? "" : value.getValue()
                            );
                    _properties.put(key, new PropertyMembers(
                        new IPropertyValue() {
                            public Object getValue() {
                                return propField.getText();
                            }
                            public void setEnabled(boolean enable) {
                                propField.setEnabled(enable);
                            }
                        }, key
                    ));
                    _propsForm.add(propField);

                } else if (kind == PropertyKind.Complex) {

                    try {
                        final JComboBox propCombo =
                                getPickList(
                                    value.getLookupClass(),
                                    value.getMethod() == null ? null : value.getMethod().invoke(_obj)
                                );
                        _properties.put(key, new PropertyMembers(
                            new IPropertyValue() {
                                public Object getValue() {
                                    return propCombo.getSelectedItem();
                                }
                                public void setEnabled(boolean enable) {
                                    propCombo.setEnabled(enable);
                                }
                            }, key
                        ));
                        _propsForm.add(propCombo);
                    } catch (IllegalAccessException ex) {
                        throw new AbstractEditablePropsFormException();
                    } catch (IllegalArgumentException ex) {
                        throw new AbstractEditablePropsFormException();
                    } catch (InvocationTargetException ex) {
                        throw new AbstractEditablePropsFormException();
                    }
                }

                addPropertySetter(
                    key,
                    new GenericPropertySetter<T>(key,value.getLookupClass())
                );
            }
        }
    }

    private void ensureSubmitButtonCreated() {
        if (_submitBtn == null) {
            _submitBtn = new JButton(getSubmitBtnText());
            add(_submitBtn,BorderLayout.SOUTH);
             _submitBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (ActionListener listener : _settersObservingSubmit)
                        listener.actionPerformed(e);
                    for (ActionListener listener : _postSubmitObservers)
                        listener.actionPerformed(e);
                }
            });
        }
    }

    protected void addPropertySetter(String propName, IPropertySetter<T> setter) {
        ensureSubmitButtonCreated();
        _submitBtn.setVisible(true);
        try {
            _properties.get(propName).editEnable(setter);
            addSubmitListener(_properties.get(propName));
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new AbstractEditablePropsFormException();
        }
    }

    private void addSubmitListener(ActionListener listener) {
        if (_settersObservingSubmit == null)
            _settersObservingSubmit = new LinkedList<ActionListener>();
        _settersObservingSubmit.add(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        if (_postSubmitObservers == null)
            _postSubmitObservers = new LinkedList<ActionListener>();
        _postSubmitObservers.add(listener);
    }

    public JComboBox getPickList(Class lookupClass, Object currValue) {
        ComboBoxModelAdapter comboModel = new ComboBoxModelAdapter();
        IDataMapper mapper = MapperRegistry.current().get(lookupClass);
        mapper.loadAllInto(comboModel);
        comboModel.setSelectedItem(currValue);
        JComboBox cmb = new JComboBox();
        cmb.setModel(comboModel);
        cmb.setSelectedItem(comboModel.getSelectedItem());
        return cmb;
    }
}
