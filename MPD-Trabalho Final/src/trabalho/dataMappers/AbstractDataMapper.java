package trabalho.dataMappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import trabalho.jdbc.AbstractJdbcAction;
import trabalho.jdbc.JdbcAction;
import trabalho.jdbc.JdbcConnector;
import trabalho.dataMappers.filters.IFilter;
import trabalho.domainObjects.DomainObject;

public abstract class AbstractDataMapper<IDType, DType extends DomainObject<IDType,DType>>
        implements IDataMapper<IDType,DType> {


    private final Map<IDType, DType> _identityMap = new Hashtable<IDType, DType>();
    private JdbcConnector _connector;

    private final Set<DType> _cleanObjects = new HashSet<DType>();
    private final Set<DType> _dirtyObjects = new HashSet<DType>();
    private final Set<DType> _removedObjects = new HashSet<DType>();
    private final Set<DType> _newObjects = new HashSet<DType>();
    
    abstract String doGetAllStatement();
    abstract String doGetFindStatement();
    abstract String doGetInsertStatement(DType t);
    abstract String doGetUpdateStatement();
    abstract String doGetDeleteStatement();

    abstract void doBindFindStatement(PreparedStatement st, IDType key);
    abstract void doBindDeleteStatement(PreparedStatement st, IDType key);
    abstract void doBindInsertStatement(PreparedStatement st, DType dObj);
    abstract void doBindUpdateStatement(PreparedStatement st, DType dObj);

    abstract IDType doGetId(ResultSet rs);
    abstract DType doLoad(ResultSet rs);

    abstract boolean doInsertRequiresUpdate(DType dObj);

    public AbstractDataMapper(){ _connector = null; }
    public AbstractDataMapper(JdbcConnector connector){ 
        _connector = connector;
    }

    @Override
    public void setConnector (JdbcConnector connector) {
        _connector = connector;
    }

    private <R> R connectorExec(JdbcAction<R> action) {
        if (_connector == null) throw new IllegalStateException();
        return _connector.exec(action);
    }

    public void removeFromIdentityMap(IDType id) {
        _identityMap.remove(id);
    }

    public Set<DType> cleanSet() { return _cleanObjects; }
    public Set<DType> dirtySet() { return _dirtyObjects; }
    public Set<DType> removedSet() { return _removedObjects; }
    public Set<DType> newSet() { return _newObjects; }

    DType load(ResultSet rs) {
        IDType key = doGetId(rs);
        DType val = _identityMap.get(key);
        if (val != null) return val;
        val = doLoad(rs);
        _identityMap.put(key, val);
        return val;
    }

    @Override
    public DType getById(final IDType id) {
        DType val = _identityMap.get(id);
        if (val != null) return val;
        return connectorExec(
            new AbstractJdbcAction<DType>(doGetFindStatement()) {
                @Override
                public DType exec(PreparedStatement st) throws SQLException {
                    doBindFindStatement(st, id);
                    ResultSet rs = st.executeQuery();
                    if (!rs.next()) return null;
                    return load(rs);
                }
            }
        );
    }

    @Override
    public void loadAllInto(final Collection<DType> col) {
        connectorExec(
            new AbstractJdbcAction<DType>(doGetAllStatement()) {
                @Override
                public DType exec(PreparedStatement st) throws SQLException {
                    ResultSet rs = st.executeQuery();
                    DType elem;
                    while(rs.next()){
                        elem = load(rs);
                        col.add(elem);
                    }
                    return null;
                }
            }
        );
    }

    @Override
    public void loadFilteredInto(final IFilter filter, final Collection<DType> col){
        connectorExec(
            new AbstractJdbcAction<DType>(doGetAllStatement() + filter.getWhereClause()) {
                @Override
                public DType exec(PreparedStatement st) throws SQLException {
                    filter.bindValues(st);
                    ResultSet rs = st.executeQuery();
                    DType elem;
                    while(rs.next()){
                        elem = load(rs);
                        col.add(elem);
                    }
                    return null;
                }
            }
        );
    }

    @Override
    public void insertNewObjects() {
        for(DType d : _newObjects){
            insert(d);
        }
    }

    @Override
    public void insert(final DType dObj) {
        connectorExec(
            new AbstractJdbcAction<Void>(doGetInsertStatement(dObj),
                    Statement.RETURN_GENERATED_KEYS){
                @Override
                public Void exec(PreparedStatement st) throws SQLException {
                    doBindInsertStatement(st, dObj);
                    st.executeUpdate();
                    IDType key;
                    if (!dObj.hasId()) {
                        ResultSet rs = st.getGeneratedKeys();
                        rs.next();
                        key = doGetId(rs);
                        dObj.setId(key);
                    } else {
                        key = dObj.getId();
                    }
                    _identityMap.put(key, dObj);
                    dObj.saved();
                    if(doInsertRequiresUpdate(dObj)) dObj.markDirty();
                    return null;
                }
            }
        );
    }

    @Override
    public void deleteRemovedObjects() {
        for(DType d : _removedObjects) {
            delete(d);
        }
    }

    @Override
    public void delete(final DType dObj){
        connectorExec(
            new AbstractJdbcAction<Void>(doGetDeleteStatement()){
                @Override
                public Void exec(PreparedStatement st) throws SQLException {
                    doBindDeleteStatement(st, dObj.getId());
                    st.executeUpdate();
                    _identityMap.remove(dObj.getId());
                    dObj.saved();
                    return null;
                }
            }
        );
    }

    @Override
    public void updateDirtyObjects() {
        for (DType d : _dirtyObjects){
            update(d);
        }
    }

    @Override
    public void update(final DType dObj){
        connectorExec(
            new AbstractJdbcAction<DType>(doGetUpdateStatement()) {
                @Override
                public DType exec(PreparedStatement st) throws SQLException {
                    doBindUpdateStatement(st, dObj);
                    st.executeUpdate();
                    dObj.saved();
                    return null;
                }
            }
        );
    }
}
