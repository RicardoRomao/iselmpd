package dataMappersTests;

import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import trabalho.dataMappers.SupplierMapper;
import trabalho.domainObjects.Supplier;
import trabalho.unitOfWork.UnitOfWork;
import static org.junit.Assert.*;

public class SupplierMapperTest extends NorthwindTest {

    private SupplierMapper sMapper;

    @Before
    public void supplierMapperSetup() {
        sMapper = (SupplierMapper)
                UnitOfWork.getCurrent().getMapper(Supplier.class);
    }

    @After
    public void supplierMapperTearDown() {
        sMapper = null;
    }

    @Test
    public void canGetAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
        sMapper.loadAllInto(suppliers);
        assertEquals(29,suppliers.size());
    }

    @Test
    public void canGetById() {
        Supplier s = sMapper.getById(1);
        assertEquals(s.getCompanyName(),"Exotic Liquids");
    }

    @Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
        sMapper.loadAllInto(suppliers);
        for (Supplier s : suppliers)
            assertTrue(s == sMapper.getById(s.getId()));
    }

    @Test
    public void canInsertNewSupplier() {
        Supplier s = new Supplier("Erika Smith", "Weedz'R'Us", "Kingston",
                "Jamaica","(223)232232323");
        UnitOfWork.getCurrent().save();

        sMapper.removeFromIdentityMap(s.getId());
        Supplier s1 = sMapper.getById(s.getId());
        assertNotSame(s, s1);
        assertEquals(s.getId(), s1.getId());
        assertEquals(s.getCity(), s1.getCity());
        assertEquals(s.getCompanyName(), s1.getCompanyName());
        assertEquals(s.getContactName(), s1.getContactName());
        assertEquals(s.getCountry(), s1.getCountry());
        assertEquals(s.getPhone(), s1.getPhone());
    }

    @Test
    public void canUpdateSupplier() {
        Supplier s = sMapper.getById(30);
        s.setCompanyName("Shrooms'R'Us");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canDeleteSupplier() {
        Supplier s = sMapper.getById(30);
        if (s != null) s.remove();
        UnitOfWork.getCurrent().save();
    }

}