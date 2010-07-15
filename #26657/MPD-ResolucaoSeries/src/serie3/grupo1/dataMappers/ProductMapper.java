package serie3.grupo1.dataMappers;

import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import serie3.grupo1.dataConnector.JdbcConnector;
import serie3.grupo1.dataMappers.filters.FilterByProduct;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.domainObjects.Category;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.Product;
import serie3.grupo1.domainObjects.Supplier;
import serie3.grupo1.domainObjects.lazyLoaders.LazySet;
import serie3.grupo1.domainObjects.lazyLoaders.ValueHolder;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;

public class ProductMapper extends AbstractDataMapper<Integer,Product>{

    public ProductMapper(JdbcConnector connector) {
        super(connector);
    }

    @Override
    String doGetAllStatement() {
        return "select productId, productName, unitPrice, unitsInStock, " +
                "supplierId, categoryId from Products ";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where productId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        }
        catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(Product p) {
        return hasCleanCategory(p) || hasCleanSupplier(p);
    }

    private final boolean hasCleanCategory(Product p) {
        return p.getCategory() != null && !p.getCategory().hasId();
    }

    private final boolean hasCleanSupplier(Product p) {
        return p.getSupplier() != null && !p.getSupplier().hasId();
    }

    @Override
    String doGetInsertStatement(Product p) {
        if (doInsertRequiresUpdate(p)) {
            if (hasCleanCategory(p) && hasCleanSupplier(p)) {
                return "insert into Products (productName, unitPrice, " +
                    " unitsInStock) values (?, ?, ?)";
            } else if (hasCleanCategory(p)) {
                return "insert into Products (productName, unitPrice, " +
                    "unitsInStock, supplierId) values (?, ?, ?, ?)";
            } else if (hasCleanSupplier(p)) {
                return "insert into Products (productName, unitPrice, " +
                    "unitsInStock, categoryId) values (?, ?, ?, ?)";
            }
        }
        return "insert into Products (productName, unitPrice, unitsInStock, " +
                "supplierId, caregoryID) values (?, ?, ?, ?, ?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, Product p) {
        try {
            st.setString(1, p.getProductName());
            st.setDouble(2, p.getUnitPrice());
            st.setInt(3, p.getUnitsInStock());
            if (doInsertRequiresUpdate(p)) {
                if (hasCleanCategory(p) && hasCleanSupplier(p)) {
                    return;
                } else if (hasCleanCategory(p)) {
                    st.setInt(4, p.getSupplier().getId());
                } else if (hasCleanSupplier(p)) {
                    st.setInt(4, p.getCategory().getId());
                }
            }
            if (p.getSupplier() != null) {
                st.setInt(4, p.getSupplier().getId());
            } else {
                st.setObject(4, null);
            }
            if (p.getCategory() != null) {
                st.setInt(5, p.getCategory().getId());
            } else {
                st.setObject(5,null);
            }
        }
        catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Products set productName = ?, unitPrice = ?, unitsInStock = ?, " +
                "supplierId = ?, categoryId = ?,  " +
                "where productId = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Product p) {
        try {
            st.setString(1, p.getProductName());
            st.setDouble(2, p.getUnitPrice());
            st.setInt(3, p.getUnitsInStock());
            if (p.getSupplier() != null) {
                st.setInt(4, p.getSupplier().getId());
            } else {
                st.setObject(4, null);
            }
            if (p.getCategory() != null) {
                st.setInt(5, p.getCategory().getId());
            } else {
                st.setObject(5,null);
            }
            st.setInt(6, p.getId());
        }
        catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Products where productId = ?";
    }
    
    @Override
    void doBindDeleteStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        }
        catch (SQLException e) {
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
    Product doLoad(ResultSet rs) {
        try {
            Product p = new Product(rs.getInt(1),
                new LazySet(
                    new FilterByProduct(rs.getInt(1)),
                        (IDataMapper<PkOrderDetails, OrderDetails>)
                                MapperRegistry.current().get(OrderDetails.class)
                ),
                rs.getString(2),
                new ValueHolder(rs.getInt(5),
                    (IDataMapper<Integer, Supplier>)
                        MapperRegistry.current().get(Supplier.class)
                ),
                new ValueHolder(rs.getInt(6),
                    (IDataMapper<Integer, Category>)
                        MapperRegistry.current().get(Category.class)
                ),
                rs.getDouble(3),
                rs.getInt(4)
            );
            return p;
        }
        catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }
}
