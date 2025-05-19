package GUI.UserPanel.LichSu;

import BLL.ExamBLL;
import DTO.ExamDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.UserPanel.MenuPanel;
import MICS.Connect;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ExamHistoryPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private ExamBLL examBLL;

    private ArrayList<ExamDTO> exams;
   // private HistoryPanel panelHistory;
    private JDialog dialog;
    private MenuPanel menuPanel;

    public ExamHistoryPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
        this.examBLL = new ExamBLL();
        initComponent();
    }

    private void initComponent() {
        exams = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Ulti.MainColor);

        // Menu panel
        add(menuPanel, BorderLayout.WEST);

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Ulti.LightGray);

        String[] columnNames = {"Mã bài", "Mã đề", "Điểm", "Thời gian làm", "Hành động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
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
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonPanelRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonPanelEditor(table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        // Load users
        load();
    }

    private void showHistory() {
        /*panelHistory = new HistoryPanel();
        dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Bài kiểm tra", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panelHistory);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        load();*/
    }

    private void load() {
        tableModel.setRowCount(0);
        exams = new ArrayList<>(examBLL.getFromUser(menuPanel.mainFrame.userBLL.getCurrent().getLoginName()));
        if (!exams.isEmpty()) {
            for (ExamDTO exam : exams) {
                tableModel.addRow(new Object[]{
                        exam.getExamId(),
                        exam.getExamStructID(),
                        exam.getScore(),
                        exam.getRemainingTime(),
                        "Edit"});
            }
         }
    }

    class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
        public ButtonPanelRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JButton edit = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "test-history.png", 20, 20));
            add(edit);
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
        private int row;

        public ButtonPanelEditor(JTable table) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.setBackground(Color.WHITE);
            editButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "test-history.png", 20, 20));

            editButton.addActionListener(e -> {
                String subjectId = table.getValueAt(row, 0).toString();
                //showHistory(subjectId);
                stopCellEditing();
            });

            panel.add(editButton);
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
}
