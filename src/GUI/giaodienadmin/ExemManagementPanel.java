package GUI.giaodienadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExemManagementPanel extends JPanel implements ActionListener {
    private JTable examTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;

    public ExemManagementPanel() {
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.decode("#ecf0f1"));

        // Top panel for buttons and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.decode("#bdc3c7"));

        addButton = createButton("Add");
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
            addExam();
        } else if (e.getSource() == editButton) {
            editExam();
        } else if (e.getSource() == deleteButton) {
            deleteExam();
        } else if (e.getSource() == searchButton) {
            searchExam();
        }
    }

    private void addExam() {
        // Logic to add a new exam
        new AddExamDialog(this).setVisible(true);
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
        // Logic to search for exams based on the keyword
        JOptionPane.showMessageDialog(this, "Search functionality not implemented yet!");
    }

    // Method to populate the table with data (for demonstration purposes)
    public void loadExams(Object[][] data) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
}