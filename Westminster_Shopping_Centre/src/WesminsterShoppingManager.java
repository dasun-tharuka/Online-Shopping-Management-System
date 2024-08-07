import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner; // Import the Scanner class to read text files and read user inputs
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors

public class WesminsterShoppingManager implements ShoppingManager {

    public static ArrayList<Product> productList;
    private int numProducts;

    public WesminsterShoppingManager(int listLength) {
        numProducts = listLength;
        productList = new ArrayList<Product>(listLength);
    }

    // Scanner class Declaration
    Scanner userInput = new Scanner(System.in);

    @Override
    public void addProduct(Product product) {
        // Check if there are spaces and add the shape to the list
        if (productList.size() < numProducts) {
            productList.add(product);
        } else {
            System.out.println("No more space in the list");
        }
    }

    @Override
    public void deleteProduct(int n) {

        while (true) {
            System.out.print("\nConfirm the product removal process. [ y / n ] : ");
            String confirmation = userInput.nextLine();

            if (confirmation.equals("y")) {
                productList.remove(n);
                System.out.println("\n** Successfully removed the product from the system **");
                System.out.println("\n*** The total number of products left in the system : "+productList.size());
                break;

            } else if (confirmation.equals("n")) {
                System.out.println("\n** The product removal process has been stopped **");
                break;

            } else {
                System.out.println("\nNote : Please enter \"y\" for yes and \"n\" for no.");
            }
        }
    }

