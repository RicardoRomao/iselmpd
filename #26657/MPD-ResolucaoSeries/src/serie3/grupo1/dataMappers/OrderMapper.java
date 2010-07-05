package serie3.grupo1.dataMappers;

import serie3.grupo1.dataMappers.registry.MapperRegistry;
import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import serie3.grupo1.dataMappers.filters.FilterByOrder;
import serie3.grupo1.domainObjects.Customer;
import serie3.grupo1.domainObjects.Order;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.Shipper;
import serie3.grupo1.domainObjects.lazyLoaders.LazySet;
import serie3.grupo1.domainObjects.lazyLoaders.ValueHolder;

public class OrderMapper extends AbstractDataMapper<Integer, Order> {

    public OrderMapper(DataSource ds) {
        super(ds);
    }

    @Override
    String doGetAllStatement() {
        return "select orderId, customerId, orderDate, shipAddress, shipName, "
                + "shipVia from Orders";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where orderId = ?";
    }


    @Override
    String doGetInsertStatement(Order o) {
        return "insert into Orders (orderId, customerId, orderDate, " +
                "shipAddres, shipName, shipVia) values (?, ?, ?, ?, ?, ?)";
    }

    @Override
    String doGetUpdateStatement() {
        return "update Orders set customerId = ?, orderDate = ?, " +
                "shipAddres = ?, shipName = ?, shipVia = ? where orderId = ?";
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Orders where orderId = ?";
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
    Order doLoad(ResultSet rs) {
        try {
            Order o = new Order(rs.getInt(1),
                new ValueHolder(
                    rs.getString(2),
                    (IDataMapper<String, Customer>) MapperRegistry.current().get(Customer.class)
                ),
                rs.getDate(3),
                rs.getString(4),
                rs.getString(5),
                new ValueHolder(
                    rs.getInt(6),
                    (IDataMapper<Integer, Shipper>) MapperRegistry.current().get(Shipper.class)
                ),
                new LazySet(
                    new FilterByOrder(rs.getInt(1)),
                    (IDataMapper<PkOrderDetails, OrderDetails>) MapperRegistry.current().get(OrderDetails.class)
                )
            );
            return o;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new Serie3_DataMapperException();
        }
    }
}
