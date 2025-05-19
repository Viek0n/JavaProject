package BLL;

import DAL.ExamDAL;
import DTO.ExamDTO;
import DTO.ExamStructDTO;
import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.Collections;

public class ExamBLL {
    private ExamStructBLL examStructBLL;
    private ExamDAL examDAL;

    public ExamBLL() {
        examStructBLL = new ExamStructBLL();
        examDAL = new ExamDAL();
    }

    public ExamDTO gen(String userID, ExamStructDTO examStruct){
        ExamDTO exam = new ExamDTO();
        exam.setExamId(examDAL.getNextId());
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

    public Boolean add(ExamDTO exam){
        return examDAL.add(exam);
    }

    public ArrayList<ExamDTO> getFromUser(String id){
        return examDAL.getAll(id);
    }

    public ExamDTO get(String id){
        return examDAL.get(id);
    }
}
