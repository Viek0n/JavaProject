package GUI.giaodienadmin;

import DTO.AnswerDTO;
import DTO.QuestionDTO;
import MICS.Enums;
import DAL.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private JPanel mainPanel; 
    private CardLayout cardLayout; 
    private PanelExemDetail panelExemDetail; 

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
                    row.getSubject() != null ? row.getSubject().getName() : "N/A", 
                    row.getDifficult() != null ? row.getDifficult().toString() : "N/A",
                    row.getChapter() != null ? row.getChapter().getName() : "N/A", 
                    "Edit"                      
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
    private void addQuestion() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Question", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(10, 2, 10, 10));
        ChapterDAL chapterDAL = new ChapterDAL();
        
        JTextArea contentField = new JTextArea(3, 20);
        JComboBox<Enums.DifficultValue> diffBox = new JComboBox<>(Enums.DifficultValue.values());
        JComboBox<String> chapterBox = new JComboBox<>();
        JTextField createdByField = new JTextField();
    
        // Các đáp án
        JTextField answerA = new JTextField();
        JTextField answerB = new JTextField();
        JTextField answerC = new JTextField();
        JTextField answerD = new JTextField();
    
        // Chọn đáp án đúng
        String[] options = {"A", "B", "C", "D"};
        JComboBox<String> correctAnswerBox = new JComboBox<>(options);
    
        // Load danh sách chương
        chapterDAL.getAll().forEach(chap -> chapterBox.addItem(chap.getID()));
    
        // UI layout
        dialog.add(new JLabel("Content:"));
        dialog.add(new JScrollPane(contentField));
    
        dialog.add(new JLabel("Difficulty:"));
        dialog.add(diffBox);
    
        dialog.add(new JLabel("Chapter ID:"));
        dialog.add(chapterBox);
    
        dialog.add(new JLabel("Created By (UserID):"));
        dialog.add(createdByField);
    
        dialog.add(new JLabel("Answer A:"));
        dialog.add(answerA);
        dialog.add(new JLabel("Answer B:"));
        dialog.add(answerB);
        dialog.add(new JLabel("Answer C:"));
        dialog.add(answerC);
        dialog.add(new JLabel("Answer D:"));
        dialog.add(answerD);
    
        dialog.add(new JLabel("Correct Answer:"));
        dialog.add(correctAnswerBox);
    
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        dialog.add(saveButton);
        dialog.add(cancelButton);
    
        saveButton.addActionListener(e -> {
            // Validate input
            if (contentField.getText().trim().isEmpty()
                || answerA.getText().trim().isEmpty()
                || answerB.getText().trim().isEmpty()
                || answerC.getText().trim().isEmpty()
                || answerD.getText().trim().isEmpty()
                || createdByField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                return;
            }
    
            // Tạo câu hỏi mới
            QuestionDTO newQues = new QuestionDTO();
            newQues.setText(contentField.getText().trim());
            newQues.setDifficult((Enums.DifficultValue) diffBox.getSelectedItem());
            newQues.setChapter(chapterDAL.get((String) chapterBox.getSelectedItem()));
            newQues.setCreatedBy(createdByField.getText().trim());
    
            // Tạo danh sách đáp án
            ArrayList<AnswerDTO> answers = new ArrayList<>();
            String[] answerTexts = {
                answerA.getText().trim(),
                answerB.getText().trim(),
                answerC.getText().trim(),
                answerD.getText().trim()
            };

            String correct = (String) correctAnswerBox.getSelectedItem();
            for (int i = 0; i < 4; i++) {
                AnswerDTO ans = new AnswerDTO();
                ans.setText(answerTexts[i]); 
                ans.setRight(correct.equals(options[i]));  
                //ans.setLabel(options[i]);  
                answers.add(ans);
            }
    
            newQues.setAns(answers);
    
            // Thêm vào DB
            if (questionDAL.add(newQues)) {
                JOptionPane.showMessageDialog(this, "Question added successfully!");
                bank = questionDAL.getAll();
                loadQuestions();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add question.");
            }
        });
    
        cancelButton.addActionListener(e -> dialog.dispose());
    
        dialog.setVisible(true);
    }
    
}

