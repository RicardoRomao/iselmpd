package trabalho.propertiesViewer;

public class PropertiesCreator<T> extends AbstractEditablePropertiesForm<T> {

    public PropertiesCreator(T t) {
        super(t);
    }

    @Override
    public String getSubmitBtnText() { return "Add"; }
}
