package DTO;
import MICS.Enums;
public class QuestionDTO {
    int ID;
    String text;
    Enums.DifficultValue difficult;
    String ChapterID;
    String Chapter;
    String CreatedBy;
    AnswerDTO ans;

    //Cons
    public QuestionDTO(int ID, String ChapterID, String CreatedBy, Enums.DifficultValue difficult, String text, AnswerDTO ans) {
        this.ID = ID;
        this.ChapterID = ChapterID;
        this.CreatedBy = CreatedBy;
        this.ans = ans;
        this.difficult = difficult;
        this.text = text;
    }

    public QuestionDTO(String ChapterID, String CreatedBy, Enums.DifficultValue difficult, String text, AnswerDTO ans) {
        this.ChapterID = ChapterID;
        this.CreatedBy = CreatedBy;
        this.ans = ans;
        this.difficult = difficult;
        this.text = text;
    }

    public QuestionDTO() {
    }

    //Setters&Getters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public String getChapterID() {
        return ChapterID;
    }

    public void setChapterID(String ChapterID) {
        this.ChapterID = ChapterID;
    }

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String Chapter) {
        this.Chapter = Chapter;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public AnswerDTO getAns() {
        return ans;
    }

    public void setAns(AnswerDTO ans) {
        this.ans = ans;
    }
}
