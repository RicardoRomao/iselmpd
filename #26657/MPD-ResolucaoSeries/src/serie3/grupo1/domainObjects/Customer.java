package serie3.grupo1.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import serie3.grupo1.dataMappers.IDataMapper;
import serie3.grupo1.dataMappers.registry.MapperRegistry;

public class Customer extends DomainObject<String, Customer> {

    private String _city;
    private String _companyName;
    private String _contactName;
    private String _country;
    private String _phone;

    public Customer(String id, String contactName, String company, String city,
            String country, String phone, boolean isNew) {
        super(id, isNew);
        bindConstructorValues(contactName, company, city, country, phone);

        System.out.println("Customer " + id + " loaded!");
    }

    public Customer(String id, String contactName, String company, String city,
            String country, String phone) {
        super(id, true);
        bindConstructorValues(contactName, company, city, country, phone);
    }

    private void bindConstructorValues(String contactName, String company,
            String city, String country, String phone) {
        _contactName = contactName;
        _companyName = company;
        _city=city;
        _country = country;
        _phone = phone;
    }
    
    @VisibleProperty(name="City", kind = PropertyKind.Simple)
    public String getCity() { return _city; }
    public void setCity(String city) { 
        markDirty();
        this._city = city;
    }

    @VisibleProperty(name="Company", kind = PropertyKind.Simple)
    public String getCompanyName() { return _companyName; }
    public void setCompanyName(String companyName) { 
        markDirty();
        this._companyName = companyName;
    }

    @VisibleProperty(name="Contact", kind = PropertyKind.Simple)
    public String getContactName() { return _contactName; }
    public void setContactName(String contactName) { 
        markDirty();
        this._contactName = contactName;
    }

    @VisibleProperty(name="Country", kind = PropertyKind.Simple)
    public String getCountry() { return _country; }
    public void setCountry(String country) { 
        markDirty();
        this._country = country;
    }

    @VisibleProperty(name="Phone", kind = PropertyKind.Simple)
    public String getPhone() { return _phone; }
    public void setPhone(String phone) { 
        markDirty();
        this._phone = phone;
    }

    @Override
    protected IDataMapper<String, Customer> mapper() {
        return MapperRegistry.current().get(this.getClass());
    }

    @Override
    protected Customer self() {
        return this;
    }
    
    @Override
    public String toString(){
        return "Customer ID:" + getId() + "\tName:" + getContactName() +
                "\tCountry:" + getCountry();
    }
}
