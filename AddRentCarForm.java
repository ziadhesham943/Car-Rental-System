package oop.project;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class AddRentCarForm extends JFrame {

    private JTextField nameField;
    private JTextField idField;
    private JTextField startField;
    private JTextField endField;
    private JComboBox<Car> carCombo;
    private JLabel totalLabel;

    public AddRentCarForm() {
        setTitle("Add Rent Car");
        setSize(420, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        nameField = new JTextField();
        idField = new JTextField();
        carCombo = new JComboBox<>(CarData.CARS.toArray(new Car[0]));
        startField = new JTextField("YYYY-MM-DD");
        endField = new JTextField("YYYY-MM-DD");
        totalLabel = new JLabel("0.00 EGP");
        totalLabel.setForeground(new java.awt.Color(0, 130, 0));

        form.add(new JLabel("Renter Name:"));
        form.add(nameField);
        form.add(new JLabel("National ID:"));
        form.add(idField);
        form.add(new JLabel("Rented Car:"));
        form.add(carCombo);
        form.add(new JLabel("Start Date:"));
        form.add(startField);
        form.add(new JLabel("End Date:"));
        form.add(endField);
        form.add(new JLabel("Total Price:"));
        form.add(totalLabel);

        JPanel btns = new JPanel();
        JButton calcBtn = new JButton("Calculate");
        JButton addBtn = new JButton("Add Rent");

        calcBtn.addActionListener(e -> calculate());
        addBtn.addActionListener(e -> addRent());

        btns.add(calcBtn);
        btns.add(addBtn);

        add(form, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);
    }

    private void calculate() {
        Car car = (Car) carCombo.getSelectedItem();
        if (car == null || CarData.CARS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cars available.");
            return;
        }
        try {
            String startRent = DateFormats.isoToRent(startField.getText().trim());
            String endRent = DateFormats.isoToRent(endField.getText().trim());
            Rent temp = new Rent("_", 0, car, startRent, endRent);
            totalLabel.setText(String.format("%.2f EGP", temp.getTotal_price()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date! Use YYYY-MM-DD");
        }
    }

    private void addRent() {
        if (nameField.getText().trim().isEmpty() || idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        Car car = (Car) carCombo.getSelectedItem();
        if (car == null || CarData.CARS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cars available.");
            return;
        }
        try {
            String startRent = DateFormats.isoToRent(startField.getText().trim());
            String endRent = DateFormats.isoToRent(endField.getText().trim());
            Rent rent = new Rent(nameField.getText().trim(), Integer.parseInt(idField.getText().trim()), car, startRent, endRent);
            JOptionPane.showMessageDialog(this, "Rent added successfully!\nTotal: " + String.format("%.2f EGP", rent.getTotal_price()));
            nameField.setText("");
            idField.setText("");
            startField.setText("YYYY-MM-DD");
            endField.setText("YYYY-MM-DD");
            totalLabel.setText("0.00 EGP");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input or dates. Use YYYY-MM-DD.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddRentCarForm().setVisible(true));
    }
}
