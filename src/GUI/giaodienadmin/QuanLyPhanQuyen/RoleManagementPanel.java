package GUI.giaodienadmin.QuanLyPhanQuyen;

import BLL.RoleBLL;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import BLL.UserBLL;
import DTO.RoleDTO;
import DTO.TaskDTO;
import DTO.UserDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.giaodienadmin.RoundedBorder;
import GUI.giaodienadmin.QuanLyMonHoc.SubjectPanel;
import GUI.giaodienadmin.QuanLyUser.UserManagementPanel;
import GUI.giaodienadmin.QuanLyUser.UserPanel;
import MICS.Connect;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoleManagementPanel extends JPanel implements ActionListener{
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private ArrayList<RoleDTO> roles;
    private RolePanel rolePanel;
    private JDialog dialog;
    private JPanel menuPanel;
    private RoleBLL roleBLL;

    public RoleManagementPanel(JPanel menuPanel) {
        this.menuPanel = menuPanel;
        this.roleBLL = new RoleBLL();
        initComponent();
    }

    private void initComponent() {
        roles = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Ulti.MainColor);

        // Menu panel
        add(menuPanel, BorderLayout.WEST);

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Ulti.LightGray);

        addButton = createButton("Tạo", Ulti.LightGreen);

        topPanel.add(addButton);

        String[] columnNames = {"Mã nhóm quyền", "Tên nhóm quyền", "Hành động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
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
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonPanelRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonPanelEditor(table));

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

        // Load users
        load();
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

    private void showAddRole() {
        rolePanel = new RolePanel();
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm nhóm quyền", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(rolePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        load();
    }

    private void showEditRole(int roleName) {
        rolePanel = new RolePanel(roleName);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chỉnh sửa nhóm quyền", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(rolePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        load();
    }

    private void delete(int roleId) {
        /*int choice = JOptionPane.showConfirmDialog(
                this,
                "Xóa người dùng " + userId + "?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (userBLL.delete(userId)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    load();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }*/
    }

    private void load() {
        tableModel.setRowCount(0);
        roles = new ArrayList<>(roleBLL.getAll());
        if (!roles.isEmpty()) {
            for (RoleDTO role : roles) {
                tableModel.addRow(new Object[]{
                        role.getID(),
                        role.getName(),
                        "Edit"});
            }
         }
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
                int roleId = Integer.parseInt(table.getValueAt(row, 0).toString());
                showEditRole(roleId);
                stopCellEditing();
            });

            deleteButton.addActionListener(e -> {
                int roleId = Integer.parseInt(table.getValueAt(row, 0).toString());
                stopCellEditing();
                delete(roleId);
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (row >= 0 && row < model.getRowCount()) {
                        model.removeRow(row);
                    }
                });
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
            showAddRole();
        }
    }
}
