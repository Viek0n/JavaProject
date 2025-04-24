package DTO;

public class SubjectDTO {
    String ID;
    String Name;

    @Override
    public String toString(){
        return ID + " - " + Name;
    }

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
        this.ID = iD;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    
}
