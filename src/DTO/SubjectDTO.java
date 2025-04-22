package DTO;

public class SubjectDTO {
    String ID;
    String Name;

    public SubjectDTO(String ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }

    public SubjectDTO() {
    }

    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    
}
