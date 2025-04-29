package BLL;

import DAL.QuestionDAL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import java.util.ArrayList;
public class QuestionBLL {
    final private QuestionDAL questionDAL;

    public QuestionBLL() {
        questionDAL = new QuestionDAL();
    }
    
    //Valid check
    private Boolean checkAns(ArrayList<AnswerDTO> ans){
        Boolean valid = false;
        for(AnswerDTO a: ans)
            if(a.getRight())
                valid = true;
        return valid;
    }
    //Get
    public QuestionDTO get(String ID){
        return questionDAL.getByID(ID);
    }

    public ArrayList<QuestionDTO> getAll(){
        return questionDAL.getAll();
    }
    //Insert
    public Boolean add(QuestionDTO quest){
        return questionDAL.add(quest);
    }
    //Update
    public Boolean update(QuestionDTO quest){
        if(checkAns(quest.getAns()))
            return questionDAL.update(quest);
        return false;
    }
    //Delete
    public Boolean delete(String ID){
        return questionDAL.deleteByID(ID);
    }
}
