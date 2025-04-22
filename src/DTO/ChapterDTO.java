package DTO;

public class ChapterDTO {
    String ID;
    String Name;

    @Override
    public String toString(){
        return ID + " - " + Name;
    }
    
    public ChapterDTO(String ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }

    public ChapterDTO() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
