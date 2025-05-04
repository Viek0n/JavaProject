package DTO;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ExamStructDTO {
    String ID;
    String Name;
    String Desc;
    Date Start, End;
    Time ExamTime;
    SubjectDTO subject;
    ArrayList<QuestionDTO> selectedQues;
    ArrayList<QuestionDTO> randomQues;
    
    @Override
    public String toString(){
        String x = ID + ": " + Name + "\n" + Desc + "\nTime: "+Start+" - "+End+"\nIn: "+ExamTime+"\n"+subject;
        x+="\nrandomQues\n";
        for(QuestionDTO q: selectedQues)
            x+=q;
        x+="\nselectedQues\n";
        for(QuestionDTO q: selectedQues)
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

    public ArrayList<QuestionDTO> getSelectedQues() {
        return selectedQues;
    }

    public void setSelectedQues(ArrayList<QuestionDTO> selectedQues) {
        this.selectedQues = selectedQues;
    }

    public ArrayList<QuestionDTO> getRandomQues() {
        return randomQues;
    }

    public void setRandomQues(ArrayList<QuestionDTO> randomQues) {
        this.randomQues = randomQues;
    }

}
