package DTO;

import java.sql.Time;
import java.util.ArrayList;

public class ExamDTO {
    private String userID;
    private String examStructID;
    private ArrayList<QuestionDTO> question;
    private ArrayList<Integer> choice;
    private Time remainingTime;

    public ExamDTO() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExamStructID() {
        return examStructID;
    }

    public void setExamStructID(String examStructID) {
        this.examStructID = examStructID;
    }

    public ArrayList<QuestionDTO> getQuestion() {
        return question;
    }

    public void setQuestion(ArrayList<QuestionDTO> question) {
        this.question = question;
    }

    public Time getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Time remainingTime) {
        this.remainingTime = remainingTime;
    }

    public ArrayList<Integer> getChoice() {
        return choice;
    }

    public void setChoice(ArrayList<Integer> choice) {
        this.choice = choice;
    }
}
