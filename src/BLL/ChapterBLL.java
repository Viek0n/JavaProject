package BLL;

import DAL.ChapterDAL;
import DTO.ChapterDTO;
import java.util.List;

public class ChapterBLL {
    final private ChapterDAL chapterDAL;
    public ChapterBLL(){
        chapterDAL = new ChapterDAL();
    }

    public ChapterDTO get(String ID){
        return chapterDAL.get(ID);
    }

    public List<ChapterDTO> getBySubject(String ID){
        return chapterDAL.getBySubject(ID);
    }

    public Boolean add(ChapterDTO chap, String subjectId){
        return chapterDAL.add(chap, subjectId);
    }

    //Update
    public Boolean update(ChapterDTO chap){
        return chapterDAL.update(chap);
    }

    //Delete
    public Boolean delete(String ID){
        return chapterDAL.delete(ID);
    }
}
