package serie3.grupo1.dataMappers.registry;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import serie3.grupo1.dataConnector.JdbcDataSource;
import serie3.grupo1.dataMappers.CategoryMapper;
import serie3.grupo1.dataMappers.CustomerMapper;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.dataMappers.OrderDetailsMapper;
import serie3.grupo1.dataMappers.OrderMapper;
import serie3.grupo1.dataMappers.ProductMapper;
import serie3.grupo1.dataMappers.ShipperMapper;
import serie3.grupo1.dataMappers.SupplierMapper;
import serie3.grupo1.domainObjects.*;

public class MapperRegistry {

    private final Map<Class<? extends DomainObject>, IDataMapper<?,? extends DomainObject>> _mappers =
            new HashMap<Class<? extends DomainObject>,IDataMapper<?,? extends DomainObject>>();
    
    private static final Object _monitor = new Object();
    private static final DataSource _ds = JdbcDataSource.getDataSource();
    
    private static MapperRegistry _current = null;
    
    public static MapperRegistry current(){
        synchronized(_monitor){
            if (_current == null)
                _current = new MapperRegistry();
            return _current;
        }
    }

    private MapperRegistry(){
        _mappers.put(Category.class, new CategoryMapper(_ds));
        _mappers.put(Customer.class, new CustomerMapper(_ds));
        _mappers.put(Shipper.class, new ShipperMapper(_ds));
        _mappers.put(Supplier.class, new SupplierMapper(_ds));
        _mappers.put(Order.class, new OrderMapper(_ds));
        _mappers.put(Product.class, new ProductMapper(_ds));
        _mappers.put(OrderDetails.class, new OrderDetailsMapper(_ds));
    }

    public IDataMapper get(Class<? extends DomainObject> klass){
        return (IDataMapper)_mappers.get(klass);
    }
}
