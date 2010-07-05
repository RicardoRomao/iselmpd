package serie3.grupo1.dataMappers;

import serie3.grupo1.dataMappers.registry.MapperRegistry;
import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import serie3.grupo1.dataMappers.filters.FilterByProduct;
import serie3.grupo1.domainObjects.Category;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.Product;
import serie3.grupo1.domainObjects.Supplier;
import serie3.grupo1.domainObjects.lazyLoaders.LazySet;
import serie3.grupo1.domainObjects.lazyLoaders.ValueHolder;

public class ProductMapper extends AbstractDataMapper<Integer,Product>{

    public ProductMapper(DataSource ds) {
        super(ds);
    }

    @Override
    String doGetAllStatement() {
        return "select productId, productName, supplierId, categoryId, unitPrice, " +
                "unitsInStock from Products ";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where productId = ?";
    }

    @Override
    String doGetInsertStatement(Product p) {
        return "insert into Products (productName, supplierId, categoryId, " +
                "unitPrice, unitsInStock) values (?, ?, ?, ?, ?)";
    }

    @Override
    String doGetUpdateStatement() {
        return "update Products set productName = ?, supplierId = ?, " +
                "categoryId = ?, unitPrice = ?, unitsInStock = ? " +
                "where productId = ?";
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Products where productId = ?";
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
                new ValueHolder(rs.getInt(3),
                    (IDataMapper<Integer, Supplier>)
                        MapperRegistry.current().get(Supplier.class)
                ),
                new ValueHolder(rs.getInt(4),
                    (IDataMapper<Integer, Category>)
                        MapperRegistry.current().get(Category.class)
                ),
                rs.getDouble(5),
                rs.getInt(6)
            );

            return p;
        }
        catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }
}
