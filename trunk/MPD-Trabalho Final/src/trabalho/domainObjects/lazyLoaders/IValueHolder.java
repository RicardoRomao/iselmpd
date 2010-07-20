package trabalho.domainObjects.lazyLoaders;

import trabalho.domainObjects.DomainObject;

public interface IValueHolder<IDType, DType extends DomainObject<IDType, DType>> {
    DType get();
    IDType getKey();
    void set(DType val);
}
