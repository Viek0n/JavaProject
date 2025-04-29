package BLL;

import DAL.RoleDAL;
import DTO.RoleDTO;

public class RoleBLL {
    private RoleDAL roleDAL;
    public RoleBLL(){
        roleDAL = new RoleDAL();
    }
    //Insert
    public Boolean add(RoleDTO a){
        if(!roleDAL.searchByID(a.getID())){
            roleDAL.addGroup(a);
            roleDAL.uploadRolePermit(a);
            return true;
        }
        return false;
    }

    //Update
    public Boolean update(RoleDTO a){
        if(roleDAL.searchByID(a.getID()))
            return roleDAL.update(a);
        return false;
    }

    //Delete
    public Boolean delete(int ID){
        return roleDAL.deleteRoleByID(ID);
    }
}
