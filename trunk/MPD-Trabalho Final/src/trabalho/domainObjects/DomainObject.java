package trabalho.domainObjects;

import trabalho.dataMappers.IDataMapper;
import trabalho.dataMappers.registry.MapperRegistry;

public abstract class DomainObject<IDType, DType extends DomainObject<IDType, DType>> {

    private IDType _id;
    private State _state;
    private IDataMapper _mapper;

    protected abstract DType self();

    public DomainObject() {
        _state = new New();
    }

    public DomainObject(IDType id){
        _id = id;
        _state = new Clean();
    }

    public DomainObject(IDType id, boolean isNew) {
        _id = id;
        _state = isNew ? new New() : new Clean();
    }

    public IDType getId() { return _id; }
    public void setId(IDType id) { _id = id; }
    public boolean hasId() { return _id != null; }

    public IDataMapper mapper() {
        if (_mapper == null)
            _mapper = MapperRegistry.current().get(self().getClass());
        return _mapper;
    }

    protected interface State {
        State onRemove();
        State onWrite();
        State onSave();
    }

    protected class New implements State {
        public New() { mapper().newSet().add(self()); }
        public State onRemove() { mapper().newSet().remove(self()); return new Removed(); }
        public State onWrite() { return this; }
        public State onSave() { mapper().newSet().remove(self()); return new Clean(); }
    }

    protected class Clean implements State {
        public State onRemove() { mapper().removedSet().add(self()); return new ToRemove(); }
        public State onWrite() { mapper().dirtySet().add(self()); return new Dirty();}
        public State onSave() { throw new IllegalStateException(); }
    }

    protected class Dirty implements State {
        public State onRemove() { mapper().dirtySet().remove(self()); mapper().removedSet().add(self()); return new ToRemove(); }
        public State onWrite() { return this; }
        public State onSave() { mapper().dirtySet().remove(self()); return new Clean(); }
    }

    protected class ToRemove implements State {
        public State onRemove() { return this; }
        public State onWrite() { throw new IllegalStateException(); }
        public State onSave() { mapper().removedSet().remove(self()); return new Removed(); }
    }
    
    protected class Removed implements State {
        public State onRemove() { throw new IllegalStateException(); }
        public State onWrite() { throw new IllegalStateException(); }
        public State onSave() { throw new IllegalStateException(); }
    }

    public void markDirty() { _state = _state.onWrite(); }
    public void saved() { _state = _state.onSave(); }
    public void remove() { _state = _state.onRemove(); }

}
