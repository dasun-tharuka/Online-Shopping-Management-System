import java.util.ArrayList;

public class ShoppingCart {

    public static ArrayList<Product> listOfProducts;

    public ShoppingCart() {
        listOfProducts = new ArrayList<>();
    }

    public void addProductCart(Product product) {
        listOfProducts.add(product);
        System.out.println("Product added");
    }

    public void removeProductCart(String productId) {
        listOfProducts.remove(productId);
    }

}
