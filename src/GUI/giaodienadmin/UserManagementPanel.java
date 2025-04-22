package GUI.giaodienadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManagementPanel extends JPanel implements ActionListener {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;

    public UserManagementPanel() {
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#ecf0f1"));

        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.decode("#bdc3c7"));

        addButton = createButton("Add");
        editButton = createButton("Edit");
        deleteButton = createButton("Delete");
        searchField = new JTextField(20);
        searchButton = createButton("Search");

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        this.add(topPanel, BorderLayout.NORTH);

        // Table for displaying users
        String[] columnNames = {"User ID", "Username", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setRowHeight(30);
        userTable.setFillsViewportHeight(true);
        userTable.setBackground(Color.WHITE);
        userTable.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(userTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#3498db"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addUser();
        } else if (e.getSource() == editButton) {
            editUser();
        } else if (e.getSource() == deleteButton) {
            deleteUser();
        } else if (e.getSource() == searchButton) {
            searchUser();
        }
    }

    private void addUser() {
        // Logic to add a new user
        JOptionPane.showMessageDialog(this, "Add User functionality not implemented yet!");
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit!");
            return;
        }
        // Logic to edit the selected user
        JOptionPane.showMessageDialog(this, "Edit User functionality not implemented yet!");
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete!");
            return;
        }
        // Logic to delete the selected user
        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "User deleted successfully!");
    }

    private void searchUser() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
            return;
        }
        // Logic to search for users based on the keyword
        JOptionPane.showMessageDialog(this, "Search functionality not implemented yet!");
    }

    // Method to populate the table with data (for demonstration purposes)
    public void loadUsers(Object[][] data) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
}