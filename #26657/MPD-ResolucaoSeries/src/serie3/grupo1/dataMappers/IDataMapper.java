package serie3.grupo1.dataMappers;

import java.util.Collection;
import java.util.Set;
import serie3.grupo1.dataMappers.filters.IFilter;

public interface IDataMapper<IDType, DType> {

    public DType getById(IDType id);
    public void loadAllInto(Collection<DType> col);
    public void loadFilteredInto(IFilter filter, Collection<DType> col);

    public Set<DType> cleanSet();
    public Set<DType> dirtySet();
    public Set<DType> removedSet();
    public Set<DType> newSet();
    
}
