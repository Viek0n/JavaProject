package GUI.giaodienadmin.QuanLyPhanCong;

import BLL.TaskBLL;
import DTO.SubjectDTO;
import DTO.TaskDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.UserPanel.MenuPanel;
import GUI.giaodienadmin.QuanLyMonHoc.SubjectManagementPanel;
import GUI.giaodienadmin.QuanLyMonHoc.SubjectPanel;
import GUI.giaodienadmin.RoundedBorder;
import MICS.Connect;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class AsignManagementPanel extends JPanel implements ActionListener{
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private TaskBLL taskBLL;

    private ArrayList<TaskDTO> tasks;
    private AsignPanel panelAsign;
    private JDialog dialog;
    private MenuPanel menuPanel;

    public AsignManagementPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
        taskBLL = new TaskBLL();
        initComponent();
    }

    private void initComponent() {
        tasks = new ArrayList<>();
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

        String[] columnNames = {"Người dùng", "Môn học", "Hành động"};
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

    private void ShowAdd() {
        panelAsign = new AsignPanel();
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Phân công", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelAsign);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        load();
    }

    private void delete(String userId, String subjectId) {
       int choice = JOptionPane.showConfirmDialog(
                this,
                "Xóa phân quyền ?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (taskBLL.delete(userId,subjectId)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }
    }

    private void load() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tableModel.setRowCount(0);
        SwingWorker<ArrayList<TaskDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected ArrayList<TaskDTO> doInBackground() throws Exception {
                return taskBLL.getAll();
            }

            @Override
            protected void done() {
                try {
                    tasks = get();
                    if (!tasks.isEmpty()) {
                        for (TaskDTO task : tasks) {
                            tableModel.addRow(new Object[]{
                            task.getUser().getLoginName() + " - " + task.getUser().getName(),
                            task.getSubject().getID() + " - " + task.getSubject().getName(),
                            "Edit"});
                        }
                    } else {
                        JOptionPane.showMessageDialog(AsignManagementPanel.this, "No subject found!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AsignManagementPanel.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }  finally {
                    setCursor(Cursor.getDefaultCursor());
                }   
            }
        };
        worker.execute();
    }

    class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
        public ButtonPanelRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JButton delete =  ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));
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
        private final JButton deleteButton;
        private int row;

        public ButtonPanelEditor(JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.setBackground(Color.WHITE);
            deleteButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));

            deleteButton.addActionListener(e -> {
                String userId = table.getValueAt(row, 0).toString();
                String subjectId = table.getValueAt(row, 1).toString();
                stopCellEditing();
                delete(userId.split(" - ")[0], subjectId.split(" - ")[0]);
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (row >= 0 && row < model.getRowCount()) {
                        model.removeRow(row);
                    }
                });
            });

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
            ShowAdd();
        }
    }
}
