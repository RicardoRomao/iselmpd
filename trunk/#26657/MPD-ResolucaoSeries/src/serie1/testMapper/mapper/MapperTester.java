package serie1.testMapper.mapper;

import serie1.testMapper.orders.Customer;
import serie1.testMapper.orders.Order;
import serie1.testMapper.orders.OrderDTO;

public class MapperTester {

    public static void main(String[] args) {
        Mapper<Order, OrderDTO> mapper =
                new Mapper<Order, OrderDTO>(Order.class, OrderDTO.class);
        Order order = new Order(new Customer("Outro Cliente com outro nome!"));
        OrderDTO orderDTO = mapper.map(order);
        System.out.println("Customer of new object is:" + orderDTO.getCustomerName());
    }
}