    @Override
    public void printListOfProducts(String category) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getCategory().equals(category)) {

                String text1 = "";
                String text2 = "";

                if (productList.get(i) instanceof Electronics electronics) {
                    text1 = "Product Brand: "+electronics.getBrand();
                    text2 = "Product Warranty period: "+electronics.getWarrantyPeriod();
                } else if (productList.get(i) instanceof Clothing clothing) {
                    text1 = "Clothing Size: "+clothing.getSize();
                    text2 = "Clothing colour: "+clothing.getColour();
                }

                arrayList.add("Product ID: "+productList.get(i).getProductId()
                        +"\nProduct Name: "+productList.get(i).getProductName()
                        +"\nProduct Category: "+productList.get(i).getCategory()
                        +"\nProduct Price (£): "+productList.get(i).getPrice()
                        +"\nNumber of Products Available: "+productList.get(i).getNumOfAvailableItems()
                        +"\n"+text1
                        +"\n"+text2);
            }
        }
        Collections.sort(arrayList);

        System.out.println("\n"+category+" :- ");
        if (arrayList.size() == 0) {
            System.out.println("-- No "+category+" products --");
        } else {
            for (String elements : arrayList) {
                System.out.println("\n" + elements);
            }
        }
    }

    @Override
    public void saveFile(String category) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getCategory().equals(category)) {

                String text1 = "";
                String text2 = "";

                if (productList.get(i) instanceof Electronics electronics) {
                    text1 = electronics.getBrand();
                    text2 = String.valueOf(electronics.getWarrantyPeriod());
                } else if (productList.get(i) instanceof Clothing clothing) {
                    text1 = clothing.getSize();
                    text2 = clothing.getColour();
                }

                arrayList.add(productList.get(i).getProductId()
                        +","+productList.get(i).getProductName()
                        +","+productList.get(i).getCategory()
                        +","+productList.get(i).getPrice()
                        +","+productList.get(i).getNumOfAvailableItems()
                        +","+text1
                        +","+text2);
            }
        }
        Collections.sort(arrayList);

        try {
            String fileName = "Saved_Product_Files/" + category + "_Product.txt";
            FileWriter fileWriter = new FileWriter(fileName);

            if (arrayList.size() == 0) {
                fileWriter.write("-- No "+category+" products --");
            } else {
                for (String elements : arrayList) {
                    fileWriter.write("\n" + elements + "\n");
                }
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("\n** An error occurred. / Product details are not saved. **");
            e.printStackTrace();
        }
    }

    public void readFile(String category) {

        try {
            String fileName = "Saved_Product_Files/" + category + "_Product.txt";
            File fileRead = new File(fileName);

            Scanner reader = new Scanner(fileRead);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                if (data.equals("")) {
                    continue;
                } else if (data.equals("-- No " + category + " products --")) {
                    break;
                }

                String[] productDetails = data.split(",");

                String productId = productDetails[0];
                String productName = productDetails[1];
                double price = Double.parseDouble(productDetails[3]);
                int numAvailableItems = Integer.parseInt(productDetails[4]);

                if (category.equals("Electronics")) {
                    String brand = productDetails[5];
                    int warrantyPeriod = Integer.parseInt(productDetails[6]);

                    Electronics electronics = new Electronics(productId, productName, numAvailableItems, price, category, brand, warrantyPeriod);
                    addProduct(electronics);

                } else if (category.equals("Clothing")) {
                    String size = productDetails[5];
                    String colour = productDetails[6];

                    Clothing clothing = new Clothing(productId, productName, numAvailableItems, price, category, size, colour);
                    addProduct(clothing);
                }
            }
            reader.close();

            System.out.println("\n** " + category +" Product details are loaded. **");

        } catch (FileNotFoundException e) {
            System.out.println("\n** " + category +" Product details not loaded. **");
        }
    }



    @Override
    public boolean consoleMenu() {
        boolean exit = false;

        System.out.println("\n------------------------------------------------------");
        System.out.println("\n1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of the products");
        System.out.println("4. Save in a file");
        System.out.println("5. Open the Graphical User Interface (GUI)");
        System.out.println("6. Exit");

        int choice1 = 0;
        while (true) {
            try {
                System.out.print("\nSelect the option from the menu: ");
                choice1 = userInput.nextInt();
                System.out.println("\n------------------------------------------------------");
                userInput.nextLine();
                break;

            } catch (Exception e) {
                System.out.println("\n------------------------------------------------------");
                System.out.println("\nNote : Please enter the relevant menu number. ");
                System.out.println("\n------------------------------------------------------");
                userInput.nextLine();
            }
        }

        switch (choice1) {

            // Add a new product
            case 1:
                System.out.println("\n** Add a new product **");

                // Declaration of variables
                int choice2;
                String category = "";

                while (true) {
                    System.out.println("\nPress 1 if you want to add a Electronics product");
                    System.out.println("Press 2 if you want to add a Clothing product");
                    System.out.println("Press 0 if you want to cancel the process");
                    System.out.print("\nSelect the product category: ");
                    choice2 = userInput.nextInt();
                    userInput.nextLine();

                    if (choice2 == 0) {
                        break;
                    } else if (choice2 == 1) {
                        category = "Electronics";
                        break;
                    } else if (choice2 == 2) {
                        category = "Clothing";
                        break;
                    } else {
                        System.out.println("\nNote : Please enter the relevant product category number.");
                    }
                }

                if (choice2 == 0) {
                    break;
                }

                System.out.println("\nCategory of the product: " + category);

                System.out.print("Enter the Product ID: ");
                String productId = userInput.nextLine();

                System.out.print("Enter the Product Name: ");
                String productName = userInput.nextLine();

                double price;
                while (true) {
                    try {
                        System.out.print("Enter the price of the product (£): ");
                        price = userInput.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("\nNote : Please enter a valid price value.\n");
                        userInput.nextLine();
                    }
                }

                int numAvailableItems;
                while (true) {
                    try {
                        System.out.print("Enter the number of items available: ");
                        numAvailableItems = userInput.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("\nNote : Please enter a valid number.\n");
                        userInput.nextLine();
                    }
                }

                userInput.nextLine();

                switch (choice2) {
                    // It is an Electronics product
                    case 1:
                        System.out.print("Enter the brand of the product: ");
                        String brand = userInput.nextLine();

                        int warrantyPeriod;
                        while (true) {
                            try {
                                System.out.print("Enter the warranty period of the product (months): ");
                                warrantyPeriod = userInput.nextInt();
                                break;
                            } catch (Exception e) {
                                System.out.println("\nNote : Please enter a valid number.\n");
                                userInput.nextLine();
                            }
                        }

                        Electronics electronics = new Electronics(productId, productName, numAvailableItems, price, category, brand, warrantyPeriod);
                        addProduct(electronics);
                        break;

                    // It is a Clothing product
                    case 2:
                        System.out.print("Enter the size of product: ");
                        String size = userInput.nextLine();

                        System.out.print("Enter the colour of the product: ");
                        String colour = userInput.nextLine();

                        Clothing clothing = new Clothing(productId, productName, numAvailableItems, price, category, size, colour);
                        addProduct(clothing);
                        break;
                }
                System.out.println("\n** Successfully added the product to the system **");
                break;

            // Delete a product
            case 2:
                System.out.println("\n** Delete a product **");

                System.out.print("\nEnter the product ID you want to remove : ");
                String id = userInput.nextLine();

                try {
                    for (int n = 0; n <= 50; n++) {
                        if (id.equals(productList.get(n).getProductId())) {

                            System.out.println("Product ID: " + productList.get(n).getProductId());
                            System.out.println("Product Name: " + productList.get(n).getProductName());
                            System.out.println("Product Category: " + productList.get(n).getCategory());
                            System.out.println("Product Price (£): " + productList.get(n).getPrice());
                            System.out.println("Number of Products Available: " + productList.get(n).getNumOfAvailableItems());

                            if (productList.get(n) instanceof Electronics electronics) {
                                System.out.println("Product Brand: " + electronics.getBrand());
                                System.out.println("Product Warranty period: " + electronics.getWarrantyPeriod());
                            } else if (productList.get(n) instanceof Clothing clothing) {
                                System.out.println("Clothing Size: " + clothing.getSize());
                                System.out.println("Clothing colour: " + clothing.getColour());
                            }

                            deleteProduct(n);

                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("\n** No such product exists. **");
                }
                break;

            // Display list of the products
            case 3:
                System.out.println("\n** Display list of the products **");

                printListOfProducts("Electronics");
                printListOfProducts("Clothing");

                break;

            // Save in a file
            case 4:
                saveFile("Electronics");
                saveFile("Clothing");

                System.out.println("\n** Product details are saved **");
                break;

            // Open the Graphical User Interface (GUI)
            case 5:
                String[][] tableData = new String[productList.size()][5];

                for (int i = 0; i < productList.size(); i++) {
                    tableData[i][0] = productList.get(i).getProductId();
                    tableData[i][1] = productList.get(i).getProductName();
                    tableData[i][2] = productList.get(i).getCategory();
                    tableData[i][3] = String.valueOf(productList.get(i).getPrice());
                    if(productList.get(i) instanceof Electronics electronics) {
                        tableData[i][4] = electronics.getBrand()+", "+electronics.getWarrantyPeriod()+" months"+", "+electronics.getNumOfAvailableItems()+" available";
                    } else if (productList.get(i) instanceof Clothing clothing) {
                        tableData[i][4] = clothing.getSize()+", "+clothing.getColour()+", "+clothing.getNumOfAvailableItems()+" available";
                    }
                }

                WestminsterShoppingCentreGUI gui = new WestminsterShoppingCentreGUI(tableData);

                System.out.println("\n** The GUI opened **");
                break;

            // Exit
            case 6:
                System.exit(0);

            default:
                System.out.println("\nNote : Please enter the relevant menu number.");
        }
        return exit;
    }




    public static void main(String[] args) {

        WesminsterShoppingManager wesminsterShoppingManager = new WesminsterShoppingManager(50);
        boolean exit = false;

        // Read all the information saved in the Text file
        wesminsterShoppingManager.readFile("Electronics");
        wesminsterShoppingManager.readFile("Clothing");

        while (!exit) {
            exit = wesminsterShoppingManager.consoleMenu();
        }
    }

}
