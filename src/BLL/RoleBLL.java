package BLL;

import DAL.RoleDAL;
import DTO.RoleDTO;
import java.util.List;

public class RoleBLL {
    final private RoleDAL roleDAL;
    public RoleBLL(){
        roleDAL = new RoleDAL();
    }

    public List<RoleDTO> getAll(){
        return roleDAL.getAll();
    }

    public RoleDTO getByName(String roleName){
        return roleDAL.getByName(roleName);
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
