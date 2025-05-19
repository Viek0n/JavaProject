package GUI.giaodienadmin.QuanLyMonHoc;

import BLL.ChapterBLL;
import BLL.QuestionBLL;
import BLL.SubjectBLL;
import DTO.ChapterDTO;
import DTO.SubjectDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.UserPanel.MenuPanel;
import GUI.giaodienadmin.RoundedBorder;
import MICS.Connect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class SubjectManagementPanel extends JPanel implements ActionListener  {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, searchButton, clearButton;
    private SubjectBLL subjectBLL;
    private ChapterBLL chapterBLL;

    private ArrayList<SubjectDTO> subjects;
    private SubjectPanel panelSubject;
    private JDialog dialog;
    private MenuPanel menuPanel;

    public SubjectManagementPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
        this.subjectBLL = new SubjectBLL();
        this.chapterBLL = new ChapterBLL();
        initComponent();
    }

    private void initComponent() {
        subjects = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Ulti.MainColor);

        // Menu panel
        add(menuPanel, BorderLayout.WEST);

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Ulti.LightGray);

        addButton = createButton("Tạo", Ulti.LightGreen);
        searchField = createSearchField();
        searchButton = createButton("Tìm", Ulti.Yellow);
        clearButton = createButton("Tải lại", Ulti.Blue);

        topPanel.add(addButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);

        String[] columnNames = {"Mã môn", "Tên môn", "Hành động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.black);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(Color.lightGray);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setBackground(Ulti.LightGray);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount()-1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonPanelRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonPanelEditor(table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Load users
        loadExam();
    }


    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createSearchField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(new CompoundBorder(
                new RoundedBorder(8),
                new EmptyBorder(0, 10, 0, 10)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.decode("#2c3e50"));
        textField.setPreferredSize(new Dimension(200, 35));
        return textField;
    }

    private void ShowAddSubject() {
        panelSubject = new SubjectPanel();
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm môn", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelSubject);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadExam();
    }

    private void ShowEditSubject(String subjectId) {
        panelSubject = new SubjectPanel(subjectId);
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chỉnh sửa môn", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelSubject);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadExam();
    }

    private void delete(String subjectId) {
       int choice = JOptionPane.showConfirmDialog(
                this,
                "Xóa môn " + subjectId + "?",
                "Xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                ArrayList<ChapterDTO> chaps = new ArrayList<>(chapterBLL.getBySubject(subjectId));
                for(ChapterDTO chap: chaps)
                    chapterBLL.delete(chap.getID());
                    if (subjectBLL.delete(subjectId)) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa!");
                    }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting exam: " + ex.getMessage());
            }
        }
    }

    private void search() {
        /*String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadExam();
            return;
        }

        try {
            ArrayList<QuestionDTO> searchResults = questionBLL.search(keyword);
            tableModel.setRowCount(0);
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng nào!", "Tìm kiếm người dùng", JOptionPane.INFORMATION_MESSAGE);
            }
            for (QuestionDTO ques : searchResults) {
                tableModel.addRow(new Object[]{
                        ques.getID(),
                        ques.getText(),
                        ques.getDifficult().toString(),
                        ques.getChapter(),
                        ques.getSubject(),
                        "Edit"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }

    private void loadExam() {
        tableModel.setRowCount(0);
        subjects = new ArrayList<>(subjectBLL.getAll());
        if (!subjects.isEmpty()) {
            for (SubjectDTO sub : subjects) {
                tableModel.addRow(new Object[]{
                        sub.getID(),
                        sub.getName(),
                        "Edit"});
            }
         }
    }

    class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
        public ButtonPanelRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JButton edit = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "edit.png", 20, 20));
            JButton delete =  ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));
            add(edit);
            add(delete);
            setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonPanelEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;
        private int row;

        public ButtonPanelEditor(JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.setBackground(Color.WHITE);
            editButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "edit.png", 20, 20));
            deleteButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));

            editButton.addActionListener(e -> {
                String subjectId = table.getValueAt(row, 0).toString();
                ShowEditSubject(subjectId);
                stopCellEditing();
            });

            deleteButton.addActionListener(e -> {
                String subjectId = table.getValueAt(row, 0).toString();
                stopCellEditing();
                delete(subjectId);
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (row >= 0 && row < model.getRowCount()) {
                        model.removeRow(row);
                    }
                });
            });

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            ShowAddSubject();
        } else if (e.getSource() == searchButton) {
            search();
        } else if (e.getSource() == clearButton) {
            searchField.setText("");
            loadExam();
        }
    }
}
