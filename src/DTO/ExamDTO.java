package DTO;

import java.sql.Time;
import java.util.ArrayList;

public class ExamDTO {
    private String examId;
    private String userID;
    private String examStructID;
    private ArrayList<QuestionDTO> question;
    private ArrayList<Integer> choice;
    private Time remainingTime;
    private float Score;

    public ExamDTO() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float calculateScore(){
        int right = 0;
        for (int i = 0; i < question.size(); i++) {
            QuestionDTO quest = question.get(i);
            int input = choice.get(i);
            if(input != -1 && quest.getAns().get(input).getRight()) right++;
        }
        Score = Math.round(((float) right / question.size()) * 10 * 100) / 100f;
        return Score;
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

    public float getScore() {
        return Score;
    }

    public void setScore(float Score) {
        this.Score = Score;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }
}
