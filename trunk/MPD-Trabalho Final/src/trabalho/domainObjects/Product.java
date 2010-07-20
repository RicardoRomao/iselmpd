package trabalho.domainObjects;

import java.util.Set;
import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import trabalho.domainObjects.primaryKeys.PkOrderDetails;
import trabalho.dataMappers.filters.FilterByProduct;
import trabalho.domainObjects.lazyLoaders.*;

public class Product extends DomainObject<Integer, Product>{

    private ILazySet<PkOrderDetails,OrderDetails> _orderDetails;
    private String _productName;
    private IValueHolder<Integer, Supplier> _supplier;
    private IValueHolder<Integer, Category> _category;
    private double _unitPrice;
    private int _unitsInStock;

    public Product(Integer key, ILazySet<PkOrderDetails,OrderDetails> orderDetails,
            String productName, IValueHolder<Integer, Supplier> supplier,
            IValueHolder<Integer, Category> category, double unitPrice,
            int unitsInStock) {
        super(key);
        bindConstructorValues(productName, unitPrice, unitsInStock);
        _supplier = supplier;
        _category = category;
        _orderDetails = orderDetails;
        System.out.println("Product " + key + " loaded!");
    }

    public Product(String productName, Supplier supplier,
            Category category, double unitPrice,
            int unitsInStock) {
        super();
        bindConstructorValues(productName, unitPrice, unitsInStock);
        _orderDetails = new LazySet<PkOrderDetails, OrderDetails>();
        if (supplier != null && supplier.hasId()) {
            _supplier = new ValueHolder<Integer, Supplier>(supplier.getId(), supplier.mapper());
        } else {
            _supplier = new ValueHolder<Integer, Supplier>(supplier);
        }
        if (category != null && category.hasId()) {
            _category = new ValueHolder<Integer, Category>(category.getId(),category.mapper());
        } else {
            _category = new ValueHolder<Integer, Category>(category);
        }
    }

    private void bindConstructorValues(String productName, double unitPrice,
            int unitsInStock) {
        _productName = productName;
        _unitPrice = unitPrice;
        _unitsInStock = unitsInStock;
    }

    @VisibleProperty(name="Product ID", kind = PropertyKind.Simple)
    public int getProductId() { return getId(); }
    @Override
    public void setId(Integer id) {
        super.setId(id);
        _orderDetails = new LazySet<PkOrderDetails, OrderDetails>(
                new FilterByProduct(id),OrderDetails.class);
    }

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
    protected Product self() {
        return this;
    }
    
    @Override
    public String toString(){
        return "Product " + getProductId() + " - " + getProductName();
    }

}
