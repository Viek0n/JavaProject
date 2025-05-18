package GUI.giaodienadmin.QuanLyDeThi;

import BLL.ChapterBLL;
import BLL.ExamStructBLL;
import BLL.QuestionBLL;
import BLL.SubjectBLL;
import DAL.ExamStructDAL;
import DAL.ExamStructSelectedDAL;
import DAL.RoleDAL;
import DAL.SubjectDAL;
import DAL.UserDAL;
import DTO.ChapterDTO;
import DTO.ExamStructDTO;
import DTO.ExamStructDetailDTO;
import DTO.ExamStructSelectedDTO;
import DTO.QuestionDTO;
import DTO.SubjectDTO;
import DTO.UserDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonEditor;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.ButtonRenderer;
import GUI.MakeColor.Ulti;
import GUI.giaodienadmin.QuanLyDeThi.ExamStructManagementPanel.ButtonPanelEditor;
import GUI.giaodienadmin.QuanLyDeThi.ExamStructManagementPanel.ButtonPanelRenderer;
import GUI.giaodienadmin.QuanLyDeThi.ExamStructPanel.ChapterEditor;
import MICS.*;
import java.awt.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ExamStructPanel extends JPanel {
    private JTextField txtId, txtName, txtUser;
    private JTextArea descriptionArea;
    private JCheckBox answerAllow;
    private JComboBox<String> subjectCmb, timeCmb;
    private JDatePickerImpl startDatePicker, endDatePicker;
    private JTable randomQues, selectQues;
    private boolean isEditMode = false;
    private JButton saveButton, nextButton, prevButton, addButton;
    private JLabel lbexamId, lbexamName, lbdesc, lbansallow, lbsubject, lbstart, lbend, lbduration, lbUser;
    private JPanel buttonPanel;
    private DefaultTableModel randomTable, selectTable;
    private String[] times = {
    "00:00:00", "00:10:00", "00:15:00", "00:20:00", "00:25:00", "00:30:00", 
    "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00", "02:00:00"
    };
    private String[] diff = {"DE","TRUNGBINH","KHO"};
    private int showMode;

    private SubjectBLL subjectBLL;
    private ExamStructBLL examStructBLL;
    private ChapterBLL chapterBLL;
    private QuestionBLL questionBLL;
    private ExamStructDTO examStruct;

    public ExamStructPanel(String str, Boolean mode) {
        subjectBLL = new SubjectBLL();
        examStructBLL = new ExamStructBLL();
        chapterBLL = new ChapterBLL();
        questionBLL = new QuestionBLL();

        initComponent();
        isEditMode = mode;
        if(isEditMode){
            examStruct = examStructBLL.get(str);
            loadExamStructData();
        }
        else{
            examStruct = new ExamStructDTO();
            txtUser.setText(str);
        }
    }

    private void initComponent() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lbexamId = new JLabel("Mã cấu trúc:");
        txtId = new JTextField(examStructBLL.getNextId());
        txtId.setEnabled(false);

        lbUser = new JLabel("Người tạo: ");
        txtUser = new JTextField();
        txtUser.setEnabled(false);

        lbexamName = new JLabel("Tên cấu trúc:");
        txtName = new JTextField();

        lbdesc = new JLabel("Mô tả:");
        descriptionArea = new JTextArea();

        lbansallow = new JLabel("Cho phép xem đáp án khi hoàn thành");
        answerAllow = new JCheckBox();

        lbsubject = new JLabel("Môn học:");
        subjectCmb = new JComboBox<>();
        loadSubjectToComboBox();
        subjectCmb.setSelectedIndex(0);
        subjectCmb.addActionListener(e -> {
            randomTable.setRowCount(0);
            selectTable.setRowCount(0);
            TableColumn chapterColumn = randomQues.getColumnModel().getColumn(0);
            TableColumn questionColumn = selectQues.getColumnModel().getColumn(0);
            chapterColumn.setCellEditor(new ChapterEditor(getChapList()));
            questionColumn.setCellEditor(new QuestionEditor(getQuestList()));
            randomQues.repaint();
            selectQues.repaint();
        });


        lbstart = new JLabel("Bắt đầu: ");
        UtilDateModel startDateModel = new UtilDateModel(new Date());
        Properties startDateProperties = new Properties();
        startDateProperties.put("text.today", "Today");
        startDateProperties.put("text.month", "Month");
        startDateProperties.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, startDateProperties);
        startDatePicker = new JDatePickerImpl(startDatePanel, new ExamDialogUtils.DateLabelFormatter());
        startDatePicker.setToolTipText("Select start date (yyyy-MM-dd)");

        lbend = new JLabel("Kết thúc: ");
        UtilDateModel endDateModel = new UtilDateModel(new Date());
        Properties endDateProperties = new Properties();
        endDateProperties.put("text.today", "Today");
        endDateProperties.put("text.month", "Month");
        endDateProperties.put("text.year", "Year");
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, endDateProperties);
        endDatePicker = new JDatePickerImpl(endDatePanel, new ExamDialogUtils.DateLabelFormatter());
        endDatePicker.setToolTipText("Select end date (yyyy-MM-dd)");

        lbduration = new JLabel("Thời lượng:");
        timeCmb = new JComboBox<>(times);
        timeCmb.setSelectedIndex(0);

        String[] randomColumn = {"Chương", "Độ khó", "Số lượng", "Hành động"};
        randomTable = new DefaultTableModel(randomColumn, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        randomQues = new JTable(randomTable);
        randomQues.setRowHeight(30);
        randomQues.setFont(new Font("Arial", Font.PLAIN, 14));
        randomQues.setFillsViewportHeight(true);
        randomQues.setBackground(Color.WHITE);
        randomQues.setGridColor(Color.black);
        randomQues.setShowVerticalLines(false);
        randomQues.setSelectionBackground(Color.lightGray);

        JTableHeader tableHeader = randomQues.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setBackground(Ulti.LightGray);

        randomQues.getColumnModel().getColumn(0).setCellEditor(new ChapterEditor(getChapList()));
        randomQues.getColumnModel().getColumn(1).setCellEditor(new DiffEditor());
        randomQues.getColumn("Hành động").setCellRenderer(new ButtonPanelRenderer());
        randomQues.getColumn("Hành động").setCellEditor(new ButtonPanelEditor(randomQues));


        String[] selectColumn = {"Câu hỏi", "Hành động"};
        selectTable = new DefaultTableModel(selectColumn, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        selectQues = new JTable(selectTable);
        selectQues.setRowHeight(30);
        selectQues.setFont(new Font("Arial", Font.PLAIN, 14));
        selectQues.setFillsViewportHeight(true);
        selectQues.setBackground(Color.WHITE);
        selectQues.setGridColor(Color.black);
        selectQues.setShowVerticalLines(false);
        selectQues.setSelectionBackground(Color.lightGray);

        tableHeader = selectQues.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setBackground(Ulti.LightGray);

        selectQues.getColumnModel().getColumn(0).setCellEditor(new QuestionEditor(getQuestList()));
        selectQues.getColumn("Hành động").setCellRenderer(new ButtonPanelRenderer());
        selectQues.getColumn("Hành động").setCellEditor(new ButtonPanelEditor(selectQues));


        nextButton = new JButton("Tiếp theo");
        nextButton.setBackground(Ulti.LightGray);
        nextButton.setForeground(Color.BLACK);
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.addActionListener(e -> next());

        prevButton = new JButton("Trở lại");
        prevButton.setBackground(Ulti.LightGray);
        prevButton.setForeground(Color.BLACK);
        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
        prevButton.addActionListener(e -> prev());

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
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        showDetail();
    }

   private void loadSubjectToComboBox() {
        List<SubjectDTO> subjects = subjectBLL.getAll();
        subjectCmb.addItem("Select a subject");
        for (SubjectDTO subject : subjects) {
            subjectCmb.addItem(subject.getID() + " - " + subject.getName());
        }
    }

    private List<String> getChapList(){
        String selectedSubject = (String) subjectCmb.getSelectedItem();
        String subjectId = selectedSubject.split(" - ")[0];
        List<ChapterDTO> chaps = chapterBLL.getBySubject(subjectId);
        List<String> result = new ArrayList<>();
        for(ChapterDTO chap: chaps){
            result.add(chap.getID() + " - " + chap.getName());
        }
        return result;
    }

    private List<String> getQuestList(){
        List<String> chapList = getChapList();
        List<QuestionDTO> questions = new ArrayList<>();
        List<String> result = new ArrayList<>();
        for(String chap: chapList)
            questions.addAll(questionBLL.getByChap(chap.split(" - ")[0]));
        for(QuestionDTO ques: questions){
            result.add(ques.getID() + " - " + ques.getText());
        }
        return result;
    }

    public boolean loadExamStructData() {
        if (examStruct == null) return false;
        ArrayList<ExamStructDetailDTO> randomQuestList = examStruct.getRandomDetail();
        ArrayList<ExamStructSelectedDTO> selectQuestList = examStruct.getSelectDetail();
        txtId.setText(examStruct.getID());
        txtName.setText(examStruct.getName());
        txtUser.setText(examStruct.getUserId());
        descriptionArea.setText(examStruct.getDesc());
        answerAllow.setSelected(examStruct.getAnswerAllow());
        subjectCmb.setSelectedItem(examStruct.getSubject().getID() + " - " + examStruct.getSubject().getName());
        timeCmb.setSelectedItem(examStruct.getExamTime().toString());
        for(ExamStructDetailDTO detail: randomQuestList)
            randomTable.addRow(new Object[]{detail.getChapID() + " - " + chapterBLL.get(detail.getChapID()).getName(),detail.getDiff().toString(),detail.getQuantity(),""});
        for(ExamStructSelectedDTO select: selectQuestList)
            selectTable.addRow(new Object[]{select.getQuestID() + " - " + questionBLL.get(select.getQuestID()).getText(),""});
        return true;
    }

    private void showDetail(){
        this.removeAll();
        this.revalidate();
        this.repaint();

        showMode = 1;
        this.setLayout(new GridLayout(11, 2, 10, 10));
        this.add(lbexamId); 
        this.add(txtId);
        this.add(lbexamName);      
        this.add(txtName);
        this.add(lbUser);
        this.add(txtUser);
        this.add(lbdesc);       
        this.add(descriptionArea);
        this.add(lbsubject);      
        this.add(subjectCmb);
        this.add(lbansallow);
        this.add(answerAllow);
        this.add(lbstart);
        this.add(startDatePicker);
        this.add(lbend);
        this.add(endDatePicker);
        this.add(lbduration);    
        this.add(timeCmb);

        buttonPanel.remove(addButton);
        buttonPanel.remove(saveButton);
        this.add(buttonPanel);
        this.add(saveButton);
    }

    private void showRandom(){
        this.removeAll();
        this.revalidate();
        this.repaint();

        showMode = 2;
        this.setLayout(new GridLayout(2, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(randomQues);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);

        this.add(scrollPane);
        buttonPanel.add(saveButton);
        buttonPanel.add(addButton);
        this.add(buttonPanel);
        getChapList();
    }

    private void showSelect(){
        this.removeAll();
        this.revalidate();
        this.repaint();

        showMode = 3;
        this.setLayout(new GridLayout(2, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(selectQues);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane.setBackground(Ulti.MainColor);

        this.add(scrollPane);
        buttonPanel.add(saveButton);
        buttonPanel.add(addButton);
        this.add(buttonPanel);
    }

    private void next(){
        switch (showMode) {
            case 1:
                showRandom();
                break;
            case 2:
                showSelect();
                break;
            default:
                break;
        }
    }

    private void prev(){
        switch (showMode) {
            case 2:
                showDetail();
                break;
            case 3:
                showRandom();
                break;
            default:
                break;
        }
    }

    private void addRow(){
        switch(showMode){
            case 2:
                randomTable.addRow(new Object[]{"","","",""});
                break;
            case 3:
                selectTable.addRow(new Object[]{"",""});
                break;
            default:
                break;
        }
    }

    private void saveExam() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String description = descriptionArea.getText().trim();
        String selectedSubject = (String) subjectCmb.getSelectedItem();
        String subjectId = selectedSubject.split(" - ")[0];
        SubjectDTO subject = subjectBLL.get(subjectId);
        Boolean ansAllow = answerAllow.isSelected();
        String durationStr = (String) timeCmb.getSelectedItem();
        Date startDate = (Date) startDatePicker.getModel().getValue();
        Date endDate = (Date) endDatePicker.getModel().getValue();
        ArrayList<ExamStructDetailDTO> randomQuestList = new ArrayList<>();
        ArrayList<ExamStructSelectedDTO> selectQuestList = new ArrayList<>();

        for (int i = 0; i < randomTable.getRowCount(); i++) {
            String chapter = (String) randomTable.getValueAt(i, 0);
            String difficulty = (String) randomTable.getValueAt(i, 1);
            int numQuestions = Integer.parseInt(randomTable.getValueAt(i, 2).toString());

            ExamStructDetailDTO dto = new ExamStructDetailDTO();
            dto.setExamStructID(id);
            dto.setChapID(chapter.split(" - ")[0]);;
            dto.setDiff(Enums.DifficultValue.valueOf(difficulty));
            dto.setQuantity(numQuestions);

            randomQuestList.add(dto);
        }

        for (int i = 0; i < selectTable.getRowCount(); i++) {
            String questId = (String) selectTable.getValueAt(i, 0);

            ExamStructSelectedDTO dto = new ExamStructSelectedDTO();
            dto.setExamStructID(id);
            dto.setQuestID(questId.split(" - ")[0]);

            selectQuestList.add(dto);
        }

        if (id.isEmpty() || name.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống!","Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không thể trước ngày bắt đầu","Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Pattern.matches("^[0-9\\s-_]+$", name)) {
            JOptionPane.showMessageDialog(this, "Tên chỉ có thể là chữ cái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalTime duration;
        try {
            duration = ExamDialogUtils.parseDuration(durationStr);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        if (subject == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy môn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(randomQuestList.isEmpty() && selectQuestList.isEmpty()){
            JOptionPane.showMessageDialog(this, "Chưa chọn câu hỏi", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chứ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        examStruct = new ExamStructDTO();
        examStruct.setID(id);
        examStruct.setName(name);
        examStruct.setDesc(description.isEmpty() ? null : description);
        examStruct.setAnswerAllow(ansAllow);
        examStruct.setStart(startDate);
        examStruct.setEnd(endDate);
        examStruct.setExamTime(Time.valueOf(duration));
        examStruct.setSubject(subject);
        examStruct.setRandomDetail(randomQuestList);
        examStruct.setSelectDetail(selectQuestList);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Boolean success;
        if(isEditMode)
            success = examStructBLL.update(examStruct);
        else
            success = examStructBLL.add(examStruct);
        
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

   class DiffEditor extends DefaultCellEditor {
        private JComboBox<String> comboBox;
        public DiffEditor() {
            super(new JComboBox<>());
            comboBox = (JComboBox<String>) getComponent();
            for (String s : diff) {
                comboBox.addItem(s);
            }
        }
    }

    class QuestionEditor extends DefaultCellEditor {
        private JComboBox<String> comboBox;
        public QuestionEditor(List<String> questions) {
            super(new JComboBox<>());
            comboBox = (JComboBox<String>) getComponent();
            comboBox.removeAllItems();
            for (String ques : questions)
                comboBox.addItem(ques);
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

