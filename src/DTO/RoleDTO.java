package DTO;

public class RoleDTO {
    private int ID;
    private String Name;
    private Boolean takeExam;
    private Boolean seeQuest, updateQuest, deleteQuest;
    private Boolean seeUser , updateUser, deleteUser;
    private Boolean seeExam, updateExam, deleteExam;
    private Boolean seeRole, updateRole, deleteRole;
    
    @Override
    public String toString(){
        return ID + "/ "+ Name;
    }
    //Cons
    public RoleDTO(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
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
    public Boolean getTakeExam() {
        return takeExam;
    }
    public void setTakeExam(Boolean takeExam) {
        this.takeExam = takeExam;
    }
    public Boolean getSeeQuest() {
        return seeQuest;
    }
    public void setSeeQuest(Boolean seeQuest) {
        this.seeQuest = seeQuest;
    }
    public Boolean getUpdateQuest() {
        return updateQuest;
    }
    public void setUpdateQuest(Boolean updateQuest) {
        this.updateQuest = updateQuest;
    }
    public Boolean getDeleteQuest() {
        return deleteQuest;
    }
    public void setDeleteQuest(Boolean deleteQuest) {
        this.deleteQuest = deleteQuest;
    }
    public Boolean getSeeUser() {
        return seeUser;
    }
    public void setSeeUser(Boolean seeUser) {
        this.seeUser = seeUser;
    }
    public Boolean getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(Boolean updateUser) {
        this.updateUser = updateUser;
    }
    public Boolean getDeleteUser() {
        return deleteUser;
    }
    public void setDeleteUser(Boolean deleteUser) {
        this.deleteUser = deleteUser;
    }
    public Boolean getSeeExam() {
        return seeExam;
    }
    public void setSeeExam(Boolean seeExam) {
        this.seeExam = seeExam;
    }
    public Boolean getUpdateExam() {
        return updateExam;
    }
    public void setUpdateExam(Boolean updateExam) {
        this.updateExam = updateExam;
    }
    public Boolean getDeleteExam() {
        return deleteExam;
    }
    public void setDeleteExam(Boolean deleteExam) {
        this.deleteExam = deleteExam;
    }
    public Boolean getSeeRole() {
        return seeRole;
    }
    public void setSeeRole(Boolean seeRole) {
        this.seeRole = seeRole;
    }
    public Boolean getUpdateRole() {
        return updateRole;
    }
    public void setUpdateRole(Boolean updateRole) {
        this.updateRole = updateRole;
    }
    public Boolean getDeleteRole() {
        return deleteRole;
    }
    public void setDeleteRole(Boolean deleteRole) {
        this.deleteRole = deleteRole;
    }
}
