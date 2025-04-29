package BLL;

import DAL.ExamStructDAL;
import DTO.ExamStructDTO;

public class ExamStructBLL {
    final private ExamStructDAL examStructDAL;

    public ExamStructBLL() {
        examStructDAL = new ExamStructDAL();
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
        return examStructDAL.deleteExamStruct(ID);
    }
}
