package DTO;

import MICS.Enums;

public class ExamStructDetailDTO {
    private String examStructID;
    private String chapID;
    private Enums.DifficultValue diff;
    private int quantity;

    public ExamStructDetailDTO() {
    }

    public ExamStructDetailDTO(String chapID, Enums.DifficultValue diff, String examStructID, int quantity) {
        if (chapID == null || chapID.trim().isEmpty()) {
            throw new IllegalArgumentException("Chapter ID cannot be null or empty");
        }
        if (diff == null) {
            throw new IllegalArgumentException("Difficulty cannot be null");
        }
        if (examStructID != null && examStructID.trim().isEmpty()) {
            throw new IllegalArgumentException("Exam structure ID cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.chapID = chapID;
        this.diff = diff;
        this.examStructID = examStructID;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return examStructID + " - " + chapID + ": " + diff + "(" + quantity + ")";
    }

    public String getExamStructID() {
        return examStructID;
    }

    public void setExamStructID(String examStructID) {
        if (examStructID != null && examStructID.trim().isEmpty()) {
            throw new IllegalArgumentException("Exam structure ID cannot be empty");
        }
        this.examStructID = examStructID;
    }

    public String getChapID() {
        return chapID;
    }

    public void setChapID(String chapID) {
        if (chapID == null || chapID.trim().isEmpty()) {
            throw new IllegalArgumentException("Chapter ID cannot be null or empty");
        }
        this.chapID = chapID;
    }

    public Enums.DifficultValue getDiff() {
        return diff;
    }

    public void setDiff(Enums.DifficultValue diff) {
        if (diff == null) {
            throw new IllegalArgumentException("Difficulty cannot be null");
        }
        this.diff = diff;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }
}