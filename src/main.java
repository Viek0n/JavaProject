import DAL.RoleDAL;
import DAL.UserDAL;
import DTO.UserDTO;
import mics.Enums;
public class main {
    public static void main(String[] args) {
        UserDTO x = UserDAL.getByLoginName("Sys");
        UserDAL.updateUser(new UserDTO("Test", "Tester", "abc", Enums.StatusValue.HOATDONG, RoleDAL.getByID(1)));
        System.out.print(x);
    }
}
