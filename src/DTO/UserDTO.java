package DTO;

import MICS.Enums;
public class UserDTO {
    private String LoginName;
    private String Name;
    private String Pass;
    private Enums.StatusValue Status;
    private RoleDTO Role;

    @Override
    public String toString(){
        return LoginName+" - "+Name+ Role;
    }
    //Cons
    public UserDTO(String LoginName, String Name, String Pass, Enums.StatusValue Status, RoleDTO Role) {
        this.LoginName = LoginName;
        this.Name = Name;
        this.Pass = Pass;
        this.Status = Status;
        this.Role = Role;
    }

    public UserDTO() {
    }

    //Getter&stetter
    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public Enums.StatusValue getStatus() {
        return Status;
    }

    public void setStatus(Enums.StatusValue Status) {
        this.Status = Status;
    }

    public RoleDTO getRole() {
        return Role;
    }

    public void setRole(RoleDTO Role) {
        this.Role = Role;
    }

}
