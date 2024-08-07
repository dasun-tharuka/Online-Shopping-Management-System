public class Clothing extends Product{

    // Variables Declaration
    protected String size;
    protected String colour;
    //protected final String category = "Clothing";

    // Non-parametric constructor
    public Clothing() {

    }

    // Parameterized constructor
    public Clothing(String size, String colour) {
        this.size = size;
        this.colour = colour;
    }

    // Parameterized constructor
    public Clothing(String productId, String productName, int numOfAvailableItems, double price, String category, String size, String colour) {
        super(productId, productName, numOfAvailableItems, price, category);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

}
