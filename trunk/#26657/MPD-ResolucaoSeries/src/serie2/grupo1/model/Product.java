package serie2.grupo1.model;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;

/**
 * A product with a price, brand, kind and description.
 */
public class Product{
	/**
	 * Create Product instances based on data from a file in the format: 
	 *   <brand>|<kind>|<description>|<price>  
	 */
	public static Product valueOf(String arg){
		String [] args = arg.split(";");
		double p = Double.parseDouble(args[3]);
		ProductKind k = ProductKind.valueOf(args[1]);
		return new Product(args[0], k, args[2], p);
	}
	/**
	 * Constructs a product.
	 * @param description the description
	 * @param price the price
	 */
	public Product(String brand, ProductKind kind, String description, double price){
		this.brand = brand;
		this.kind = kind;
		this.description = description;
		this.price = price;
	}
	//
	// Properties
	//
    @VisibleProperty(name = "Brand", kind=PropertyKind.Simple)
	public String getBrand(){return brand;}

    @VisibleProperty(name = "Brand", kind=PropertyKind.Simple)
    public void setBrand(String brand){
        System.out.println("Setting brand!");
        this.brand = brand;
    }

    @VisibleProperty(name = "Kind", kind=PropertyKind.Simple)
    public ProductKind getKind(){return kind;}

    @VisibleProperty(name = "Kind", kind=PropertyKind.Simple)
    public void setKind(ProductKind kind){this.kind = kind;}

    @VisibleProperty(name = "Description", kind=PropertyKind.Simple)
    public String getDescription() {return description;}

    @VisibleProperty(name = "Description", kind=PropertyKind.Simple)
    public void setDescription(String description) {this.description = description;}

    @VisibleProperty(name = "Price", kind=PropertyKind.Simple)
    public double getPrice() { return price;}

    @VisibleProperty(name = "Price", kind=PropertyKind.Simple)
    public void setPrice(double price) { this.price = price;}

	@Override
	public String toString() {
		return "[brand=" + brand + ", description=" + description
		+ ", kind=" + kind + ", price=" + price + "]";
	}
	//
	// Fields
	//
	private String brand;
	private ProductKind kind;
	private String description;
	private double price;
}
