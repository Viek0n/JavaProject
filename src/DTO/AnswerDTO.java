package DTO;

public class AnswerDTO {
    String A,B,C,D;
    int RightAns;
    int RandVal;

    //Cons
    public AnswerDTO(String A, String B, String C, String D, int RightAns, int RandVal) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.RightAns = RightAns;
        this.RandVal = RandVal;
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

    public int getRandVal() {
        return RandVal;
    }

    public void setRandVal(int randVal) {
        RandVal = randVal;
    }
    
}
