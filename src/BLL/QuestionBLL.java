package BLL;

import DAL.QuestionDAL;
import DTO.QuestionDTO;

public class QuestionBLL {
    public static QuestionDTO get(String ID){
        return QuestionDAL.getByID(ID);
    }
    //Insert
    public static Boolean add(QuestionDTO quest){
        QuestionDAL.add(quest);
        return false;
    }
    //Update
    public static Boolean update(QuestionDTO quest){
        QuestionDAL.update(quest);
        return false;
    }
    //Delete
    public static Boolean delete(String ID){
        QuestionDAL.deleteByID(ID);
        return false;
    }
}
