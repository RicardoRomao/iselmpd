package serie3.grupo1.dataMappers;

import exceptions.Serie3_DataMapperException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import serie3.grupo1.dataMappers.filters.IFilter;

public abstract class AbstractDataMapper<IDType, DType>
        implements IDataMapper<IDType, DType>{


    private final Map<IDType, DType> _identityMap = new Hashtable<IDType, DType>();
    private final DataSource _ds;

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
    
    abstract IDType doGetId(ResultSet rs);
    abstract DType doLoad(ResultSet rs);

    public AbstractDataMapper(DataSource ds){ _ds = ds; }

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

    public DType getById(IDType id) {
        DType val = _identityMap.get(id);
        if (val != null) return val;

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = _ds.getConnection();
            st = conn.prepareStatement(doGetFindStatement());
            doBindFindStatement(st, id);
            rs = st.executeQuery();
            if (!rs.next()) return null;
            return load(rs);
        }
        catch (SQLException se){
            throw new Serie3_DataMapperException();
        }
        finally{
            try { conn.close(); }
            catch (SQLException se) { throw new Serie3_DataMapperException(); }
        }
    }

    public void loadAllInto(Collection<DType> col) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = _ds.getConnection();
            st = conn.prepareStatement(doGetAllStatement());
            rs = st.executeQuery();
            DType elem;
            while(rs.next()){
                elem = load(rs);
                col.add(elem);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
            throw new Serie3_DataMapperException();
        }
        finally{
            try { conn.close(); }
            catch (SQLException se) { throw new Serie3_DataMapperException(); }
        }
    }

    public void loadFilteredInto(IFilter filter, Collection<DType> col){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = _ds.getConnection();
            st = conn.prepareStatement(doGetAllStatement() + filter.getWhereClause());
            filter.bindValues(st);
            rs = st.executeQuery();
            DType elem;
            while(rs.next()){
                elem = load(rs);
                col.add(elem);
            }
        }
        catch(SQLException e){
            throw new Serie3_DataMapperException();
        }
        finally{
            try { conn.close(); }
            catch (SQLException se) { throw new Serie3_DataMapperException(); }
        }
    }

}
