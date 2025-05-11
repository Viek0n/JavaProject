package GUI.giaodienadmin;

import DAL.ExamStructDAL;
import DTO.ExamStructDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class ExemManagementPanel extends JPanel implements ActionListener {
    private JTable examTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ExamStructDAL examStructDAL;

    public ExemManagementPanel() {
        initComponent();
    }

    private void initComponent() {
        examStructDAL = new ExamStructDAL();
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#ecf0f1"));

        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.decode("#bdc3c7"));

        addButton = createButton("+ THÊM MỚI");
        addButton.setBackground(Color.decode("#003f5c"));
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        editButton = createButton("Edit");
        deleteButton = createButton("Delete");
        searchField = new JTextField(20);
        searchButton = createButton("Search");

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        this.add(topPanel, BorderLayout.NORTH);

        // Table for displaying exams
        String[] columnNames = {"Exam ID", "Exam Name", "Start Date", "End Date", "Duration"};
        tableModel = new DefaultTableModel(columnNames, 0);
        examTable = new JTable(tableModel);
        examTable.setRowHeight(30);
        examTable.setFillsViewportHeight(true);
        examTable.setBackground(Color.WHITE);
        examTable.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(examTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);

        // Load initial data
        loadExams();
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
        }
    }

    public void addExamToTable(Object[] rowData) {
        if (rowData == null || rowData.length != 5) {
            JOptionPane.showMessageDialog(this, "Invalid data format. Expected 5 values.");
            return;
        }

        String examId = (String) rowData[0];
        String examName = (String) rowData[1];
        String startDateStr = (String) rowData[2];
        String endDateStr = (String) rowData[3];
      
       


        
        if (examId == null || examId.trim().isEmpty() || examName == null || examName.trim().isEmpty() ||
            startDateStr == null || startDateStr.trim().isEmpty() || endDateStr == null || endDateStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Exam ID, Name, Start Date, and End Date cannot be empty.");
            return;
        }

        try {
            LocalDate.parse(startDateStr, DATE_FORMATTER);
            LocalDate.parse(endDateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        tableModel.addRow(rowData);
        JOptionPane.showMessageDialog(this, "Exam added successfully!");
    }

    private void editExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exam to edit!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Edit Exam functionality not implemented yet!");
    }

    private void deleteExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exam to delete!");
            return;
        }

        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Exam deleted successfully!");
    }

    private void searchExam() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
            return;
        }

        DefaultTableModel filteredModel = new DefaultTableModel(new String[]{
            "Exam ID", "Exam Name", "Start Date", "End Date", "Duration"
        }, 0);

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            boolean match = false;
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                if (value != null && value.toString().toLowerCase().contains(keyword)) {
                    match = true;
                    break;
                }
            }
            if (match) {
                Object[] row = new Object[tableModel.getColumnCount()];
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    row[j] = tableModel.getValueAt(i, j);
                }
                filteredModel.addRow(row);
            }
        }

        examTable.setModel(filteredModel);
    }

    public void loadExams() {
        tableModel.setRowCount(0);

        List<ExamStructDTO> exams = examStructDAL.getAll();
        for (ExamStructDTO exam : exams) {
            tableModel.addRow(new Object[]{
               exam.getID(),
                exam.getName(),
                exam.getStart(),
                exam.getEnd(),
                exam.getExamTime()
            });
        }
    }

    private void showAddExamDialog() {
        AddExamDialog dialog = new AddExamDialog(this);
        dialog.setVisible(true);
    }
}