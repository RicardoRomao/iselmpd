package serie3.grupo1.dataMappers;

import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import serie3.grupo1.domainObjects.Supplier;

public class SupplierMapper extends AbstractDataMapper<Integer, Supplier> {

    public SupplierMapper(DataSource ds) {
        super(ds);
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
    String doGetInsertStatement(Supplier s) {
        return "insert into Suppliers (contactName, companyName, city, country, " +
                "phone) values (?, ?, ?, ?, ?)";
    }

    @Override
    String doGetUpdateStatement() {
        return "update Suppliers set contactName = ?, companyName = ?, city = ?, " +
                "country = ?, phone = ? where supplierId = ?";
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Suppliers where supplierId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    Integer doGetId(ResultSet rs) {
        try {
            return rs.getInt(1);
        } catch (SQLException se) {
            throw new Serie3_DataMapperException();
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
            throw new Serie3_DataMapperException();
        }
    }
}
