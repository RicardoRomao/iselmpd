package trabalho.domainObjects;

import annotations.PropertyValidator;
import annotations.VisibleProperty;
import exceptions.DomainObjectValidatorException;
import trabalho.propertiesUtils.PropertyKind;

public class Customer extends DomainObject<String, Customer> {

    private String _city;
    private String _companyName;
    private String _contactName;
    private String _country;
    private String _phone;

    public Customer() { super(); }
    
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
    
    @VisibleProperty(name="Customer Id", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getCustomerId() { return getId(); }
    @VisibleProperty(name="Customer Id", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setCustomerId(String id) {
        markDirty();
        setId(id);
    }
    @PropertyValidator(name="Customer Id")
    public void customerIdLength(String id) throws DomainObjectValidatorException {
        if (id.length() == 0 || id.length() > 5)
            throw new DomainObjectValidatorException("Customer id must be no longer than 5 chars and must have at least 1.");
    }

    @VisibleProperty(name="City", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getCity() { return _city; }
    @VisibleProperty(name="City", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setCity(String city) {
        markDirty();
        this._city = city;
    }
    @PropertyValidator(name="City")
    public void cityLength(String city) throws DomainObjectValidatorException {
        if (city.length() > 15)
            throw new DomainObjectValidatorException("City must be no longer than 15 chars.");
    }

    @VisibleProperty(name="Company", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getCompanyName() { return _companyName; }
    @VisibleProperty(name="Company", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setCompanyName(String companyName) { 
        markDirty();
        this._companyName = companyName;
    }
    @PropertyValidator(name="Company")
    public void companyLength(String company) throws DomainObjectValidatorException {
        if (company.length() == 0 || company.length() > 40)
            throw new DomainObjectValidatorException("Customer must be no longer than 40 chars and must have at least 1.");
    }

    @VisibleProperty(name="Contact Name", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getContactName() { return _contactName; }
    @VisibleProperty(name="Contac Namet", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setContactName(String contactName) { 
        markDirty();
        this._contactName = contactName;
    }
    @PropertyValidator(name="Contact Name")
    public void contactLength(String contact) throws DomainObjectValidatorException {
        if (contact.length() > 30)
            throw new DomainObjectValidatorException("Contact name must be no longer than 30 chars.");
    }

    @VisibleProperty(name="Country", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getCountry() { return _country; }
    @VisibleProperty(name="Country", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setCountry(String country) { 
        markDirty();
        this._country = country;
    }
    @PropertyValidator(name="Country")
    public void countryLength(String country) throws DomainObjectValidatorException {
        if (country.length() > 15)
            throw new DomainObjectValidatorException("Country must be no longer than 15 chars.");
    }

    @VisibleProperty(name="Phone", kind = PropertyKind.Simple, isAutoGenerated=false)
    public String getPhone() { return _phone; }
    @VisibleProperty(name="Phone", kind = PropertyKind.Simple, isAutoGenerated=false)
    public void setPhone(String phone) { 
        markDirty();
        this._phone = phone;
    }
    @PropertyValidator(name="Phone")
    public void phoneLength(String phone) throws DomainObjectValidatorException {
        if (phone.length() > 24)
            throw new DomainObjectValidatorException("Phone must be no longer than 24 chars.");
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
