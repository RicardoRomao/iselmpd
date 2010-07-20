package trabalho.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import trabalho.domainObjects.primaryKeys.PkOrderDetails;
import trabalho.domainObjects.lazyLoaders.IValueHolder;
import trabalho.domainObjects.lazyLoaders.ValueHolder;

public class OrderDetails extends DomainObject<PkOrderDetails, OrderDetails>{

    private double _unitPrice;
    private int _quantity;
    private double _discount;
    private IValueHolder<Integer,Product> _product;
    private IValueHolder<Integer,Order> _order;


    public OrderDetails(IValueHolder<Integer,Product> product,
            IValueHolder<Integer,Order> order, double unitPrice,
            int quantity, double discount) {
        super(new PkOrderDetails(order.getKey(),product.getKey()));
        _product = product;
        _order = order;
        _unitPrice = unitPrice;
        _quantity = quantity;
        _discount = discount;
        System.out.println("OrderDetail (Order:" + order.getKey() +
                "/Product:" + product.getKey() + ") loaded!");
    }

    public OrderDetails(Order order, Product product, double unitPrice,
            int quantity, double discount) {
        super();

        _unitPrice = unitPrice;
        _quantity = quantity;
        _discount = discount;
        if (product.hasId()) {
            _product = new ValueHolder<Integer,Product>(product.getId(), product.mapper());
        } else {
            _product = new ValueHolder<Integer,Product>(product);
        }
        if (order.hasId()) {
            _order = new ValueHolder<Integer,Order>(order.getId(), order.mapper());
        } else {
            _order = new ValueHolder<Integer,Order>(order);
        }
        if (order.hasId() && product.hasId())
            setId(new PkOrderDetails(order.getId(), product.getId()));
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
    protected OrderDetails self() {
        return this;
    }

    @Override
    public String toString(){
        return "OrderDetail " + getId().getOrderId() + "/" + getId().getProductId() +
                ": UnitPrice=" + getUnitPrice() + "\tQuantity=" + getQuantity();
    }
}
