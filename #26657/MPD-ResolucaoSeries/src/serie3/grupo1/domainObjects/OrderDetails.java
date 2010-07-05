package serie3.grupo1.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import serie3.grupo1.domainObjects.lazyLoaders.IValueHolder;

public class OrderDetails extends DomainObject<PkOrderDetails, OrderDetails>{

    private double _unitPrice;
    private int _quantity;
    private double _discount;
    private IValueHolder<Integer,Product> _product;
    private IValueHolder<Integer,Order> _order;


    public OrderDetails(PkOrderDetails id, IValueHolder<Integer,Product> product,
            IValueHolder<Integer,Order> order, double unitPrice,
            int quantity, double discount, boolean isNew) {
        super(id, isNew);
        bindConstructorValues(product, order, unitPrice, quantity, discount);
        System.out.println("OrderDetail (Order:" + id.getOrderId() +
                "/Product:" + id.getProductId() + ") loaded!");
    }

    public OrderDetails(PkOrderDetails id, IValueHolder<Integer,Product> product,
            IValueHolder<Integer,Order> order, double unitPrice,
            int quantity, double discount) {
        super(id, true);
        bindConstructorValues(product, order, unitPrice, quantity, discount);
    }

    private void bindConstructorValues(IValueHolder<Integer,Product> product,
            IValueHolder<Integer,Order> order, double unitPrice,
            int quantity, double discount) {
        _product = product;
        _order = order;
        _unitPrice = unitPrice;
        _quantity = quantity;
        _discount = discount;
    }

    @VisibleProperty(name="Unit Price", kind = PropertyKind.Simple)
    public double getUnitPrice() { return _unitPrice; }
    public void setUnitPrice(double unitPrice) {
        markDirty();
        this._unitPrice = unitPrice;
    }

    @VisibleProperty(name="Quantity", kind = PropertyKind.Simple)
    public int getQuantity() { return _quantity; }
    public void setQuantity(int quantity) {
        markDirty();
        this._quantity = quantity;
    }

    @VisibleProperty(name="Discount", kind = PropertyKind.Simple)
    public double getDiscount() { return _discount; }
    public void setDiscount(double discount) {
        markDirty();
        this._discount = discount;
    }
   
    @VisibleProperty(name="Product ID", kind = PropertyKind.Simple)
    public Integer getProductId() { return _product.getKey(); }
    @VisibleProperty(name="Product", kind = PropertyKind.Complex)
    public Product getProduct() { return _product.get(); }
    public void setProduct(IValueHolder<Integer,Product> product){
        markDirty();
        _product = product;
    }

    @VisibleProperty(name="Order ID", kind = PropertyKind.Simple)
    public Integer getOrderId() { return _order.getKey(); }
    @VisibleProperty(name="Order", kind = PropertyKind.Complex)
    public Order getOrder() { return _order.get(); }
    public void setOrder(IValueHolder<Integer,Order> order){
        markDirty();
        _order = order;
    }

    @Override
    protected IDataMapper<PkOrderDetails, OrderDetails> mapper() {
        return MapperRegistry.current().get(this.getClass());
    }

    @Override
    protected OrderDetails self() {
        return this;
    }

     @Override
    public String toString(){
        return "OrderDetail " + getId().getOrderId() + "/" + getId().getProductId() +
                ": UnitPrice=" + getUnitPrice() + "\tQuantity=" + getQuantity();
    }
}
