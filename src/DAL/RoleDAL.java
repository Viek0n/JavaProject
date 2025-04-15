package DAL;
import DTO.RoleDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mics.Connect;
public class RoleDAL {
    //Locate
    public static RoleDTO getByID(int ID){
        String sql = "SELECT * FROM nhomquyen WHERE MaNQ = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url,Connect.user,Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Integer.toString(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                RoleDTO Role = new RoleDTO(rs.getInt("MaNQ"),rs.getString("TenNhom"));
                RoleLoad(Role);
                return Role;
            }
        }catch(SQLException e){
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean searchByID(int ID){
        String sql = "SELECT * FROM nhomquyen WHERE MaNQ = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url,Connect.user,Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Integer.toString(ID));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }

    //Load role
    public static void RoleLoad(RoleDTO Role){
        String sql = "SELECT * FROM bangchiaquyen WHERE MaNQ = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url,Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Integer.toString(Role.getID()));
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    String Quyen = rs.getString("MaQuyen");
                    switch(Quyen) {
                        case "CH01":
                            Role.setSeeQuest(true);
                            break;
                        case "CH02":
                            Role.setAddQuest(true);
                            break;
                        case "CH03":
                            Role.setUpdateQuest(true);
                            break;
                        case "CH04":
                            Role.setDeleteQuest(true);
                            break;
                        case "KT01":
                            Role.setSeeExam(true);
                            break;
                        case "KT02":
                            Role.setAddExam(true);
                            break;
                        case "KT03":
                            Role.setUpdateExam(true);
                            break;
                        case "KT04":
                            Role.setDeleteExam(true);
                            break;
                        case "ND01":
                            Role.setSeeUser(true);
                            break;
                        case "ND02":
                            Role.setAddUser(true);
                            break;
                        case "ND03":
                            Role.setUpdateUser(true);
                            break;
                        case "ND04":
                            Role.setDeleteUser(true);
                            break;
                        case "NQ01":
                            Role.setSeeRole(true);
                            break;
                        case "NQ02":
                            Role.setAddRole(true);
                            break;
                        case "NQ03":
                            Role.setUpdateRole(true);
                            break;
                        case "NQ04":
                            Role.setDeleteRole(true);
                            break;
                        case "TG01":
                            Role.setTakeExam(true);
                            break;
                        default:
                            System.out.print("Quyền không xác định ");
                            break;
                    }
                }
            }
        }catch(SQLException e) {
            System.out.println("Kết nối bangchiaquyen thất bại!");
            e.printStackTrace();
        }
    }

    //Add role
    public static Boolean addRoleGroup(RoleDTO a){
        String sql = "INSERT INTO nhomquyen (MaNQ, TenNhom) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getID());
            stmt.setString(2, a.getName());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Update role
    public static Boolean updateRole(RoleDTO a){
        String sql = "UPDATE nhomquyen SET TenNhom = ? WHERE MaNQ = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getName());
            stmt.setInt(2, a.getID());
            stmt.executeUpdate();
            clearRolePermit(a.getID());
            uploadRolePermit(a);
        } catch (SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Clear role permit
    public static Boolean clearRolePermit(int ID){
        String sql = "DELETE FROM bangchiaquyen WHERE MaNQ = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Upload Role permit
    public static Boolean uploadRolePermit(RoleDTO a){
        String sql = "INSERT INTO bangchiaquyen (MaNQ, MaQuyen) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            if(a.getTakeExam()){
                stmt.setInt(1, a.getID());
                stmt.setString(2, "TG01");
                stmt.executeUpdate();
            }
            Boolean tmp[] = a.getPermit();
            for(int i = 0; i < 16; i++){
                switch(i) {
                    case 0:
                        if(a.getSeeQuest()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "CH01");
                            stmt.executeUpdate();
                        }
                        break;
                    case 1:
                        if(a.getAddQuest()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "CH02");
                            stmt.executeUpdate();
                        }
                        break;
                    case 2:
                        if(a.getUpdateQuest()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "CH03");
                            stmt.executeUpdate();
                        }
                        break;
                    case 3:
                        if(a.getDeleteQuest()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "CH04");
                            stmt.executeUpdate();
                        }
                        break;
                    case 4:
                        if(a.getSeeUser()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "ND01");
                            stmt.executeUpdate();
                        }
                        break;
                    case 5:
                        if(a.getAddUser()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "ND02");
                            stmt.executeUpdate();
                        }
                        break;
                    case 6:
                        if(a.getUpdateUser()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "ND03");
                            stmt.executeUpdate();
                        }
                        break;
                    case 7:
                        if(a.getDeleteUser()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "ND04");
                            stmt.executeUpdate();
                        }
                        break;
                    case 8:
                        if(a.getSeeExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT01");
                            stmt.executeUpdate();
                        }
                        break;
                    case 9:
                        if(a.getAddExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT02");
                            stmt.executeUpdate();
                        }
                        break;
                    case 10:
                        if(a.getUpdateExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT03");
                            stmt.executeUpdate();
                        }
                        break;
                    case 11:
                        if(a.getDeleteExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT04");
                            stmt.executeUpdate();
                        }
                        break;
                    case 12:
                        if(a.getSeeRole()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "NQ01");
                            stmt.executeUpdate();
                        }
                        break;
                    case 13:
                        if(a.getAddRole()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "NQ02");
                            stmt.executeUpdate();
                        }
                        break;
                    case 14:
                        if(a.getUpdateRole()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "NQ03");
                            stmt.executeUpdate();
                        }
                        break;
                    case 15:
                        if(a.getDeleteRole()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "NQ04");
                            stmt.executeUpdate();
                        }
                        break;
                    default:
                        System.out.print("Quyền không xác định ");
                        break;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Delete role
    public static Boolean deleteRoleByID(int ID){
        String sql = "DELETE FROM nhomquyen WHERE MaNQ = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);
            clearRolePermit(ID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }
}
