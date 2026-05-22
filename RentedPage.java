package oop.project;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class RentedPage extends JFrame {

    private static final Color BG_COLOR = new Color(15, 20, 35);
    private static final Color PANEL_COLOR = new Color(22, 30, 50);
    private static final Color ACCENT_COLOR = new Color(99, 179, 237);
    private static final Color BTN_ADD = new Color(56, 161, 105);
    private static final Color BTN_CUR = new Color(99, 179, 237);
    private static final Color BTN_EXT = new Color(214, 158, 46);
    private static final Color TEXT_COLOR = new Color(226, 232, 240);
    private static final Color TABLE_HEADER = new Color(30, 42, 70);
    private static final Color TABLE_ROW1 = new Color(22, 30, 50);
    private static final Color TABLE_ROW2 = new Color(18, 25, 42);
    private static final Color BORDER_COLOR = new Color(45, 60, 90);

    private DefaultTableModel tableModel;
    private JTable table;
    private final List<Rent> rentalList = new ArrayList<>();

    public RentedPage() {
        this(null);
    }

    public RentedPage(MainMenu menuParent) {
        setTitle("Rented Page - Car Rental System");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(950, 620);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(menuParent != null ? menuParent : null);
        setBackground(BG_COLOR);

        initUI();
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

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 18, 0));

        JLabel titleLabel = new JLabel("Rented Page");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnPanel.setBackground(BG_COLOR);
        btnPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JButton addRentBtn = createStyledButton("+  Add Rent", BTN_ADD);
        JButton curRentBtn = createStyledButton("Cut Rent", BTN_CUR);
        JButton extendRentBtn = createStyledButton("Extend Rent", BTN_EXT);

        btnPanel.add(addRentBtn);
        btnPanel.add(curRentBtn);
        btnPanel.add(extendRentBtn);

        String[] columns = {"Rental Name", "Nation ID", "Rented Car", "Start Date", "End Date", "Delete"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };

        table = new JTable(tableModel);
        styleTable();

        table.getColumn("Delete").setCellRenderer(new DeleteButtonRenderer());
        table.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(PANEL_COLOR);
        scrollPane.getViewport().setBackground(TABLE_ROW1);
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1));

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(BG_COLOR);
        topSection.add(headerPanel, BorderLayout.NORTH);
        topSection.add(btnPanel, BorderLayout.CENTER);

        mainPanel.add(topSection, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);

        addRentBtn.addActionListener(e -> showAddRentDialog());
        curRentBtn.addActionListener(e -> showCutRentDialog());
        extendRentBtn.addActionListener(e -> showExtendDialog());
    }

    private void showAddRentDialog() {
        if (CarData.CARS.isEmpty()) {
            showError(this, "No cars available. Add cars from Car Management first.");
            return;
        }

        JDialog dialog = createDialog("Add New Rent", 460, 420);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL_COLOR);
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 20, 24));
        GridBagConstraints gbc = defaultGBC();

        JTextField nameField = createStyledField();
        JTextField idField = createStyledField();
        JComboBox<Car> carCombo = new JComboBox<>(CarData.CARS.toArray(new Car[0]));
        carCombo.setBackground(new Color(30, 42, 70));
        carCombo.setForeground(TEXT_COLOR);
        carCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JTextField startField = createStyledField();
        JTextField endField = createStyledField();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        startField.setText(today);

        int row = 0;
        addFormRow(form, gbc, row++, "Rental Name:", nameField);
        addFormRow(form, gbc, row++, "Nation ID:", idField);
        addFormRowCombo(form, gbc, row++, "Rented Car:", carCombo);
        addFormRow(form, gbc, row++, "Start Date (yyyy-MM-dd):", startField);
        addFormRow(form, gbc, row++, "End Date (yyyy-MM-dd):", endField);

        JButton saveBtn = createStyledButton("Save", BTN_ADD);
        saveBtn.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty()
                    || idField.getText().trim().isEmpty()
                    || startField.getText().trim().isEmpty()
                    || endField.getText().trim().isEmpty()) {
                showError(dialog, "Please fill all fields.");
                return;
            }
            Car car = (Car) carCombo.getSelectedItem();
            if (car == null) {
                showError(dialog, "Select a car.");
                return;
            }
            try {
                String startIso = startField.getText().trim();
                String endIso = endField.getText().trim();
                String startRent = DateFormats.isoToRent(startIso);
                String endRent = DateFormats.isoToRent(endIso);
                int nid = Integer.parseInt(idField.getText().trim());
                Rent rent = new Rent(nameField.getText().trim(), nid, car, startRent, endRent);
                rentalList.add(rent);
                tableModel.addRow(new Object[]{
                    rent.getRenter_name(),
                    String.valueOf(rent.getNational_ID()),
                    rent.getRented_car().getName(),
                    startIso,
                    endIso,
                    ""
                });
                dialog.dispose();
            } catch (Exception ex) {
                showError(dialog, "Invalid dates (use yyyy-MM-dd) or Nation ID.");
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setBackground(PANEL_COLOR);
        btnRow.add(saveBtn);

        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(PANEL_COLOR);
        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void addFormRow(JPanel form, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(TEXT_COLOR);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(field, gbc);
    }

    private void addFormRowCombo(JPanel form, GridBagConstraints gbc, int row, String labelText, JComboBox<Car> combo) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(TEXT_COLOR);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(combo, gbc);
    }

    private void showCutRentDialog() {
        if (tableModel.getRowCount() == 0) {
            showError(this, "No rental records to cut.");
            return;
        }

        JDialog dialog = createDialog("Cut Rent", 420, 180);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(PANEL_COLOR);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL_COLOR);
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 10, 24));
        GridBagConstraints gbc = defaultGBC();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lbl = new JLabel("Select Rental to Cut:");
        lbl.setForeground(TEXT_COLOR);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        Vector<String> options = new Vector<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            options.add((i + 1) + ". " + tableModel.getValueAt(i, 0) + " — " + tableModel.getValueAt(i, 2));
        }
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setBackground(new Color(30, 42, 70));
        combo.setForeground(TEXT_COLOR);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(combo, gbc);

        JButton cutBtn = createStyledButton("Cut Rent", new Color(197, 48, 48));
        cutBtn.addActionListener(e -> {
            int idx = combo.getSelectedIndex();
            String name = tableModel.getValueAt(idx, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Are you sure you want to cut the rental for: " + name + "?",
                    "Confirm Cut",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            
            JOptionPane.showMessageDialog(rootPane, "Total price is " +rentalList.get(idx).getTotal_price()+ "EGP") ;
            
            if (confirm == JOptionPane.YES_OPTION) {
                rentalList.remove(idx);
                tableModel.removeRow(idx);
                dialog.dispose();
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setBackground(PANEL_COLOR);
        btnRow.add(cutBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showExtendDialog() {
        if (tableModel.getRowCount() == 0) {
            showError(this, "No records to extend.");
            return;
        }

        JDialog dialog = createDialog("Extend Rent", 400, 220);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(PANEL_COLOR);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL_COLOR);
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 10, 24));
        GridBagConstraints gbc = defaultGBC();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel rowLbl = new JLabel("Select Record:");
        rowLbl.setForeground(TEXT_COLOR);
        rowLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(rowLbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        Vector<String> options = new Vector<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            options.add((i + 1) + ". " + tableModel.getValueAt(i, 0) + " — " + tableModel.getValueAt(i, 2));
        }
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setBackground(new Color(30, 42, 70));
        combo.setForeground(TEXT_COLOR);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(combo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel dateLbl = new JLabel("New End Date (yyyy-MM-dd):");
        dateLbl.setForeground(TEXT_COLOR);
        dateLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(dateLbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField newDate = createStyledField();
        newDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        form.add(newDate, gbc);

        JButton extBtn = createStyledButton("Extend", BTN_EXT);
        extBtn.addActionListener(e -> {
            int idx = combo.getSelectedIndex();
            String nd = newDate.getText().trim();
            if (nd.isEmpty()) {
                showError(dialog, "Enter a new end date.");
                return;
            }
            try {
                Rent rent = rentalList.get(idx);
                rent.extend_rental_time(DateFormats.isoToRent(nd));
                tableModel.setValueAt(nd, idx, 4);
                dialog.dispose();
            } catch (Exception ex) {
                showError(dialog, "Invalid date format.");
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setBackground(PANEL_COLOR);
        btnRow.add(extBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void styleTable() {
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_COLOR);
        table.setBackground(TABLE_ROW1);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(49, 90, 150));
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(1, 0));

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER);
        header.setForeground(ACCENT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_COLOR));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setForeground(TEXT_COLOR);
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!sel) {
                    setBackground(row % 2 == 0 ? TABLE_ROW1 : TABLE_ROW2);
                } else {
                    setBackground(new Color(49, 90, 150));
                }
                return this;
            }
        });

        int[] widths = {150, 100, 150, 110, 110, 90};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    private class DeleteButtonRenderer extends JButton implements TableCellRenderer {

        DeleteButtonRenderer() {
            setOpaque(true);
            setText("Delete");
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setBackground(new Color(197, 48, 48));
            setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object val,
                                                         boolean sel, boolean foc, int row, int col) {
            return this;
        }
    }

    private class DeleteButtonEditor extends DefaultCellEditor {

        private final JButton btn;
        private int currentRow;

        DeleteButtonEditor(JCheckBox cb) {
            super(cb);
            btn = new JButton("Delete");
            btn.setOpaque(true);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(197, 48, 48));
            btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> {
                fireEditingStopped();
                int confirm = JOptionPane.showConfirmDialog(
                        RentedPage.this,
                        "Are you sure you want to delete this record?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    rentalList.remove(currentRow);
                    tableModel.removeRow(currentRow);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable t, Object val,
                                                     boolean sel, int row, int col) {
            currentRow = row;
            return btn;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    private JButton createStyledButton(String text, Color base) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed() ? base.darker()
                        : getModel().isRollover() ? base.brighter() : base;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(140, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTextField createStyledField() {
        JTextField f = new JTextField();
        f.setBackground(new Color(30, 42, 70));
        f.setForeground(TEXT_COLOR);
        f.setCaretColor(ACCENT_COLOR);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        f.setPreferredSize(new Dimension(200, 32));
        return f;
    }

    private JDialog createDialog(String title, int w, int h) {
        JDialog d = new JDialog(this, title, true);
        d.setSize(w, h);
        d.setLocationRelativeTo(this);
        d.getContentPane().setBackground(PANEL_COLOR);
        return d;
    }

    private GridBagConstraints defaultGBC() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        return g;
    }

    private void showError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new RentedPage().setVisible(true));
    }
}
