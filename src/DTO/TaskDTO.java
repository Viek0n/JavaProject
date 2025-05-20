package DTO;

public class TaskDTO {
    private UserDTO user;
    private SubjectDTO subject;

    public TaskDTO() {
    }

    public TaskDTO(UserDTO user, SubjectDTO subject) {
        this.subject = subject;
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

}
