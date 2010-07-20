package trabalho.domainObjects.lazyLoaders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import trabalho.dataMappers.IDataMapper;
import trabalho.dataMappers.filters.IFilter;
import trabalho.dataMappers.registry.MapperRegistry;
import trabalho.domainObjects.DomainObject;

public class LazySet<IDType, DType extends DomainObject<IDType, DType>>
        implements ILazySet
{
    private final IFilter _filter;
    private final IDataMapper<IDType,DType> _mapper;
    private Set<DType> _set = null;

    public LazySet(IFilter filter, Class<? extends DomainObject> klass){
        _mapper = MapperRegistry.current().get(klass);
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
