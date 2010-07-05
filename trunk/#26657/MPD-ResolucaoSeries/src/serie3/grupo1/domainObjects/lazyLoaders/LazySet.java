package serie3.grupo1.domainObjects.lazyLoaders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.dataMappers.filters.IFilter;
import serie3.grupo1.domainObjects.DomainObject;

public class LazySet<IDType, DType extends DomainObject<IDType, DType>>
        implements ILazySet
{
    private final IFilter _filter;
    private final IDataMapper<IDType,DType> _mapper;
    private Set<DType> _set = null;

    public LazySet(IFilter filter, IDataMapper<IDType, DType> mapper){
        _mapper = mapper;
        _filter = filter;
    }

    public LazySet(){
        _mapper = null;
        _filter = null;
        _set = new HashSet<DType>();
    }

    private void ensureLoad() {
        if (_set != null) return;
        _set = new HashSet<DType>();
        _mapper.loadFilteredInto(_filter, _set);
    }

    public Set<DType> getSet() {
        ensureLoad();
        return _set;
    }

    public Set<DType> getImmutableSet() {
        ensureLoad();
        return Collections.unmodifiableSet(_set);
    }
}
