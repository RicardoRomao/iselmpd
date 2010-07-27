package trabalho.dataMappers;

import exceptions.DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import trabalho.jdbc.JdbcConnector;
import trabalho.domainObjects.Supplier;

public class SupplierMapper extends AbstractDataMapper<Integer, Supplier> {

    public SupplierMapper(JdbcConnector connector) {
        super(connector);
    }

    @Override
    String doGetAllStatement() {
        return "select supplierId, contactName, companyName, city, country,"
                + " phone from Suppliers ";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where supplierId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        } catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(Supplier s) { return false; }

    @Override
    String doGetInsertStatement(Supplier s) {
        return "insert into Suppliers (contactName, companyName, city, country, " +
                "phone) values (?, ?, ?, ?, ?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, Supplier s) {
        try {
            st.setString(1, s.getContactName());
            st.setString(2, s.getCompanyName());
            st.setString(3, s.getCity());
            st.setString(4, s.getCountry());
            st.setString(5, s.getPhone());
        } catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Suppliers set contactName = ?, companyName = ?, city = ?, " +
                "country = ?, phone = ? where supplierId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Supplier s) {
        try {
            st.setString(1, s.getContactName());
            st.setString(2, s.getCompanyName());
            st.setString(3, s.getCity());
            st.setString(4, s.getCountry());
            st.setString(5, s.getPhone());
            st.setInt(6, s.getId());
        } catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Suppliers where supplierId = ?";
    }

    @Override
    void doBindDeleteStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        } catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    Integer doGetId(ResultSet rs) {
        try {
            return rs.getInt(1);
        } catch (SQLException se) {
            throw new DataMapperException();
        }
    }

    @Override
    Supplier doLoad(ResultSet rs) {
        try {
            Supplier s = new Supplier(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
            );
            return s;
        } catch (SQLException se) {
            throw new DataMapperException();
        }
    }
}
