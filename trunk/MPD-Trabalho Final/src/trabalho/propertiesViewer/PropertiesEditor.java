package trabalho.propertiesViewer;

public class PropertiesEditor<T> extends AbstractEditablePropertiesForm<T>{

    public PropertiesEditor(T t) {
        super(t);
    }

    @Override
    public String getSubmitBtnText() { return "Update"; }
}
