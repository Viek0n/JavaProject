package BLL;

import DAL.ChapterDAL;
import DTO.ChapterDTO;

public class ChapterBLL {
    private ChapterDAL chapterDAL;
    public ChapterBLL(){
        chapterDAL = new ChapterDAL();
    }
    public Boolean add(ChapterDTO chap){
        return chapterDAL.add(chap);
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
