package serie3.grupo1.dataMappers;

import serie3.grupo1.dataMappers.registry.MapperRegistry;
import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import serie3.grupo1.domainObjects.Order;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.Product;
import serie3.grupo1.domainObjects.lazyLoaders.ValueHolder;

public class OrderDetailsMapper extends AbstractDataMapper<PkOrderDetails, OrderDetails> {

    public OrderDetailsMapper(DataSource ds) {
        super(ds);
    }

    @Override
    String doGetAllStatement() {
        return "select orderId, productId, unitPrice, quantity,"
                + " discount from [Order Details]";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where orderId = ? and productId = ?";
    }

    @Override
    String doGetInsertStatement(OrderDetails o) {
        return "insert into [Order Details] (OrderId, productId, unitPrice, " +
                "quantity, discount) values (?, ?, ?, ?, ?)";
    }

    @Override
    String doGetUpdateStatement() {
        return "update [Order Details] set unitPrice = ?, quantity = ?, " +
                " discount = ? where orderId = ? and productId = ?";
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from [Order Details] where orderId = ? and productId = ?";
    }
    
    @Override
    void doBindFindStatement(PreparedStatement st, PkOrderDetails key) {
        try {
            st.setInt(1, key.getOrderId());
            st.setInt(2, key.getProductId());
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    PkOrderDetails doGetId(ResultSet rs) {
        try {
            return new PkOrderDetails(rs.getInt(1), rs.getInt(2));
        } catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    OrderDetails doLoad(ResultSet rs) {
        try {
            PkOrderDetails id = doGetId(rs);
            OrderDetails od = new OrderDetails(id,
                new ValueHolder<Integer, Product>(
                    id.getProductId(),
                    (IDataMapper<Integer, Product>) MapperRegistry.current().get(Product.class)
                ),
                new ValueHolder<Integer, Order>(
                    id.getOrderId(),
                    (IDataMapper<Integer, Order>) MapperRegistry.current().get(Order.class)
                ),
                rs.getDouble(3),
                rs.getInt(4),
                rs.getDouble(5),
                false
            );
            return od;
        } catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }
}
