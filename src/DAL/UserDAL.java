package DAL;
import DTO.UserDTO;
import MICS.Connect;
import MICS.Enums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {
    //Locate User
    public static UserDTO getByLoginName(String LoginName){
        String sql = "SELECT * FROM nguoidung WHERE MaND = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginName);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new UserDTO(rs.getString("MaND"),
                                rs.getString("Ten"),
                                rs.getString("MatKhau"),
                                Enums.StatusValue.valueOf(rs.getString("TrangThai")),
                                RoleDAL.getByID(rs.getInt("MaNQ")));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean searchByLoginName(String LoginName){
        String sql = "SELECT * FROM nguoidung WHERE MaND = ?";
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

    //Add user
    public static Boolean addUser(UserDTO a){
        String sql = "INSERT INTO nguoidung (MaND, Ten, MatKhau, TrangThai, NhomQuyen) VALUES (?, ?, ?, ?, ?)";
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
    
    //Delete user
    public static Boolean deleteUserByLoginName(String LoginName){
        String sql = "DELETE FROM nguoidung WHERE MaND = ?";
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

    //update user
    public static Boolean updateUser(UserDTO user){
        String sql = "UPDATE nguoidung SET Ten = ?, MatKhau = ?, TrangThai = ?, MaNQ = ? WHERE MaND = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPass());
            stmt.setString(3, user.getStatus().name());
            stmt.setInt(4, user.getRole().getID());
            stmt.setString(5, user.getLoginName());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return false;
    }
}
