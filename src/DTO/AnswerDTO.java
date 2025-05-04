package DTO;

import MICS.Enums;

public class AnswerDTO {
    String Text;
    Boolean right;
    Enums.AnswerID ID;

    @Override
    public String toString(){
        return ID+". "+Text + " " + (right ? 1 : 0) + "\n";
    }
    //Cons
    public AnswerDTO() {
    }

    public AnswerDTO(String Text, Boolean right, Enums.AnswerID ID) {
        this.Text = Text;
        this.right = right;
        this.ID = ID;
    }

    //Setters&Getters
    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }
    public Enums.AnswerID getID() {
        return ID;
    }
    public void setID(Enums.AnswerID iD) {
        ID = iD;
    }
}
