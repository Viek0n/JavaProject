package BLL;

import DAL.QuestionDAL;
import DTO.QuestionDTO;

public class QuestionBLL {
    //Insert
    public static Boolean addQuestion(QuestionDTO quest){
        QuestionDAL.addQuestion(quest);
        return false;
    }
    //Update
    public static Boolean updateQuestion(QuestionDTO quest){
        QuestionDAL.updateQuestion(quest);
        return false;
    }
    //Delete
    public static Boolean deleteQuestion(int ID){
        QuestionDAL.deleteQuestByID(ID);
        return false;
    }
}
