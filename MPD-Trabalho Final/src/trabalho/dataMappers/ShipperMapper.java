package trabalho.dataMappers;

import exceptions.DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import trabalho.jdbc.JdbcConnector;
import trabalho.domainObjects.Shipper;

public class ShipperMapper extends AbstractDataMapper<Integer,Shipper>{

    public ShipperMapper(JdbcConnector connector){
        super(connector);
    }

    @Override
    String doGetAllStatement() {
        return "select shipperId, companyName, phone from Shippers ";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where shipperId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        }
        catch(SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(Shipper s) { return false; }

    @Override
    String doGetInsertStatement(Shipper s) {
        return "insert into Shippers (companyName, phone) values (?, ?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, Shipper s) {
        try {
            st.setString(1, s.getCompanyName());
            st.setString(2, s.getPhone());
        }
        catch(SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Shippers set companyName = ?, phone = ? where shipperId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Shipper s) {
        try {
            st.setString(1, s.getCompanyName());
            st.setString(2, s.getPhone());
            st.setInt(3,s.getId());
        }
        catch(SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Shippers where shipperId = ?";
    }

    @Override
    void doBindDeleteStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        }
        catch(SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    Integer doGetId(ResultSet rs) {
        try {
            return rs.getInt(1);
        }
        catch (SQLException se) {
            throw new DataMapperException();
        }
    }

    @Override
    Shipper doLoad(ResultSet rs) {
        try {
            Shipper s = new Shipper(rs.getInt(1),rs.getString(2),rs.getString(3));
            return s;
        }
        catch (SQLException se) {
            throw new DataMapperException();
        }
    }
}
