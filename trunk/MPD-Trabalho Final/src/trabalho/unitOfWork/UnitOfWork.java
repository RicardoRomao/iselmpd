package trabalho.unitOfWork;

import trabalho.jdbc.JdbcConnector;
import trabalho.dataMappers.registry.MapperRegistry;
import trabalho.dataMappers.*;
import trabalho.domainObjects.*;

public class UnitOfWork {

    private final MapperRegistry _mappers = MapperRegistry.current();
    private final JdbcConnector _connector;
    private static UnitOfWork _current;
    
    public UnitOfWork (JdbcConnector connector) { _connector = connector; }

    public IDataMapper getMapper(Class<? extends DomainObject> klass) {
        return _mappers.get(klass);
    }

    public void save() {
        try {
            for (IDataMapper m : _mappers.getAllMappersInReverse())
                m.deleteRemovedObjects();
            for (IDataMapper m : _mappers.getAllMappers())
                m.insertNewObjects();
            for (IDataMapper m : _mappers.getAllMappers())
                m.updateDirtyObjects();
        } catch (Exception e) { e.printStackTrace();}
    }

    public static void setCurrent(UnitOfWork uow) { _current = uow; }
    public static UnitOfWork getCurrent() { return _current; }

}
