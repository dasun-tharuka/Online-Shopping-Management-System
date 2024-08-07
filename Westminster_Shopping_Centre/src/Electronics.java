public class Electronics extends Product{

    // Variables Declaration
    protected String brand;
    protected int warrantyPeriod;
    //protected final String category = "Electronics";

    // Non-parametric constructor
    public Electronics() {

    }

    // Parameterized constructor
    public Electronics(String brand, int warrantyPeriod) {
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Parameterized constructor
    public Electronics(String productId, String productName, int numOfAvailableItems, double price, String category, String brand, int warrantyPeriod) {
        super(productId, productName, numOfAvailableItems, price, category);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

}
