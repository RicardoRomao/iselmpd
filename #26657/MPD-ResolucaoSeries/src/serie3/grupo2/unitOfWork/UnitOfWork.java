package serie3.grupo2.unitOfWork;

import serie3.grupo1.dataConnector.JdbcConnector;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.dataMappers.*;
import serie3.grupo1.domainObjects.*;

public class UnitOfWork {

    private final MapperRegistry _mappers = MapperRegistry.current();
    private final JdbcConnector _connector;
    private static UnitOfWork _current;
    
    public UnitOfWork (JdbcConnector connector) { _connector = connector; }

    public UnitOfWork initMappers() {
        _mappers.add(Category.class, new CategoryMapper(_connector));
        _mappers.add(Customer.class, new CustomerMapper(_connector));
        _mappers.add(Shipper.class, new ShipperMapper(_connector));
        _mappers.add(Supplier.class, new SupplierMapper(_connector));
        _mappers.add(Order.class, new OrderMapper(_connector));
        _mappers.add(Product.class, new ProductMapper(_connector));
        _mappers.add(OrderDetails.class, new OrderDetailsMapper(_connector));
        return this;
    }

    public IDataMapper getMapper(Class<? extends DomainObject> klass) {
        return _mappers.get(klass);
    }

    public void save() {
        try {
            _connector.beginTransaction();
            for (IDataMapper m : _mappers.getAllMappersInReverse())
                m.deleteRemovedObjects();
            for (IDataMapper m : _mappers.getAllMappers())
                m.insertNewObjects();
            for (IDataMapper m : _mappers.getAllMappers())
                m.updateDirtyObjects();
            _connector.commitTransaction();
        } catch (Exception e) { e.printStackTrace();}
    }

    public static void setCurrent(UnitOfWork uow) { _current = uow; }
    public static UnitOfWork getCurrent() { return _current; }

}
