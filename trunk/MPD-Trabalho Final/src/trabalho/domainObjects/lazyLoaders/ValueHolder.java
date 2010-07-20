package trabalho.domainObjects.lazyLoaders;

import trabalho.dataMappers.IDataMapper;
import trabalho.domainObjects.DomainObject;

public class ValueHolder<IDType, DType extends DomainObject<IDType, DType>>
        implements IValueHolder<IDType, DType>{

    private boolean _loaded = false;
    private IDType _key;
    private DType _value = null;
    private IDataMapper<IDType, DType> _mapper;

    public ValueHolder(IDType key, IDataMapper<IDType,DType> mapper){
        _key = key;
        _mapper = mapper;
    }

    public ValueHolder(DType value){
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
