package GUI.giaodienadmin.QuanLyDeThi;

import DAL.ExamStructDAL;
import DTO.ExamStructDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.UserPanel.MenuPanel;
import GUI.giaodienadmin.RoundedBorder;
import MICS.Connect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ExamStructManagementPanel extends JPanel implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton, clearSearchButton;
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private ExamStructDAL examStructDAL;
    private ArrayList<ExamStructDTO> exams;
    private JDialog dialog;
    private MenuPanel menuPanel;
    private ExamStructPanel examStructPanel;

    public ExamStructManagementPanel(MenuPanel menuPanel) {
        examStructDAL = new ExamStructDAL();
        exams = examStructDAL.getAll();
        this.menuPanel = menuPanel;
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new BorderLayout());
        setBackground(Ulti.MainColor);

        this.add(menuPanel, BorderLayout.WEST);
        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Ulti.LightGray);

        addButton = createButton("Thêm",Ulti.LightGreen);
        editButton = createButton("Edit", Ulti.Yellow);
        deleteButton = createButton("Delete", Ulti.BoldRed);
        searchField = createSearchField();
        searchButton = createButton("Tìm",Ulti.Blue);
        clearSearchButton = createButton("Tải lại", Ulti.Blue);

        topPanel.add(addButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearSearchButton);

        // Table for displaying exams
        String[] columnNames = {"Mã đề", "Tên đề", "Ngày bắt đầu", "Ngày kết thúc", "Thời lượng", "Môn", "Hành động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
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
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonPanelRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonPanelEditor(table));

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
        clearSearchButton.addActionListener(this);

        // Load initial data
        loadExamData();
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
    
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showAddExam() {
        examStructPanel = new ExamStructPanel(menuPanel.mainFrame.userBLL.getCurrent().getLoginName(), false);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm đề", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(examStructPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadExamData();
    }

    private void showEditExam(String examId) {
        examStructPanel = new ExamStructPanel(examId, true);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa đề", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(examStructPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadExamData();
    }

    private void deleteExam(String examId) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Xóa đề " + examId + "?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (examStructDAL.delete(examId)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadExamData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }
    }

    private void searchExam() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadExamData();
            return;
        }

        tableModel.setRowCount(0);
        for (ExamStructDTO exam : exams) {
            if (exam.getID().toLowerCase().contains(keyword) ||
                exam.getName().toLowerCase().contains(keyword) ||
                DATE_FORMATTER.format(exam.getStart()).toLowerCase().contains(keyword) ||
                DATE_FORMATTER.format(exam.getEnd()).toLowerCase().contains(keyword) ||
                TIME_FORMATTER.format(exam.getExamTime().toLocalTime()).toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{
                        exam.getID(),
                        exam.getName(),
                        DATE_FORMATTER.format(exam.getStart()),
                        DATE_FORMATTER.format(exam.getEnd()),
                        TIME_FORMATTER.format(exam.getExamTime().toLocalTime()),
                        exam.getSubject() != null ? exam.getSubject().getName() : "N/A",
                        exam.getDesc() != null ? exam.getDesc() : "No description"
                });
            }
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No exams found matching the keyword!");
        }
    }

    public void loadExamData() {
        tableModel.setRowCount(0);
        exams = examStructDAL.getAll();
        for (ExamStructDTO exam : exams) {
            tableModel.addRow(new Object[]{
                    exam.getID(),
                    exam.getName(),
                    DATE_FORMATTER.format(exam.getStart()),
                    DATE_FORMATTER.format(exam.getEnd()),
                    TIME_FORMATTER.format(exam.getExamTime().toLocalTime()),
                    exam.getSubject() != null ? exam.getSubject().getName() : "N/A",
                    "action"
            });
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
                String examId = table.getValueAt(row, 0).toString();
                showEditExam(examId);
                stopCellEditing();
            });

            deleteButton.addActionListener(e -> {
                String examId = table.getValueAt(row, 0).toString();
                deleteExam(examId);
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
            showAddExam();
        }  else if (e.getSource() == searchButton) {
            searchExam();
        } else if (e.getSource() == clearSearchButton) {
            loadExamData();
            searchField.setText("");
        }
    }
}