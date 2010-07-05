package serie1.testMapper.orders;

import java.util.LinkedList;
import java.util.List;

/**
 * @author  mcarvalho
 */
public class Order {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //-----------------------      FIELDS         -----------------------
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * @uml.associationEnd  multiplicity="(0 -1)" aggregation="shared" inverse="order:testmapper.orders.OrderLineItem"
     */
    List<OrderLineItem> orderLineItems = new LinkedList<OrderLineItem>();
    /**
     * @uml.property  name="customer"
     * @uml.associationEnd aggregation="shared"
     */
    Customer customer;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //-----------------------    CONSTRUCTOR      -----------------------
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Order(Customer customer) {
        super();
        this.customer = customer;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //-----------------------      METHODS        -----------------------
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * @return
     * @uml.property  name="orderLineItems"
     */
    public OrderLineItem[] getOrderLineItems() {
        return orderLineItems.toArray(new OrderLineItem[orderLineItems.size()]);
    }

    public void addOrderLineItem(Product product, int quantity) {
        orderLineItems.add(new OrderLineItem(product, quantity));
    }

    public double getTotal() {
        return 0;
//        return Iterables.Sum(orderLineItems, new Func<OrderLineItem, Double>() {
//
//            public Double Invoke(OrderLineItem arg) {
//                return arg.getTotal();
//            }
//        });
    }

    public String getCustomerName() {
        return customer.getName();
    }
}
