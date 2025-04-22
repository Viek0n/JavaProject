package BLL;

import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import java.util.ArrayList;
public class QuestionBLL {
    //Valid check
    private static Boolean checkAns(ArrayList<AnswerDTO> ans){
        Boolean valid = false;
        for(AnswerDTO a: ans)
            if(a.getRight())
                valid = true;
        return valid;
    }
    //Get
    public static QuestionDTO get(String ID){
        return QuestionDAL.getByID(ID);
    }
    //Insert
    public static Boolean add(QuestionDTO quest){
        if(checkAns(quest.getAns()))
            return QuestionDAL.add(quest);
        return false;
    }
    //Update
    public static Boolean update(QuestionDTO quest){
        if(checkAns(quest.getAns()))
            return QuestionDAL.update(quest);
        return false;
    }
    //Delete
    public static Boolean delete(String ID){
        return QuestionDAL.deleteByID(ID);
    }
}
