package GUI.UserPanel;

import DAL.QuestionDAL;
import DTO.QuestionDTO;
import GUI.MakeColor.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class QuestionList extends JPanel{
    private ArrayList<QuestionDTO> bank;
    private QuestionDAL questionDAL;
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JPanel menuPanel;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    public QuestionList(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        init();
    }
    private void init() {
        questionDAL = new QuestionDAL();
        bank = questionDAL.getAll(); // Lấy danh sách câu hỏi từ cơ sở dữ liệu
        setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout để dễ quản lý
        setBackground(Ulti.MainColor);
    
        // Menu Panel (bên trái)
        menuPanel = new JPanel();
        menuPanel.setBackground(Ulti.MainColor);
        menuPanel.setPreferredSize(new Dimension(214, 900));
        menuPanel.setLayout(null);
    
        JLabel questionB = new JLabel("Quản Lý Câu Hỏi", JLabel.CENTER);
        questionB.setFont(new Font("Arial", Font.BOLD, 18));
        questionB.setBounds(7, 50, 200, 100);
        menuPanel.add(questionB);
    
        JButton questionButton = ButtonFactory.createConfirmButton(mainFrame, 32, 7, 50, 200, 100, Ulti.BananaLeaf, e -> {
            mainFrame.showPanel("QuestionListPanel");
        });
        menuPanel.add(questionButton);
    
        JLabel testB = new JLabel("Quản Lý Bài kiểm tra", JLabel.CENTER);
        testB.setFont(new Font("Arial", Font.BOLD, 18));
        testB.setBounds(7, 170, 200, 100);
        menuPanel.add(testB);
    
        JButton testButton = ButtonFactory.createConfirmButton(mainFrame, 32, 7, 170, 200, 100, Ulti.BananaLeaf, e -> {
            mainFrame.showPanel("TestListPanel");
        });
        menuPanel.add(testButton);
    
        JLabel userB = new JLabel("Quản Lý Người dùng", JLabel.CENTER);
        userB.setFont(new Font("Arial", Font.BOLD, 18));
        userB.setBounds(7, 290, 200, 100);
        menuPanel.add(userB);
    
        JButton userButton = ButtonFactory.createConfirmButton(mainFrame, 32, 7, 290, 200, 100, Ulti.BananaLeaf, e -> {
            mainFrame.showPanel("UserListPanel");
        });
        menuPanel.add(userButton);
    
        this.add(menuPanel, BorderLayout.WEST);
    
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Ability Panel (thanh công cụ trên cùng)
        JPanel abilityPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abilityPanel.setBackground(Color.white);
        abilityPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        abilityPanel.setPreferredSize(new Dimension(1386, 50));
        abilityPanel.setLayout(null);
    
        JLabel addLabel = new JLabel("Add", JLabel.CENTER);
        addLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addLabel.setBounds(10, 10, 100, 30);
        abilityPanel.add(addLabel);
    
        JButton addButton = ButtonFactory.createConfirmButton(mainFrame, 0, 10, 10, 100, 30, Ulti.MainColor, e -> {
            JOptionPane.showMessageDialog(this, "Add functionality not implemented yet!");
        });
        abilityPanel.add(addButton);
    
        JLabel deleteLabel = new JLabel("Delete", JLabel.CENTER);
        deleteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        deleteLabel.setBounds(130, 10, 100, 30);
        abilityPanel.add(deleteLabel);
    
        JButton deleteButton = ButtonFactory.createConfirmButton(mainFrame, 0, 130, 10, 100, 30, Ulti.MainColor, e -> {
            JOptionPane.showMessageDialog(this, "Delete functionality not implemented yet!");
        });
        abilityPanel.add(deleteButton);
    
        JLabel editLabel = new JLabel("Edit", JLabel.CENTER);
        editLabel.setFont(new Font("Arial", Font.BOLD, 14));
        editLabel.setBounds(250, 10, 100, 30);
        abilityPanel.add(editLabel);
    
        JButton editButton = ButtonFactory.createConfirmButton(mainFrame, 0, 250, 10, 100, 30, Ulti.MainColor, e -> {
            JOptionPane.showMessageDialog(this, "Edit functionality not implemented yet!");
        });
        abilityPanel.add(editButton);
    
        JTextField searchField = new RoundedTextField(20);
        searchField.setBounds(360, 10, 200, 30);
        abilityPanel.add(searchField);
        
        JLabel searchLabel = new JLabel("Search", JLabel.CENTER);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setBounds(570, 10, 100, 30);

        JButton searchButton = ButtonFactory.createConfirmButton(mainFrame, 0, 570, 10, 100, 30, Ulti.MainColor, e -> {
            String searchText = searchField.getText();
            if (searchText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Tìm kiếm: " + searchText, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        abilityPanel.add(searchButton);
    
        mainPanel.add(abilityPanel, BorderLayout.NORTH);
    
        // Table for displaying questions
        String[] columnNames = {"Question ID", "Content", "Subject", "Chapter", "Detail"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        questionTable.setRowHeight(30);
        questionTable.setFillsViewportHeight(true);
        questionTable.setBackground(Color.WHITE);
        questionTable.setGridColor(Color.GRAY);
        // Thêm nút vào cột "Detail"
        questionTable.getColumn("Detail").setCellRenderer(new ButtonRenderer());
        questionTable.getColumn("Detail").setCellEditor(new ButtonEditor(e -> {
            // Lấy thông tin hàng từ thuộc tính của nút
            JButton source = (JButton) e.getSource();
            int row = (int) source.getClientProperty("row");

            // Thực hiện hành động riêng cho từng hàng
            handleRowAction(row);
        }));
        JScrollPane scrollPane = new JScrollPane(questionTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        this.add(mainPanel, BorderLayout.CENTER);
        // Load questions into the table
        loadQuestions();
    }
    // Phương thức xử lý hành động riêng cho từng hàng
    private void handleRowAction(int row) {
        switch (row) {
            case 0:
                JOptionPane.showMessageDialog(this, "Action for row 0");
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "Action for row 1");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Default action for row " + row);
                break;
        }
    }
    private void loadQuestions() {
        tableModel.setRowCount(0); // Clear existing rows
        if (bank != null) {
            for (QuestionDTO question : bank) {
                tableModel.addRow(new Object[]{
                    question.getID(),
                    question.getText(),
                    question.getSubject().getName(),
                    question.getChapter().getName(),
                    "Detail", // Placeholder for detail, replace with actual detail if available
                });
            }
        }
    }
}

