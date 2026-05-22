package oop.project;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 280);
        setLocationRelativeTo(null);

        JButton btnCar = new JButton("Car Management");
        JButton btnRent = new JButton("Rent Management");
        JButton btnLogout = new JButton("Logout");

        btnCar.addActionListener(e -> {
            setVisible(false);
            new CarManagmentPage(MainMenu.this).setVisible(true);
        });
        btnRent.addActionListener(e -> {
            setVisible(false);
            new RentedPage(MainMenu.this).setVisible(true);
        });
        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 14, 14));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 48, 28, 48));
        panel.add(btnCar);
        panel.add(btnRent);
        panel.add(btnLogout);
        add(panel);
    }
}
