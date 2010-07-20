package dataMappersTests;

import java.util.ArrayList;
import java.util.Collection;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import trabalho.dataMappers.CategoryMapper;
import trabalho.dataMappers.ProductMapper;
import trabalho.dataMappers.SupplierMapper;
import trabalho.domainObjects.Category;
import trabalho.domainObjects.OrderDetails;
import trabalho.domainObjects.Product;
import trabalho.domainObjects.Supplier;
import trabalho.unitOfWork.UnitOfWork;
import static org.junit.Assert.*;

public class ProductMapperTest extends NorthwindTest {

    private ProductMapper pMapper;
    private CategoryMapper cMapper;
    private SupplierMapper sMapper;

    @Before
    public void mappersSetup() {
        pMapper = (ProductMapper)
                UnitOfWork.getCurrent().getMapper(Product.class);
        cMapper = (CategoryMapper)
                UnitOfWork.getCurrent().getMapper(Category.class);
        sMapper = (SupplierMapper)
                UnitOfWork.getCurrent().getMapper(Supplier.class);
    }

    @After
    public void mappersTearDown() {
        pMapper = null;
        cMapper = null;
        sMapper = null;
    }

    @Test
    public void canGetAllProducts() {
        ArrayList<Product> products = new ArrayList<Product>();
        pMapper.loadAllInto(products);
        assertEquals(77,products.size());
    }

    @Test
    public void canGetById() {
        Product c = pMapper.getById(10);
        assertEquals(c.getProductName(),"Ikura");
    }

    @Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<Product> products = new ArrayList<Product>();
        pMapper.loadAllInto(products);
        for (Product p : products)
            assertTrue(p == pMapper.getById(p.getId()));
    }

    @Test
    public void canInsertNewProduct() {
        Supplier s1 = sMapper.getById(1);
        Category c1 = cMapper.getById(1);
        Product p = new Product("New Product",s1,c1,2.3f,69);
        assertTrue(p.getOrderDetails().size() == 0);
        UnitOfWork.getCurrent().save();

        pMapper.removeFromIdentityMap(p.getId());
        Product p1 = pMapper.getById(p.getId());
        assertNotSame(p, p1);
        assertSame(p.getSupplier(), p1.getSupplier());
        assertSame(p1.getSupplier(), s1);
        assertSame(p.getCategory(), p1.getCategory());
        assertSame(p1.getCategory(), c1);
        assertEquals(p.getProductName(), p1.getProductName());
        assertEquals(p.getUnitPrice(), p1.getUnitPrice(),0.1f);
        assertEquals(p.getUnitsInStock(), p1.getUnitsInStock());
    }

    //@Test
    public void canInsertNewProductWNewSupplier() {
        Category c1 = cMapper.getById(1);
        Supplier s1 = new Supplier("Peter Tosh", "Weedz'R'Us", "Kingston","Jamaica", "(351)231233");
        Product p = new Product("New Product",s1,c1,2.3f,69);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canInsertNewProductWNewCategory() {
        Category c1 = new Category("New Category","Category Inserted with App");
        Supplier s1 = sMapper.getById(3);
        Product p = new Product("New Product",s1,c1,2.3f,69);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canInsertNewProductWNewCategoryAndSupplier() {
        Category c1 = new Category("Earth Tobacco","From the purest leaves");
        Supplier s1 = new Supplier("Peter Tosh", "Weedz'R'Us", "Kingston","Jamaica", "(351)231233");
        Product p = new Product("Natural Distresser",s1,c1,2.3f,69);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canUpdateProduct() {
        Product p = pMapper.getById(89);
        p.setUnitsInStock(4096);
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canDeleteProduct() {
        Product p = pMapper.getById(89);
        if (p != null) p.remove();
        UnitOfWork.getCurrent().save();
    }

}