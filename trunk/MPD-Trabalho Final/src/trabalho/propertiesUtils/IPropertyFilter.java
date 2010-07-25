package trabalho.propertiesUtils;

import annotations.VisibleProperty;

public interface IPropertyFilter {

    public boolean accept(VisibleProperty prop);
}
