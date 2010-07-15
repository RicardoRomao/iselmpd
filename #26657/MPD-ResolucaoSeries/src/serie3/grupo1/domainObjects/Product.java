package serie3.grupo1.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.lazyLoaders.IValueHolder;
import java.util.Set;
import serie3.grupo1.dataMappers.filters.FilterByProduct;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.domainObjects.lazyLoaders.ILazySet;
import serie3.grupo1.domainObjects.lazyLoaders.LazySet;

public class Product extends DomainObject<Integer, Product>{

    private ILazySet<PkOrderDetails,OrderDetails> _orderDetails;
    private String _productName;
    private IValueHolder<Integer, Supplier> _supplier;
    private IValueHolder<Integer, Category> _category;
    private double _unitPrice;
    private int _unitsInStock;

    public Product(Integer key, ILazySet<PkOrderDetails,OrderDetails> orderDetails,
            String productName, IValueHolder<Integer, Supplier> supplier,
            IValueHolder<Integer, Category> _category, double unitPrice,
            int unitsInStock) {
        super(key);
        bindConstructorValues(orderDetails, productName, supplier, _category,
            unitPrice, unitsInStock
        );
        System.out.println("Product " + key + " loaded!");
    }

    public Product(String productName, IValueHolder<Integer, Supplier> supplier,
            IValueHolder<Integer, Category> _category, double unitPrice,
            int unitsInStock) {
        super();
        bindConstructorValues(
            new LazySet<PkOrderDetails,OrderDetails>(
                new FilterByProduct(getId()),
                (IDataMapper<PkOrderDetails, OrderDetails>)
                                MapperRegistry.current().get(OrderDetails.class)
            ), productName, supplier, _category, unitPrice, unitsInStock
        );
    }

    private void bindConstructorValues(ILazySet<PkOrderDetails,OrderDetails> orderDetails,
            String productName, IValueHolder<Integer, Supplier> supplier,
            IValueHolder<Integer, Category> category, double unitPrice,
            int unitsInStock) {
        _orderDetails = orderDetails;
        _productName = productName;
        _supplier = supplier;
        _category = category;
        _unitPrice = unitPrice;
        _unitsInStock = unitsInStock;
    }

    @VisibleProperty(name="Product ID", kind = PropertyKind.Simple)
    public int getProductId() { return getId(); }

    public Set<OrderDetails> getOrderDetails() { return _orderDetails.getImmutableSet(); }

    @VisibleProperty(name="Name", kind = PropertyKind.Simple)
    public String getProductName() { return _productName; }
    public void setProductName(String productName) {
        markDirty();
        this._productName = productName;
    }

     @VisibleProperty(name="Supplier", kind = PropertyKind.Complex)
    public Supplier getSupplier() { return _supplier.get(); }
    public void setSupplier(IValueHolder<Integer, Supplier> supplier) {
        markDirty();
        _supplier = supplier;
    }

    @VisibleProperty(name="Category", kind = PropertyKind.Complex)
    public Category getCategory() { return _category.get(); }
    public void setCategory(IValueHolder<Integer,Category> category) {
        markDirty();
        _category = category;
    }

    @VisibleProperty(name="Unit Price", kind = PropertyKind.Simple)
    public double getUnitPrice() { return _unitPrice; }
    public void setUnitPrice(double unitPrice) { 
        markDirty();
        this._unitPrice = unitPrice;
    }

    @VisibleProperty(name="Units In Stock", kind = PropertyKind.Simple)
    public int getUnitsInStock() { return _unitsInStock; }
    public void setUnitsInStock(int unitsInStock) { 
        markDirty();
        this._unitsInStock = unitsInStock;
    }

    @Override
    protected IDataMapper<Integer, Product> mapper() {
        return MapperRegistry.current().get(this.getClass());
    }

    @Override
    protected Product self() {
        return this;
    }
    
    @Override
    public String toString(){
        return "Product " + getProductId() + " - " + getProductName();
    }

}
