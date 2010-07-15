package mappersTests;

import java.util.ArrayList;
import northwind.NorthwindTest;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import serie3.grupo1.dataMappers.CategoryMapper;
import serie3.grupo1.domainObjects.Category;
import serie3.grupo2.unitOfWork.UnitOfWork;

public class CategoryMapperTest extends NorthwindTest{

    private CategoryMapper cMapper;

    @Before
    public void catMapperSetup() {
        cMapper = (CategoryMapper)
                UnitOfWork.getCurrent().getMapper(Category.class);
    }

    @After
    public void catMapperTearDown() {
        cMapper = null;
    }

    @Test
    public void canGetAllCategories() {
        ArrayList<Category> cats = new ArrayList<Category>();
        cMapper.loadAllInto(cats);
        assertEquals(8,cats.size());
    }
    
    @Test
    public void canGetById() {
        Category c = cMapper.getById(8);
        assertEquals(c.getCategoryName(),"Seafood");
    }

    @Test
    public void multipleGetsAlwaysReturnTheSame() {
        ArrayList<Category> cats = new ArrayList<Category>();
        cMapper.loadAllInto(cats);
        for (Category c : cats)
            assertTrue(c == cMapper.getById(c.getId()));
    }

    @Test
    public void canInsertNewCategory() {
        Category c = new Category("Custom Category", "Unit testing...");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canUpdateCategory() {
        Category c = cMapper.getById(21);
        c.setDescription("New description...");
        UnitOfWork.getCurrent().save();
    }

    //@Test
    public void canDeleteCategory() {
        Category c = cMapper.getById(22);
        c.remove();
        UnitOfWork.getCurrent().save();
    }
}