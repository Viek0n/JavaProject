package DTO;
import mics.Enums;
public class QuestionDTO {
    int ID;
    String text;
    Enums.DifficultValue difficult;
    String ChapterID;
    String Chapter;
    String Subject;
    String SubjectID;
    String CreatedBy;
    AnswerDTO ans;

    //Cons
    public QuestionDTO(String Chapter, String ChapterID, String CreatedBy, int ID, String Subject, String SubjectID, AnswerDTO ans, Enums.DifficultValue difficult, String text) {
        this.Chapter = Chapter;
        this.ChapterID = ChapterID;
        this.CreatedBy = CreatedBy;
        this.ID = ID;
        this.Subject = Subject;
        this.SubjectID = SubjectID;
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

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String SubjectID) {
        this.SubjectID = SubjectID;
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
