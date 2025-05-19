package DAL;
import DTO.RoleDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAL {
    public ArrayList<RoleDTO> getAll(){
        ArrayList<RoleDTO> array = new ArrayList<>();
        String sql = "SELECT * FROM nhomquyen";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                array.add(new RoleDTO(rs.getInt("MaNQ"), rs.getString("TenNhom")));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return array;
    }
    //Locate
    public  RoleDTO getByID(int ID){
        String sql = "SELECT * FROM nhomquyen WHERE MaNQ = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url,Connect.user,Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Integer.toString(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                RoleDTO Role = new RoleDTO(rs.getInt("MaNQ"),rs.getString("TenNhom"));
                load(Role);
                return Role;
            }
        }catch(SQLException e){
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public int getNextId(){
        String sql = "SELECT * FROM nhomquyen ORDER BY MaNQ DESC LIMIT 1";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int number = rs.getInt(1); 
                return number+1;
            }
        }catch(SQLException e){
            System.out.println("Kết nối nhomquyen thất bại! Không tìm được Id tiếp theo");
            e.printStackTrace();
        }
        return 0;
    }

    public  Boolean searchByID(int ID){
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
    public  void load(RoleDTO Role){
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
                        case "TG":
                            Role.setTakeExam(true);
                            break;
                        case "QT":
                            Role.setAdmin(true);
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
    public  Boolean addGroup(RoleDTO a){
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
    public  Boolean update(RoleDTO a){
        String sql = "UPDATE nhomquyen SET TenNhom = ? WHERE MaNQ = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getName());
            stmt.setInt(2, a.getID());
            
            clearRolePermit(a.getID());
            uploadRolePermit(a);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Clear role permit
    public  Boolean clearRolePermit(int ID){
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
    public  Boolean uploadRolePermit(RoleDTO a){
        String sql = "INSERT INTO bangchiaquyen (MaNQ, MaQuyen) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            if(a.getTakeExam()){
                stmt.setInt(1, a.getID());
                stmt.setString(2, "TG");
                stmt.executeUpdate();
            }
            if(a.getAdmin()){
                stmt.setInt(1, a.getID());
                stmt.setString(2, "QT");
                stmt.executeUpdate();
            }
            Boolean tmp[] = a.getPermit();
            for(int i = 0; i < 8; i++){
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
                        if(a.getSeeExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT01");
                            stmt.executeUpdate();
                        }
                        break;
                    case 5:
                        if(a.getAddExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT02");
                            stmt.executeUpdate();
                        }
                        break;
                    case 6:
                        if(a.getUpdateExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT03");
                            stmt.executeUpdate();
                        }
                        break;
                    case 7:
                        if(a.getDeleteExam()){
                            stmt.setInt(1, a.getID());
                            stmt.setString(2, "KT04");
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
    public  Boolean deleteRoleByID(int ID){
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
    public RoleDTO getByName(String roleName) {
        List<RoleDTO> roles = getAll(); // Assuming getAll() fetches all roles
        for (RoleDTO role : roles) {
            if (role.getName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        return null; // Return null if no matching role is found
    }
}
