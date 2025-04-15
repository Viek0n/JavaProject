package BLL;

import DAL.RoleDAL;
import DTO.RoleDTO;
import DTO.UserDTO;

public class RoleBLL {
    UserDTO user;
    public RoleBLL(UserDTO x){
        user = x;
    }
    //Insert
    public static Boolean addRole(RoleDTO a){
        if(!RoleDAL.searchByID(a.getID())){
            RoleDAL.addRoleGroup(a);
            RoleDAL.uploadRolePermit(a);
            return true;
        }
        return false;
    }

    //Update
    public static Boolean updateRole(RoleDTO a){
        if(RoleDAL.searchByID(a.getID()))
            return updateRole(a);
        return false;
    }

    //Delete
    public static Boolean deleteRole(int ID){
        return RoleDAL.deleteRoleByID(ID);
    }
}
