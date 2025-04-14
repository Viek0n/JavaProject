package DAL;
import DTO.UserDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mics.Connect;
import mics.Enums;

public class UserDAL {
    public static UserDTO getByLoginName(String LoginName){
        String sql = "SELECT * FROM nguoidung WHERE TenDangNhap = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginName);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new UserDTO(rs.getString("TenDangNhap"),
                                rs.getString("Ten"),
                                rs.getString("MatKhau"),
                                Enums.StatusValue.valueOf(rs.getString("TrangThai")),
                                RoleDAL.getByID(rs.getInt("NhomQuyen")));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean searchByLoginName(String LoginName){
        String sql = "SELECT * FROM nguoidung WHERE TenDangNhap = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginName);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean pushUser(UserDTO a){
        String sql = "INSERT INTO nguoidung (TenDangNhap, Ten, MatKhau, TrangThai, NhomQuyen) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getLoginName());
            stmt.setString(2, a.getPass());
            stmt.setString(3, a.getPass());
            stmt.setString(4, a.getStatus().name());
            stmt.setInt(5, a.getRole().getID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    
    public static Boolean deleteUserByLoginName(String LoginName){
        String sql = "DELETE FROM nguoidung WHERE TenDangNhap = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginName);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean updateUser(UserDTO user){
        String sql = "UPDATE nguoidung SET Ten = ?, MatKhau = ?, TrangThai = ?, NhomQuyen = ? WHERE TenDangNhap = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPass());
            stmt.setString(3, user.getPass());
            stmt.setString(4, user.getStatus().name());
            stmt.setString(5, user.getLoginName());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return false;
    }
}
