package BLL;

import DAL.RoleDAL;
import DTO.RoleDTO;

public class RoleBLL {
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
            return RoleDAL.updateRole(a);
        return false;
    }

    //Delete
    public static Boolean deleteRole(int ID){
        return RoleDAL.deleteRoleByID(ID);
    }
}
