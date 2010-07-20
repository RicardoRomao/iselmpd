package trabalho.domainObjects;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;

public class Supplier extends DomainObject<Integer,Supplier>{

    private String _contactName;
    private String _companyName;
    private String _city;
    private String _country;
    private String _phone;

    public Supplier(Integer id, String contact, String companyName,
            String city, String country, String phone){
        super(id);
        bindConstructorValues(companyName, contact, city, country, phone);
        System.out.println("Supplier " + id + " loaded!");
    }

    public Supplier(String contact, String companyName, String city,
            String country, String phone){
        super();
        bindConstructorValues(companyName, contact, city, country, phone);
    }

    private void bindConstructorValues(String companyName, String contact,
            String city, String country, String phone){
        _companyName = companyName;
        _contactName = contact;
        _city = city;
        _country = country;
        _phone = phone;
    }

    @VisibleProperty(name = "Company", kind = PropertyKind.Simple)
    public String getCompanyName() { return _companyName; }
    public void setCompanyName(String companyName) {
        markDirty();
        this._companyName = companyName;
    }

    @VisibleProperty(name = "Contact Name", kind = PropertyKind.Simple)
    public String getContactName() { return _contactName; }
    public void setContactName(String contactName) {
        markDirty();
        this._contactName = contactName;
    }

    @VisibleProperty(name = "City", kind = PropertyKind.Simple)
    public String getCity() { return _city; }
    public void setCity(String city) { 
        markDirty();
        this._city = city;
    }

    @VisibleProperty(name = "Country", kind = PropertyKind.Simple)
    public String getCountry() { return _country; }
    public void setCountry(String country) { 
        markDirty();
        this._country = country;
    }

    @VisibleProperty(name = "Phone", kind = PropertyKind.Simple)
    public String getPhone() { return _phone; }
    public void setPhone(String phone) { 
        markDirty();
        this._phone = phone;
    }

    @Override
    protected Supplier self() {
        return this;
    }
}
