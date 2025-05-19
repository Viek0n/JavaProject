package DTO;
/*
see - add - update - delete
 * 0 - 3: Quest
 * 4 - 7: Exam
*/
public class RoleDTO {
    private int ID;
    private String Name;
    private Boolean takeExam, admin;
    private Boolean Permit[] = new Boolean[8];
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void setPermit(Boolean[] permit) {
        Permit = permit;
    }
    private void init(){
        takeExam = false;
        admin = false;
        for(int i = 0; i < 8; i++)
            Permit[i] = false;
    }
    
    //Cons
    public RoleDTO(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
        init();
    }

    public RoleDTO() {
    }

    //Getter&stetter
    
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    
    public Boolean[] getPermit() {
        return Permit;
    }

    public Boolean getTakeExam() {
        return takeExam;
    }
    public void setTakeExam(Boolean takeExam) {
        this.takeExam = takeExam;
    }

    //Permit
    public Boolean getSeeQuest() {
        return Permit[0];
    }

    public void setSeeQuest(Boolean seeQuest) {
        this.Permit[0] = seeQuest;
    }

    public Boolean getAddQuest() {
        return Permit[1];
    }

    public void setAddQuest(Boolean addQuest) {
        this.Permit[1] = addQuest;
    }

    public Boolean getUpdateQuest() {
        return Permit[2];
    }
    public void setUpdateQuest(Boolean updateQuest) {
        this.Permit[2] = updateQuest;
    }

    public Boolean getDeleteQuest() {
        return Permit[3];
    }
    public void setDeleteQuest(Boolean deleteQuest) {
        this.Permit[3] = deleteQuest;
    }

    public Boolean getSeeExam() {
        return Permit[4];
    }
    public void setSeeExam(Boolean seeExam) {
        this.Permit[4] = seeExam;
    }

    public Boolean getAddExam() {
        return Permit[5];
    }
    public void setAddExam(Boolean addExam) {
        this.Permit[5] = addExam;
    }

    public Boolean getUpdateExam() {
        return Permit[6];
    }
    public void setUpdateExam(Boolean updateExam) {
        this.Permit[6] = updateExam;
    }

    public Boolean getDeleteExam() {
        return Permit[7];
    }
    public void setDeleteExam(Boolean deleteExam) {
        this.Permit[7] = deleteExam;
    } 
}
