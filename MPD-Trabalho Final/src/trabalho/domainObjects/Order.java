package trabalho.domainObjects;

import java.util.Calendar;
import java.util.Set;
import annotations.VisibleProperty;
import trabalho.dataMappers.filters.FilterByOrder;
import trabalho.domainObjects.primaryKeys.PkOrderDetails;
import trabalho.domainObjects.lazyLoaders.*;
import trabalho.propertiesUtils.PropertyKind;

public class Order extends DomainObject<Integer, Order> {

    private int _orderId;
    private IValueHolder<String,Customer> _customer;
    private Calendar _orderDate;
    private String _shipAddress;
    private String _shipName;
    private IValueHolder<Integer,Shipper> _shipVia;
    private ILazySet<PkOrderDetails,OrderDetails> _details;

    public Order(Integer id, IValueHolder<String,Customer> customer,
            Calendar date, String shipAddress, String shipName,
            IValueHolder<Integer,Shipper> shipVia,
            ILazySet<PkOrderDetails,OrderDetails> details) {
        super(id);
        bindConstructorValues(date, shipAddress, shipName);
        _details = details;
        _customer = customer;
        _shipVia = shipVia;
        System.out.println("Order " + id + " loaded!");
    }

    public Order(Customer customer, Calendar date, String shipAddress,
            String shipName, Shipper shipVia) {
        super();
        bindConstructorValues(date, shipAddress, shipName);
        _details = new LazySet<PkOrderDetails,OrderDetails>();
        if (customer != null && customer.hasId()) {
            _customer = new ValueHolder<String, Customer>(customer.getId(), customer.mapper());
        } else {
            _customer = new ValueHolder<String,Customer>(customer);
        }
        if (shipVia != null && shipVia.hasId()) {
            _shipVia = new ValueHolder<Integer,Shipper>(shipVia.getId(), shipVia.mapper());
        } else {
            _shipVia = new ValueHolder<Integer,Shipper>(shipVia);
        }
    }

    private void bindConstructorValues (Calendar date, String shipAddress,
            String shipName) {
        _orderDate = date;
        _shipAddress = shipAddress;
        _shipName = shipName;
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
        _details = new LazySet<PkOrderDetails,OrderDetails>(
                new FilterByOrder(id),OrderDetails.class);
    }

    @VisibleProperty(name="Customer", kind = PropertyKind.Complex)
    public Customer getCustomer() { return _customer.get(); }
    public void setCustomer(IValueHolder<String, Customer> customer) {
        markDirty();
        this._customer = customer;
    }

    @VisibleProperty(name="Date", kind = PropertyKind.Simple)
    public Calendar getOrderDate() { return _orderDate; }
    public void setOrderDate(Calendar orderDate) {
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
