package GUI.giaodienadmin.QuanLyCauHoi;

import DAL.*;
import DTO.AnswerDTO;
import DTO.ChapterDTO;
import DTO.QuestionDTO;
import GUI.MakeColor.Ulti;
import MICS.Enums;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import BLL.UserBLL;

public class QuestionManagementPanel extends JPanel implements ActionListener {
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton, backButton, filterButton;
    private ArrayList<QuestionDTO> bank;
    private QuestionDAL questionDAL;
    private JPanel mainPanel; // Parent panel to swap content
    private JComboBox<Enums.DifficultValue> diffFilterBox;
    private JComboBox<ChapterDTO> chapterFilterBox;
    private boolean isSearchMode = false;
    private List<QuestionDTO> originalBank;
    private JPanel filterPanel;
    private JDialog addQuestionDialog;
    private JPopupMenu popupMenu;
    private int selectedRow;
    private PanelQuestionDetail panelExemDetail;
    private UserBLL userBLL;

    public QuestionManagementPanel(JPanel mainPanel,UserBLL userBLL) {
        this.mainPanel = mainPanel;
        this.userBLL=userBLL;
        initComponent();
    }

    private void initComponent() {
        questionDAL = new QuestionDAL();
        bank = questionDAL.getAll();
        originalBank = new ArrayList<>(bank);
        this.setLayout(new BorderLayout());
        this.setBackground(Ulti.MainColor);
        
        this.add(mainPanel, BorderLayout.WEST);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        topPanel.setBackground(Color.decode("#e0e0e0"));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        addButton = createButton("+ THÊM MỚI");
        addButton.setBackground(Color.decode("#4caf50"));
        addButton.setForeground(Color.WHITE);
        editButton = createButton("Sửa");
        editButton.setBackground(Color.decode("#008cba"));
        editButton.setForeground(Color.WHITE);
        deleteButton = createButton("Xóa");
        deleteButton.setBackground(Color.decode("#f44336"));
        deleteButton.setForeground(Color.WHITE);

        // Initialize searchField
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(new RoundedBorder(8));

        // Create a panel for the search button
        JPanel searchButtonPanel = new JPanel();
        searchButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Create the search button
        searchButton = createButton("Tìm kiếm");
        searchButton.setBackground(Color.decode("#ffc107"));
        searchButton.setForeground(Color.BLACK);
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setBorder(new RoundedBorder(8));

        // Add the button to the panel
        searchButtonPanel.add(searchButton);

        // Add the panel to the search field
        searchField.setLayout(new BorderLayout());
        searchField.add(searchButtonPanel, BorderLayout.EAST);

        backButton = createButton("Trở lại");
        backButton.setBackground(Color.decode("#808080"));
        backButton.setForeground(Color.WHITE);
        backButton.setVisible(false);
        backButton.addActionListener(this);

        // Initialize filter button with icon
        filterButton = new JButton("Lọc");
        filterButton.setBackground(Color.decode("#2196f3"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        filterButton.setBorder(new RoundedBorder(8));
        filterButton.addActionListener(this);

        diffFilterBox = new JComboBox<>(Enums.DifficultValue.values());
        diffFilterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel) diffFilterBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        diffFilterBox.setBorder(new RoundedBorder(8));

        ChapterDAL chapterDAL = new ChapterDAL();
        chapterFilterBox = new JComboBox<>();
        chapterFilterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel) chapterFilterBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        chapterDAL.getAll().forEach(chapterFilterBox::addItem);
        chapterFilterBox.setBorder(new RoundedBorder(8));

        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.add(diffFilterBox);
        filterPanel.add(chapterFilterBox);
        filterPanel.setVisible(false);

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(backButton);
        topPanel.add(filterButton);
        topPanel.add(filterPanel);

    

        String[] columnNames = {"Mã câu hỏi", "Môn học", "Độ khó", "Chương", "Chi tiết"};
        /*questionTable = new JTable(tableModel);
        questionTable.setRowHeight(35);
        questionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        questionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        questionTable.setFillsViewportHeight(true);
        questionTable.setBackground(Color.WHITE);
        questionTable.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(questionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);
        this.add(scrollPane, BorderLayout.CENTER);*/
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        questionTable.setRowHeight(35);
        questionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JTableHeader tableHeader = questionTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#f0f4f8"));
        tableHeader.setForeground(Color.BLACK);
        questionTable.setFillsViewportHeight(true);
        questionTable.setBackground(Color.WHITE);
        questionTable.setGridColor(Color.LIGHT_GRAY);
        questionTable.setSelectionBackground(Color.decode("#b0e0e6"));
        questionTable.setSelectionForeground(Color.BLACK);

        questionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        questionTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        questionTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        questionTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        questionTable.getColumnModel().getColumn(4).setPreferredWidth(80);

        questionTable.getColumnModel().getColumn(0).setCellRenderer(new CustomLabelRenderer());
        questionTable.getColumnModel().getColumn(1).setCellRenderer(new CustomLabelRenderer());
        questionTable.getColumnModel().getColumn(2).setCellRenderer(new CustomLabelRenderer());
        questionTable.getColumnModel().getColumn(3).setCellRenderer(new CustomLabelRenderer());
        questionTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        questionTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(questionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel,BorderLayout.CENTER);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        loadQuestions();

        // Popup Menu
        popupMenu = new JPopupMenu();
        JMenuItem editMenuItem = new JMenuItem("Sửa câu hỏi");
        JMenuItem deleteMenuItem = new JMenuItem("Xóa câu hỏi");

        editMenuItem.addActionListener(e -> editQuestion());
        deleteMenuItem.addActionListener(e -> deleteQuestion());

        popupMenu.add(editMenuItem);
        popupMenu.add(deleteMenuItem);
        questionTable.setComponentPopupMenu(popupMenu);

        questionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = questionTable.rowAtPoint(evt.getPoint());
                questionTable.clearSelection();
                questionTable.setRowSelectionInterval(row, row);
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    selectedRow = row;
                    popupMenu.show(questionTable, evt.getX(), evt.getY());
                }
            }
        });

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (questionTable.getSelectedRow() == -1) {
                    editMenuItem.setEnabled(false);
                    deleteMenuItem.setEnabled(false);
                } else {
                    editMenuItem.setEnabled(true);
                    deleteMenuItem.setEnabled(true);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(8));
        return button;
    }

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
        } else if (e.getSource() == backButton) {
            isSearchMode = false;
            backButton.setVisible(false);
            filterPanel.setVisible(false);
            loadQuestions();
        } else if (e.getSource() == filterButton) {
            filterPanel.setVisible(!filterPanel.isVisible());
            if (filterPanel.isVisible()) {
                isSearchMode = true;
                backButton.setVisible(true);
                Point location = searchField.getLocationOnScreen();
                int y = location.y + searchField.getHeight();
                SwingUtilities.convertPointFromScreen(location, this);
                filterPanel.setLocation(location.x, y);
                this.setComponentZOrder(filterPanel, 0);
                filterPanel.revalidate();
                filterPanel.repaint();
            } else {
                isSearchMode = false;
                backButton.setVisible(false);
                loadQuestions();
            }
        }
    }

    private void editQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để sửa!");
            return;
        }
        String questionID = tableModel.getValueAt(selectedRow, 0).toString();
        showQuestionDetails(questionID);
    }

    private void deleteQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để xóa!");
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa câu hỏi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String questionIdToDelete = tableModel.getValueAt(selectedRow, 0).toString();
            if (questionDAL.delete(questionIdToDelete)) {
                tableModel.removeRow(selectedRow);
                bank.removeIf(q -> q.getID().equals(questionIdToDelete));
                JOptionPane.showMessageDialog(this, "Xóa câu hỏi thành công!");
                loadQuestions();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa câu hỏi!");
            }
        }
    }

    private void searchQuestion() {
        isSearchMode = true;
        backButton.setVisible(true);

        String keyword = searchField.getText().trim();
        Enums.DifficultValue selectedDifficulty = (Enums.DifficultValue) diffFilterBox.getSelectedItem();
        ChapterDTO selectedChapter = (ChapterDTO) chapterFilterBox.getSelectedItem();

        List<QuestionDTO> searchResults = questionDAL.search(keyword, selectedDifficulty, selectedChapter);

        if (searchResults != null && !searchResults.isEmpty()) {
            loadSearchResults(searchResults);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy câu hỏi nào phù hợp với tiêu chí.", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            loadQuestions();
        }
    }

    private void loadSearchResults(List<QuestionDTO> results) {
        tableModel.setRowCount(0);
        for (QuestionDTO question : results) {
            tableModel.addRow(new Object[]{
                    question.getID(),
                    question.getSubject() != null ? question.getSubject().getName() : "N/A",
                    question.getDifficult() != null ? question.getDifficult().toString() : "N/A",
                    question.getChapter() != null ? question.getChapter().getName() : "N/A",
                    "Xem"
            });
        }
        questionTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        questionTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    public void loadQuestions() {
        bank = questionDAL.getAll();
        tableModel.setRowCount(0);
        if (bank != null && !bank.isEmpty()) {
            for (QuestionDTO row : bank) {
                tableModel.addRow(new Object[]{
                        row.getID(),
                        row.getSubject() != null ? row.getSubject().getName() : "N/A",
                        row.getDifficult() != null ? row.getDifficult().toString() : "N/A",
                        row.getChapter() != null ? row.getChapter().getName() : "N/A",
                        "Xem"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có câu hỏi nào để hiển thị.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        questionTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        questionTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    public void searchID(String ID) {
        // Note: This method seems incomplete or incorrect as it doesn't use the ID parameter
        tableModel.setRowCount(0);
        if (bank != null && !bank.isEmpty()) {
            for (QuestionDTO row : bank) {
                if (row.getID().equals(ID)) {
                    tableModel.addRow(new Object[]{
                            row.getID(),
                            row.getSubject() != null ? row.getSubject().getName() : "N/A",
                            row.getDifficult() != null ? row.getDifficult().toString() : "N/A",
                            row.getChapter() != null ? row.getChapter().getName() : "N/A",
                            "Xem"
                    });
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có câu hỏi nào để hiển thị.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        questionTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        questionTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void showQuestionDetails(String questionID) {
        // Create a new PanelExemDetail instance
        PanelQuestionDetail panelExemDetail = new PanelQuestionDetail(this, mainPanel);
        panelExemDetail.loadQuestionDetails(questionID);

        // Replace the content of mainPanel with PanelExemDetail
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(panelExemDetail, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

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
            setText((value == null) ? "Xem" : value.toString());
            return this;
        }
    }

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
            label = (value == null) ? "Xem" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int selectedRow = questionTable.getSelectedRow();
                String questionID = tableModel.getValueAt(selectedRow, 0).toString();
                showQuestionDetails(questionID);
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
        addQuestionDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm câu hỏi mới", true);
        addQuestionDialog.setSize(600, 600);
        addQuestionDialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        ChapterDAL chapterDAL = new ChapterDAL();

        JTextArea contentField = new JTextArea(3, 20);
        contentField.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane contentScrollPane = new JScrollPane(contentField);
        contentScrollPane.setBorder(new RoundedBorder(8));

        JComboBox<Enums.DifficultValue> diffBox = new JComboBox<>(Enums.DifficultValue.values());
        diffBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel) diffBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        diffBox.setBorder(new RoundedBorder(8));

        JComboBox<ChapterDTO> chapterBox = new JComboBox<>();
        chapterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel) chapterBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        chapterDAL.getAll().forEach(chapterBox::addItem);
        chapterBox.setBorder(new RoundedBorder(8));

        JTextField createdByField = new JTextField(userBLL.getCurrent().getLoginName());
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
        ((JLabel) correctAnswerBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        correctAnswerBox.setBorder(new RoundedBorder(8));

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel contentLabel = new JLabel("Nội dung:");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(contentLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(contentScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel diffLabel = new JLabel("Độ khó:");
        diffLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(diffLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(diffBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel chapterLabel = new JLabel("Chương:");
        chapterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(chapterLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(chapterBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel createdByLabel = new JLabel("Người tạo (UserID):");
        createdByLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(createdByLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createdByField, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerALabel = new JLabel("Đáp án A:");
        answerALabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerALabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerA, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerBLabel = new JLabel("Đáp án B:");
        answerBLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerBLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerB, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerCLabel = new JLabel("Đáp án C:");
        answerCLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerCLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerC, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel answerDLabel = new JLabel("Đáp án D:");
        answerDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(answerDLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(answerD, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        JLabel correctAnswerLabel = new JLabel("Đáp án đúng:");
        correctAnswerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(correctAnswerLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(correctAnswerBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton saveButton = new JButton("Lưu");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(Color.decode("#4caf50"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(new RoundedBorder(8));

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.decode("#f44336"));
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
        addQuestionDialog.setContentPane(scrollPane);

        saveButton.addActionListener(e -> {
            if (contentField.getText().trim().isEmpty()
                    || answerA.getText().trim().isEmpty()
                    || answerB.getText().trim().isEmpty()
                    || answerC.getText().trim().isEmpty()
                    || answerD.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(addQuestionDialog, "Vui lòng điền đầy đủ thông tin.");
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
                JOptionPane.showMessageDialog(this, "Thêm câu hỏi thành công!");
                bank = questionDAL.getAll();
                loadQuestions();
                addQuestionDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm câu hỏi.");
            }
        });

        cancelButton.addActionListener(e -> addQuestionDialog.dispose());
        addQuestionDialog.setUndecorated(true);
        addQuestionDialog.setVisible(true);
    }
    class CustomLabelRenderer extends JLabel implements TableCellRenderer {
        public CustomLabelRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(new Font("Arial", Font.PLAIN, 14));
            setForeground(Color.BLACK);
            setBackground(row % 2 == 0 ? Color.decode("#eaf2f8") : Color.WHITE);
            if (isSelected) {
                setBackground(Color.decode("#AED6F1"));
                setForeground(Color.BLACK);
            }
            setHorizontalAlignment(SwingConstants.LEFT);
            return this;
        }
    }
}