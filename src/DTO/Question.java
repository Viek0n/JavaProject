package DTO;
public class Question {
    int ID;
    String text;
    int difficult;
    String ChapterID;
    String Chapter;
    String Subject;
    String SubjectID;
    String CreatedBy;
    Answer ans;

    //Cons
    public Question(String Chapter, String ChapterID, String CreatedBy, int ID, String Subject, String SubjectID, Answer ans, int difficult, String text) {
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

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
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

    public Answer getAns() {
        return ans;
    }

    public void setAns(Answer ans) {
        this.ans = ans;
    }
}
