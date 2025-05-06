package DTO;

import MICS.Enums;

public class ExamStructDetailDTO {
    private String examStructID;
    private String chapID;
    private Enums.DifficultValue diff;
    private int quantity;

    public String toString(){
        return examStructID + " - " + chapID + ": " + diff + "(" +quantity+")\n";
    }

    public ExamStructDetailDTO() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ExamStructDetailDTO(String chapID, Enums.DifficultValue diff, String examStructID, int quantity) {
        this.chapID = chapID;
        this.diff = diff;
        this.examStructID = examStructID;
        this.quantity = quantity;
    }

    public String getExamStructID() {
        return examStructID;
    }

    public void setExamStructID(String examStructID) {
        this.examStructID = examStructID;
    }

    public String getChapID() {
        return chapID;
    }

    public void setChapID(String chapID) {
        this.chapID = chapID;
    }

    public Enums.DifficultValue getDiff() {
        return diff;
    }

    public void setDiff(Enums.DifficultValue diff) {
        this.diff = diff;
    }

}
