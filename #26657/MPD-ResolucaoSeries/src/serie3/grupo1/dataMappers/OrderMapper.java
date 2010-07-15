package serie3.grupo1.dataMappers;

import serie3.grupo1.dataMappers.registry.MapperRegistry;
import exceptions.Serie3_DataMapperException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import serie3.grupo1.dataConnector.JdbcConnector;
import serie3.grupo1.dataMappers.filters.FilterByOrder;
import serie3.grupo1.domainObjects.Customer;
import serie3.grupo1.domainObjects.Order;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.Shipper;
import serie3.grupo1.domainObjects.lazyLoaders.LazySet;
import serie3.grupo1.domainObjects.lazyLoaders.ValueHolder;

public class OrderMapper extends AbstractDataMapper<Integer, Order> {

    public OrderMapper(JdbcConnector connector) {
        super(connector);
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
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(Order o){
        return hasCleanCustomer(o) || hasCleanShipper(o);
    }

    private final boolean hasCleanCustomer(Order o) {
        return o.getCustomer() != null && !o.getCustomer().hasId();
    }

    private final boolean hasCleanShipper(Order o) {
        return o.getShipVia() != null && !o.getShipVia().hasId();
    }

    @Override
    String doGetInsertStatement(Order o) {
        if (doInsertRequiresUpdate(o)){
            if (hasCleanShipper(o) && hasCleanCustomer(o)) {
                return "insert into Orders (orderDate, shipAddress, shipName)" +
                    " values (?, ?, ?)";
            } else if (hasCleanShipper(o)) {
                return "insert into Orders (orderDate, shipAddress, shipName, " +
                    "customerId) values (?, ?, ?, ?)";
            } else if (hasCleanCustomer(o)) {
                return "insert into Orders (orderDate, shipAddress, shipName, " +
                    "shipVia) values (?, ?, ?, ?)";
            }
        }
        return "insert into Orders (orderDate, shipAddress, shipName, " +
                "customerId, shipVia) values (?, ?, ?, ?, ?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, Order o) {
        try {
            st.setDate(1, (Date)o.getOrderDate());
            st.setString(2, o.getShipAddress());
            st.setString(3, o.getShipName());
            if (doInsertRequiresUpdate(o)){
                if (hasCleanShipper(o) && hasCleanCustomer(o)) {
                    return;
                } else if (hasCleanShipper(o)) {
                    st.setString(4,o.getCustomer().getId());
                } else if (hasCleanCustomer(o)) {
                    st.setInt(4,o.getShipVia().getId());
                }
            }
            if (o.getCustomer() != null) {
                st.setString(4,o.getCustomer().getId());
            } else {
                st.setObject(4,null);
            }
            if (o.getShipVia() != null) {
                st.setInt(5,o.getShipVia().getId());
            } else {
                st.setObject(5,null);
            }
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Orders set orderDate = ?, shipAddres = ?, shipName = ?, " +
                "customerId = ?, shipVia = ? where orderId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Order o) {
        try {
            st.setDate(1, (Date)o.getOrderDate());
            st.setString(2, o.getShipAddress());
            st.setString(3, o.getShipName());
            if (o.getCustomer() != null) {
                st.setString(4,o.getCustomer().getId());
            } else {
                st.setObject(4,null);
            }
            if (o.getShipVia() != null) {
                st.setInt(5,o.getShipVia().getId());
            } else {
                st.setObject(5,null);
            }
            st.setInt(6, o.getId());
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Orders where orderId = ?";
    }
    
    @Override
    void doBindDeleteStatement(PreparedStatement st, Integer key) {
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
