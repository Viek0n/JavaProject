package GUI.giaodienadmin.QuanLyCauHoi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.logging.Logger;
import DAL.AnswerDAL;
import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import java.util.concurrent.ExecutionException;

public class PanelQuestionDetail extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(PanelQuestionDetail.class.getName());

    private JLabel questionIDLabel;
    private JTextField contentField;
    private JTable answerTable;
    private DefaultTableModel tableModel;
    private JButton saveButton, backButton;
    private String currentQuestionID;
    private QuestionManagementPanel questionManagementPanel;
    private JPanel mainPanel;
    private JFrame parentFrame;
    private boolean isModified; // Tracks if changes have been made

    public PanelQuestionDetail(QuestionManagementPanel questionManagementPanel, JPanel mainPanel, JFrame parentFrame) {
        this.questionManagementPanel = questionManagementPanel;
        this.mainPanel = mainPanel;
        this.parentFrame = parentFrame;
        this.isModified = false;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.PANEL_BACKGROUND);
      
        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        topPanel.setBackground(UIConstants.TOP_PANEL_BACKGROUND);
        questionIDLabel = new JLabel("Question ID: ");
        questionIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(questionIDLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentField = new JTextField();
        contentField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                isModified = true;
            }
        });
        contentPanel.add(contentField, BorderLayout.CENTER);
        centerPanel.add(contentPanel, BorderLayout.NORTH);

        // Answer table
        tableModel = new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Both columns are editable
            }
        };
        answerTable = new JTable(tableModel);
        answerTable.setRowHeight(35);
        answerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        answerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        answerTable.getTableHeader().setBackground(UIConstants.TABLE_HEADER_BG);

        // Set column widths
        answerTable.getColumnModel().getColumn(0).setPreferredWidth(400); // Answer text
        answerTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Correct

        // Set up editors and renderers
        TableColumn answerColumn = answerTable.getColumnModel().getColumn(0);
        JTextField answerEditor = new JTextField();
        answerEditor.setFont(new Font("Arial", Font.PLAIN, 14));
        answerEditor.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        answerColumn.setCellEditor(new DefaultCellEditor(answerEditor) {
            @Override
            public boolean stopCellEditing() {
                isModified = true;
                return super.stopCellEditing();
            }
        });
        answerColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Arial", Font.PLAIN, 14));
                return label;
            }
        });

        TableColumn correctColumn = answerTable.getColumnModel().getColumn(1);
        correctColumn.setCellEditor(new DefaultCellEditor(createCorrectComboBox()) {
            @Override
            public boolean stopCellEditing() {
                isModified = true;
                return super.stopCellEditing();
            }
        });
        correctColumn.setCellRenderer(new CorrectCellRenderer());

        JScrollPane scrollPane = new JScrollPane(answerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        bottomPanel.setBackground(UIConstants.TOP_PANEL_BACKGROUND);
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(UIConstants.SAVE_BUTTON_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(new QuestionManagementPanel.RoundedBorder(8));

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(UIConstants.BACK_BUTTON_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new QuestionManagementPanel.RoundedBorder(8));

        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        saveButton.addActionListener(e -> saveQuestionContent());
        backButton.addActionListener(e -> handleBackAction());
    }

    public void loadQuestionDetails(String questionID) {
        currentQuestionID = questionID;
        isModified = false;
        SwingWorker<QuestionDTO, Void> worker = new SwingWorker<>() {
            @Override
            protected QuestionDTO doInBackground() {
                QuestionDAL questionDAL = new QuestionDAL();
                return questionDAL.getByID(questionID);
            }

            @Override
            protected void done() {
                try {
                    QuestionDTO question = get();
                    if (question != null) {
                        questionIDLabel.setText("Question ID: " + questionID);
                        contentField.setText(question.getText());
                        loadAnswers(questionID);
                    } else {
                        JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                            "No question found with ID: " + questionID, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.severe("Error loading question: " + e.getMessage());
                    JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                        "Error loading question: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void loadAnswers(String questionID) {
        SwingWorker<ArrayList<AnswerDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected ArrayList<AnswerDTO> doInBackground() {
                AnswerDAL answerDAL = new AnswerDAL();
                return answerDAL.getAllByQId(questionID);
            }

            @Override
            protected void done() {
                try {
                    ArrayList<AnswerDTO> answers = get();
                    tableModel.setRowCount(0);
                    if (answers != null && answers.size() == 4) {
                        for (AnswerDTO answer : answers) {
                            tableModel.addRow(new Object[]{
                                answer.getText(),
                                answer.getRight() ? "Yes" : "No",
                                answer.getText() // Store ID in hidden column or elsewhere
                            });
                        }
                    } else {
                        JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                            "Invalid number of answers for question ID: " + questionID, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.severe("Error loading answers: " + e.getMessage());
                    JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                        "Error loading answers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void saveQuestionContent() {
        String newText = contentField.getText().trim();
        if (newText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate answers
        ArrayList<AnswerDTO> newAnswers = new ArrayList<>();
        boolean hasCorrectAnswer = false;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String answerText = ((String) tableModel.getValueAt(row, 0)).trim();
            String isCorrectStr = (String) tableModel.getValueAt(row, 1);
            boolean isCorrect = "Yes".equals(isCorrectStr);

            if (answerText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Answer text cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (isCorrect) {
                hasCorrectAnswer = true;
            }

            AnswerDTO answer = new AnswerDTO();
            answer.setText(answerText);
            answer.setRight(isCorrect);
            // Note: Answer ID should be set if available from the database
            newAnswers.add(answer);
        }

        if (tableModel.getRowCount() != 4) {
            JOptionPane.showMessageDialog(this, "Exactly 4 answers are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!hasCorrectAnswer) {
            JOptionPane.showMessageDialog(this, "At least one answer must be marked as correct!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    QuestionDAL questionDAL = new QuestionDAL();
                    QuestionDTO updated = questionDAL.getByID(currentQuestionID);
                    if (updated == null) {
                        return false;
                    }
                    updated.setText(newText);
                    updated.setAns(newAnswers);
                    return questionDAL.update(updated);
                } catch (Exception e) {
                    LOGGER.severe("Error updating question: " + e.getMessage());
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                            "Question and answers updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        isModified = false;
                        questionManagementPanel.loadQuestions();
                        if (parentFrame != null) {
                            parentFrame.dispose();
                        }
                        restoreQuestionManagementPanel();
                    } else {
                        JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                            "Failed to update question!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.severe("Error saving question: " + e.getMessage());
                    JOptionPane.showMessageDialog(PanelQuestionDetail.this,
                        "Error saving question: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void handleBackAction() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this,
                "You have unsaved changes. Do you want to save before exiting?",
                "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                saveQuestionContent();
                return; // Save action will handle closing
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return; // Stay in the panel
            }
        }

        if (parentFrame != null) {
            parentFrame.dispose();
        }
        restoreQuestionManagementPanel();
    }

    private void restoreQuestionManagementPanel() {
     
        questionManagementPanel.loadQuestions();
        questionManagementPanel.setVisible(true);
        questionManagementPanel.loadQuestions();
        mainPanel.revalidate();
        mainPanel.repaint();
    
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

// UIConstants class for color management
class UIConstants {
    public static final Color PANEL_BACKGROUND = Color.decode("#f0f4f8");
    public static final Color TOP_PANEL_BACKGROUND = Color.decode("#e0e0e0");
    public static final Color TABLE_HEADER_BG = Color.decode("#f0f4f8");
    public static final Color SAVE_BUTTON_COLOR = Color.decode("#4caf50");
    public static final Color BACK_BUTTON_COLOR = Color.decode("#f44336");
}