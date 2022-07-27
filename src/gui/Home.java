/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import static gui.System_login.userId;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import model.MySQL;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author thari
 */
public class Home extends javax.swing.JFrame {

    private static int HEADER_HEIGHT = 35;
    public String product_set_id;
    public int set_qty = 1;
    public int productrow;
    public int pay_id = 0;
    public Vector stock_id = new Vector();
    public Vector product_id = new Vector();
    public Vector product_name = new Vector();
    public Vector category = new Vector();
    public Vector brand = new Vector();
    public Vector quantity = new Vector();
    public Vector mfd = new Vector();
    public Vector exd = new Vector();
    public Vector<Double> selling_price = new Vector();
    public Vector discount = new Vector();
    public String payment;
    public String payment_type;
    public double balance;
    public int count;

    public Home() {
        initComponents();
        load_components();
        jPopupMenu1.add(jPanel5);
        jPopupMenu1.setFocusable(false);
        button_disable();
    }

    private int search_state = 1;

    private void load_components() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);

        setExtendedState(MAXIMIZED_BOTH);

        jTable1.getTableHeader().setFont(new Font("Open Sans SemiBold", 0, 14));
        model.JTableUtilities.setCellsAlignment(jTable1, (int) LEFT_ALIGNMENT);

        JTableHeader header = jTable1.getTableHeader();
        header.setPreferredSize(new Dimension(100, HEADER_HEIGHT));

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        jLabel9.setBorder(new MatteBorder(0, 0, 1, 0, Color.lightGray));

        jButton5.setVerticalTextPosition(JButton.BOTTOM);
        jButton5.setHorizontalTextPosition(JButton.CENTER);

        jButton4.setVerticalTextPosition(JButton.BOTTOM);
        jButton4.setHorizontalTextPosition(JButton.CENTER);

        jButton6.setVerticalTextPosition(JButton.BOTTOM);
        jButton6.setHorizontalTextPosition(JButton.CENTER);

        jButton7.setVerticalTextPosition(JButton.BOTTOM);
        jButton7.setHorizontalTextPosition(JButton.CENTER);

        jButton8.setVerticalTextPosition(JButton.BOTTOM);
        jButton8.setHorizontalTextPosition(JButton.CENTER);

        jButton9.setVerticalTextPosition(JButton.BOTTOM);
        jButton9.setHorizontalTextPosition(JButton.CENTER);

        jButton10.setVerticalTextPosition(JButton.BOTTOM);
        jButton10.setHorizontalTextPosition(JButton.CENTER);

        jButton13.setVerticalTextPosition(JButton.BOTTOM);
        jButton13.setHorizontalTextPosition(JButton.CENTER);

        jButton14.setVerticalTextPosition(JButton.BOTTOM);
        jButton14.setHorizontalTextPosition(JButton.CENTER);

        jButton15.setVerticalTextPosition(JButton.BOTTOM);
        jButton15.setHorizontalTextPosition(JButton.CENTER);

        jButton16.setVerticalTextPosition(JButton.BOTTOM);
        jButton16.setHorizontalTextPosition(JButton.CENTER);

        jButton17.setVerticalTextPosition(JButton.BOTTOM);
        jButton17.setHorizontalTextPosition(JButton.CENTER);

        jButton11.setVerticalTextPosition(JButton.BOTTOM);
        jButton11.setHorizontalTextPosition(JButton.CENTER);
    }

    private void button_disable() {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `user` WHERE `id` = '" + userId + "'");
            rs.next();

            String v = rs.getString("user_type_id");

            if (v.equals("1")) {
                jButton11.setEnabled(true);
            } else if (v.equals("2")) {
                jButton11.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void load_status() {
        switch (search_state) {
            case 1:
                if (jTextField1.getText().equals("")) {
                    jTextField1.setText("Search product by name");
                    jTextField1.setForeground(new Color(153, 153, 153));
                }
            case 2:
                if (jTextField1.getText().equals("")) {
                    jTextField1.setText("Search product by barcode");
                    jTextField1.setForeground(new Color(153, 153, 153));
                }
            case 3:
                if (jTextField1.getText().equals("")) {
                    jTextField1.setText("Search product by product id");
                    jTextField1.setForeground(new Color(153, 153, 153));
                }
        }
    }

    public void payment() {

        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please add products", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (payment_type.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select payment method", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Pattern.compile("(0)|([1-9][0-9]*|(([1-9][0-9]*)[.]([0]*[1-9][0-9]*))|([0][.]([0]*[1-9][0-9]*)))").matcher(payment).matches()) {
            JOptionPane.showMessageDialog(this, "Invalid payment", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

//            Invoice insert
            long mTime = System.currentTimeMillis();
            String unique_id = mTime + "-" + userId;
            String cid = jLabel3.getText();

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            String dNow = sdf2.format(new Date());
            String dnow2 = sdf3.format(new Date());

            MySQL.iud("INSERT INTO `invoice`(`customer_id`,`date_time`,`user_id`,`unique_id`) VALUES ('1','" + dNow + "','" + userId + "','" + unique_id + "')");
            //Invoive_insert

            try {

//                Invoice payment insert
                ResultSet rs = MySQL.search("SELECT * FROM `invoice` WHERE `unique_id`='" + unique_id + "'");
                rs.next();
                String id = rs.getString("id");

                MySQL.iud("INSERT INTO `invoice_payment` (`invoice_id`,`payment_type_id`,`payment`,`balance`) VALUES ('" + id + "','" + pay_id + "','" + payment + "','" + balance + "')");
                //Invoice_payment_insert

                //Invoice Item INSERT and Stock UPDATE
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    product_id.get(i);
                    int sid = Integer.parseInt(stock_id.get(i).toString());
                    product_name.get(i);
                    category.get(i);
                    brand.get(i);
                    int qty = Integer.parseInt(quantity.get(i).toString());
                    mfd.get(i);
                    exd.get(i);
                    selling_price.get(i);
                    discount.get(i);

                    ResultSet rs3 = MySQL.search("SELECT * FROM `stock` WHERE `stock`.`id`='" + sid + "'");
                    rs3.next();

                    String availableQty = rs3.getString("quantity");

                    int updatedQty = Integer.parseInt(availableQty) - qty;

                    MySQL.iud("UPDATE `stock` SET `quantity`='" + updatedQty + "' WHERE `id`='" + sid + "'");

                    MySQL.iud("INSERT INTO `invoice_item`(`qty`,`invoice_id`,`stock_id`,`discount`) VALUES('" + qty + "','" + id + "','" + sid + "','" + discount.get(i) + "')");

//                    MySQL.iud("UPDATE `grn_item` SET `quantity` = '" + qty + "' WHERE `stock_id` = '" + sid + "'");
                }

                count += 1;

                int jb = count;

                jButton16.setText(String.valueOf(jb));

                InputStream filePath = getClass().getResourceAsStream("/reports/invoice_generate.jrxml");
                JasperReport jr = JasperCompileManager.compileReport(filePath);

                HashMap parameters = new HashMap();

                parameters.put("Parameter1", jLabel6.getText());
                parameters.put("Parameter2", jLabel7.getText());
                parameters.put("Parameter3", jLabel8.getText());
                parameters.put("Parameter4", unique_id);
                parameters.put("Parameter5", dnow2);

                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                JRTableModelDataSource dataSource = new JRTableModelDataSource(dtm);

                JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dataSource);
                JasperViewer.viewReport(jp, false);

                pay_id = 0;

                jButton8.setBackground(new Color(203, 229, 255));
                jButton9.setBackground(new Color(203, 229, 255));
                jButton10.setBackground(new Color(203, 229, 255));

                jLabel6.setText("0.00");
                jLabel7.setText("0.00");
                jLabel8.setText("0.00");

                JOptionPane.showMessageDialog(this, "New Invoice created", "Success", JOptionPane.INFORMATION_MESSAGE);

                dtm.setRowCount(0);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        jScrollPane2.setBorder(null);

        list.setBackground(new java.awt.Color(255, 255, 255));
        list.setFont(new java.awt.Font("Open Sans Semibold", 0, 14)); // NOI18N
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(list);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NeoX - POS");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity", "Price", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(550);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_price_tag_20px.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_barcode_25px.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_numbers_25px.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("Search product by name");
        jTextField1.setBorder(null);
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_20px.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel2.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel2.setText("Discount");

        jLabel3.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel3.setText("Subtotal");

        jLabel4.setFont(new java.awt.Font("Open Sans Semibold", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

        jLabel5.setFont(new java.awt.Font("Open Sans Semibold", 0, 14)); // NOI18N
        jLabel5.setText("TOTAL");

        jLabel6.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("0.00");

        jLabel7.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("0.00");

        jLabel8.setFont(new java.awt.Font("Open Sans Semibold", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_35px.png"))); // NOI18N
        jButton4.setText("Search");

        jButton5.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_delete_35px.png"))); // NOI18N
        jButton5.setText("Delete");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_plus_35px.png"))); // NOI18N
        jButton6.setText("New Sale");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_new_ticket_35px.png"))); // NOI18N
        jButton7.setText("Quantity");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(203, 229, 255));
        jButton8.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton8.setText("Debit Card");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(203, 229, 255));
        jButton9.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton9.setText("Cash");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(203, 229, 255));
        jButton10.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton10.setText("Credit Card");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_card_payment_40px.png"))); // NOI18N
        jButton12.setText("   Payment");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_refund_35px.png"))); // NOI18N
        jButton13.setText("Refund");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_neo_cryptocurrency_150px.png"))); // NOI18N

        jButton14.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_discount_35px.png"))); // NOI18N
        jButton14.setText("Discount");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jButton15.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_logout_rounded_left_35px.png"))); // NOI18N
        jButton15.setText("Logout");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_user_35px.png"))); // NOI18N
        jButton16.setText("Count");

        jButton17.setFont(new java.awt.Font("Open Sans Semibold", 0, 13)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_remove_35px.png"))); // NOI18N
        jButton17.setText("Clear");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_more_35px.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        if (jTextField1.getText().equals("Search product by name")) {
            jTextField1.setText("");
            jTextField1.setForeground(new Color(153, 153, 153));
        } else if (jTextField1.getText().equals("Search product by barcode")) {
            jTextField1.setText("");
            jTextField1.setForeground(new Color(153, 153, 153));
        } else if (jTextField1.getText().equals("Search product by product id")) {
            jTextField1.setText("");
            jTextField1.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost

        load_status();
    }//GEN-LAST:event_jTextField1FocusLost

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int r = jTable1.getSelectedRow();

        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(r);

            product_id.remove(r);
            stock_id.remove(r);
            product_name.remove(r);
            category.remove(r);
            brand.remove(r);
            quantity.remove(r);
            mfd.remove(r);
            exd.remove(r);
            selling_price.remove(r);
            discount.remove(r);

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        int r = jTable1.getSelectedRow();

        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Please select a item first", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            Discount_add da = new Discount_add(this, true);
            da.setVisible(true);

        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        userId = 0;

        System_login sl = new System_login();
        sl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextField1.setText("Search product by barcode");
        search_state = 2;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextField1.setText("Search product by product id");
        search_state = 3;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setText("Search product by name");
        search_state = 1;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String search = jTextField1.getText().trim();
        if (search_state == 1) {

            if (!search.equals("")) {

                try {
                    ResultSet rs = MySQL.search("SELECT * FROM `product` WHERE `name` LIKE '%" + search + "%' ");

                    DefaultListModel ls = new DefaultListModel();
                    while (rs.next()) {
                        ls.addElement(rs.getString("name"));

                    }

                    list.setModel(ls);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                jPopupMenu1.show(jTextField1, 0, jTextField1.getHeight());

            }
        } else if (search_state == 3) {

            if (!search.equals("")) {

                try {
                    ResultSet rs = MySQL.search("SELECT * FROM `product` WHERE `id` LIKE '%" + search + "%' ");

                    DefaultListModel ls = new DefaultListModel();
                    while (rs.next()) {
                        ls.addElement(rs.getString("name"));
                    }

                    list.setModel(ls);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                jPopupMenu1.show(jTextField1, 0, jTextField1.getHeight());

            }

        } else if (search_state == 2) {
            if (!search.equals("")) {

                try {
                    ResultSet rs = MySQL.search("SELECT * FROM `product` WHERE `barcode` LIKE '%" + search + "%' ");

                    DefaultListModel ls = new DefaultListModel();
                    while (rs.next()) {
                        ls.addElement(rs.getString("name"));
                    }

                    list.setModel(ls);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                jPopupMenu1.show(jTextField1, 0, jTextField1.getHeight());

            }
        }


    }//GEN-LAST:event_jTextField1KeyReleased

    private void listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listMouseClicked
        String selected_item = list.getSelectedValue();

        if (evt.getClickCount() == 2) {
            try {
                ResultSet add_item = MySQL.search("SELECT * FROM `product` WHERE `name` = '" + selected_item + "'");
                add_item.next();

                product_set_id = add_item.getString("id");

                Item_stock_selector iss = new Item_stock_selector(this, true);
                iss.setVisible(true);

            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_listMouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        manage_panel mp = new manage_panel();
        mp.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() == 1) {
            productrow = jTable1.getSelectedRow();

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        int r = jTable1.getSelectedRow();

        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Please select a item first", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            Quantity_changer qc = new Quantity_changer(this, true);
            qc.setVisible(true);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int r = jTable1.getRowCount();
        if (r == 0) {
            JOptionPane.showMessageDialog(this, "Please add product to make a payment", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (pay_id == 0) {
            JOptionPane.showMessageDialog(this, "Select Payment Method", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Payment_popup pp = new Payment_popup(this, true);
            pp.setVisible(true);
        }

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        pay_id = 1;
        jButton9.setBackground(new Color(104, 253, 184));
        jButton10.setBackground(new Color(203, 229, 255));
        jButton8.setBackground(new Color(203, 229, 255));
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        pay_id = 3;
        jButton10.setBackground(new Color(104, 253, 184));
        jButton9.setBackground(new Color(203, 229, 255));
        jButton8.setBackground(new Color(203, 229, 255));

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        pay_id = 2;
        jButton8.setBackground(new Color(104, 253, 184));
        jButton9.setBackground(new Color(203, 229, 255));
        jButton10.setBackground(new Color(203, 229, 255));
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        product_id.removeAllElements();
        stock_id.removeAllElements();
        product_name.removeAllElements();
        category.removeAllElements();
        brand.removeAllElements();
        quantity.removeAllElements();
        mfd.removeAllElements();
        exd.removeAllElements();
        selling_price.removeAllElements();
        discount.removeAllElements();

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        jLabel6.setText("0.00");
        jLabel7.setText("0.00");
        jLabel8.setText("0.00");

        jTextField1.setText("");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        product_id.removeAllElements();
        stock_id.removeAllElements();
        product_name.removeAllElements();
        category.removeAllElements();
        brand.removeAllElements();
        quantity.removeAllElements();
        mfd.removeAllElements();
        exd.removeAllElements();
        selling_price.removeAllElements();
        discount.removeAllElements();

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        jTextField1.setText("");

        jButton16.setText("Count");

        pay_id = 0;

        jButton8.setBackground(new Color(203, 229, 255));
        jButton9.setBackground(new Color(203, 229, 255));
        jButton10.setBackground(new Color(203, 229, 255));

        jLabel6.setText("0.00");
        jLabel7.setText("0.00");
        jLabel8.setText("0.00");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        Refund_panel rp = new Refund_panel(this, true);
        rp.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightFlatIJTheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTable1;
    public javax.swing.JTextField jTextField1;
    private javax.swing.JList<String> list;
    // End of variables declaration//GEN-END:variables
}
