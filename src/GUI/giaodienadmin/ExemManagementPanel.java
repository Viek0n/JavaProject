package GUI.giaodienadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ExemManagementPanel extends JPanel implements ActionListener {
    private JTable examTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExemManagementPanel() {
        initComponent();
    }

    private void initComponent() {
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
            //addExamToTable(); // Removed the call to the empty addExamToTable()
            showAddExamDialog(); //show the dialog.
        } else if (e.getSource() == editButton) {
            editExam();
        } else if (e.getSource() == deleteButton) {
            deleteExam();
        } else if (e.getSource() == searchButton) {
            searchExam();
        }
    }

    // Method to add a new exam to the table
    public void addExamToTable(Object[] rowData) {
        // Perform data validation before adding to the table
        if (rowData == null || rowData.length != 5) {
            JOptionPane.showMessageDialog(this, "Invalid data format. Expected 5 values.");
            return; // Important: Exit if data is invalid
        }

        String examId = (String) rowData[0];
        String examName = (String) rowData[1];
        String startDateStr = (String) rowData[2];
        String endDateStr = (String) rowData[3];
        Integer duration;

        //check if the duration is an Integer.
        try {
            duration = Integer.parseInt((String) rowData[4]);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid duration format.  Must be a number");
            return;
        }
        // Further data validation (e.g., date format, ID uniqueness) can be added here
        if (examId == null || examId.trim().isEmpty() || examName == null || examName.trim().isEmpty() || startDateStr == null || startDateStr.trim().isEmpty() || endDateStr == null || endDateStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Exam ID, Name, Start Date and End Date cannot be empty.");
            return;
        }

        try {
            LocalDate.parse(startDateStr, DATE_FORMATTER);
            LocalDate.parse(endDateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format.  Please use YYYY-MM-DD");
            return;
        }

        // Add the row to the table model
        tableModel.addRow(rowData);
        JOptionPane.showMessageDialog(this, "Exam added successfully!");
    }

    private void editExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exam to edit!");
            return;
        }
        // Logic to edit the selected exam
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
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Search functionality not implemented yet!");
    }

    public void loadExams(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void showAddExamDialog() {
        AddExamDialog dialog = new AddExamDialog(this); // Pass the parent panel
        dialog.setVisible(true);
    }
}

