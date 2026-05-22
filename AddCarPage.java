package oop.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddCarPage extends JFrame {

    private JTextField carNameField;
    private JTextField carYearField;
    private JTextField carColorField;
    private JTextField carQuantityField;
    private JTextField carPriceField;

    public AddCarPage() {
        this(null);
    }

    public AddCarPage(CarManagmentPage returnToCarPage) {
        setTitle("Add New Car");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(returnToCarPage != null ? returnToCarPage : null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 248, 255)); 

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel titleLabel = new JLabel("Add New Car");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 80, 160));
        titleLabel.setBounds(180, 15, 200, 35);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(40, 60, 420, 10);
        separator.setForeground(new Color(30, 80, 160));
        add(separator);

        JLabel lblName = new JLabel("Car Name :");
        lblName.setFont(labelFont);
        lblName.setBounds(40, 90, 120, 30);
        add(lblName);

        carNameField = new JTextField();
        carNameField.setFont(fieldFont);
        carNameField.setBounds(170, 90, 290, 30);
        setupPlaceholder(carNameField, "Enter car name (e.g., BMW 320i)");
        add(carNameField);

        JLabel lblYear = new JLabel("Car Year :");
        lblYear.setFont(labelFont);
        lblYear.setBounds(40, 140, 120, 30);
        add(lblYear);

        carYearField = new JTextField();
        carYearField.setFont(fieldFont);
        carYearField.setBounds(170, 140, 290, 30);
        setupPlaceholder(carYearField, "Enter manufacturing year (e.g., 2024)");
        add(carYearField);

        JLabel lblColor = new JLabel("Car Color :");
        lblColor.setFont(labelFont);
        lblColor.setBounds(40, 190, 120, 30);
        add(lblColor);

        carColorField = new JTextField();
        carColorField.setFont(fieldFont);
        carColorField.setBounds(170, 190, 290, 30);
        setupPlaceholder(carColorField, "Enter car color");
        add(carColorField);

        JLabel lblQuantity = new JLabel("Car Quantity :");
        lblQuantity.setFont(labelFont);
        lblQuantity.setBounds(40, 240, 120, 30);
        add(lblQuantity);

        carQuantityField = new JTextField();
        carQuantityField.setFont(fieldFont);
        carQuantityField.setBounds(170, 240, 290, 30);
        setupPlaceholder(carQuantityField, "Enter available quantity");
        add(carQuantityField);

        JLabel lblPrice = new JLabel("Car Rent Price :");
        lblPrice.setFont(labelFont);
        lblPrice.setBounds(40, 290, 120, 30);
        add(lblPrice);

        carPriceField = new JTextField();
        carPriceField.setFont(fieldFont);
        carPriceField.setBounds(170, 290, 290, 30);
        setupPlaceholder(carPriceField, "Enter rent price per day (e.g., 350.0)");
        add(carPriceField);

        JButton addBtn = new JButton("Add Car");
        addBtn.setBackground(new Color(92, 184, 92)); 
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addBtn.setBounds(180, 360, 160, 45); 
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(addBtn);

        addBtn.addActionListener(e -> {
            String name = carNameField.getText().trim();
            String color = carColorField.getText().trim();
            String yearStr = carYearField.getText().trim();
            String quantityStr = carQuantityField.getText().trim();
            String priceStr = carPriceField.getText().trim();

            if (name.isEmpty() || name.startsWith("Enter") ||
                color.isEmpty() || color.startsWith("Enter") ||
                yearStr.isEmpty() || yearStr.startsWith("Enter") ||
                quantityStr.isEmpty() || quantityStr.startsWith("Enter") ||
                priceStr.isEmpty() || priceStr.startsWith("Enter")) {
                
                JOptionPane.showMessageDialog(this, "Please fill in all fields correctly.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int year = Integer.parseInt(yearStr);
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                if (year < 1900 || quantity < 0 || price < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid positive numbers for Year, Quantity, and Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String msg = "Car Added Successfully!\n\n"
                           + "Name     : " + name + "\n"
                           + "Year     : " + year + "\n"
                           + "Color    : " + color + "\n"
                           + "Quantity : " + quantity + "\n"
                           + "Price/Day: " + price + " EGP";
                           
                CarData.CARS.add(new Car(name, year, color, price, quantity));
                JOptionPane.showMessageDialog(this, msg, "Success \u2714", JOptionPane.INFORMATION_MESSAGE);

                resetFields();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Year, Quantity, and Price must be NUMBERS only!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        if (returnToCarPage != null) {
            final CarManagmentPage back = returnToCarPage;
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    back.refreshTableFromCatalog();
                    back.setVisible(true);
                }
            });
        }
    }

    private void setupPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    private void resetFields() {
        carNameField.setText("Enter car name (e.g., BMW 320i)");
        carNameField.setForeground(Color.GRAY);
        
        carYearField.setText("Enter manufacturing year (e.g., 2024)");
        carYearField.setForeground(Color.GRAY);
        
        carColorField.setText("Enter car color");
        carColorField.setForeground(Color.GRAY);
        
        carQuantityField.setText("Enter available quantity");
        carQuantityField.setForeground(Color.GRAY);
        
        carPriceField.setText("Enter rent price per day (e.g., 350.0)");
        carPriceField.setForeground(Color.GRAY);

        getContentPane().requestFocusInWindow();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new AddCarPage().setVisible(true));
    }
}