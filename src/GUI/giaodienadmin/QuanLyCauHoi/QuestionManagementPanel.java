package GUI.giaodienadmin.QuanLyCauHoi;

import BLL.QuestionBLL;
import DTO.QuestionDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.UserPanel.MenuPanel;
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


public class QuestionManagementPanel extends JPanel implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, searchButton, clearButton;
    private QuestionBLL questionBLL;
    private ArrayList<QuestionDTO> question;
    private QuestionPanel panelQuestion;
    private JDialog dialog;
    private MenuPanel menuPanel;

    public QuestionManagementPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
        this.questionBLL = new QuestionBLL();
        initComponent();
    }

    private void initComponent() {
        question = new ArrayList<>();
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

        String[] columnNames = {"Mã câu hỏi", "Nội Dung", "Độ khó", "Chương", "Môn", "Hành động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
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
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonPanelRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonPanelEditor(table));

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
        loadExam();
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

    private void ShowAddQuestion() {
        panelQuestion = new QuestionPanel(menuPanel.mainFrame.userBLL.getCurrent().getLoginName(), false);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm câu hỏi", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelQuestion);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadExam();
    }

    private void ShowEditQuestion(String quesId) {
        panelQuestion = new QuestionPanel(quesId, true);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chỉnh sửa câu hỏi", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelQuestion);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadExam();
    }

    private void delete(String quesId) {
       int choice = JOptionPane.showConfirmDialog(
                this,
                "Xóa câu hỏi " + quesId + "?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (questionBLL.delete(quesId)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadExam();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }
    }

    private void search() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadExam();
            return;
        }

        try {
            ArrayList<QuestionDTO> searchResults = questionBLL.search(keyword);
            tableModel.setRowCount(0);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng nào!", "Tìm kiếm người dùng", JOptionPane.INFORMATION_MESSAGE);
            }
            for (QuestionDTO ques : searchResults) {
                tableModel.addRow(new Object[]{
                        ques.getID(),
                        ques.getText(),
                        ques.getDifficult().toString(),
                        ques.getChapter(),
                        ques.getSubject(),
                        "Edit"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadExam() {
        question = questionBLL.getAll();
        tableModel.setRowCount(0);
        if (!question.isEmpty()) {
            for (QuestionDTO ques : question) {
                tableModel.addRow(new Object[]{
                        ques.getID(),
                        ques.getText(),
                        ques.getDifficult().toString(),
                        ques.getChapter(),
                        ques.getSubject(),
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
                String quesId = table.getValueAt(row, 0).toString();
                ShowEditQuestion(quesId);
                stopCellEditing();
            });

            deleteButton.addActionListener(e -> {
                String quesId = table.getValueAt(row, 0).toString();
                delete(quesId);
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
            ShowAddQuestion();
        } else if (e.getSource() == searchButton) {
            search();
        } else if (e.getSource() == clearButton) {
            searchField.setText("");
            loadExam();
        }
    }
}
