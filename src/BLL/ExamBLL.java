package BLL;

import DTO.ExamDTO;
import DTO.ExamStructDTO;
import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.Collections;

public class ExamBLL {
    private ExamStructBLL examStructBLL;

    public ExamBLL() {
        examStructBLL = new ExamStructBLL();
    }

    public ExamDTO gen(String userID, ExamStructDTO examStruct){
        ExamDTO exam = new ExamDTO();
        exam.setUserID(userID);
        exam.setExamStructID(examStruct.getID());
        ArrayList<QuestionDTO> quest = examStructBLL.genRandom(examStruct.getID());
        quest.addAll(examStructBLL.loadSelect(examStruct.getID()));
        Collections.shuffle(quest);
        exam.setQuestion(quest);
        exam.setRemainingTime(examStruct.getExamTime());
        exam.setChoice(new ArrayList<>(Collections.nCopies(quest.size(), -1)));
        return exam;
    }

    public float calculate(ExamDTO exam){
        int right = 0;
        for (int i = 0; i < exam.getQuestion().size(); i++) {
            QuestionDTO quest = exam.getQuestion().get(i);
            int choice = exam.getChoice().get(i);
        
            if(choice != -1 && quest.getAns().get(choice).getRight()) right++;
        }

        return ((float)right/exam.getQuestion().size())*10;
    }
}
