package DTO;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ExamStructDTO {
    String ID;
    String Name;
    String Desc;
    String UserId;
    Boolean answerAllow;
    Date Start, End;
    Time ExamTime;
    SubjectDTO subject;
    ArrayList<ExamStructDetailDTO> randomDetail;
    ArrayList<ExamStructSelectedDTO> selectDetail;
    
    @Override
    public String toString(){
        String x = ID + ": " + Name + "\n" + Desc + "\nTime: "+Start+" - "+End+"\nIn: "+ExamTime+"\n"+subject;
        x+="\nrandomQues\n";
        for(ExamStructDetailDTO q: randomDetail)
            x+=q;
        x+="\nselectedQues\n";
        for(ExamStructSelectedDTO q: selectDetail)
            x+=q;
        return x;
    }

    //Cons
    public ExamStructDTO() {
    }

    public ExamStructDTO(String Name, String Desc, Date Start,  Date End, Time ExamTime, SubjectDTO subject) {
        this.Desc = Desc;
        this.End = End;
        this.ExamTime = ExamTime;
        this.Name = Name;
        this.Start = Start;
        this.subject = subject;
    }
    
    //Getters&Setters
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public Date getStart() {
        return Start;
    }
    public void setStart(Date start) {
        this.Start = start;
    }
    public Date getEnd() {
        return End;
    }
    public void setEnd(Date end) {
        this.End = end;
    }
    public Time getExamTime() {
        return ExamTime;
    }
    public void setExamTime(Time examTime) {
        this.ExamTime = examTime;
    } 

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public ArrayList<ExamStructDetailDTO> getRandomDetail() {
        return randomDetail;
    }

    public void setRandomDetail(ArrayList<ExamStructDetailDTO> randomDetail) {
        this.randomDetail = randomDetail;
    }

    public ArrayList<ExamStructSelectedDTO> getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(ArrayList<ExamStructSelectedDTO> selectDetail) {
        this.selectDetail = selectDetail;
    }

    public Boolean getAnswerAllow() {
        return answerAllow;
    }

    public void setAnswerAllow(Boolean answerAllow) {
        this.answerAllow = answerAllow;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}
