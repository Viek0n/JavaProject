package DTO;
import MICS.Enums;
import java.util.ArrayList;
public class QuestionDTO {
    String ID;
    String text;
    Enums.DifficultValue difficult;
    ChapterDTO chap;
    SubjectDTO subject;
    String CreatedBy;
    ArrayList<AnswerDTO> ans; 

    @Override
    public String toString(){
        String tmp = "======================\nID: "+ID+"\nDo kho: "+difficult.toString()+"\n"+chap+ "\n"+ subject + "\n"+text+"\n";
        tmp+= ans.get(0);
        tmp+= ans.get(1);
        tmp+= ans.get(2);
        tmp+= ans.get(3);
        return tmp;
    }
    
    //Cons
    public QuestionDTO(String ID, ChapterDTO chap, String CreatedBy, Enums.DifficultValue difficult, String text) {
        this.ID = ID;
        this.chap = chap;
        this.CreatedBy = CreatedBy;
        this.difficult = difficult;
        this.text = text;
    }

    public QuestionDTO(String ID, ChapterDTO chap, String CreatedBy, Enums.DifficultValue difficult, String text, ArrayList<AnswerDTO> ans) {
        this.ID = ID;
        this.chap = chap;
        this.CreatedBy = CreatedBy;
        this.ans = ans;
        this.difficult = difficult;
        this.text = text;
    }

    public QuestionDTO(ChapterDTO chap, String CreatedBy, Enums.DifficultValue difficult, String text, ArrayList<AnswerDTO> ans) {
        this.chap = chap;
        this.CreatedBy = CreatedBy;
        this.ans = ans;
        this.difficult = difficult;
        this.text = text;
    }

    public QuestionDTO() {
    }

    //Setters&Getters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Enums.DifficultValue getDifficult() {
        return difficult;
    }

    public void setDifficult(Enums.DifficultValue difficult) {
        this.difficult = difficult;
    }

    public ChapterDTO getChapter() {
        return chap;
    }

    public void setChapter(ChapterDTO chap) {
        this.chap = chap;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public ArrayList<AnswerDTO> getAns() {
        return ans;
    }

    public void setAns(ArrayList<AnswerDTO> ans) {
        this.ans = ans;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }
}
