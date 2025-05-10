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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
        this.setBackground(Color.decode("#f0f4f8"));

        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(Color.decode("#e0e0e0"));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        addButton = createButton("+ THÊM MỚI");
        addButton.setBackground(Color.decode("#4caf50")); // Green
        addButton.setForeground(Color.WHITE);
        editButton = createButton("Edit");
        editButton.setBackground(Color.decode("#008cba"));  // Blue
        editButton.setForeground(Color.WHITE);
        deleteButton = createButton("Delete");
        deleteButton.setBackground(Color.decode("#f44336")); // Red
        deleteButton.setForeground(Color.WHITE);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton = createButton("Search");
        searchButton.setBackground(Color.decode("#ffc107")); // Yellow
        searchButton.setForeground(Color.BLACK);

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        this.add(topPanel, BorderLayout.NORTH);

        // Table for displaying questions
        String[] columnNames = {"Question ID", "Subject", "Level", "Chapter", "Detail"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        questionTable.setRowHeight(35);
        questionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        questionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        questionTable.setFillsViewportHeight(true);
        questionTable.setBackground(Color.WHITE);
        questionTable.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(questionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        loadQuestions();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(8));
        return button;
    }

    // Rounded border class
    private static class RoundedBorder extends LineBorder {
        private int radius;

        public RoundedBorder(int radius) {
            super(Color.GRAY);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getLineColor());
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 2, this.radius + 2, this.radius + 2, this.radius + 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
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
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Question deleted successfully!");
        }
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
            tableModel.addRow(new Object[]{
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
                tableModel.addRow(new Object[]{
                        row.getID(), 
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
            setFont(new Font("Arial", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setBackground(Color.decode("#008cba"));
            setBorder(new RoundedBorder(8));
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
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.decode("#008cba"));
            button.setBorder(new RoundedBorder(8));
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
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        ChapterDAL chapterDAL = new ChapterDAL();

        // Components
        JTextArea contentField = new JTextArea(3, 20);
        contentField.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane contentScrollPane = new JScrollPane(contentField);
        contentScrollPane.setBorder(new RoundedBorder(8));

        JComboBox<Enums.DifficultValue> diffBox = new JComboBox<>(Enums.DifficultValue.values());
        diffBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel)diffBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        JComboBox<ChapterDTO> chapterBox = new JComboBox<>();
        chapterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel)chapterBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);


        JTextField createdByField = new JTextField();
        createdByField.setFont(new Font("Arial", Font.PLAIN, 14));
        createdByField.setBorder(new RoundedBorder(8));


        JTextField answerA = new JTextField();
        answerA.setFont(new Font("Arial", Font.PLAIN, 14));
        answerA.setBorder(new RoundedBorder(8));
        JTextField answerB = new JTextField();
        answerB.setFont(new Font("Arial", Font.PLAIN, 14));
        answerB.setBorder(new RoundedBorder(8));
        JTextField answerC = new JTextField();
        answerC.setFont(new Font("Arial", Font.PLAIN, 14));
        answerC.setBorder(new RoundedBorder(8));
        JTextField answerD = new JTextField();
        answerD.setFont(new Font("Arial", Font.PLAIN, 14));
        answerD.setBorder(new RoundedBorder(8));
        JComboBox<String> correctAnswerBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        correctAnswerBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel)correctAnswerBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);


        chapterDAL.getAll().forEach(chapterBox::addItem); // Ensure ChapterDTO has meaningful toString()

        // Helper to add components
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(contentLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(contentScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel diffLabel = new JLabel("Difficulty:");
        diffLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(diffLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(diffBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel chapterLabel = new JLabel("Chapter:");
        chapterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(chapterLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(chapterBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel createdByLabel = new JLabel("Created By (UserID):");
        createdByLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(createdByLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createdByField, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerALabel = new JLabel("Answer A:");
        answerALabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerALabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerA, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerBLabel = new JLabel("Answer B:");
        answerBLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerBLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerB, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerCLabel = new JLabel("Answer C:");
        answerCLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerCLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerC, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerDLabel = new JLabel("Answer D:");
        answerDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerDLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerD, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(correctAnswerLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(correctAnswerBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(Color.decode("#4caf50")); // Green
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(new RoundedBorder(8));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.decode("#f44336")); // Red
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(new RoundedBorder(8));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
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

