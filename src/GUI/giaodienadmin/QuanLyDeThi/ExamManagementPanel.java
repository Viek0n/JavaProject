package GUI.giaodienadmin.QuanLyDeThi;

import DAL.ExamStructDAL;
import DTO.ExamStructDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExamManagementPanel extends JPanel implements ActionListener {
    private JTable examTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton, clearSearchButton;
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private ExamStructDAL examStructDAL;
    private ArrayList<ExamStructDTO> exams;
    private AddExamDialog dialog;

    public ExamManagementPanel() {
        examStructDAL = new ExamStructDAL();
        exams = examStructDAL.getAll();
        
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#ecf0f1"));

        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.decode("#bdc3c7"));

        addButton = createButton("Add New");
        addButton.setBackground(Color.decode("#27ae60"));
        addButton.setToolTipText("Add a new exam");
        editButton = createButton("Edit");
        editButton.setBackground(Color.decode("#f39c12"));
        editButton.setToolTipText("Edit selected exam");
        deleteButton = createButton("Delete");
        deleteButton.setBackground(Color.decode("#c0392b"));
        deleteButton.setToolTipText("Delete selected exam");
        searchField = new JTextField(20);
        searchField.setToolTipText("Enter keyword to search exams");
        searchButton = createButton("Search");
        searchButton.setToolTipText("Search exams by ID, name, or dates");
        clearSearchButton = createButton("Clear Search");
        clearSearchButton.setToolTipText("Restore full exam list");

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearSearchButton);

        this.add(topPanel, BorderLayout.NORTH);

        // Table for displaying exams
        String[] columnNames = {"Exam ID", "Exam Name", "Start Date", "End Date", "Duration", "Subject", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        examTable = new JTable(tableModel);
        examTable.setRowHeight(30);
        examTable.setFillsViewportHeight(true);
        examTable.setBackground(Color.WHITE);
        examTable.setGridColor(Color.GRAY);
        examTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(examTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        clearSearchButton.addActionListener(this);

        // Load initial data
        loadExamData();
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
            showAddExamDialog();
        } else if (e.getSource() == editButton) {
            editExam();
        } else if (e.getSource() == deleteButton) {
            deleteExam();
        } else if (e.getSource() == searchButton) {
            searchExam();
        } else if (e.getSource() == clearSearchButton) {
            loadExamData();
            searchField.setText("");
        }
    }

    private void showAddExamDialog() {
    try {
        AddExamDialog dialog = new AddExamDialog(this, examStructDAL);
        dialog.setVisible(true);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error opening Add Exam Dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

    private void editExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exam to edit!");
            return;
        }

        String examId = (String) tableModel.getValueAt(selectedRow, 0);
        ExamStructDTO exam = exams.stream()
                .filter(e -> e.getID().equals(examId))
                .findFirst()
                .orElse(null);

        if (exam == null) {
            JOptionPane.showMessageDialog(this, "Selected exam not found!");
            return;
        }

        EditExamDialog dialog = new EditExamDialog(this, exam);
        dialog.setVisible(true);
    }

    private void deleteExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exam to delete!");
            return;
        }

        String examId = (String) tableModel.getValueAt(selectedRow, 0);
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete exam " + examId + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (examStructDAL.delete(examId)) {
                    tableModel.removeRow(selectedRow);
                    exams = examStructDAL.getAll();
                    JOptionPane.showMessageDialog(this, "Exam deleted successfully!");
                    loadExamData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete exam!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }
    }

    private void searchExam() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
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
                    exam.getDesc() != null ? exam.getDesc() : "No description"
            });
        }
    }
}