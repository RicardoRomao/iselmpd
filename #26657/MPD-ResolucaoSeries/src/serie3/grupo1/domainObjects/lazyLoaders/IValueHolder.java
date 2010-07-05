package serie3.grupo1.domainObjects.lazyLoaders;

import serie3.grupo1.domainObjects.DomainObject;

public interface IValueHolder<IDType, DType extends DomainObject<IDType, DType>> {
    DType get();
    IDType getKey();
    void set(DType val);
}
