package serie3.grupo1.domainObjects.lazyLoaders;

import java.util.Set;
import serie3.grupo1.domainObjects.DomainObject;

public interface ILazySet<IDType, DType extends DomainObject<IDType, DType>>  {

    Set<DType> getSet();
    Set<DType> getImmutableSet();
}
