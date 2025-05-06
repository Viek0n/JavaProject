package GUI.giaodienadmin;

import DTO.AnswerDTO;
import DTO.ChapterDTO;
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
        
        // Search by question ID
        QuestionDTO question = questionDAL.getByID(keyword);
        if (question != null) {
            // Clear the table and display the found question
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[] {
                question.getID(), 
                question.getSubject() != null ? question.getSubject().getName() : "N/A", 
                question.getDifficult() != null ? question.getDifficult().toString() : "N/A",
                question.getChapter() != null ? question.getChapter().getName() : "N/A", 
                "Edit"
            });
        } else {
            JOptionPane.showMessageDialog(this, "No question found with this ID: " + keyword, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
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
    dialog.setSize(600, 600);
    dialog.setLocationRelativeTo(this);
    
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    
    ChapterDAL chapterDAL = new ChapterDAL();
    
    // Components
    JTextArea contentField = new JTextArea(3, 20);
    JComboBox<Enums.DifficultValue> diffBox = new JComboBox<>(Enums.DifficultValue.values());
    JComboBox<ChapterDTO> chapterBox = new JComboBox<>();
    JTextField createdByField = new JTextField();
    
    JTextField answerA = new JTextField();
    JTextField answerB = new JTextField();
    JTextField answerC = new JTextField();
    JTextField answerD = new JTextField();
    JComboBox<String> correctAnswerBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});
    
    chapterDAL.getAll().forEach(chapterBox::addItem); // Ensure ChapterDTO has meaningful toString()
    
    // Helper to add components
    int row = 0;
    gbc.gridx = 0; gbc.gridy = row;
    formPanel.add(new JLabel("Content:"), gbc);
    gbc.gridx = 1;
    formPanel.add(new JScrollPane(contentField), gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Difficulty:"), gbc);
    gbc.gridx = 1;
    formPanel.add(diffBox, gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Chapter:"), gbc);
    gbc.gridx = 1;
    formPanel.add(chapterBox, gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Created By (UserID):"), gbc);
    gbc.gridx = 1;
    formPanel.add(createdByField, gbc);

    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Answer A:"), gbc);
    gbc.gridx = 1;
    formPanel.add(answerA, gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Answer B:"), gbc);
    gbc.gridx = 1;
    formPanel.add(answerB, gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Answer C:"), gbc);
    gbc.gridx = 1;
    formPanel.add(answerC, gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Answer D:"), gbc);
    gbc.gridx = 1;
    formPanel.add(answerD, gbc);
    
    gbc.gridx = 0; gbc.gridy = ++row;
    formPanel.add(new JLabel("Correct Answer:"), gbc);
    gbc.gridx = 1;
    formPanel.add(correctAnswerBox, gbc);
    
    // Buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    buttonPanel.add(saveButton);
    buttonPanel.add(cancelButton);
    
    gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
    formPanel.add(buttonPanel, gbc);

    JScrollPane scrollPane = new JScrollPane(formPanel);
    dialog.setContentPane(scrollPane);

    // Save logic
    saveButton.addActionListener(e -> {
        if (contentField.getText().trim().isEmpty()
                || answerA.getText().trim().isEmpty()
                || answerB.getText().trim().isEmpty()
                || answerC.getText().trim().isEmpty()
                || answerD.getText().trim().isEmpty()
                || createdByField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
            return;
        }

        QuestionDTO newQues = new QuestionDTO();
        newQues.setText(contentField.getText().trim());
        newQues.setDifficult((Enums.DifficultValue) diffBox.getSelectedItem());
        newQues.setChapter((ChapterDTO) chapterBox.getSelectedItem());
        newQues.setCreatedBy(createdByField.getText().trim());

        ArrayList<AnswerDTO> answers = new ArrayList<>();
        String[] answerTexts = {
            answerA.getText().trim(),
            answerB.getText().trim(),
            answerC.getText().trim(),
            answerD.getText().trim()
        };

        String correct = (String) correctAnswerBox.getSelectedItem();
        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            AnswerDTO ans = new AnswerDTO();
            ans.setText(answerTexts[i]);
            ans.setRight(correct.equals(labels[i]));
            //ans.setLabel(labels[i]);
            answers.add(ans);
        }

        newQues.setAns(answers);

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
   dialog.setUndecorated(true);
    dialog.setVisible(true);
}

    
}

