package GUI.giaodienadmin;

import DAL.QuestionDAL;
import DTO.QuestionDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class QuestionManagementPanel extends JPanel implements ActionListener {
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private ArrayList<QuestionDTO> bank;
    private QuestionDAL questionDAL;
    public QuestionManagementPanel() {
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

        // Table for displaying questions
        String[] columnNames = {"Question ID", "Content", "Subject", "Chapter", "Detail"};
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
        editButton.addActionListener(this);
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

    private void addQuestion() {
        JOptionPane.showMessageDialog(this, "Add Question functionality not implemented yet!");
    }

    private void editQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to edit!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Edit Question functionality not implemented yet!");
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
        // Logic to search for questions based on the keyword
        JOptionPane.showMessageDialog(this, "Search functionality not implemented yet!");
    }


    public void loadQuestions() {
        tableModel.setRowCount(0);
        for (QuestionDTO row : bank) {
            tableModel.insertRow(0, new Object[] {row.getID(),row.getText(),row.getSubject().getName(),row.getChapter().getName()});
        }
    }
}