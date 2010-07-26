package trabalho.domainObjects;

import java.util.Set;
import annotations.VisibleProperty;
import java.util.Calendar;
import java.util.Date;
import trabalho.dataMappers.filters.FilterByOrder;
import trabalho.domainObjects.primaryKeys.PkOrderDetails;
import trabalho.domainObjects.lazyLoaders.*;
import trabalho.propertiesUtils.PropertyKind;

public class Order extends DomainObject<Integer, Order> {

    private IValueHolder<String,Customer> _customer;
    private Date _orderDate;
    private String _shipAddress;
    private String _shipName;
    private IValueHolder<Integer,Shipper> _shipVia;
    private ILazySet<PkOrderDetails,OrderDetails> _details;

    public Order() { super(); }

    public Order(Integer id, IValueHolder<String,Customer> customer,
            Date date, String shipAddress, String shipName,
            IValueHolder<Integer,Shipper> shipVia,
            ILazySet<PkOrderDetails,OrderDetails> details) {
        super(id);
        bindConstructorValues(date, shipAddress, shipName);
        _details = details;
        _customer = customer;
        _shipVia = shipVia;
        System.out.println("Order " + id + " loaded!");
    }

    public Order(Customer customer, Date date, String shipAddress,
            String shipName, Shipper shipVia) {
        super();
        bindConstructorValues(date, shipAddress, shipName);
        _details = new LazySet<PkOrderDetails,OrderDetails>();
        setCustomer(customer);
        setShipVia(shipVia);
        
    }

    private void bindConstructorValues (Date date, String shipAddress,
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

    @VisibleProperty(name="Customer", kind = PropertyKind.Complex, isAutoGenerated=false)
    public Customer getCustomer() { return _customer.get(); }
    @VisibleProperty(name="Customer", kind = PropertyKind.Complex, isAutoGenerated=false)
    public void setCustomer(Customer customer) {
        markDirty();
        if (customer != null && customer.hasId()) {
            _customer = new ValueHolder<String, Customer>(customer.getId(), customer.mapper());
        } else {
            _customer = new ValueHolder<String,Customer>(customer);
        }
    }

    @VisibleProperty(name="Date", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getOrderDate() {
        return _orderDate.toString();
    }
    @VisibleProperty(name="Date", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setOrderDate(String orderDate) {
        markDirty();
        System.out.println("Order.class - About to parse " + orderDate);
        _orderDate = new Date(Date.parse(orderDate));
    }

    @VisibleProperty(name="Order ID", kind = PropertyKind.Simple, isAutoGenerated=true)
    public Integer getOrderId() { return getId(); }
    @VisibleProperty(name="Order ID", kind = PropertyKind.Simple, isAutoGenerated=true)
    public void setOrderId(int orderId) { 
        markDirty();
        setId(orderId);
    }

    @VisibleProperty(name="Ship Address", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getShipAddress() { return _shipAddress; }
    @VisibleProperty(name="Ship Address", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setShipAddress(String shipAddress) {
        markDirty();
        _shipAddress = shipAddress;
    }

    @VisibleProperty(name="Ship Name", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getShipName() { return _shipName; }
    @VisibleProperty(name="Ship Name", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setShipName(String shipName) { 
        markDirty();
        _shipName = shipName;
    }

    @VisibleProperty(name="Ship Via", kind = PropertyKind.Complex, isAutoGenerated=false)
    public Shipper getShipVia() { return _shipVia.get(); }
    @VisibleProperty(name="Ship Via", kind = PropertyKind.Complex, isAutoGenerated=false)
    public void setShipVia(Shipper shipVia) {
        markDirty();
        if (shipVia != null && shipVia.hasId()) {
            _shipVia = new ValueHolder<Integer,Shipper>(shipVia.getId(), shipVia.mapper());
        } else {
            _shipVia = new ValueHolder<Integer,Shipper>(shipVia);
        }
    }

    @VisibleProperty(name="Order Details", kind = PropertyKind.List, isAutoGenerated=false)
    public Set<OrderDetails> getDetails() { return _details.getImmutableSet(); }

    @Override
    protected Order self() {
        return this;
    }

    @Override
    public String toString() {
        return "Order " + getId() + " - "
                + getOrderDate() + " - "
                + getShipAddress() + " - "
                + getShipName();
    }
}