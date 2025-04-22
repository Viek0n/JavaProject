package BLL;

import DAL.RoleDAL;
import DTO.RoleDTO;

public class RoleBLL {
    //Insert
    public static Boolean add(RoleDTO a){
        if(!RoleDAL.searchByID(a.getID())){
            RoleDAL.addGroup(a);
            RoleDAL.uploadRolePermit(a);
            return true;
        }
        return false;
    }

    //Update
    public static Boolean update(RoleDTO a){
        if(RoleDAL.searchByID(a.getID()))
            return RoleDAL.update(a);
        return false;
    }

    //Delete
    public static Boolean delete(int ID){
        return RoleDAL.deleteRoleByID(ID);
    }
}
