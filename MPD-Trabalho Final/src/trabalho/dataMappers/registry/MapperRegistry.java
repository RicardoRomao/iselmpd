package trabalho.dataMappers.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import trabalho.dataMappers.CategoryMapper;
import trabalho.dataMappers.CustomerMapper;
import trabalho.jdbc.JdbcConnector;
import trabalho.jdbc.JdbcDataSource;
import trabalho.dataMappers.IDataMapper;
import trabalho.dataMappers.OrderDetailsMapper;
import trabalho.dataMappers.OrderMapper;
import trabalho.dataMappers.ProductMapper;
import trabalho.dataMappers.ShipperMapper;
import trabalho.dataMappers.SupplierMapper;
import trabalho.domainObjects.*;

public class MapperRegistry {

    private final Map<Class<? extends DomainObject>, IDataMapper<?,? extends DomainObject>> _mappers =
            new LinkedHashMap<Class<? extends DomainObject>,IDataMapper<?,? extends DomainObject>>();
    
    private static final Object _monitor = new Object();
    private static final DataSource _ds = JdbcDataSource.getDataSource();
    private static final JdbcConnector _connector = new JdbcConnector(_ds);
    private static MapperRegistry _current = null;
    
    public static MapperRegistry current(){
        synchronized(_monitor){
            if (_current == null)
                _current = new MapperRegistry();
            return _current;
        }
    }

    private MapperRegistry(){
        _mappers.put(Category.class, new CategoryMapper(_connector));
        _mappers.put(Customer.class, new CustomerMapper(_connector));
        _mappers.put(Shipper.class, new ShipperMapper(_connector));
        _mappers.put(Supplier.class, new SupplierMapper(_connector));
        _mappers.put(Order.class, new OrderMapper(_connector));
        _mappers.put(Product.class, new ProductMapper(_connector));
        _mappers.put(OrderDetails.class, new OrderDetailsMapper(_connector));
    }

    public IDataMapper get(Class<? extends DomainObject> klass){
        return _mappers.get(klass);
    }

    public Collection<IDataMapper<?,? extends DomainObject>> getAllMappers() {
        return Collections.unmodifiableCollection(_mappers.values());
    }

    public Collection<IDataMapper<?,? extends DomainObject>> getAllMappersInReverse() {
        return Collections.unmodifiableCollection(_mappers.values());
    }
}
