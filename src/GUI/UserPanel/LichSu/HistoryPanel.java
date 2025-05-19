/*package GUI.UserPanel.LichSu;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import BLL.ChapterBLL;
import BLL.ExamBLL;
import BLL.ExamStructBLL;
import BLL.QuestionBLL;
import BLL.SubjectBLL;
import DTO.ChapterDTO;
import DTO.ExamStructDTO;
import DTO.ExamStructDetailDTO;
import DTO.ExamStructSelectedDTO;
import DTO.QuestionDTO;
import DTO.SubjectDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.Ulti;
import GUI.giaodienadmin.QuanLyDeThi.ExamDialogUtils;
import MICS.Connect;
import MICS.Enums;
import java.awt.Color;
import java.awt.Font;

public class HistoryPanel extends JPanel{
    private JTable table;
    private JPanel buttonPanel;
    private DefaultTableModel tableModel;
    private JTextField txtScore;

    private SubjectBLL subjectBLL;
    private ExamStructBLL examStructBLL;
    private QuestionBLL questionBLL;
    private ExamBLL examBLL;
    private ExamDTO exam;

    public HistoryPanel(String str) {
        subjectBLL = new SubjectBLL();
        examBLL = new ExamBLL();
        questionBLL = new QuestionBLL();

        initComponent();
        exam = examBLL.get(str);
        loadExamStructData();
    }

    private void initComponent() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        this.setLayout(new GridLayout(2, 1, 10, 10));

        JLabel lbScore = new JLabel("Điểm");
        txtScore = new JTextField();
        String[] randomColumn = {"Câu hỏi", "A", "B", "C", "D", "Lựa chọn", "Câu trả lời đúng"};
        tableModel = new DefaultTableModel(randomColumn, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
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

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        showDetail();
    }

    public boolean loadExamStructData() {
        if (examStruct == null) return false;
        ArrayList<ExamStructDetailDTO> tabletList = examStruct.getRandomDetail();
        ArrayList<ExamStructSelectedDTO> selectQuestList = examStruct.getSelectDetail();
        txtId.setText(examStruct.getID());
        txtName.setText(examStruct.getName());
        txtUser.setText(examStruct.getUserId());
        descriptionArea.setText(examStruct.getDesc());
        answerAllow.setSelected(examStruct.getAnswerAllow());
        subjectCmb.setSelectedItem(examStruct.getSubject().getID() + " - " + examStruct.getSubject().getName());
        timeCmb.setSelectedItem(examStruct.getExamTime().toString());
        for(ExamStructDetailDTO detail: tabletList)
            randomTable.addRow(new Object[]{detail.getChapID() + " - " + chapterBLL.get(detail.getChapID()).getName(),detail.getDiff().toString(),detail.getQuantity(),""});
        for(ExamStructSelectedDTO select: selectQuestList)
            selectTable.addRow(new Object[]{select.getQuestID() + " - " + questionBLL.get(select.getQuestID()).getText(),""});
        return true;
    }
}
 */