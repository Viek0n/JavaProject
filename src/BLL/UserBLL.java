package BLL;
import DAL.UserDAL;
import DTO.UserDTO;
import MICS.Enums;
public class UserBLL {
    private UserDTO cur;
    private UserDAL userDAL;
    //Cons
    public UserBLL(){
        cur = null;
        userDAL = new UserDAL();
    }

    //Get
    public UserDTO getCurrent(){
        return cur;
    }
    //SelfManage
    public Enums.UserError login(String LoginName, String Pass){
        UserDTO tmp = userDAL.getByLoginName(LoginName);
        if(tmp != null){
            if(!Pass.equals(tmp.getPass()))
                return Enums.UserError.WRONGPASS;
            if(tmp.getStatus() == Enums.StatusValue.KHOA)
                return Enums.UserError.LOCKED;
            cur = tmp;
            return Enums.UserError.NORMAL;
        }
        return Enums.UserError.NOUSER;
    }

    public void logout(){
        cur = null;
    }

    //Insert
    public Boolean add(UserDTO user){
        if(!userDAL.searchByLoginName(user.getLoginName()))
            return userDAL.add(user);
        return false;
    }

    //Update
    public Boolean update(UserDTO user){
        return userDAL.update(user);
    }

    //Delete
    public Boolean delete(String LoginName){
        return userDAL.deleteByLoginName(LoginName);
    }
}
