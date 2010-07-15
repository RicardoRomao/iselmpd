package serie3.grupo1.domainObjects.lazyLoaders;

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

    public ValueHolder(DType value){
        _loaded = true;
        _key = null;
        _mapper = null;
        _value = value;
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
