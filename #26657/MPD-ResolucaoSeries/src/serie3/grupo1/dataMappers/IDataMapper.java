package serie3.grupo1.dataMappers;

import java.util.Collection;
import java.util.Set;
import serie3.grupo1.dataConnector.JdbcConnector;
import serie3.grupo1.dataMappers.filters.IFilter;
import serie3.grupo1.domainObjects.DomainObject;

public interface IDataMapper<IDType, DType extends DomainObject<IDType,DType>> {

    public void setConnector(JdbcConnector connector);

    public DType getById(IDType id);
    public void loadAllInto(Collection<DType> col);
    public void loadFilteredInto(IFilter filter, Collection<DType> col);

    public void insertNewObjects();
    public void insert(DType dObj);
    public void deleteRemovedObjects();
    public void delete(DType dObj);
    public void updateDirtyObjects();
    public void update(DType dObj);


    public Set<DType> cleanSet();
    public Set<DType> dirtySet();
    public Set<DType> removedSet();
    public Set<DType> newSet();
    
}
