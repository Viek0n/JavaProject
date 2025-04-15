package DTO;

public class AnswerDTO {
    String A,B,C,D;
    int RightAns;

    //Cons
    public AnswerDTO(String A, String B, String C, String D, int RightAns) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.RightAns = RightAns;
    }

    public AnswerDTO() {
    }

    //Setters&Getters
    public String getA() {
        return A;
    }
    public void setA(String a) {
        A = a;
    }
    public String getB() {
        return B;
    }
    public void setB(String b) {
        B = b;
    }
    public String getC() {
        return C;
    }
    public void setC(String c) {
        C = c;
    }
    public String getD() {
        return D;
    }
    public void setD(String d) {
        D = d;
    }
    public int getRightAns() {
        return RightAns;
    }
    public void setRightAns(int rightAns) {
        RightAns = rightAns;
    }
}
