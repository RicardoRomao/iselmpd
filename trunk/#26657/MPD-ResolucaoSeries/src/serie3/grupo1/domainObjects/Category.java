package serie3.grupo1.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.dataMappers.registry.MapperRegistry;

public class Category extends DomainObject<Integer, Category> {

    private String _categoryName;
    private String _description;

    public Category(Integer id, String name, String description){
        super(id);
        this._categoryName = name;
        this._description = description;
        System.out.println("Category " + id + " loaded!");
    }

    public Category(String name, String description){
        super();
        this._categoryName = name;
        this._description = description;
    }
    
    @VisibleProperty(name="Id", kind=PropertyKind.Simple)
    public int getCategoryId() { return getId(); }
    
    @VisibleProperty(name = "Category Name", kind = PropertyKind.Simple)
    public String getCategoryName() { return _categoryName; }
    public void setCategoryName(String categoryName) { 
        markDirty();
        this._categoryName = categoryName;
    }

    @VisibleProperty(name = "Description", kind = PropertyKind.Simple)
    public String getDescription() { return _description; }
    public void setDescription(String description) {
        markDirty();
        this._description = description;
    }

    @Override
    public IDataMapper<Integer, Category> mapper() {
        return MapperRegistry.current().get(this.getClass());
    }

    @Override
    protected Category self() {
        return this;
    }

    @Override
    public String toString() {
        return "Category " + getId() + " - "
                + getCategoryName() + "\n\t"
                + getDescription();
    }
}
