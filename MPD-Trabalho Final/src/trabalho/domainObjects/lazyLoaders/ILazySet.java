package trabalho.domainObjects.lazyLoaders;

import java.util.Set;
import trabalho.domainObjects.DomainObject;

public interface ILazySet<IDType, DType extends DomainObject<IDType, DType>>  {

    Set<DType> getSet();
    Set<DType> getImmutableSet();
}
