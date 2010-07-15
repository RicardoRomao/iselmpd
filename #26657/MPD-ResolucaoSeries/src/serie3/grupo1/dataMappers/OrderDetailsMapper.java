package serie3.grupo1.dataMappers;

import serie3.grupo1.dataMappers.registry.MapperRegistry;
import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import serie3.grupo1.dataConnector.JdbcConnector;
import serie3.grupo1.domainObjects.Order;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.Product;
import serie3.grupo1.domainObjects.lazyLoaders.ValueHolder;

public class OrderDetailsMapper extends AbstractDataMapper<PkOrderDetails, OrderDetails> {

    public OrderDetailsMapper(JdbcConnector connector) {
        super(connector);
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
    void doBindFindStatement(PreparedStatement st, PkOrderDetails key) {
        try {
            st.setInt(1, key.getOrderId());
            st.setInt(2, key.getProductId());
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(OrderDetails o) {
        return hasCleanProduct(o) || hasCleanOrder(o);
    }

    private final boolean hasCleanProduct(OrderDetails o) {
        return o.getProduct() != null && !o.getProduct().hasId();
    }

    private final boolean hasCleanOrder(OrderDetails o) {
        return o.getOrder() != null && !o.getOrder().hasId();
    }

    @Override
    String doGetInsertStatement(OrderDetails o) {
        if (doInsertRequiresUpdate(o)){
            if (hasCleanOrder(o) && hasCleanProduct(o)){
                return  "insert into [Order Details] (unitPrice, quantity, " +
                        "discount) values (?, ?, ?)";
            } else if (hasCleanProduct(o)) {
                return  "insert into [Order Details] (unitPrice, quantity, " +
                        "discount, OrderId) values (?, ?, ?, ?)";
            } else if (hasCleanOrder(o)) {
                return  "insert into [Order Details] (unitPrice, quantity, " +
                        "discount, productId) values (?, ?, ?, ?)";
            }
        }
        return  "insert into [Order Details] (unitPrice, quantity, " +
                "discount, OrderId, productId) values (?, ?, ?, ?, ?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, OrderDetails o) {
        try {
            st.setDouble(1, o.getUnitPrice());
            st.setInt(2, o.getQuantity());
            st.setDouble(3, o.getDiscount());
            if (doInsertRequiresUpdate(o)) {
                if (hasCleanOrder(o) && hasCleanProduct(o)){
                    return;
                } else if (hasCleanProduct(o)) {
                    st.setInt(4, o.getOrder().getId());
                } else if (hasCleanOrder(o)) {
                    st.setInt(4, o.getProduct().getId());
                }
            }
            if (o.getOrder().getId() != null) {
                st.setInt(4, o.getOrder().getId());
            } else {
                st.setObject(4,null);
            }
            if (o.getProduct().getId() != null) {
                st.setInt(5, o.getProduct().getId());
            } else {
                st.setObject(5,null);
            }
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update [Order Details] set unitPrice = ?, quantity = ?, " +
                " discount = ? where orderId = ? and productId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, OrderDetails o) {
        try {
            st.setDouble(1, o.getUnitPrice());
            st.setInt(2, o.getQuantity());
            st.setDouble(3, o.getDiscount());
            if (o.getOrder().getId() != null) {
                st.setInt(4, o.getOrder().getId());
            } else {
                st.setObject(4,null);
            }
            if (o.getProduct().getId() != null) {
                st.setInt(5, o.getProduct().getId());
            } else {
                st.setObject(5,null);
            }

        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from [Order Details] where orderId = ? and productId = ?";
    }
    
    @Override
    void doBindDeleteStatement(PreparedStatement st, PkOrderDetails key) {
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
            OrderDetails od = new OrderDetails(
                new ValueHolder<Integer, Product>(
                    rs.getInt(2),
                    (IDataMapper<Integer, Product>) MapperRegistry.current().get(Product.class)
                ),
                new ValueHolder<Integer, Order>(
                    rs.getInt(1),
                    (IDataMapper<Integer, Order>) MapperRegistry.current().get(Order.class)
                ),
                rs.getDouble(3),
                rs.getInt(4),
                rs.getDouble(5)
            );
            return od;
        } catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }
}
