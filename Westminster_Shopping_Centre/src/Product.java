public abstract class Product {

    // Variables Declaration
    protected String productId;
    protected String productName;
    protected int numOfAvailableItems;
    protected double price;
    protected String category;


    // Non-parametric constructor
    public Product() {

    }

    // Parameterized constructor
    public Product(String productId, String productName, int numOfAvailableItems, double price, String category) {
        this.productId = productId;
        this.productName = productName;
        this.numOfAvailableItems = numOfAvailableItems;
        this.price = price;
        this.category = category;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumOfAvailableItems() {
        return numOfAvailableItems;
    }

    public void setNumOfAvailableItems(int numOfAvailableItems) {
        this.numOfAvailableItems = numOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}