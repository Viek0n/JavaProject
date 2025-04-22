package DTO;

import java.sql.Time;
import java.util.Date;

public class ExamStructDTO {
    int ID;
    String Name;
    String Desc;
    Date Start, End;
    Time ExamTime;
    SubjectDTO subject;
    
    //Cons


    public ExamStructDTO() {
    }

    public ExamStructDTO(String Desc, Date End, Time ExamTime, String Name, Date Start, SubjectDTO subject) {
        this.Desc = Desc;
        this.End = End;
        this.ExamTime = ExamTime;
        this.Name = Name;
        this.Start = Start;
        this.subject = subject;
    }
    
    //Getters&Setters
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public Date getStart() {
        return Start;
    }
    public void setStart(Date start) {
        Start = start;
    }
    public Date getEnd() {
        return End;
    }
    public void setEnd(Date end) {
        End = end;
    }
    public Time getExamTime() {
        return ExamTime;
    }
    public void setExamTime(Time examTime) {
        ExamTime = examTime;
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

}
