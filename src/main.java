import DAL.UserDAL;
import DTO.UserDTO;
public class main {
    public static void main(String[] args) {
        UserDTO x = UserDAL.getByUsername("Sys");
        System.out.print(x);
    }
}
