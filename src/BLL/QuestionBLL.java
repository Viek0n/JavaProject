package BLL;

import DAL.QuestionDAL;
import DTO.QuestionDTO;

public class QuestionBLL {
    //Valid check
    
    //Get
    public static QuestionDTO get(String ID){
        return QuestionDAL.getByID(ID);
    }
    //Insert
    public static Boolean add(QuestionDTO quest){
        return QuestionDAL.add(quest);
    }
    //Update
    public static Boolean update(QuestionDTO quest){
        return QuestionDAL.update(quest);
    }
    //Delete
    public static Boolean delete(String ID){
        return QuestionDAL.deleteByID(ID);
    }
}
