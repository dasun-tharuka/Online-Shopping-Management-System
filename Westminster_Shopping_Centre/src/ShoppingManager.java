public interface ShoppingManager {

    public abstract void addProduct(Product product);

    public abstract void deleteProduct(int n);

    public abstract void printListOfProducts(String category);

    public abstract void saveFile(String category);

    public abstract boolean consoleMenu();

}
