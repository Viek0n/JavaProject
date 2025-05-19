package GUI.UserPanel.LichSu;

import BLL.ExamBLL;
import BLL.ExamStructBLL;
import DTO.AnswerDTO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import GUI.MakeColor.Ulti;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HistoryPanel extends JPanel{
    private ExamBLL examBLL;
    private ExamStructBLL examStructBLL;
    private ExamDTO exam;
    private JScrollPane scrollPane;
    private Boolean seeAns;

    public HistoryPanel(String str) {
        examBLL = new ExamBLL();
        examStructBLL = new ExamStructBLL();
        exam = examBLL.get(str);
        seeAns = examStructBLL.get(exam.getExamStructID()).getAnswerAllow();
        initComponent();
    }

    private void initComponent() {
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        this.setLayout(new BorderLayout());

        scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Ulti.MainColor);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        loadExamStructData();
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public boolean loadExamStructData() {
        if (exam == null) return false;
        ArrayList<QuestionDTO> questList = exam.getQuestion();
        ArrayList<Integer> choiceList = exam.getChoice();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // Vertical stacking
        container.setBackground(Color.WHITE);

        for (int i = 0; i < questList.size(); i++) {
            JPanel questPanel = new JPanel(new BorderLayout());
            questPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            questPanel.setBackground(Color.LIGHT_GRAY);

            JLabel question = new JLabel((i+1) + ". " + questList.get(i).getText());
            question.setFont(new Font("Arial", Font.BOLD, 14));

            ArrayList<AnswerDTO> ans = questList.get(i).getAns();
            JLabel[] lb = new JLabel[4];
            JPanel ansPanel = new JPanel(new GridLayout(2, 2, 1, 1));
            
            int select = choiceList.get(i);
            int right = -1;
            for (int j = 0; j < 4; j++) {
                lb[j] = new JLabel((char)('A' + j) + ". " + ans.get(j).getText());
                if(ans.get(j).getRight())
                    right = j;
                ansPanel.add(lb[j]);
            }

            if(select!=-1){
                if(seeAns){
                    if(ans.get(select).getRight())
                        lb[select].setForeground(Color.green);
                    else{
                        lb[select].setForeground(Color.red);
                        lb[right].setForeground(Color.green);
                    }
                }else
                    lb[select].setForeground(Color.green);
            }
            else
                question.setForeground(Color.RED);

            questPanel.add(question, BorderLayout.NORTH);
            questPanel.add(ansPanel, BorderLayout.CENTER);
            container.add(questPanel);
        }

        scrollPane.setViewportView(container); 
        return true;
    }
}