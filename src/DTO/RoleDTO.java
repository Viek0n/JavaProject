package DTO;
/*
see - add - update - delete
 * 0 - 3: Quest
 * 4 - 7: User
 * 8 - 11: Exam
 * 12 - 15: Role
*/
public class RoleDTO {
    private int ID;
    private String Name;
    private Boolean takeExam;
    private Boolean Permit[] = new Boolean[16];

    private void init(){
        takeExam = false;
        for(int i = 0; i < 16; i++)
            Permit[i] = false;
    }
    
    @Override
    public String toString() {
        return  "\nID = " + ID +
                "\nName = '" + Name + '\'' +
                "\ntakeExam = " + takeExam +
                "\nseeQuest = " + Permit[0] +
                ", addQuest = " + Permit[1] +
                ", updateQuest = " + Permit[2] +
                ", deleteQuest = " + Permit[3] +
                "\nseeUser = " + Permit[4] +
                ", addUser = " + Permit[5] +
                ", updateUser = " + Permit[6] +
                ", deleteUsers = " + Permit[7] +
                "\nseeExam = " + Permit[8] +
                ", addExam = " + Permit[9] +
                ", updateExam = " + Permit[10] +
                ", deleteExam = " + Permit[11] +
                "\nseeRole = " + Permit[12] +
                ", addRole = " + Permit[13] +
                ", updateRole = " + Permit[14] +
                ", deleteRole = " + Permit[15] +
                "\n";
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

    public Boolean getSeeUser() {
        return Permit[4];
    }
    public void setSeeUser(Boolean seeUser) {
        this.Permit[4] = seeUser;
    }

    public Boolean getAddUser() {
        return Permit[5];
    }
    public void setAddUser(Boolean addUser) {
        this.Permit[5] = addUser;
    }

    public Boolean getUpdateUser() {
        return Permit[6];
    }
    public void setUpdateUser(Boolean updateUser) {
        this.Permit[6] = updateUser;
    }

    public Boolean getDeleteUser() {
        return Permit[7];
    }
    public void setDeleteUser(Boolean deleteUser) {
        this.Permit[7] = deleteUser;
    }

    public Boolean getSeeExam() {
        return Permit[8];
    }
    public void setSeeExam(Boolean seeExam) {
        this.Permit[8] = seeExam;
    }

    public Boolean getAddExam() {
        return Permit[9];
    }
    public void setAddExam(Boolean addExam) {
        this.Permit[9] = addExam;
    }

    public Boolean getUpdateExam() {
        return Permit[10];
    }
    public void setUpdateExam(Boolean updateExam) {
        this.Permit[10] = updateExam;
    }

    public Boolean getDeleteExam() {
        return Permit[11];
    }
    public void setDeleteExam(Boolean deleteExam) {
        this.Permit[11] = deleteExam;
    }

    public Boolean getSeeRole() {
        return Permit[12];
    }
    public void setSeeRole(Boolean seeRole) {
        this.Permit[12] = seeRole;
    }

    public Boolean getAddRole() {
        return Permit[13];
    }
    public void setAddRole(Boolean addRole) {
        this.Permit[13] = addRole;
    }

    public Boolean getUpdateRole() {
        return Permit[14];
    }
    public void setUpdateRole(Boolean updateRole) {
        this.Permit[14] = updateRole;
    }
    public Boolean getDeleteRole() {
        return Permit[15];
    }
    public void setDeleteRole(Boolean deleteRole) {
        this.Permit[15] = deleteRole;
    }

    public Boolean[] getPermit() {
        return Permit;
    }

}
