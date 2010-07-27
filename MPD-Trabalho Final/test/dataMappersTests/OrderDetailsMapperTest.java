package dataMappersTests;

import java.sql.Date;
import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import trabalho.dataMappers.*;
import trabalho.domainObjects.*;
import trabalho.domainObjects.primaryKeys.PkOrderDetails;
import trabalho.unitOfWork.UnitOfWork;
import static org.junit.Assert.*;

public class OrderDetailsMapperTest extends NorthwindTest {

    private OrderDetailsMapper odMapper;
    private OrderMapper oMapper;
    private ProductMapper pMapper;
    private CustomerMapper cMapper;
    private ShipperMapper sMapper;
    private CategoryMapper catMapper;
    private SupplierMapper supMapper;

    @Before
    public void mappersSetup() {
        odMapper = (OrderDetailsMapper)
                UnitOfWork.getCurrent().getMapper(OrderDetails.class);
        oMapper = (OrderMapper)
                UnitOfWork.getCurrent().getMapper(Order.class);
        pMapper = (ProductMapper)
                UnitOfWork.getCurrent().getMapper(Product.class);
        cMapper = (CustomerMapper)
                UnitOfWork.getCurrent().getMapper(Customer.class);
        sMapper = (ShipperMapper)
                UnitOfWork.getCurrent().getMapper(Shipper.class);
        catMapper = (CategoryMapper)
                UnitOfWork.getCurrent().getMapper(Category.class);
        supMapper = (SupplierMapper)
                UnitOfWork.getCurrent().getMapper(Supplier.class);
    }

    @After
    public void mappersTearDown() {
        odMapper = null;
        oMapper = null;
        pMapper = null;
        cMapper = null;
        catMapper = null;
        sMapper = null;
    }

    //@Test
    public void canGetAllOrdersDetails() {
        ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
        odMapper.loadAllInto(orderDetails);
        assertEquals(830,orderDetails.size());
    }

    //@Test
    public void canGetById() {
        OrderDetails od = odMapper.getById(new PkOrderDetails(10250, 41));
        assertEquals(od.getQuantity(),new Double(10));
    }

    //@Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
        odMapper.loadAllInto(orderDetails);
        for (OrderDetails od : orderDetails)
            assertTrue(od == odMapper.getById(od.getId()));
    }

    //@Test
    public void canInsertNewOrderDetails() {
        Order o1 = oMapper.getById(10248);
        Product p1 = pMapper.getById(3);
        OrderDetails od = new OrderDetails (o1, p1, 12.3f, 10, 0.3f);
        UnitOfWork.getCurrent().save();

        odMapper.removeFromIdentityMap(od.getId());
        OrderDetails od1 = odMapper.getById(od.getId());
        assertNotSame(od, od1);
        assertSame(od.getOrder(), od1.getOrder());
        assertSame(od1.getOrder(), o1);
        assertSame(od.getProduct(), od1.getProduct());
        assertSame(od1.getProduct(), p1);
        assertEquals(od.getDiscount(), od1.getDiscount(), 0.1f);
        assertEquals(od.getQuantity(), od1.getQuantity());
    }

    //@Test
    public void canInsertNewOrderDetailsWNewOrder() {
        Customer c1 = cMapper.getById("BERGS");
        Shipper s1 = sMapper.getById(3);
        Date date = new Date(2010, 07, 16);
        Order o1 = new Order(c1, date, "New order to test order details","Somewhere", s1);
        Product p1 = pMapper.getById(3);
        //assertTrue(o1.getDetails().size() == 0);
        OrderDetails od = new OrderDetails (o1, p1, 12.3f, 10, 0.3f);
        UnitOfWork.getCurrent().save();

        //assertTrue(o1.getDetails().size() == 1);
    }

    //@Test
    public void canInsertNewOrderDetailsWNewProduct() {
        Category cat1 = catMapper.getById(1);
        Supplier sup1 = supMapper.getById(1);
        Product p1 = new Product("Some product",sup1, cat1, 12.3f, 69);
        assertTrue(p1.getOrderDetails().size() == 0);
        Order o1 = oMapper.getById(10248);
        OrderDetails od = new OrderDetails (o1, p1, 12.3f, 10, 0.3f);
        UnitOfWork.getCurrent().save();

        assertTrue(p1.getOrderDetails().size() == 1);
    }

    @Test
    public void canInsertNewOrderDetailsWNewOrderAndProduct() {
        //Creation of new Order
        Customer c1 = cMapper.getById("BERGS");
        Shipper s1 = sMapper.getById(3);
        Date date = new Date(2010, 07, 16);
        Order o1 = new Order(c1, date, "Test new order details with new order and product","Somewhere", s1);
        assertTrue(o1.getDetails().size() == 0);

        //Creation of new Product
        Category cat1 = catMapper.getById(1);
        Supplier sup1 = supMapper.getById(1);
        Product p1 = new Product("Some product",sup1, cat1, 12.3f, 69);
        assertTrue(p1.getOrderDetails().size() == 0);

        //Creation of new OrderDetails
        OrderDetails od = new OrderDetails (o1, p1, 78.99f, 100, 0.9f);

        UnitOfWork.getCurrent().save();

        OrderDetails od1 = odMapper.getById(od.getId());
        assertTrue(o1.getDetails().size() == 1);
        assertTrue(p1.getOrderDetails().size() == 1);
        assertSame(od.getProduct(),p1);
        assertSame(od.getOrder(),o1);
        assertEquals(od.getQuantity(), od1.getQuantity());
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