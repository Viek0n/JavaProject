package DTO;
public class User {
    private String ID;
    private String LoginName;
    private String Name;
    private String Pass;
    private int Status;
    private Role x;

    //Cons
    public User(String ID, String LoginName, String Name, String Pass, int Status, Role x) {
        this.ID = ID;
        this.LoginName = LoginName;
        this.Name = Name;
        this.Pass = Pass;
        this.Status = Status;
        this.x = x;
    }

    //Getter&stetter
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Role getX() {
        return x;
    }

    public void setX(Role x) {
        this.x = x;
    }

}
