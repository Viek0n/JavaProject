package GUI.giaodienadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;


import java.util.ArrayList;
import DAL.AnswerDAL;
import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;

public class PanelExemDetail extends JPanel {
    private JLabel questionIDLabel;
    private JTextField contentField;
    private JTable answerTable;
    private DefaultTableModel tableModel;
    private JButton saveButton, backButton;
    private String currentQuestionID;


    public PanelExemDetail(JPanel mainPanel, CardLayout cardLayout) {

        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#f0f4f8")); // Màu nền nhạt

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(Color.decode("#e0e0e0")); // Màu xám nhạt
        questionIDLabel = new JLabel("Question ID: ");
        questionIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(questionIDLabel);

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
                return column == 1;
            }
        };
        answerTable = new JTable(tableModel);
        answerTable.setRowHeight(35);
        answerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        answerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(answerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        bottomPanel.setBackground(Color.decode("#e0e0e0"));
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(Color.decode("#4caf50")); // Màu xanh lá
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.decode("#f44336")); // Màu đỏ
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "QuanLyCauHoi"));
        saveButton.addActionListener(e -> saveQuestionContent());
        TableColumn correctColumn = answerTable.getColumnModel().getColumn(1);
        correctColumn.setCellEditor(new DefaultCellEditor(createCorrectComboBox()));
        correctColumn.setCellRenderer(new CorrectCellRenderer()); // Sử dụng renderer tùy chỉnh
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
    QuestionDTO updated = questionDAL.getByID(currentQuestionID); // Lấy lại đầy đủ dữ liệu
    if (updated == null) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    updated.setText(newText);

    // Lấy danh sách câu trả lời mới từ bảng
    ArrayList<AnswerDTO> newAnswers = new ArrayList<>();
    for (int row = 0; row < tableModel.getRowCount(); row++) {
        String answerText = (String) tableModel.getValueAt(row, 0);
        String isCorrectStr = (String) tableModel.getValueAt(row, 1);
        boolean isCorrect = "Yes".equals(isCorrectStr);
        AnswerDTO answer = new AnswerDTO();
        answer.setText(answerText);
        answer.setRight(isCorrect);
        newAnswers.add(answer);
    }
    updated.setAns(newAnswers); // Cập nhật danh sách câu trả lời

    boolean success = questionDAL.update(updated);
    if (success) {
        JOptionPane.showMessageDialog(this, "Cập nhật câu hỏi và đáp án thành công.");
    } else {
        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    private JComboBox<String> createCorrectComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Yes", "No"});
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        return comboBox;
    }

    // Custom cell renderer for "Correct" column
    private class CorrectCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ("Yes".equals(value)) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            label.setFont(new Font("Arial", Font.BOLD, 14)); // In đậm chữ Yes/No
            label.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa chữ
            return label;
        }
    }
}
