package dataMappersTests;

import java.sql.Date;
import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import trabalho.dataMappers.CustomerMapper;
import trabalho.dataMappers.OrderMapper;
import trabalho.dataMappers.ShipperMapper;
import trabalho.domainObjects.Customer;
import trabalho.domainObjects.Order;
import trabalho.domainObjects.Shipper;
import trabalho.unitOfWork.UnitOfWork;
import static org.junit.Assert.*;

public class OrderMapperTest extends NorthwindTest{

    private OrderMapper oMapper;
    private CustomerMapper cMapper;
    private ShipperMapper sMapper;

    @Before
    public void mappersSetup() {
        oMapper = (OrderMapper)
                UnitOfWork.getCurrent().getMapper(Order.class);
        cMapper = (CustomerMapper)
                UnitOfWork.getCurrent().getMapper(Customer.class);
        sMapper = (ShipperMapper)
                UnitOfWork.getCurrent().getMapper(Shipper.class);
    }

    @After
    public void mappersTearDown() {
        oMapper = null;
        cMapper = null;
        sMapper = null;
    }

    @Test
    public void canGetAllOrders() {
        ArrayList<Order> orders = new ArrayList<Order>();
        oMapper.loadAllInto(orders);
        assertEquals(830,orders.size());
    }

    @Test
    public void canGetById() {
        Order o = oMapper.getById(11074);
        assertEquals(o.getShipName(),"Simons bistro");
    }

    @Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<Order> orders = new ArrayList<Order>();
        oMapper.loadAllInto(orders);
        for (Order o : orders)
            assertTrue(o == oMapper.getById(o.getId()));
    }

    @Test
    public void canInsertNewOrder() {
        Shipper s1 = sMapper.getById(2);
        Customer c1 = cMapper.getById("BLONP");
        Date date = new Date(2010, 07, 16);
        Order o = new Order(c1, date, "Receivers Address", "Guadalajara", s1);

        UnitOfWork.getCurrent().save();

        oMapper.removeFromIdentityMap(o.getId());
        Order o1 = oMapper.getById(o.getId());
        assertNotSame(o, o1);
        assertSame(o.getCustomer(), o1.getCustomer());
        assertSame(o1.getCustomer(), c1);
        assertSame(o.getShipVia(), o1.getShipVia());
        assertSame(o1.getShipVia(), s1);
        assertEquals(o.getShipAddress(), o1.getShipAddress());
        assertEquals(o.getShipName(), o1.getShipName());
    }

    //@Test
    public void canInsertNewOrderWNewShipper() {
        Customer c1 = cMapper.getById("BERGS");
        Shipper s1 = new Shipper("Transatlantic", "(351)24324234");
        Date date = new Date(2010, 07, 16);
        Order o = new Order(c1, date, "Order inserted with clean Shipper","Somewhere", s1);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canInsertNewOrderWNewCustomer() {
        Customer c1 = new Customer("ZZTOP", "Andy Summers",
                "Tunes Warehouse","Lisboa","Portugal","+351218805318");
        Shipper s1 = sMapper.getById(3);
        Date date = new Date(2010, 07, 16);
        Order o = new Order(c1, date,"Order inserted with clean Customer","Bells Beach", s1);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canInsertNewOrderWNewCategoryAndSupplier() {
        Customer c1 = new Customer("ZZTOP", "Andy Summers",
                "Tunes Warehouse","Lisboa","Portugal","+351218805318");
        Shipper s1 = new Shipper("Transatlantic", "(351)24324234");
        Date date = new Date(2010, 07, 16);
        Order o = new Order(c1, date, "Order inserted with clean Customer","Bells Beach", s1);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canUpdateOrder() {
        Order o = oMapper.getById(11081);
        o.setShipAddress("Icelandik");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canDeleteOrder() {
        Order o = oMapper.getById(11081);
        if (o != null) o.remove();
        UnitOfWork.getCurrent().save();
    }
}