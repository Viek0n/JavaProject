package GUI.giaodienadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAL.AnswerDAL;
import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;

import java.awt.*;
import java.util.ArrayList;

public class PanelExemDetail extends JPanel {
    private JLabel questionIDLabel;
    private JTextField contentField;
    private JTable answerTable;
    private DefaultTableModel tableModel;
    private JButton saveButton, backButton;

    private String currentQuestionID;

    public PanelExemDetail(JPanel mainPanel, CardLayout cardLayout) {
        this.setLayout(new BorderLayout(10, 10));

        // Top: Question ID
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        questionIDLabel = new JLabel("Question ID: ");
        topPanel.add(questionIDLabel);

        // Center: Content + Answer Table
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Content:"), BorderLayout.NORTH);
        contentField = new JTextField();
        contentPanel.add(contentField, BorderLayout.CENTER);

        // Table: Answer content and correct flag
        tableModel = new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table is read-only
            }
        };
        answerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(answerTable);

        centerPanel.add(contentPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        backButton = new JButton("Back");
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);

        // Add all
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "QuanLyCauHoi"));

        saveButton.addActionListener(e -> saveQuestionContent());
    }

    // Load question info and answers into UI
    public void loadQuestionDetails(String questionID) {
        currentQuestionID = questionID;

        QuestionDAL questionDAL = new QuestionDAL();
        QuestionDTO question = questionDAL.getByID(questionID);

        if (question != null) {
            questionIDLabel.setText("Question ID: " + questionID);
            contentField.setText(question.getText());

            // Load answers
            AnswerDAL answerDAL = new AnswerDAL();
            ArrayList<AnswerDTO> answers = answerDAL.getAllByQId(questionID);

            tableModel.setRowCount(0); // Clear old data
            for (AnswerDTO answer : answers) {
                tableModel.addRow(new Object[]{
                    answer.getText(),
                    answer.getRight() ? "Yes" : "No"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Question not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save question content
    private void saveQuestionContent() {
        String newText = contentField.getText().trim();
        if (newText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        QuestionDAL questionDAL = new QuestionDAL();
        QuestionDTO updated = new QuestionDTO();
        updated.setID(currentQuestionID);
        updated.setText(newText);

        boolean success = questionDAL.update(updated);
        if (success) {
            JOptionPane.showMessageDialog(this, "Question updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update question.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
