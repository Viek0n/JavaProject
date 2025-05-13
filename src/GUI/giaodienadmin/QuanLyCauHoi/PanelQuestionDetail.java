package GUI.giaodienadmin.QuanLyCauHoi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import BLL.UserBLL;

import java.awt.*;
import java.util.ArrayList;
import DAL.AnswerDAL;
import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;

public class PanelQuestionDetail extends JPanel {
    private JLabel questionIDLabel;
    private JTextField contentField;
    private JTable answerTable;
    private DefaultTableModel tableModel;
    private JButton saveButton, backButton;
    private String currentQuestionID;
    private QuestionManagementPanel questionManagementPanel;
    private JPanel mainPanel;


    public PanelQuestionDetail(QuestionManagementPanel questionManagementPanel, JPanel mainPanel) {
        this.questionManagementPanel = questionManagementPanel;
        this.mainPanel = mainPanel;

        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#f0f4f8")); // Light background

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(Color.decode("#e0e0e0")); // Light gray
        questionIDLabel = new JLabel("Question ID: ");
        questionIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(questionIDLabel);
        this.add(topPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentField = new JTextField();
        contentField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPanel.add(contentField, BorderLayout.CENTER);
        centerPanel.add(contentPanel, BorderLayout.NORTH);

        // Answer table
        tableModel = new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Only the "Correct" column is editable
            }
        };
        answerTable = new JTable(tableModel);
        answerTable.setRowHeight(35);
        answerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        answerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(answerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        bottomPanel.setBackground(Color.decode("#e0e0e0"));
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(Color.decode("#4caf50")); // Green
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.decode("#f44336")); // Red
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Back button action: Return to QuestionManagementPanel
        backButton.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(questionManagementPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        saveButton.addActionListener(e -> saveQuestionContent());

        // Set up the "Correct" column editor and renderer
        TableColumn correctColumn = answerTable.getColumnModel().getColumn(1);
        correctColumn.setCellEditor(new DefaultCellEditor(createCorrectComboBox()));
        correctColumn.setCellRenderer(new CorrectCellRenderer());
    }

    public void loadQuestionDetails(String questionID) {
        currentQuestionID = questionID;
        QuestionDAL questionDAL = new QuestionDAL();
        QuestionDTO question = questionDAL.getByID(questionID);
        if (question != null) {
            questionIDLabel.setText("Question ID: " + questionID);
            contentField.setText(question.getText());
            AnswerDAL answerDAL = new AnswerDAL();
            ArrayList<AnswerDTO> answers = answerDAL.getAllByQId(questionID);
            tableModel.setRowCount(0);
            for (AnswerDTO answer : answers) {
                tableModel.addRow(new Object[]{
                        answer.getText(),
                        answer.getRight() ? "Yes" : "No"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No question found with this ID: " + questionID, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveQuestionContent() {
        String newText = contentField.getText().trim();
        if (newText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        QuestionDAL questionDAL = new QuestionDAL();
        QuestionDTO updated = questionDAL.getByID(currentQuestionID);
        if (updated == null) {
            JOptionPane.showMessageDialog(this, "Question not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        updated.setText(newText);

        // Validate and update answers
        ArrayList<AnswerDTO> newAnswers = new ArrayList<>();
        boolean hasCorrectAnswer = false;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String answerText = (String) tableModel.getValueAt(row, 0);
            String isCorrectStr = (String) tableModel.getValueAt(row, 1);
            boolean isCorrect = "Yes".equals(isCorrectStr);

            if (answerText == null || answerText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Answer text cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (isCorrect) {
                hasCorrectAnswer = true;
            }

            AnswerDTO answer = new AnswerDTO();
            answer.setText(answerText);
            answer.setRight(isCorrect);
            newAnswers.add(answer);
        }

        if (!hasCorrectAnswer) {
            JOptionPane.showMessageDialog(this, "At least one answer must be marked as correct!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        updated.setAns(newAnswers);

        boolean success = questionDAL.update(updated);
        if (success) {
            JOptionPane.showMessageDialog(this, "Question and answers updated successfully.");
            // Refresh QuestionManagementPanel after saving
            questionManagementPanel.loadQuestions();
            // Return to QuestionManagementPanel
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(questionManagementPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update question!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JComboBox<String> createCorrectComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Yes", "No"});
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        return comboBox;
    }

    private class CorrectCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ("Yes".equals(value)) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
}