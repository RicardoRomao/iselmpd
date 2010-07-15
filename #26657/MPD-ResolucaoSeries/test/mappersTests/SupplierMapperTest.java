package mappersTests;

import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import serie3.grupo1.dataMappers.SupplierMapper;
import serie3.grupo1.domainObjects.Supplier;
import serie3.grupo2.unitOfWork.UnitOfWork;
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

    //@Test
    public void canInsertNewSupplier() {
        Supplier s = new Supplier("Erika Smith", "Weedz'R'Us", "Kingston",
                "Jamaica","(223)232232323");
        UnitOfWork.getCurrent().save();
    }

    //@Test
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