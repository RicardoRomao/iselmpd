package trabalho.dataMappers;

import java.util.Collection;
import java.util.Set;
import trabalho.jdbc.JdbcConnector;
import trabalho.dataMappers.filters.IFilter;
import trabalho.domainObjects.DomainObject;

public interface IDataMapper<IDType, DType extends DomainObject<IDType,DType>> {

    public void setConnector(JdbcConnector connector);
    public void removeFromIdentityMap(IDType id);

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
