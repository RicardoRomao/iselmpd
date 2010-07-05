package serie3.grupo1.dataMappers;

import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import serie3.grupo1.domainObjects.Shipper;

public class ShipperMapper extends AbstractDataMapper<Integer,Shipper>{

    public ShipperMapper(DataSource ds){
        super(ds);
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
    String doGetInsertStatement(Shipper s) {
        return "insert into Shippers (companyName, phone) values (?, ?)";
    }

    @Override
    String doGetUpdateStatement() {
        return "update Shippers set companyName = ?, phone = ? where shipperId = ?";
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Shippers where shipperId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        }
        catch(SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    Integer doGetId(ResultSet rs) {
        try {
            return rs.getInt(1);
        }
        catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    Shipper doLoad(ResultSet rs) {
        try {
            Shipper s = new Shipper(rs.getInt(1),rs.getString(2),rs.getString(3));
            return s;
        }
        catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }
}
