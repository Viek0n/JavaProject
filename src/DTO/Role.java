package DTO;

public class Role {
    private String ID;
    private String name;
    private Boolean takeExam;
    private Boolean seeQuest, updateQuest, deleteQuest;
    private Boolean seeUser , updateUser, deleteUser;
    private Boolean seeExam, updateExam, deleteExam;
    private Boolean seeRole, updateRole, deleteRole;
    
    //Cons
    public Role(String ID, Boolean deleteExam, Boolean deleteQuest, Boolean deleteRole, Boolean deleteUser, String name, Boolean seeExam, Boolean seeQuest, Boolean seeRole, Boolean seeUser, Boolean takeExam, Boolean updateExam, Boolean updateQuest, Boolean updateRole, Boolean updateUser) {
        this.ID = ID;
        this.deleteExam = deleteExam;
        this.deleteQuest = deleteQuest;
        this.deleteRole = deleteRole;
        this.deleteUser = deleteUser;
        this.name = name;
        this.seeExam = seeExam;
        this.seeQuest = seeQuest;
        this.seeRole = seeRole;
        this.seeUser = seeUser;
        this.takeExam = takeExam;
        this.updateExam = updateExam;
        this.updateQuest = updateQuest;
        this.updateRole = updateRole;
        this.updateUser = updateUser;
    }

    //Getter&stetter
    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
