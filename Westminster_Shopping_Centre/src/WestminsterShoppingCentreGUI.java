import java.util.Arrays;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class WestminsterShoppingCentreGUI extends JFrame {

    private JButton shoppingCartButton, addToShoppingCartButton;
    private JComboBox<String> categoryComboBox;
    private JScrollPane tableScroll;
    private JTable productsTable;
    private DefaultTableModel productsModel;
    private String[][] tableData;
    private ShoppingCart shoppingCart;

    private JTextField productIdField, productCategoryField, productNameField, productBrandField, warrantyPeriodField,
            productSizeField, colourField, itemAvailableField, priceField;

    public WestminsterShoppingCentreGUI(String[][] tableData) {
        this.tableData = tableData;

        setTitle("Westminster Shopping Centre");
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(800, 620));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create and Add shopping cart Button Panel to Content Pane
        getContentPane().add(shoppingCartButtonPanel(), BorderLayout.NORTH);

        // Create and Add components
        add(productCategoryPanel()); // Product category ComboBox
        add(productTablePanel()); // Product Table
        add(productDetailsPanel()); // Selected Product Details display
        add(addToShoppingCartButtonPanel()); // Add to Shopping Cart Button

        pack(); // Adjusting the window to fit the preferred size and layouts of its subcomponents
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Shopping Cart Button Panel
    private JPanel shoppingCartButtonPanel() {
        JPanel shoppingCartButtonPanel = new JPanel();
        shoppingCartButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> {
            ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart);
            //shoppingCartGUI.updateShoppingCartTable();
            //shoppingCartGUI.updateCheckoutDetails();
            //shoppingCartGUI.setVisible(true);
        });
        shoppingCartButtonPanel.add(shoppingCartButton);

        return shoppingCartButtonPanel;
    }

    // Product Category Panel
    private JPanel productCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.add(new JLabel("Select Product Category"));

        String[] category = {"All", "Electronics", "Clothing"};
        categoryComboBox = new JComboBox<>(category);
        categoryComboBox.addActionListener(this::updateProductTable);

        categoryPanel.add(categoryComboBox);

        return categoryPanel;
    }

    // Update the product table in each selectedCategory
    private void updateProductTable(ActionEvent e) {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String[][] tableDataSelected;

        if ("All".equals(selectedCategory)) {
            tableDataSelected = tableData;
        } else {
            tableDataSelected = new String[tableData.length][5];
            int j = 0;
            for (String[] row : tableData) {
                if (row[2].equals(selectedCategory)) {
                    tableDataSelected[j++] = row;
                }
            }
            tableDataSelected = Arrays.copyOf(tableDataSelected, j);
        }
        productsModel.setDataVector(tableDataSelected, new String[]{"Product ID", "Name", "Category", "Price(£)", "Info"});
    }

    // Product Table Panel
    private JPanel productTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        String[] column = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        productsModel = new DefaultTableModel(tableData, column);
        productsTable = new JTable(productsModel);

        // Set the row height to a desired value (e.g., 30 pixels)
        productsTable.setRowHeight(30);

        // Set custom cell renderer
        setCustomCellRenderer();

        tableScroll = new JScrollPane(productsTable);

        // Calculate the preferred height for 5 rows plus the table header
        int rowHeight = productsTable.getRowHeight();
        int headerHeight = productsTable.getTableHeader().getPreferredSize().height;
        int preferredHeight = (rowHeight * productsModel.getRowCount()) + headerHeight;

        // Adjust the preferred size to make the table smaller
        tableScroll.setPreferredSize(new Dimension(600, preferredHeight));

        // Add an empty border to create space around the table
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a ListSelectionListener to handle row selection changes
        productsTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                // Get the selected row index
                int selectedRow = productsTable.getSelectedRow();
                // Check if a row is actually selected
                if (selectedRow != -1) {
                    // Convert view row index to model row index
                    int modelRow = productsTable.convertRowIndexToModel(selectedRow);
                    // Get the value at the first column of the selected row
                    String productID = (String) productsTable.getModel().getValueAt(modelRow, 0);

                    updateProductDetails(productID);
                }
            }
        });

        tablePanel.add(tableScroll, BorderLayout.CENTER);

        return tablePanel;
    }

    // Changing table row color if available items < 3
    private void setCustomCellRenderer() {
        productsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String info = (String) table.getModel().getValueAt(row, 4);
                int availableItems = Integer.parseInt(info.split(", ")[2].split(" ")[0]);
                if (availableItems < 3) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
    }

    // Update the product details when the client/user select an item in the table
    private void updateProductDetails(String productID) {
        for (String[] row : tableData) {
            if (row[0].equals(productID)) {
                productIdField.setText(row[0]);
                productCategoryField.setText(row[2]);
                productNameField.setText(row[1]);
                itemAvailableField.setText(row[4].split(", ")[2].split(" ")[0]);
                priceField.setText(row[3]);
                if ("Electronics".equals(row[2])) {
                    productBrandField.setText(row[4].split(", ")[0]);
                    warrantyPeriodField.setText(row[4].split(", ")[1]);
                    productBrandField.setVisible(true);
                    warrantyPeriodField.setVisible(true);
                    productSizeField.setVisible(false);
                    colourField.setVisible(false);
                } else if ("Clothing".equals(row[2])) {
                    productSizeField.setText(row[4].split(", ")[0]);
                    colourField.setText(row[4].split(", ")[1]);
                    productSizeField.setVisible(true);
                    colourField.setVisible(true);
                    productBrandField.setVisible(false);
                    warrantyPeriodField.setVisible(false);
                }
                break;
            }
        }
    }

    // Product Details Table Panel
    private JPanel productDetailsPanel() {
        JPanel detailsPanelMain = new JPanel();
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(9,2,5,5)); // rows = 6 , column = 2

        //Creates text fields for each category
        detailsPanel.add(new JLabel("Product ID: "));
        productIdField = new JTextField(10);
        productIdField.setEditable(false);
        detailsPanel.add(productIdField);

        detailsPanel.add(new JLabel("Product Category: "));
        productCategoryField = new JTextField(20);
        productCategoryField.setEditable(false);
        detailsPanel.add(productCategoryField);

        detailsPanel.add(new JLabel("Product Name: "));
        productNameField = new JTextField(20);
        productNameField.setEditable(false);
        detailsPanel.add(productNameField);

        productBrandField = new JTextField(20);
        productBrandField.setEditable(false);
        detailsPanel.add(new JLabel("Brand: "));
        detailsPanel.add(productBrandField);

        warrantyPeriodField = new JTextField(20);
        warrantyPeriodField.setEditable(false);
        detailsPanel.add(new JLabel("Warranty Period: "));
        detailsPanel.add(warrantyPeriodField);

        productSizeField = new JTextField(10);
        productSizeField.setEditable(false);
        detailsPanel.add(new JLabel("Size: "));
        detailsPanel.add(productSizeField);

        colourField = new JTextField(10);
        colourField.setEditable(false);
        detailsPanel.add(new JLabel("Colour: "));
        detailsPanel.add(colourField);

        detailsPanel.add(new JLabel("Items Available: "));
        itemAvailableField = new JTextField(10);
        itemAvailableField.setEditable(false);
        detailsPanel.add(itemAvailableField);

        detailsPanel.add(new JLabel("Price (£): "));
        priceField = new JTextField(20);
        priceField.setEditable(false);
        detailsPanel.add(priceField);

        // Add an empty border to create space around the panel
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailsPanelMain.add(detailsPanel);

        // Add a border factory to create titled border around the panel
        detailsPanelMain.setBorder(BorderFactory.createTitledBorder("Selected Product - Details"));

        return detailsPanelMain;
    }

    // Add to shopping cart button Panel
    private JPanel addToShoppingCartButtonPanel() {
        JPanel addToShoppingCartPanel = new JPanel();
        // Centering the FlowLayout
        addToShoppingCartPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addToShoppingCartButton = new JButton("Add To Shopping Cart");
        //addToShoppingCartButton.addActionListener(e -> addProductToCart());
        addToShoppingCartPanel.add(addToShoppingCartButton);

        return addToShoppingCartPanel;
    }




//    private void addProductToCart() {
//        int selectedRow = productsTable.getSelectedRow();
//        // Check if a row is actually selected
//        if (selectedRow != -1) {
//            // Convert view row index to model row index
//            int modelRow = productsTable.convertRowIndexToModel(selectedRow);
//            // Get the value at the first column of the selected row
//            String productID = (String) productsTable.getModel().getValueAt(modelRow, 0);
//
//            System.out.println(modelRow);
//
//            Product selectedProduct = WesminsterShoppingManager.productList.get(modelRow);
//
//            if (selectedProduct != null) {
//                // Add the selected product to the shopping cart
//                shoppingCart.addProduct(selectedProduct);
//
//                // Update the availability of the product
//                selectedProduct.setNumOfAvailableItems(selectedProduct.getNumOfAvailableItems() - 1);
//
//                // Update the table to reflect the new availability
//                productsModel.fireTableDataChanged();
//
//                // Show a confirmation message
//                JOptionPane.showMessageDialog(this, "Product added to cart successfully!");
//            } else {
//                // Show an error message if no product is selected
//                JOptionPane.showMessageDialog(this, "Please select a product to add to the cart.");
//            }
//        }
//    }


}
