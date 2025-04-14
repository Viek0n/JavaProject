package DAL;
import DTO.RoleDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mics.Connect;
public class RoleDAL {
    public static RoleDTO getByID(int ID){
        String sql = "SELECT * FROM nhomquyen WHERE MaNhom = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url,Connect.user,Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, Integer.toString(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                RoleDTO Role = new RoleDTO(rs.getInt("MaNhom"),rs.getString("TenNhom"));
                RoleLoad(Role);
                return Role;
            }
        }catch(SQLException e){
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public static void RoleLoad(RoleDTO Role){
        String sql = "SELECT * FROM bangchiaquyen WHERE MaNhom = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url,Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
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
        }catch(SQLException e){
            System.out.println("Kết nối bangchiaquyen thất bại!");
            e.printStackTrace();
        }
    }
}
