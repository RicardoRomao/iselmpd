package serie1.testMapper.orders;

/**
 * @author  mcarvalho
 */
public class OrderLineItem {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //-----------------------      FIELDS         -----------------------
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * @uml.property name="product"
     * @uml.associationEnd aggregation="shared"
     */
    private Product product;
    private int quantity;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //-----------------------    CONSTRUCTOR      -----------------------
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public OrderLineItem(Product product, int quantity) {
        super();
        this.product = product;
        this.quantity = quantity;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //-----------------------      METHODS        -----------------------
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public double getTotal() {
        return quantity * product.getPrice();
    }
}
