package BLL;

import DAL.SubjectDAL;
import DTO.SubjectDTO;

public class SubjectBLL {
    final private SubjectDAL subjectDAL;
    public SubjectBLL(){
        subjectDAL = new SubjectDAL();
    }
    public Boolean add(SubjectDTO subject){
        return subjectDAL.add(subject);
    }
    //Update
    public Boolean update(SubjectDTO subject){
        return subjectDAL.update(subject);
    }
    //Delete
    public Boolean delete(String ID){
        return subjectDAL.delete(ID);
    }
}
