package DTO;

public class AnswerDTO {
    String Text;
    Boolean right;

    @Override
    public String toString(){
        return " "+Text + " " + (right ? 1 : 0);
    }
    //Cons
    public AnswerDTO() {
    }

    public AnswerDTO(String Text, Boolean right) {
        this.Text = Text;
        this.right = right;
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
}
