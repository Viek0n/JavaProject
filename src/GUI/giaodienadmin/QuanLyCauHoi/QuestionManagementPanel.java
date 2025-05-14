package GUI.giaodienadmin.QuanLyCauHoi;

import MICS.*;
import DAL.*;
import DTO.AnswerDTO;
import DTO.ChapterDTO;
import DTO.QuestionDTO;
import MICS.Enums;
import BLL.UserBLL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class QuestionManagementPanel extends JPanel implements ActionListener {
    private static final Logger LOGGER = Logger.getLogger(QuestionManagementPanel.class.getName());

    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, searchButton, backButton, filterButton;
    private ArrayList<QuestionDTO> bank;
    private QuestionDAL questionDAL;
    private JPanel mainPanel;
    private JComboBox<Enums.DifficultValue> diffFilterBox;
    private JComboBox<ChapterDTO> chapterFilterBox;
    private JPanel filterPanel;
    private boolean isSearchMode = false;
    private JDialog addQuestionDialog;
    private JPopupMenu popupMenu;
    private int selectedRow;
    private UserBLL userBLL;

    public QuestionManagementPanel(JPanel mainPanel, UserBLL userBLL) {
        this.mainPanel = mainPanel;
        this.userBLL = userBLL;
        this.questionDAL = new QuestionDAL();
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f0f4f8"));
        add(mainPanel, BorderLayout.WEST);
        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.decode("#e0e0e0"));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(Color.decode("#e0e0e0"));

        addButton = createButton("+ THÊM MỚI");
        addButton.setBackground(Color.decode("#4caf50"));
        addButton.setForeground(Color.WHITE);
        editButton = createButton("Sửa");
        editButton.setBackground(Color.decode("#008cba"));
        editButton.setForeground(Color.WHITE);
        deleteButton = createButton("Xóa");
        deleteButton.setBackground(Color.decode("#f44336"));
        deleteButton.setForeground(Color.WHITE);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(new RoundedBorder(8));
        JPanel searchButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        searchButton = createButton("Tìm kiếm");
        searchButton.setBackground(Color.decode("#ffc107"));
        searchButton.setForeground(Color.BLACK);
        searchButton.setPreferredSize(new Dimension(100, 30));
         //searchButtonPanel.add(searchButton);
        searchField.setLayout(new BorderLayout());
        searchField.add(searchButtonPanel, BorderLayout.EAST); // Fixed search button placement

        backButton = createButton("Trở lại");
        backButton.setBackground(Color.decode("#808080"));
        backButton.setForeground(Color.WHITE);
        backButton.setVisible(false);

        filterButton = createButton("Lọc");
        filterButton.setBackground(Color.decode("#2196f3"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBorder(new RoundedBorder(8));

        // Difficulty filter JComboBox
        diffFilterBox = new JComboBox<>();
        diffFilterBox.addItem(null); // Default "Tất cả"
        diffFilterBox.addItem(Enums.DifficultValue.DE);
        diffFilterBox.addItem(Enums.DifficultValue.TRUNGBINH);
        diffFilterBox.addItem(Enums.DifficultValue.KHO);
        diffFilterBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    value = "Tất cả";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        diffFilterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel) diffFilterBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        diffFilterBox.setBorder(new RoundedBorder(8));

        // Chapter filter JComboBox
        chapterFilterBox = new JComboBox<>();
        chapterFilterBox.addItem(null); // Default "Tất cả"
        ChapterDAL chapterDAL = new ChapterDAL();
        chapterDAL.getAll().forEach(chapterFilterBox::addItem);
        chapterFilterBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    value = "Tất cả";
                } else if (value instanceof ChapterDTO) {
                    value = ((ChapterDTO) value).getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        chapterFilterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JLabel) chapterFilterBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        chapterFilterBox.setBorder(new RoundedBorder(8));

        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.decode("#e0e0e0"));
        filterPanel.add(diffFilterBox);
        filterPanel.add(chapterFilterBox);
        filterPanel.setVisible(false);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(backButton); // Added back button to UI

        topPanel.add(buttonPanel);
        topPanel.add(filterPanel);

        // Table setup
        String[] columnNames = {"Mã câu hỏi", "Môn học", "Độ khó", "Chương", "Chi tiết"};
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
        scrollPane.setBackground(Color.decode("#f0f4f8"));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.decode("#f0f4f8"));
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Action listeners
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        backButton.addActionListener(this);
        filterButton.addActionListener(this);

        // Popup menu
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
                editMenuItem.setEnabled(questionTable.getSelectedRow() != -1);
                deleteMenuItem.setEnabled(questionTable.getSelectedRow() != -1);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        loadQuestions();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(8));
        return button;
    }

    public static class RoundedBorder extends LineBorder {
        private final int radius;

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
            return new Insets(radius + 2, radius + 2, radius + 2, radius + 2);
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
            searchField.setText("");
            diffFilterBox.setSelectedItem(null);
            chapterFilterBox.setSelectedItem(null);
            loadQuestions();
        } else if (e.getSource() == filterButton) {
            filterPanel.setVisible(!filterPanel.isVisible());
            if (filterPanel.isVisible()) {
                isSearchMode = true;
                backButton.setVisible(true);
            } else {
                isSearchMode = false;
                backButton.setVisible(false);
                loadQuestions();
            }
        }
    }

    private void editQuestion() {
        selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String questionID = tableModel.getValueAt(selectedRow, 0).toString();
        showQuestionDetails(questionID);
    }

    private void deleteQuestion() {
        selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa câu hỏi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String questionIdToDelete = tableModel.getValueAt(selectedRow, 0).toString();
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    try {
                        return questionDAL.delete(questionIdToDelete);
                    } catch (Exception e) {
                        LOGGER.severe("Error deleting question: " + e.getMessage());
                        return false;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get()) {
                            tableModel.removeRow(selectedRow);
                            bank.removeIf(q -> q.getID().equals(questionIdToDelete));
                            JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                                "Xóa câu hỏi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                                "Không thể xóa câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.severe("Error deleting question: " + e.getMessage());
                        JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                            "Lỗi khi xóa câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    private void searchQuestion() {
        isSearchMode = true;
        backButton.setVisible(true);

        String keyword = searchField.getText().trim();
        Enums.DifficultValue selectedDifficulty = (Enums.DifficultValue) diffFilterBox.getSelectedItem();
        ChapterDTO selectedChapter = (ChapterDTO) chapterFilterBox.getSelectedItem();

        // If both filters are "Tất cả" and keyword is non-empty, search by ID only
        if (selectedDifficulty == null && selectedChapter == null && !keyword.isEmpty()) {
            searchID(keyword);
            return;
        }

        SwingWorker<List<QuestionDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<QuestionDTO> doInBackground() {
                try {
                    return questionDAL.search(keyword, selectedDifficulty, selectedChapter);
                } catch (Exception e) {
                    LOGGER.severe("Error searching questions: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    List<QuestionDTO> searchResults = get();
                    if (searchResults != null && !searchResults.isEmpty()) {
                        populateTable(searchResults);
                    } else {
                        JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                            "Không tìm thấy câu hỏi nào phù hợp với tiêu chí.", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setRowCount(0);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.severe("Error searching questions: " + e.getMessage());
                    JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                        "Lỗi khi tìm kiếm câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void loadQuestions() {
        SwingWorker<ArrayList<QuestionDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected ArrayList<QuestionDTO> doInBackground() {
                try {
                    return questionDAL.getAll();
                } catch (Exception e) {
                    LOGGER.severe("Error loading questions: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    bank = get();
                    populateTable(bank);
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.severe("Error loading questions: " + e.getMessage());
                    JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                        "Lỗi khi tải câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void searchID(String ID) {
        if (ID == null || ID.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID câu hỏi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Enums.DifficultValue selectedDifficulty = (Enums.DifficultValue) diffFilterBox.getSelectedItem();
        ChapterDTO selectedChapter = (ChapterDTO) chapterFilterBox.getSelectedItem();

        // If both filters are "Tất cả", search by ID only
        if (selectedDifficulty == null && selectedChapter == null) {
            SwingWorker<QuestionDTO, Void> worker = new SwingWorker<>() {
                @Override
                protected QuestionDTO doInBackground() {
                    try {
                        return questionDAL.getByID(ID.trim());
                    } catch (Exception e) {
                        LOGGER.severe("Error searching question by ID: " + e.getMessage());
                        return null;
                    }
                }

                @Override
                protected void done() {
                    try {
                        QuestionDTO question = get();
                        List<QuestionDTO> result = new ArrayList<>();
                        if (question != null) {
                            result.add(question);
                        }
                        populateTable(result);
                        if (question == null) {
                            JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                                "Không tìm thấy câu hỏi với ID: " + ID, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.severe("Error searching question by ID: " + e.getMessage());
                        JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                            "Lỗi khi tìm kiếm câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        } else {
            // Use search with filters
            SwingWorker<List<QuestionDTO>, Void> worker = new SwingWorker<>() {
                @Override
                protected List<QuestionDTO> doInBackground() {
                    try {
                        return questionDAL.search(ID.trim(), selectedDifficulty, selectedChapter);
                    } catch (Exception e) {
                        LOGGER.severe("Error searching questions: " + e.getMessage());
                        return null;
                    }
                }

                @Override
                protected void done() {
                    try {
                        List<QuestionDTO> searchResults = get();
                        if (searchResults != null && !searchResults.isEmpty()) {
                            populateTable(searchResults);
                        } else {
                            JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                                "Không tìm thấy câu hỏi với ID: " + ID, "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                            tableModel.setRowCount(0);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.severe("Error searching questions: " + e.getMessage());
                        JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                            "Lỗi khi tìm kiếm câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    private void populateTable(List<QuestionDTO> questions) {
        tableModel.setRowCount(0);
        if (questions != null && !questions.isEmpty()) {
            for (QuestionDTO question : questions) {
                tableModel.addRow(new Object[]{
                    question.getID(),
                    question.getSubject() != null ? question.getSubject().getName() : "N/A",
                    question.getDifficult() != null ? question.getDifficult().toString() : "N/A",
                    question.getChapter() != null ? question.getChapter().getName() : "N/A",
                    "Xem"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có câu hỏi nào để hiển thị.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        questionTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        questionTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void showQuestionDetails(String questionID) {
        JFrame detailFrame = new JFrame("Question Details");
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setSize(800, 600);
        detailFrame.setLocationRelativeTo(null);

        PanelQuestionDetail panelQuestionDetail = new PanelQuestionDetail(this, mainPanel, detailFrame);
        panelQuestionDetail.loadQuestionDetails(questionID);

        detailFrame.setLayout(new BorderLayout());
        detailFrame.add(panelQuestionDetail, BorderLayout.CENTER);
        detailFrame.setVisible(true);
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

        JTextField createdByField = new JTextField(userBLL.getCurrent() != null ? userBLL.getCurrent().getLoginName() : "SYSTEM");
        createdByField.setFont(new Font("Arial", Font.PLAIN, 14));
        createdByField.setBorder(new RoundedBorder(8));
        createdByField.setEditable(false);

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
        JButton saveButton = createButton("Lưu");
        saveButton.setBackground(Color.decode("#4caf50"));
        saveButton.setForeground(Color.WHITE);
        JButton cancelButton = createButton("Hủy");
        cancelButton.setBackground(Color.decode("#808080"));
        cancelButton.setForeground(Color.WHITE);
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
            String content = contentField.getText().trim();
            String answerAText = answerA.getText().trim();
            String answerBText = answerB.getText().trim();
            String answerCText = answerC.getText().trim();
            String answerDText = answerD.getText().trim();
            ChapterDTO chapter = (ChapterDTO) chapterBox.getSelectedItem();

            if (content.isEmpty() || answerAText.isEmpty() || answerBText.isEmpty() ||
                answerCText.isEmpty() || answerDText.isEmpty() || chapter == null) {
                JOptionPane.showMessageDialog(addQuestionDialog, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    try {
                        QuestionDTO newQues = new QuestionDTO();
                        newQues.setText(content);
                        newQues.setDifficult((Enums.DifficultValue) diffBox.getSelectedItem());
                        newQues.setChapter(chapter);
                        newQues.setCreatedBy(createdByField.getText().trim());

                        ArrayList<AnswerDTO> answers = new ArrayList<>();
                        String[] answerTexts = {answerAText, answerBText, answerCText, answerDText};
                        String correct = (String) correctAnswerBox.getSelectedItem();
                        String[] labels = {"A", "B", "C", "D"};
                        for (int i = 0; i < 4; i++) {
                            AnswerDTO ans = new AnswerDTO();
                            ans.setText(answerTexts[i]);
                            ans.setRight(correct.equals(labels[i]));
                            answers.add(ans);
                        }
                        newQues.setAns(answers);

                        return questionDAL.add(newQues);
                    } catch (Exception ex) {
                        LOGGER.severe("Error adding question: " + ex.getMessage());
                        return false;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get()) {
                            JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                                "Thêm câu hỏi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            loadQuestions();
                            addQuestionDialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                                "Không thể thêm câu hỏi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (InterruptedException | ExecutionException ex) {
                        LOGGER.severe("Error adding question: " + ex.getMessage());
                        JOptionPane.showMessageDialog(QuestionManagementPanel.this,
                            "Lỗi khi thêm câu hỏi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        });

        cancelButton.addActionListener(e -> addQuestionDialog.dispose());
        addQuestionDialog.setVisible(true);
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

    class CustomLabelRenderer extends JLabel implements TableCellRenderer {
        public CustomLabelRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(new Font("Arial", Font.PLAIN, 14));
            setForeground(Color.BLACK);
            setBackground(row % 2 == 0 ? Color.decode("#eaf2f8") : Color.WHITE);
            if (isSelected) {
                setBackground(Color.decode("#b0e0e6"));
                setForeground(Color.BLACK);
            }
            setHorizontalAlignment(SwingConstants.LEFT);
            return this;
        }
    }
}