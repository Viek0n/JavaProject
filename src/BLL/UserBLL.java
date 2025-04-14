package BLL;
import DAL.UserDAL;
import DTO.UserDTO;
import mics.Enums;
public class UserBLL {
    private UserDTO cur;
    //SelfManage
    public Enums.UserError loginUser(String LoginName, String Pass){
        cur = UserDAL.getByLoginName(LoginName);
        if(cur != null){
            if(!Pass.equals(cur.getPass())){
                cur = null;
                return Enums.UserError.WRONGPASS;
            }
            else
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
            return UserDAL.pushUser(user);
        return false;
    }
    //Update
    public Boolean updateUser(UserDTO user){
        return false;
    }
    //Delete
    //Retrieve
}
