package serie3.grupo1.dataMappers.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import serie3.grupo1.dataConnector.JdbcConnector;
import serie3.grupo1.dataConnector.JdbcDataSource;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.domainObjects.*;

public class MapperRegistry {

    private final Map<Class<? extends DomainObject>, IDataMapper<?,? extends DomainObject>> _mappers =
            new HashMap<Class<? extends DomainObject>,IDataMapper<?,? extends DomainObject>>();
    
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

    private MapperRegistry(){ }

    public void add(Class<? extends DomainObject> klass,
            IDataMapper<?,? extends DomainObject> mapper) {
        _mappers.put(klass, mapper);
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
