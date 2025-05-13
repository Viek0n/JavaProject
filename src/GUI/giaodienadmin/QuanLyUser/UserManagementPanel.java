package GUI.giaodienadmin.QuanLyUser;

import DAL.UserDAL;
import DTO.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import DAL.RoleDAL;

public class UserManagementPanel extends JPanel implements ActionListener {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, deleteButton, searchButton, clearButton;
    private UserDAL userDAL;
    private ArrayList<UserDTO> users;
    private PanelAddUser panelAddUser;
    private JDialog dialog;
    private EditUser editUserDialog;
    private RoleDAL roleDAL;

    public UserManagementPanel() {
        initComponent();
    }

    private void initComponent() {
        userDAL = new UserDAL();
        roleDAL = new RoleDAL();
        users = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#f0f4f8"));

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(Color.decode("#e0e0e0"));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        addButton = createButton("+ Add User", "#4caf50", Color.WHITE);
        deleteButton = createButton("Delete", "#f44336", Color.WHITE);
        searchField = createSearchField();
        searchButton = createButton("Search", "#ffc107", Color.BLACK);
        clearButton = createButton("Clear", "#808080", Color.WHITE);

        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);

        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"User ID", "Username", "Status", "Role", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setRowHeight(35);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JTableHeader tableHeader = userTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#f0f4f8"));
        tableHeader.setForeground(Color.BLACK);
        userTable.setFillsViewportHeight(true);
        userTable.setBackground(Color.WHITE);
        userTable.setGridColor(Color.LIGHT_GRAY);
        userTable.setSelectionBackground(Color.decode("#b0e0e6"));
        userTable.setSelectionForeground(Color.BLACK);

        userTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        userTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        userTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        userTable.getColumnModel().getColumn(4).setPreferredWidth(80);

        userTable.getColumnModel().getColumn(0).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(1).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(2).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(3).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Initialize dialog
       /* panelAddUser = new PanelAddUser(userDAL);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "User Management", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelAddUser);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);  */           

        loadUsers();
    }

    private JButton createButton(String text, String bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(8));
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(Color.decode(bgColor));
        button.setForeground(fgColor);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createSearchField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(new CompoundBorder(
                new RoundedBorder(8),
                new EmptyBorder(5, 10, 5, 10)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.decode("#2c3e50"));
        textField.setPreferredSize(new Dimension(200, 35));
        return textField;
    }

    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.decode("#e0e0e0"));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }
    }

    class CustomLabelRenderer extends JLabel implements TableCellRenderer {
        public CustomLabelRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(new Font("Arial", Font.PLAIN, 14));
            setForeground(Color.BLACK);
            setBackground(row % 2 == 0 ? Color.decode("#eaf2f8") : Color.WHITE);
            if (isSelected) {
                setBackground(Color.decode("#AED6F1"));
                setForeground(Color.BLACK);
            }
            setHorizontalAlignment(SwingConstants.LEFT);
            return this;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Arial", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setBackground(Color.decode("#008cba"));
            setBorder(new RoundedBorder(8));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Edit");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.decode("#008cba"));
            button.setBorder(new RoundedBorder(8));
            button.addActionListener(e -> {
                clicked = true;
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = "Edit";
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                String userID = (String) tableModel.getValueAt(row, 0);
                showEditUserPanel(userID);
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            showAddUserPanel();
        } else if (e.getSource() == deleteButton) {
            deleteUser();
        } else if (e.getSource() == searchButton) {
            searchUser();
        } else if (e.getSource() == clearButton) {
            searchField.setText("");
            loadUsers();
        }
    }
    private void showAddUserPanel() {
        if (dialog == null || panelAddUser == null) {
            panelAddUser = new PanelAddUser(userDAL);
            dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Add User", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.getContentPane().add(panelAddUser);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
        }
        panelAddUser.resetFields();
        dialog.setTitle("Add User");
        dialog.setVisible(true);
        loadUsers();
    }

    private void showEditUserPanel(String userID) {
        UserDTO userToEdit = userDAL.getByLoginName(userID);
        if (userToEdit == null) {
            JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        EditUser editUserDialog = new EditUser(SwingUtilities.getWindowAncestor(this), userToEdit, userDAL, roleDAL);
        editUserDialog.setVisible(true);
        loadUsers();
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete!", "Delete User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String userID = (String) tableModel.getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                if (userDAL.deleteByLoginName(userID)) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "User deleted successfully!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchUser() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!", "Search User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            ArrayList<UserDTO> searchResults = userDAL.searchUser(keyword);
            tableModel.setRowCount(0);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No users found!", "Search User", JOptionPane.INFORMATION_MESSAGE);
            }
            for (UserDTO user : searchResults) {
                tableModel.addRow(new Object[]{
                        user.getLoginName(),
                        user.getName(),
                        user.getStatus().name(),
                        user.getRole().getName(),
                        "Edit"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadUsers() {
        SwingWorker<ArrayList<UserDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected ArrayList<UserDTO> doInBackground() throws Exception {
                return userDAL.getAll();
            }

            @Override
            protected void done() {
                try {
                    users = get();
                    tableModel.setRowCount(0);
                    if (!users.isEmpty()) {
                        for (UserDTO user : users) {
                            tableModel.addRow(new Object[]{
                                    user.getLoginName(),
                                    user.getName(),
                                    user.getStatus().name(),
                                    user.getRole().getName(),
                                    "Edit"
                            });
                        }
                    } else {
                        JOptionPane.showMessageDialog(UserManagementPanel.this, "No users found!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserManagementPanel.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}

// Placeholder for PanelAddUser (to be implemented)
class PanelAddUser extends JPanel {
    private UserDAL userDAL;

    public PanelAddUser(UserDAL userDAL) {
        this.userDAL = userDAL;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.decode("#f0f4f8"));
        // Implement fields (loginName, name, password, status, role), buttons, and save logic
    }

    public void resetFields() {
        // Clear fields
    }

    public boolean loadUserData(String userID) {
        // Load user data from userDAL
        return true; // Placeholder
    }
}