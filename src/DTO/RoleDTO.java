package DTO;

public class RoleDTO {
    private int ID;
    private String Name;
    private Boolean takeExam;
    private Boolean seeQuest, addQuest, updateQuest, deleteQuest;
    private Boolean seeUser , addUser, updateUser, deleteUser;
    private Boolean seeExam, addExam, updateExam, deleteExam;
    private Boolean seeRole, addRole, updateRole, deleteRole;

    private void init(){
        this.addExam = false;
        this.addQuest = false;
        this.addRole = false;
        this.addUser = false;
        this.deleteExam = false;
        this.deleteQuest = false;
        this.deleteRole = false;
        this.deleteUser = false;
        this.seeExam = false;
        this.seeQuest = false;
        this.seeRole = false;
        this.seeUser = false;
        this.takeExam = false;
        this.updateExam = false;
        this.updateQuest = false;
        this.updateRole = false;
        this.updateUser = false;
    }
    
    @Override
    public String toString() {
        return  "\nID = " + ID +
                "\nName = '" + Name + '\'' +
                "\ntakeExam = " + takeExam +
                "\nseeQuest = " + seeQuest +
                ", addQuest = " + addQuest +
                ", updateQuest = " + updateQuest +
                ", deleteQuest = " + deleteQuest +
                "\nseeUser = " + seeUser +
                ", addUser = " + addUser +
                ", updateUser = " + updateUser +
                ", deleteUsers = " + deleteUser +
                "\nseeExam = " + seeExam +
                ", addExam = " + addExam +
                ", updateExam = " + updateExam +
                ", deleteExam = " + deleteExam +
                "\nseeRole = " + seeRole +
                ", addRole = " + addRole +
                ", updateRole = " + updateRole +
                ", deleteRole = " + deleteRole +
                "\n}";
    }
    //init
    
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

    public Boolean getAddQuest() {
        return addQuest;
    }

    public void setAddQuest(Boolean addQuest) {
        this.addQuest = addQuest;
    }

    public Boolean getAddUser() {
        return addUser;
    }

    public void setAddUser(Boolean addUser) {
        this.addUser = addUser;
    }

    public Boolean getAddExam() {
        return addExam;
    }

    public void setAddExam(Boolean addExam) {
        this.addExam = addExam;
    }

    public Boolean getAddRole() {
        return addRole;
    }

    public void setAddRole(Boolean addRole) {
        this.addRole = addRole;
    }

}
