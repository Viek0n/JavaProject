package GUI.giaodienadmin.QuanLyUser;

import BLL.UserBLL;
import DTO.UserDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.giaodienadmin.RoundedBorder;
import MICS.Connect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class UserManagementPanel extends JPanel implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, searchButton, clearButton;
    private UserBLL userBLL;
    private ArrayList<UserDTO> users;
    private UserPanel panelAddUser;
    private JDialog dialog;
    private JPanel menuPanel;

    public UserManagementPanel(JPanel menuPanel) {
        this.menuPanel = menuPanel;
        this.userBLL = new UserBLL();
        initComponent();
    }

    private void initComponent() {
        users = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Ulti.MainColor);

        // Menu panel
        add(menuPanel, BorderLayout.WEST);

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Ulti.LightGray);

        addButton = createButton("Tạo", Ulti.LightGreen);
        searchField = createSearchField();
        searchButton = createButton("Tìm", Ulti.Yellow);
        clearButton = createButton("Tải lại", Ulti.Blue);

        topPanel.add(addButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);

        String[] columnNames = {"Tên đăng nhập", "Tên người dùng", "Trạng thái", "Nhóm quyền", "Hành động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.black);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(Color.lightGray);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setBackground(Ulti.LightGray);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount()-1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonPanelRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonPanelEditor(table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Load users
        loadUsers();
    }


    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createSearchField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(new CompoundBorder(
                new RoundedBorder(8),
                new EmptyBorder(0, 10, 0, 10)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.decode("#2c3e50"));
        textField.setPreferredSize(new Dimension(200, 35));
        return textField;
    }

    private void showAddUserPanel() {
        panelAddUser = new UserPanel();
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm người dùng", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelAddUser);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadUsers();
    }

    private void showEditUserPanel(String userID) {
        panelAddUser = new UserPanel(userID);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chỉnh sửa người dùng", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelAddUser);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadUsers();
    }

    private void deleteUser(String userId) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Xóa người dùng " + userId + "?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (userBLL.delete(userId)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }
    }

    private void searchUser() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadUsers();
            return;
        }

        try {
            ArrayList<UserDTO> searchResults = userBLL.search(keyword);
            tableModel.setRowCount(0);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng nào!", "Tìm kiếm người dùng", JOptionPane.INFORMATION_MESSAGE);
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
                return userBLL.getAll();
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
                        JOptionPane.showMessageDialog(UserManagementPanel.this, "Không tìm thấy người dùng nào!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserManagementPanel.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
        public ButtonPanelRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JButton edit = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "edit.png", 20, 20));
            JButton delete =  ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));
            add(edit);
            add(delete);
            setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonPanelEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;
        private int row;

        public ButtonPanelEditor(JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.setBackground(Color.WHITE);
            editButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "edit.png", 20, 20));
            deleteButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));

            editButton.addActionListener(e -> {
                String userId = table.getValueAt(row, 0).toString();
                showEditUserPanel(userId);
                stopCellEditing();
            });

            deleteButton.addActionListener(e -> {
                String userId = table.getValueAt(row, 0).toString();
                deleteUser(userId);
                stopCellEditing();
            });

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            showAddUserPanel();
        } else if (e.getSource() == searchButton) {
            searchUser();
        } else if (e.getSource() == clearButton) {
            searchField.setText("");
            loadUsers();
        }
    }
}