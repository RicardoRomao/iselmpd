package trabalho.domainObjects;

import annotations.PropertyValidator;
import annotations.VisibleProperty;
import exceptions.DomainObjectValidatorException;
import trabalho.propertiesUtils.PropertyKind;

public class Category extends DomainObject<Integer, Category> {

    private String _categoryName;
    private String _description;

    public Category() { super(); }
    
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
    
    @VisibleProperty(name="Id", kind=PropertyKind.Simple, isAutoGenerated=true)
    public Integer getCategoryId() { return getId(); }
    
    @VisibleProperty(name = "Category Name", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getCategoryName() { return _categoryName; }
    @VisibleProperty(name = "Category Name", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setCategoryName(String categoryName) { 
        markDirty();
        this._categoryName = categoryName;
    }
    @PropertyValidator(name = "Category Name")
    public void categoryNameLength(String name) throws DomainObjectValidatorException {
        if (name.length() == 0 || name.length() > 15)
            throw new DomainObjectValidatorException("Category name should be no longer than 15 chars and must have at least 1.");
    }

    @VisibleProperty(name = "Description", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getDescription() { return _description; }
    @VisibleProperty(name = "Description", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setDescription(String description) {
        markDirty();
        this._description = description;
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
