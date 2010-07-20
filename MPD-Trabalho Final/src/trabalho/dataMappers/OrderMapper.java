package trabalho.dataMappers;

import trabalho.dataMappers.registry.MapperRegistry;
import exceptions.Serie3_DataMapperException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import trabalho.jdbc.JdbcConnector;
import trabalho.dataMappers.filters.FilterByOrder;
import trabalho.domainObjects.Customer;
import trabalho.domainObjects.Order;
import trabalho.domainObjects.OrderDetails;
import trabalho.domainObjects.Shipper;
import trabalho.domainObjects.lazyLoaders.LazySet;
import trabalho.domainObjects.lazyLoaders.ValueHolder;

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
            st.setDate(1, new Date(o.getOrderDate().getTimeInMillis()));
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
            else {
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
            }
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Orders set orderDate = ?, shipAddress = ?, shipName = ?, " +
                "customerId = ?, shipVia = ? where orderId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Order o) {
        try {
            st.setDate(1, new Date(o.getOrderDate().getTimeInMillis()));
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
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(rs.getDate(3));
            Order o = new Order(rs.getInt(1),
                new ValueHolder(
                    rs.getString(2),
                    (IDataMapper<String, Customer>) MapperRegistry.current().get(Customer.class)
                ),
                calendar,
                rs.getString(4),
                rs.getString(5),
                new ValueHolder(
                    rs.getInt(6),
                    (IDataMapper<Integer, Shipper>) MapperRegistry.current().get(Shipper.class)
                ),
                new LazySet(new FilterByOrder(rs.getInt(1)),OrderDetails.class)
            );
            return o;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new Serie3_DataMapperException();
        }
    }
}
