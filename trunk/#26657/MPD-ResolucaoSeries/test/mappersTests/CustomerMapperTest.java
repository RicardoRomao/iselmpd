package mappersTests;

import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import serie3.grupo1.dataMappers.CustomerMapper;
import serie3.grupo1.domainObjects.Customer;
import serie3.grupo2.unitOfWork.UnitOfWork;

public class CustomerMapperTest extends NorthwindTest {

    private CustomerMapper cMapper;

    @Before
    public void custMapperSetup() {
        cMapper = (CustomerMapper)
                UnitOfWork.getCurrent().getMapper(Customer.class);
    }

    @After
    public void custMapperTearDown() {
        cMapper = null;
    }

    @Test
    public void canGetAllCustomers() {
        ArrayList<Customer> custs = new ArrayList<Customer>();
        cMapper.loadAllInto(custs);
        assertEquals(91,custs.size());
    }

    @Test
    public void canGetById() {
        Customer c = cMapper.getById("RICAR");
        assertEquals(c.getCompanyName(),"Ricardo Adocicados");
    }

    @Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<Customer> custs = new ArrayList<Customer>();
        cMapper.loadAllInto(custs);
        for (Customer c : custs)
            assertTrue(c == cMapper.getById(c.getId()));
    }

    //@Test
    public void canInsertNewCustomer() {
        Customer c = new Customer("ZZTOP", "Andy Summers",
                "Tunes Warehouse","Lisboa","Portugal","+351218805318");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canUpdateCustomer() {
        Customer c = cMapper.getById("ZZTOP");
        c.setContactName("Eric Clapton");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canDeleteCategory() {
        Customer c = cMapper.getById("ZZTOP");
        if (c != null) c.remove();
        UnitOfWork.getCurrent().save();
    }

}