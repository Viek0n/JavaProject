package DTO;

public class ExamStructSelectedDTO {
    private String examStructID;
    private String questID;

    @Override
    public String toString(){
        return examStructID + " - " + questID + "\n";
    }
    public String getExamStructID() {
        return examStructID;
    }

    public void setExamStructID(String examStructID) {
        this.examStructID = examStructID;
    }

    public String getQuestID() {
        return questID;
    }

    public void setQuestID(String questID) {
        this.questID = questID;
    }

    public ExamStructSelectedDTO(String examStructID, String questID) {
        this.examStructID = examStructID;
        this.questID = questID;
    }

    public ExamStructSelectedDTO() {
    }


}
