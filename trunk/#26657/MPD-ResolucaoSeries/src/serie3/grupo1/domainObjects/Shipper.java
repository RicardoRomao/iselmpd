package serie3.grupo1.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.dataMappers.registry.MapperRegistry;

public class Shipper extends DomainObject<Integer, Shipper>{

    private String _companyName;
    private String _phone;

    public Shipper(Integer id, String name, String phone){
        super(id);
        _companyName = name;
        _phone = phone;
        System.out.println("Shipper " + id + " loaded!");
    }

    public Shipper(String name, String phone){
        super();
        _companyName = name;
        _phone = phone;
    }

    @VisibleProperty(name = "Company", kind = PropertyKind.Simple)
    public String getCompanyName() { return _companyName; }
    public void setCompanyName(String companyName) {
        markDirty();
        this._companyName = companyName;
    }

    @VisibleProperty(name = "Phone", kind = PropertyKind.Simple)
    public String getPhone() { return _phone; }
    public void setPhone(String phone) { 
        markDirty();
        this._phone = phone;
    }

    @Override
    protected IDataMapper<Integer, Shipper> mapper() {
        return MapperRegistry.current().get(this.getClass());
    }

    @Override
    protected Shipper self() {
        return this;
    }

    @Override
    public String toString(){
        return "Shipper " + getId() + " - " + getCompanyName();
    }
}
