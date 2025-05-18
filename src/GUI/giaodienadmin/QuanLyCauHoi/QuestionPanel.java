package GUI.giaodienadmin.QuanLyCauHoi;

import BLL.ChapterBLL;
import BLL.SubjectBLL;
import BLL.QuestionBLL;
import DTO.AnswerDTO;
import DTO.ChapterDTO;
import DTO.QuestionDTO;
import DTO.SubjectDTO;
import MICS.Enums;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class QuestionPanel extends JPanel {
    private JTextField txtId, txtCreate;
    private JTextArea txtText;
    private JComboBox<String> diffCmb, cmbChap, cmbSubject;
    private JTextField[] txtAns;
    private JCheckBox[] right;
    private JButton btnSave;

    private QuestionBLL questionBLL;
    private ChapterBLL chapterBLL;
    private SubjectBLL subjectBLL;

    private QuestionDTO question;
    private boolean isEditMode;

    public QuestionPanel(String str, Boolean mode) {
        isEditMode = mode;
        questionBLL = new QuestionBLL();
        chapterBLL = new ChapterBLL();
        subjectBLL = new SubjectBLL();
        initComponent();

        if(isEditMode){
            question = questionBLL.get(str);
            loadQuestionData(str);
        }else{
            question = new QuestionDTO();
            txtCreate.setText(str);
        }
    }

    private void initComponent() {
        this.setLayout(new GridLayout(12, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lbId = new JLabel("Mã câu hỏi:");
        txtId = new JTextField(questionBLL.getNextId());
        txtId.setEditable(false);

        JLabel lbText = new JLabel("Câu hỏi:");
        txtText = new JTextArea();

        JLabel lbCreate = new JLabel("Người tạo:");
        txtCreate = new JTextField();
        txtCreate.setEditable(false);

        JLabel lbDiff = new JLabel("Độ khó:");
        diffCmb = new JComboBox<>(new String[]{"DE", "TRUNGBINH", "KHO"});

        JLabel lbSubject = new JLabel("Môn");
        cmbSubject = new JComboBox<>();
        loadSubject();
        cmbSubject.setSelectedIndex(0);
        cmbSubject.addActionListener(e -> {
            loadChap();
        });

        JLabel lbChap = new JLabel("Chương");
        cmbChap = new JComboBox<>();

        JLabel lbAns = new JLabel("Đáp án");
        txtAns = new JTextField[4];
        for (int i = 0; i < txtAns.length; i++) {
            txtAns[i] = new JTextField();
        }

        JLabel lbCheck = new JLabel("Đúng");
        right = new JCheckBox[4];
        for (int i = 0; i < right.length; i++) {
            right[i] = new JCheckBox();
            final int index = i;
            right[i].addActionListener(e -> {
                 for (int j = 0; j < right.length; j++)
                    right[j].setSelected(false);
                right[index].setSelected(true);
            });
        }


        btnSave = new JButton("Lưu");
        btnSave.setBackground(Color.decode("#4caf50"));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> save());

        this.add(lbId); 
        this.add(txtId);
        this.add(lbText);      
        this.add(txtText);
        this.add(lbCreate);      
        this.add(txtCreate);
        this.add(lbDiff);    
        this.add(diffCmb);
        this.add(lbSubject);
        this.add(cmbSubject);
        this.add(lbChap);
        this.add(cmbChap);
        this.add(lbAns);
        this.add(lbCheck);
        for (int i = 0; i < 4; i++) {
            this.add(txtAns[i]);
            this.add(right[i]);
        }
        this.add(new JLabel()); 
        this.add(btnSave);
    }

    public boolean loadQuestionData(String quesId) {
        question = questionBLL.get(quesId);
        if (question == null) return false;

        ArrayList<AnswerDTO> ans = question.getAns();
        txtId.setText(question.getID());
        txtText.setText(question.getText());
        txtCreate.setText(question.getCreatedBy());
        diffCmb.setSelectedItem(question.getDifficult().toString());
        cmbSubject.setSelectedItem(question.getSubject().getID() + " - " + question.getSubject().getName());
        cmbChap.setSelectedItem(question.getChapter().getID() + " - " + question.getChapter().getName());

        for(int i = 0; i < 4; i++){
            txtAns[i].setText(ans.get(i).getText());
            right[i].setSelected(ans.get(i).getRight());
        }
        return true;
    }

    private void loadSubject(){
        List<SubjectDTO> subjects = subjectBLL.getAll();
        cmbSubject.addItem("Chọn môn");
        for(SubjectDTO sub: subjects)
            cmbSubject.addItem(sub.getID() + " - " + sub.getName());
    }

    private void loadChap(){
        String subject = (String) cmbSubject.getSelectedItem();
        List<ChapterDTO> chaps = chapterBLL.getBySubject(subject.split(" - ")[0]);
        cmbChap.removeAllItems();
        for(ChapterDTO chap: chaps)
            cmbChap.addItem(chap.getID() + " - " + chap.getName());
    }

    private void save() {
        String id = txtId.getText();
        String text = txtText.getText();
        String diff =   (String)diffCmb.getSelectedItem();
        String subject = (String)cmbSubject.getSelectedItem();
        String chapter = (String)cmbChap.getSelectedItem();
        ArrayList<AnswerDTO> ansList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            AnswerDTO ans = new AnswerDTO(txtAns[i].getText(), right[i].isSelected());
            ansList.add(ans);
        }

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chứ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        question.setID(id);
        question.setText(text);
        question.setCreatedBy(txtCreate.getText());
        question.setDifficult(Enums.DifficultValue.valueOf(diff));
        question.setSubject(subjectBLL.get(subject.split(" - ")[0]));
        question.setChapter(chapterBLL.get(chapter.split(" - ")[0]));
        question.setAns(ansList);
        boolean success;
        if (isEditMode) {
            success = questionBLL.update(question);
        } else {
            success = questionBLL.add(question);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.getWindowAncestor(this).dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Thất bại", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
