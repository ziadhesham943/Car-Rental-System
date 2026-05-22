package oop.project;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CarManagmentPage extends javax.swing.JFrame {

    public CarManagmentPage() {
        this(null);
    }

    public CarManagmentPage(MainMenu menuParent) {
        initComponents();
        refreshTableFromCatalog();
        if (menuParent != null) {
            final MainMenu opener = menuParent;
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    opener.setVisible(true);
                }
            });
        }
    }

    public void refreshTableFromCatalog() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Car c : CarData.CARS) {
            model.addRow(new Object[]{
                c.getName(),
                String.valueOf(c.getPrice()),
                c.getColor(),
                String.valueOf(c.getYear()),
                String.valueOf(c.getQuantity())
            });
        }
    }

    private void initComponents() {

        Add_Car = new javax.swing.JButton();
        Edit_Car = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Add_Car.setBackground(new java.awt.Color(51, 0, 204));
        Add_Car.setForeground(new java.awt.Color(255, 255, 255));
        Add_Car.setText("Add Car");
        Add_Car.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_CarActionPerformed(evt);
            }
        });

        Edit_Car.setBackground(new java.awt.Color(51, 0, 204));
        Edit_Car.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Car.setText("Edit Car");
        Edit_Car.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Edit_CarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Car Name", "Price", "Color", "Year", "Quantity"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(Add_Car, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Edit_Car, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Edit_Car, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                                        .addComponent(Add_Car, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void Add_CarActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
        AddCarPage addPage = new AddCarPage(this);
        addPage.setVisible(true);
    }

    private void Edit_CarActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= CarData.CARS.size()) {
            JOptionPane.showMessageDialog(this, "Please select a car from the table first!");
            return;
        }
        Car car = CarData.CARS.get(selectedRow);
        String newName = JOptionPane.showInputDialog(this, "Edit Car Name:", car.getName());
        if (newName != null && !newName.trim().isEmpty()) {
            car.setName(newName.trim());
            refreshTableFromCatalog();
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CarManagmentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CarManagmentPage().setVisible(true);
        });
    }


    private javax.swing.JButton Add_Car;
    private javax.swing.JButton Edit_Car;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

}