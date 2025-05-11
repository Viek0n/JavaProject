package GUI.giaodienadmin;

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
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;

public class UserManagementPanel extends JPanel implements ActionListener {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private UserDAL userDAL;
    private ArrayList<UserDTO> users;
    private PanelAddUser panelAddUser;
    private JDialog dialog;

    public UserManagementPanel() {
        initComponent();
    }

    private void initComponent() {
        userDAL = new UserDAL();
        users= userDAL.getAll();
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#f0f4f8"));

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(Color.decode("#e0e0e0"));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        addButton = createButton("+ Add User");
        addButton.setBackground(Color.decode("#4caf50"));
        addButton.setForeground(Color.WHITE);
        editButton = createButton("Edit");
        editButton.setBackground(Color.decode("#008cba"));
        editButton.setForeground(Color.WHITE);
        deleteButton = createButton("Delete");
        deleteButton.setBackground(Color.decode("#f44336"));
        deleteButton.setForeground(Color.WHITE);

        searchField = createSearchField();
        searchButton = createButton("Search");
        searchButton.setBackground(Color.decode("#ffc107"));
        searchButton.setForeground(Color.BLACK);

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        this.add(topPanel, BorderLayout.NORTH);

        // Table for displaying users
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

        // Apply custom renderers and editors
        userTable.getColumnModel().getColumn(0).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(1).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(2).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(3).setCellRenderer(new CustomLabelRenderer());
        userTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        loadUsers();

        // Initialize panelAddUser
        /*panelAddUser = new PanelAddUser();
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelAddUser);
        dialog.pack();
        dialog.setLocationRelativeTo(this);*/
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#3498db"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(new RoundedBorder(8));
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createSearchField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
       /*  textField.setBorder(new CompoundBorder(
                new RoundedBorder(8),
                new EmptyBorder(5, 10, 5, 10)
        ));*/
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.decode("#2c3e50"));
        textField.setPreferredSize(new Dimension(200, 35));
        return textField;
    }

    private static class RoundedBorder extends AbstractBorder {
        private int radius;

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
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.top = insets.left = insets.bottom = insets.right = this.radius;
            return insets;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            showAddUserPanel();
        } else if (e.getSource() == editButton) {
            editUser();
        } else if (e.getSource() == deleteButton) {
            deleteUser();
        } else if (e.getSource() == searchButton) {
            searchUser();
        }
    }

    private void showAddUserPanel() {
       // panelAddUser.resetFields();
        dialog.setTitle("Add User");
        dialog.setVisible(true);
        loadUsers();
    }

    private void showEditUserPanel(String userID) {
        panelAddUser.loadUserData(userID);
        dialog.setTitle("Edit User");
        dialog.setVisible(true);
        loadUsers();
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit!", "Edit User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String userID = (String) tableModel.getValueAt(selectedRow, 0);
        showEditUserPanel(userID);
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
            if (userDAL.deleteByLoginName(userID)) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "User deleted successfully!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        loadUsers();
    }

    private void searchUser() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!", "Search User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<UserDTO> searchResults = userDAL.searchUser(keyword);
        tableModel.setRowCount(0);
        for (UserDTO user : searchResults) {
            tableModel.addRow(new Object[]{
                    user.getLoginName(),
                    user.getName(),
                    user.getStatus().name(),
                    user.getRole().getName(),
                    "Edit"
            });
        }
    }

    public void loadUsers() {
       
        tableModel.setRowCount(0);
        for (UserDTO user : users) {
            tableModel.addRow(new Object[]{
                    user.getLoginName(),
                    user.getName(),
                    user.getStatus().name(),
                    user.getRole().getName(),
                    "Edit"
            });
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
            setText((value == null) ? "Edit" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private JTable table;
        private int row;
        private ActionListener actionListener;

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
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(row)));
                }
            });
        }

        public ButtonEditor(JCheckBox checkBox, ActionListener listener) {
            this(checkBox);
            this.actionListener = listener;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            label = (value == null) ? "Edit" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                String userID = (String) table.getValueAt(row, 0);
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
}
