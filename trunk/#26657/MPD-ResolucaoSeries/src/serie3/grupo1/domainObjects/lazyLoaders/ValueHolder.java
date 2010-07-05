package serie3.grupo1.domainObjects.lazyLoaders;

import exceptions.Serie3_ValueHolderException;
import java.sql.SQLException;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.domainObjects.DomainObject;

public class ValueHolder<IDType, DType extends DomainObject<IDType, DType>>
        implements IValueHolder<IDType, DType>{

    private boolean _loaded;
    private final IDType _key;
    private DType _value = null;
    private final IDataMapper<IDType, DType> _mapper;

    public ValueHolder(IDType key, IDataMapper<IDType,DType> mapper){
        _loaded = false;
        _key = key;
        _mapper = mapper;
    }

    public ValueHolder(){
        _loaded = true;
        _key = null;
        _mapper = null;
    }

    public DType get() {
        if (_key == null || _loaded) return _value;
        _value = _mapper.getById(_key);
        return _value;
    }

    public IDType getKey(){
        return _key;
    }

    public void set(DType val) {
        _loaded = true;
        _value = val;
    }
}
