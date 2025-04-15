package DTO;

import java.sql.Time;
import java.util.Date;

public class ExamStructDTO {
    int ID;
    String Name;
    String Style;
    Date Start, End;
    Time ExamTime;
    String Subject;
    
    //Cons
    public ExamStructDTO(int iD, String name, String style, Date start, Date end, Time examTime, String subject) {
        ID = iD;
        Name = name;
        Style = style;
        Start = start;
        End = end;
        ExamTime = examTime;
        Subject = subject;
    }

    public ExamStructDTO() {
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
    public String getStyle() {
        return Style;
    }
    public void setStyle(String style) {
        Style = style;
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
    public String getSubject() {
        return Subject;
    }
    public void setSubject(String subject) {
        Subject = subject;
    }
    
}
