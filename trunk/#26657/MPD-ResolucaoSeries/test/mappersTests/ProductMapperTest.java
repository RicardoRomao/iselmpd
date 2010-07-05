package mappersTests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.dataMappers.ProductMapper;
import serie3.grupo1.domainObjects.Product;

public class ProductMapperTest {

    //@Test
    public void canGetAll() {
        ArrayList<Product> products = new ArrayList<Product>();
        MapperRegistry.current().get(Product.class).loadAllInto(products);
        assertEquals(products.size(), 77);
    }

    @Test
    public void canGetById() {
        Product p = ((ProductMapper) MapperRegistry.current().get(Product.class)).
                getById(new Integer(10));
        assertEquals(p.getProductName(), "Ikura");

    }
}
