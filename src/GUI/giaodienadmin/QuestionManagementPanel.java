package GUI.giaodienadmin;

import DAL.AnswerDAL;
import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class QuestionManagementPanel extends JPanel implements ActionListener {
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private ArrayList<QuestionDTO> bank;
    private QuestionDAL questionDAL;
    private JPanel mainPanel; // Panel chứa CardLayout
    private CardLayout cardLayout; // CardLayout để chuyển đổi giữa các giao diện
    private PanelExemDetail panelExemDetail; // Giao diện chỉnh sửa câu hỏi

    public QuestionManagementPanel(JPanel mainPanel, CardLayout cardLayout, PanelExemDetail panelExemDetail) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.panelExemDetail = panelExemDetail;
        initComponent();
    }

    private void initComponent() {
        questionDAL = new QuestionDAL();
        bank = questionDAL.getAll();
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#ecf0f1"));
    
        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.decode("#bdc3c7"));
    
        addButton = createButton("Add");
        deleteButton = createButton("Delete");
        searchField = new JTextField(20);
        searchButton = createButton("Search");
    
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
    
        this.add(topPanel, BorderLayout.NORTH);
    
        // Table for displaying questions
        String[] columnNames = {"Question ID", "Subject","level", "Chapter", "Detail"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        questionTable.setRowHeight(30);
        questionTable.setFillsViewportHeight(true);
        questionTable.setBackground(Color.WHITE);
        questionTable.setGridColor(Color.GRAY);
    
        JScrollPane scrollPane = new JScrollPane(questionTable);
        this.add(scrollPane, BorderLayout.CENTER);
    
        // Add action listeners
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        loadQuestions();
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
            addQuestion();
        } else if (e.getSource() == editButton) {
            editQuestion();
        } else if (e.getSource() == deleteButton) {
            deleteQuestion();
        } else if (e.getSource() == searchButton) {
            searchQuestion();
        }
    }

    private void addQuestion() {
        JOptionPane.showMessageDialog(this, "Add Question functionality not implemented yet!");
    }

    private void editQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to edit!");
            return;
        }
        String questionID = tableModel.getValueAt(selectedRow, 0).toString();
    
        // Chuyển sang giao diện chỉnh sửa câu hỏi
        panelExemDetail.loadQuestionDetails(questionID);
        cardLayout.show(mainPanel, "EditPanel");
    }

    private void deleteQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to delete!");
            return;
        }
        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Question deleted successfully!");
    }

    private void searchQuestion() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Search functionality not implemented yet!");
    }
    
    public void loadQuestions() {
        tableModel.setRowCount(0); // Xóa tất cả các hàng trước khi tải dữ liệu mới
    
        if (bank != null && !bank.isEmpty()) {
            for (QuestionDTO row : bank) {
                tableModel.addRow(new Object[] {
                    row.getID(),                // Question ID
                    row.getDifficult(),
                    row.getSubject() != null ? row.getSubject().getName() : "N/A", // Subject
                    row.getChapter() != null ? row.getChapter().getName() : "N/A", // Chapter
                    "Edit"                      // Nút "Edit" trong cột "Detail"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No questions available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    
        // Thêm renderer và editor cho cột "Detail"
        questionTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        questionTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    // Custom renderer cho nút
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Edit" : value.toString());
            return this;
        }
    }
    
    // Custom editor cho nút
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
    
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Edit" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int selectedRow = questionTable.getSelectedRow();
                String questionID = tableModel.getValueAt(selectedRow, 0).toString();
                panelExemDetail.loadQuestionDetails(questionID);
                cardLayout.show(mainPanel, "EditPanel");
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