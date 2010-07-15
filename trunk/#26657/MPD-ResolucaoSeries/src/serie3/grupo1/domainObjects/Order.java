package serie3.grupo1.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;
import java.util.Date;
import java.util.Set;
import serie3.grupo1.dataMappers.filters.FilterByOrder;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.domainObjects.lazyLoaders.ILazySet;
import serie3.grupo1.domainObjects.lazyLoaders.IValueHolder;
import serie3.grupo1.domainObjects.lazyLoaders.LazySet;

public class Order extends DomainObject<Integer, Order> {

    private int _orderId;
    private IValueHolder<String,Customer> _customer;
    private Date _orderDate;
    private String _shipAddress;
    private String _shipName;
    private IValueHolder<Integer,Shipper> _shipVia;
    private ILazySet<PkOrderDetails,OrderDetails> _details;

    public Order(Integer id, IValueHolder<String,Customer> customer,
            Date date, String shipAddress, String shipName,
            IValueHolder<Integer,Shipper> shipVia,
            ILazySet<PkOrderDetails,OrderDetails> details) {
        super(id);
        bindConstructorValues(customer, date, shipAddress, shipName, shipVia, details);
        System.out.println("Order " + id + " loaded!");
    }

    public Order(IValueHolder<String,Customer> customer,
            Date date, String shipAddress, String shipName,
            IValueHolder<Integer,Shipper> shipVia) {
        super();
        bindConstructorValues(customer, date, shipAddress, shipName, shipVia,
            new LazySet(
                new FilterByOrder(getId()),
                (IDataMapper<PkOrderDetails, OrderDetails>) MapperRegistry.current().get(OrderDetails.class)
            )
        );
    }

    private void bindConstructorValues (IValueHolder<String,Customer> customer,
            Date date, String shipAddress, String shipName,
            IValueHolder<Integer,Shipper> shipVia,
            ILazySet<PkOrderDetails,OrderDetails> details) {
        _customer = customer;
        _orderDate = date;
        _shipAddress = shipAddress;
        _shipName = shipName;
        _shipVia = shipVia;
        _details = details;
    }

    @VisibleProperty(name="Customer", kind = PropertyKind.Complex)
    public Customer getCustomer() { return _customer.get(); }
    public void setCustomer(IValueHolder<String, Customer> customer) {
        markDirty();
        this._customer = customer;
    }

    @VisibleProperty(name="Date", kind = PropertyKind.Simple)
    public Date getOrderDate() { return _orderDate; }
    public void setOrderDate(Date orderDate) { 
        markDirty();
        this._orderDate = orderDate;
    }

    @VisibleProperty(name="Order ID", kind = PropertyKind.Simple)
    public int getOrderId() { return _orderId; }
    public void setOrderId(int orderId) { 
        markDirty();
        this._orderId = orderId;
    }

    @VisibleProperty(name="Ship Address", kind = PropertyKind.Simple)
    public String getShipAddress() { return _shipAddress; }
    public void setShipAddress(String shipAddress) {
        markDirty();
        this._shipAddress = shipAddress;
    }

    @VisibleProperty(name="Ship Name", kind = PropertyKind.Simple)
    public String getShipName() { return _shipName; }
    public void setShipName(String shipName) { 
        markDirty();
        this._shipName = shipName;
    }

    @VisibleProperty(name="Ship Via", kind = PropertyKind.Complex)
    public Shipper getShipVia() { return _shipVia.get(); }
    public void setShipVia(IValueHolder<Integer, Shipper> shipVia) {
        markDirty();
        this._shipVia = shipVia;
    }

    @VisibleProperty(name="Order Details", kind = PropertyKind.List)
    public Set<OrderDetails> getDetails() { return _details.getImmutableSet(); }

    @Override
    protected IDataMapper<Integer, Order> mapper() {
        return MapperRegistry.current().get(this.getClass());
    }

    @Override
    protected Order self() {
        return this;
    }

    @Override
    public String toString() {
        return "Order " + getId() + " - "
                + getOrderDate() + "\n\t"
                + getShipAddress() + "\n\t"
                + getShipName();
    }
}
