/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MySQL;

/**
 *
 * @author thari
 */
public class Item_stock_selector extends javax.swing.JDialog {

    Home hr;

    public String stock_id;
    public String product_id;
    public String category;
    public String brand;
    public String product_name;
    public String quantity;
    public String selling_price;
    public String mfd;
    public String exd;
    public double subtotal;
    public double total_discount;
    private static final int HEADER_HEIGHT = 27;

    public Item_stock_selector(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.hr = (Home) parent;
        initComponents();
        loader();

        jTable1.getTableHeader().setFont(new Font("Open Sans SemiBold", 0, 13));

        JTableHeader header = jTable1.getTableHeader();
        header.setPreferredSize(new Dimension(100, HEADER_HEIGHT));

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }

    DecimalFormat df = new DecimalFormat("0.00");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void loader() {
        String load = hr.product_set_id;
        if (load.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a product", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet rs = MySQL.search("SELECT DISTINCT `stock`.`id`,`product`.`id`,`category`.`name`,`brand`.`name`,`product`.`name`,`stock`.`quantity`,`grn_item`.`buying_price`,`stock`.`selling_price`,`stock`.`mfd`, `stock`.`exd` FROM `stock` INNER JOIN `grn_item` ON `grn_item`.`stock_id` = `stock`.`id` INNER JOIN `product` ON `stock`.`product_id` = `product`.`id` INNER JOIN `brand` ON `product`.`brand_id` = `brand`.`id` INNER JOIN category ON `product`.`category_id` = `category`.id WHERE `product`.`id` = '" + load + "' ORDER BY `product`.`name` ASC");
                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                dtm.setRowCount(0);

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("stock.id"));
                    v.add(rs.getString("product.id"));
                    v.add(rs.getString("category.name"));
                    v.add(rs.getString("brand.name"));
                    v.add(rs.getString("product.name"));
                    v.add(rs.getString("stock.quantity"));
                    v.add(rs.getString("stock.selling_price"));
                    v.add(rs.getString("stock.mfd"));
                    v.add(rs.getString("stock.exd"));
                    dtm.addRow(v);
                }

                jTable1.setModel(dtm);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void totalcal() {
        for (int i = 0; i < hr.jTable1.getRowCount(); i++) {
            Double amount = Double.parseDouble(hr.jTable1.getValueAt(i, 3).toString());

            subtotal += amount;

            hr.jLabel6.setText(df.format(subtotal).toString());

            double dis = Double.parseDouble(hr.selling_price.get(i).toString()) / 100 * Double.parseDouble(hr.discount.get(i).toString());

            total_discount += dis;

            hr.jLabel7.setText(df.format(total_discount));

            double total = subtotal - total_discount;

            hr.jLabel8.setText(df.format(total));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Stock Selector");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "Product ID", "Category", "Brand", "Product Name", "Quantity", "Selling Price", "MFD", "EXD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Select a product stock");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int selectedRow = jTable1.getSelectedRow();

        // Set to invoice
        if (evt.getClickCount() == 2) {
            int qt = Integer.parseInt(jTable1.getValueAt(selectedRow, 5).toString());

            if (qt <= 0) {
                JOptionPane.showMessageDialog(this, "This item is out of stock", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                stock_id = jTable1.getValueAt(selectedRow, 0).toString();
                product_id = jTable1.getValueAt(selectedRow, 1).toString();
                product_name = jTable1.getValueAt(selectedRow, 4).toString();
                category = jTable1.getValueAt(selectedRow, 3).toString();
                brand = jTable1.getValueAt(selectedRow, 2).toString();
                mfd = jTable1.getValueAt(selectedRow, 7).toString();
                selling_price = df.format(Double.parseDouble((String) jTable1.getValueAt(selectedRow, 6)));
                exd = jTable1.getValueAt(selectedRow, 8).toString();

                DefaultTableModel dtm = (DefaultTableModel) hr.jTable1.getModel();

                Double val = hr.set_qty * Double.parseDouble(selling_price);

                Vector v = new Vector();
                v.add(product_name);
                v.add(hr.set_qty);
                v.add(selling_price.toString());
                v.add(df.format(val));

                hr.product_id.add(product_id);
                hr.stock_id.add(stock_id);
                hr.product_name.add(product_name);
                hr.category.add(category);
                hr.brand.add(brand);
                hr.quantity.add(1);
                hr.mfd.add(mfd);
                hr.exd.add(exd);
                hr.selling_price.add(Double.parseDouble(selling_price));
                hr.discount.add(0);

                dtm.addRow(v);

                hr.jTable1.setModel(dtm);

                hr.jTextField1.setText("");

                totalcal();
                this.dispose();
            }

        }

    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightFlatIJTheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Item_stock_selector dialog = new Item_stock_selector(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
