package BLL;

import DAL.SubjectDAL;
import DTO.SubjectDTO;
import java.util.List;

public class SubjectBLL {
    final private SubjectDAL subjectDAL;
    public SubjectBLL(){
        subjectDAL = new SubjectDAL();
    }

    public List<SubjectDTO> getAll(){
        return subjectDAL.getAll();
    }

    public SubjectDTO get(String subjectId){
        return subjectDAL.get(subjectId);
    }

    public String getNextId(){
        return subjectDAL.getNextId();
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
