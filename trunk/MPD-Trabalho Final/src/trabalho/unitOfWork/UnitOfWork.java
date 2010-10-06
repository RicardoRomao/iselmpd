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
        System.out.println("Saving UOW...");
        try {
            //_connector.beginTransaction();
            for (IDataMapper m : _mappers.getAllMappers())
                m.insertNewObjects();
            for (IDataMapper m : _mappers.getAllMappers())
                m.updateDirtyObjects();
            for (IDataMapper m : _mappers.getAllMappersInReverse())
                m.deleteRemovedObjects();
            //_connector.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setCurrent(UnitOfWork uow) { _current = uow; }
    public static UnitOfWork getCurrent() { return _current; }

}
