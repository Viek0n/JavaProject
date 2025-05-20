package GUI.giaodienadmin.QuanLyMonHoc;

import BLL.ChapterBLL;
import BLL.SubjectBLL;
import DTO.ChapterDTO;
import DTO.SubjectDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import MICS.Connect;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class SubjectPanel extends JPanel {
    private JTextField txtId, txtName;
    private JTable chapTable;
    private boolean isEditMode = false;
    private JButton saveButton, subjectButton, chapButton, addButton;
    private JLabel lbId, lbName;
    private JPanel buttonPanel;
    private DefaultTableModel chapTableModel;

    private SubjectBLL subjectBLL;
    private ChapterBLL chapterBLL;
    private SubjectDTO subject;
    private List<ChapterDTO> chapList;

    public SubjectPanel() {
        subjectBLL = new SubjectBLL();
        chapterBLL = new ChapterBLL();

        initComponent();
        isEditMode = false;
        subject = new SubjectDTO();
        subject.setID(subjectBLL.getNextId());
        txtId.setText(subject.getID());
    }

    public SubjectPanel(String str) {
        subjectBLL = new SubjectBLL();
        chapterBLL = new ChapterBLL();

        initComponent();
        isEditMode = true;
        subject = subjectBLL.get(str);
        loadSubjectData();
    }

    private void initComponent() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lbId = new JLabel("Mã môn:");
        txtId = new JTextField();
        txtId.setEnabled(false);

        lbName = new JLabel("Tên môn:");
        txtName = new JTextField();

        String[] randomColumn = {"Mã Chương","Tên chương", "Hành động"};
        chapTableModel = new DefaultTableModel(randomColumn, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 1;
            }
        };
        chapTable = new JTable(chapTableModel);
        chapTable.setRowHeight(30);
        chapTable.setFont(new Font("Arial", Font.PLAIN, 14));
        chapTable.setFillsViewportHeight(true);
        chapTable.setBackground(Color.WHITE);
        chapTable.setGridColor(Color.black);
        chapTable.setShowVerticalLines(false);
        chapTable.setSelectionBackground(Color.lightGray);

        JTableHeader tableHeader = chapTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setBackground(Ulti.LightGray);

        //chapTable.getColumnModel().getColumn(0).setCellEditor(new ChapterEditor(getChapList()));
        //chapTable.getColumnModel().getColumn(1).setCellEditor(new DiffEditor());
        chapTable.getColumn("Hành động").setCellRenderer(new ButtonPanelRenderer());
        chapTable.getColumn("Hành động").setCellEditor(new ButtonPanelEditor(chapTable));

        subjectButton = new JButton("Môn");
        subjectButton.setBackground(Ulti.LightGray);
        subjectButton.setForeground(Color.BLACK);
        subjectButton.setFont(new Font("Arial", Font.BOLD, 14));
        subjectButton.addActionListener(e -> showDetail());

        chapButton = new JButton("Chương");
        chapButton.setBackground(Ulti.LightGray);
        chapButton.setForeground(Color.BLACK);
        chapButton.setFont(new Font("Arial", Font.BOLD, 14));
        chapButton.addActionListener(e -> showChap());

        saveButton = new JButton("Lưu");
        saveButton.setBackground(Ulti.LightGreen);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> saveExam());

        addButton = new JButton("Thêm");
        addButton.setBackground(Ulti.BoldRed);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(e -> addRow());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        buttonPanel.add(subjectButton);
        buttonPanel.add(chapButton);
        showDetail();
    }

    public boolean loadSubjectData() {
        if (subject == null) return false;
        txtId.setText(subject.getID());
        txtName.setText(subject.getName());
        chapList = chapterBLL.getBySubject(subject.getID());
        for(ChapterDTO chap: chapList)
            chapTableModel.addRow(new Object[]{chap.getID(),chap.getName(),""});
        return true;
    }

    private void showDetail(){
        this.removeAll();
        this.revalidate();
        this.repaint();

        this.setLayout(new GridLayout(3, 2, 10, 10));
        this.add(lbId); 
        this.add(txtId);
        this.add(lbName);      
        this.add(txtName);

        buttonPanel.remove(addButton);
        buttonPanel.remove(saveButton);
        this.add(buttonPanel);
        this.add(saveButton);
    }

    private void showChap(){
        this.removeAll();
        this.revalidate();
        this.repaint();

        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chapTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Ulti.MainColor);

        this.add(scrollPane, BorderLayout.CENTER);
        buttonPanel.add(saveButton);
        buttonPanel.add(addButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addRow(){
        chapTableModel.addRow(new Object[]{subject.getID()+"C"+(chapTable.getRowCount()+1),""});
    }

    private void saveExam() {
        if (chapTable.isEditing())
            chapTable.getCellEditor().stopCellEditing();

        String name = txtName.getText();
        ArrayList<ChapterDTO> chaps = new ArrayList<>();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống!","Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < chapTableModel.getRowCount(); i++) {
            String chapId = (String) chapTableModel.getValueAt(i, 0);
            String chapName = (String) chapTableModel.getValueAt(i, 1);
            if(chapName == null || chapName.trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Vui lòng hoàn thành bảng chương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Pattern.matches("^[\\s-_]+$", chapName)) {
                JOptionPane.showMessageDialog(this, "Tên chương không được chứa ký tự đặc biệt!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ChapterDTO dto = new ChapterDTO();
            dto.setID(chapId);
            dto.setName(chapName);
            chaps.add(dto);
        }

        if(chaps.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng thêm chương!","Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chứ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        subject.setName(name);

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Boolean success = false;
        if(isEditMode){
            success = subjectBLL.update(subject);
            if(chaps.size() < chapList.size()){
                for(int i = 0; i<chaps.size();i++)
                    success = chapterBLL.update(chaps.get(i));
                for(int i = chaps.size(); i < chapList.size(); i++)
                    success = chapterBLL.delete(chapList.get(i).getID());
            }else{
                for(int i = 0; i < chapList.size(); i++)
                    success = chapterBLL.update(chaps.get(i));
                for(int i = chapList.size(); i < chaps.size(); i++)
                    success = chapterBLL.add(chaps.get(i), subject.getID());
            }
        }
        else{
            success = subjectBLL.add(subject);
            for(ChapterDTO chap: chaps)
                success = chapterBLL.add(chap,subject.getID());
        }
        
        if(success){
            JOptionPane.showMessageDialog(this, "Thành công!", "Good", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.getWindowAncestor(this).dispose(); 
        }
        else{
            JOptionPane.showMessageDialog(this, "Không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    class ChapterRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

            JComboBox<String> comboBox = new JComboBox<>();
            comboBox.addItem("Select chap");
            if (value != null) comboBox.setSelectedItem(value.toString());
            return comboBox;
        }
    }

    class ChapterEditor extends DefaultCellEditor {
        private JComboBox<String> comboBox;
        public ChapterEditor(List<String> chapters) {
            super(new JComboBox<>());
            comboBox = (JComboBox<String>) getComponent();
            comboBox.removeAllItems();
            for (String chap : chapters)
                comboBox.addItem(chap);
        }
    }


    class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
        public ButtonPanelRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JButton delete = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));
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
        private final JButton deleteButton;
        private int row;
        private JTable table;

        public ButtonPanelEditor(JTable table) {
            this.table = table;

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.setBackground(Color.WHITE);

            deleteButton = ButtonFactory.createClearButton(AddImage.createImageIcon(Connect.img + "delete.png", 20, 20));
            deleteButton.addActionListener(e -> {
                stopCellEditing(); // Stop editing BEFORE removing the row
                // Use invokeLater to safely remove the row after editing has finished
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (row >= 0 && row < model.getRowCount()) {
                        model.removeRow(row);
                    }
                });
            });

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
}
