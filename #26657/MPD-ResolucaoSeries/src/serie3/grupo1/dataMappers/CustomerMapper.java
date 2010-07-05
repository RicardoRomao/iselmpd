package serie3.grupo1.dataMappers;

import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import serie3.grupo1.domainObjects.Customer;

public class CustomerMapper extends AbstractDataMapper<String, Customer> {

    public CustomerMapper(DataSource ds) {
        super(ds);
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
    String doGetInsertStatement(Customer c) {
        return "insert into Customers (CustomerId, companyName, contactName," +
                "city, country, phone) values (?,?,?,?,?,?)";
    }

    @Override
    String doGetUpdateStatement() {
        return "update Customers set companyName = ?, contactName = ?, " +
                "city = ?, country = ?, phone = ? where customerId = ?";
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Customers where customerId = ?";
    }
    
    @Override
    void doBindFindStatement(PreparedStatement st, String key) {
        try {
            st.setString(1, key);
        }
        catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetId(ResultSet rs) {
        try{
            return rs.getString(1);
        }
        catch(SQLException se){
            throw new Serie3_DataMapperException();
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
            throw new Serie3_DataMapperException();
        }
    }
}
