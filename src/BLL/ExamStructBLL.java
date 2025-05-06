package BLL;

import DAL.ExamStructDAL;
import DTO.ExamStructDTO;
import DTO.QuestionDTO;
import java.util.ArrayList;

public class ExamStructBLL {
    final private ExamStructDAL examStructDAL;

    public ExamStructBLL() {
        examStructDAL = new ExamStructDAL();
    }
    
    public ExamStructDTO get(String id){
        return examStructDAL.get(id);
    }
    //Insert
    public Boolean add(ExamStructDTO exam){
        return examStructDAL.add(exam);
    }
    //Update
    public Boolean update(ExamStructDTO exam){
        return examStructDAL.update(exam);
    }
    //Delete
    public Boolean deleteExamStruct(String ID){
        return examStructDAL.delete(ID);
    }
    //Get random
    public ArrayList<QuestionDTO> genRandom(String ID){
       return examStructDAL.loadRandom(ID);
    }

    public ArrayList<QuestionDTO> loadSelect(String ID){
        return examStructDAL.loadSelected(ID);
     }
}
