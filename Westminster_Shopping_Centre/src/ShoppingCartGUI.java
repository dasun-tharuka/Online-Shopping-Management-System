import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingCartGUI extends JFrame {

    private DefaultTableModel cartTableModel;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        setTitle("Shopping Cart");
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(600, 550));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Cart Table Panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        String[] cartColumns = {"Product", "Quantity", "Price"};
        cartTableModel = new DefaultTableModel(cartColumns, 0);
        JTable cartTable = new JTable(cartTableModel);
        cartTable.setPreferredScrollableViewportSize(new Dimension(600, 150));
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.add(cartPanel);
        getContentPane().add(wrapperPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

//    public void updateShoppingCartTable() {
//        cartTableModel.setRowCount(0);
//        for (Product product : ShoppingCart.products) {
//            cartTableModel.addRow(new Object[]{product.getProductName(), 1, product.getPrice()});
//        }
//    }


}
