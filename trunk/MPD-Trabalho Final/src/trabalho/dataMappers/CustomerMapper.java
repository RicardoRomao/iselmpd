package trabalho.dataMappers;

import exceptions.DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import trabalho.jdbc.JdbcConnector;
import trabalho.domainObjects.Customer;

public class CustomerMapper extends AbstractDataMapper<String, Customer> {

    public CustomerMapper(JdbcConnector connector) {
        super(connector);
    }

    @Override
    String doGetAllStatement() {
        return "select customerId, contactName, companyName, city, country,"
                + " phone from Customers ";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where customerId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, String key) {
        try {
            st.setString(1, key);
        }
        catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(Customer c) { return false; }

    @Override
    String doGetInsertStatement(Customer c) {
        return "insert into Customers (CustomerId, companyName, contactName," +
                "city, country, phone) values (?,?,?,?,?,?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, Customer c) {
        try {
            st.setString(1,c.getId());
            st.setString(2,c.getCompanyName());
            st.setString(3,c.getContactName());
            st.setString(4,c.getCity());
            st.setString(5,c.getCountry());
            st.setString(6,c.getPhone());
        }catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Customers set companyName = ?, contactName = ?, " +
                "city = ?, country = ?, phone = ? where customerId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Customer c) {
        try {
            st.setString(1,c.getCompanyName());
            st.setString(2,c.getContactName());
            st.setString(3,c.getCity());
            st.setString(4,c.getCountry());
            st.setString(5,c.getPhone());
            st.setString(6,c.getId());
        }catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Customers where customerId = ?";
    }
    
    @Override
    void doBindDeleteStatement(PreparedStatement st, String key) {
        try {
            st.setString(1,key);
        }catch (SQLException e) {
            throw new DataMapperException();
        }
    }

    @Override
    String doGetId(ResultSet rs) {
        try{
            return rs.getString(1);
        }
        catch(SQLException se){
            throw new DataMapperException();
        }
    }

    @Override
    Customer doLoad(ResultSet rs)  {
        try{
            Customer c = new Customer(rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                false
            );
            return c;
        }
        catch(SQLException se){
            throw new DataMapperException();
        }
    }
}
