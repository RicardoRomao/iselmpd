package mappersTests;

import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import serie3.grupo1.dataMappers.ShipperMapper;
import serie3.grupo1.domainObjects.Shipper;
import serie3.grupo2.unitOfWork.UnitOfWork;
import static org.junit.Assert.*;

public class ShipperMapperTest extends NorthwindTest {

    private ShipperMapper sMapper;

    @Before
    public void shipperMapperSetup() {
        sMapper = (ShipperMapper)
                UnitOfWork.getCurrent().getMapper(Shipper.class);
    }

    @After
    public void shipperMapperTearDown() {
        sMapper = null;
    }

    @Test
    public void canGetAllShippers() {
        ArrayList<Shipper> shippers = new ArrayList<Shipper>();
        sMapper.loadAllInto(shippers);
        assertEquals(3,shippers.size());
    }

    @Test
    public void canGetById() {
        Shipper s = sMapper.getById(1);
        assertEquals(s.getCompanyName(),"Speedy Express");
    }

    @Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<Shipper> shippers = new ArrayList<Shipper>();
        sMapper.loadAllInto(shippers);
        for (Shipper s : shippers)
            assertTrue(s == sMapper.getById(s.getId()));
    }

    //@Test
    public void canInsertNewShipper() {
        Shipper s = new Shipper("CTT Portugal","(351)33432323");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canUpdateShipper() {
        Shipper s = sMapper.getById(6);
        s.setCompanyName("Transporta Lda.");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canDeleteShipper() {
        Shipper s = sMapper.getById(6);
        if (s != null) s.remove();
        UnitOfWork.getCurrent().save();
    }

}