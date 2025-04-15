package BLL;
import DAL.UserDAL;
import DTO.UserDTO;
import mics.Enums;
public class UserBLL {
    private UserDTO cur;
    //Cons
    UserBLL(){
        cur = null;
    }

    //Get
    public UserDTO getCurUser(){
        return cur;
    }
    //SelfManage
    public Enums.UserError loginUser(String LoginName, String Pass){
        UserDTO tmp = UserDAL.getByLoginName(LoginName);
        if(cur != null){
            if(!Pass.equals(cur.getPass()))
                return Enums.UserError.WRONGPASS;
            if(cur.getStatus() == Enums.StatusValue.KHOA)
                return Enums.UserError.LOCKED;
            cur = tmp;
            return Enums.UserError.NORMAL;
        }
        return Enums.UserError.NOUSER;
    }

    public void logoutUser(){
        cur = null;
    }

    //Insert
    public Boolean addUser(UserDTO user){
        if(!UserDAL.searchByLoginName(user.getLoginName()))
            return UserDAL.addUser(user);
        return false;
    }

    //Update
    public Boolean updateUser(UserDTO user){
        return UserDAL.updateUser(user);
    }

    //Delete
    public Boolean deleteUser(String LoginName){
        return UserDAL.deleteUserByLoginName(LoginName);
    }
}
