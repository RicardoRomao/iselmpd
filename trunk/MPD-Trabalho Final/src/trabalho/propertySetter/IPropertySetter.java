package trabalho.propertySetter;

import exceptions.DomainObjectValidatorException;

public interface IPropertySetter<T> {

    void setValue (T obj, Object newValue) throws DomainObjectValidatorException;

}
