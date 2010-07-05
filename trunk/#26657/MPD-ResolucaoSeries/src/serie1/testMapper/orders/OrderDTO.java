package serie1.testMapper.orders;

/**
 * @author  mcarvalho
 */
public class OrderDTO {

    String customerName;
    double total;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
