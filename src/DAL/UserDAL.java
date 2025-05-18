package DAL;

import DTO.UserDTO;
import MICS.Connect;
import MICS.Enums;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAL {
    private RoleDAL roleDal;

    public UserDAL() {
        roleDal = new RoleDAL();
    }

    public ArrayList<UserDTO> getAll() {
    ArrayList<UserDTO> array = new ArrayList<>();
    String sql = "SELECT * FROM nguoidung";
    try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            array.add(new UserDTO(rs.getString("MaND"),
                    rs.getString("Ten"), rs.getString("MatKhau"),
                    Enums.StatusValue.valueOf(rs.getString("TrangThai")),
                    roleDal.getByID(rs.getInt("MaNQ"))));
        }
    } catch (SQLException e) {
        System.out.println("Kết nối nguoidung thất bại!");
        e.printStackTrace();
    }
    return array;
}

    //Locate User
    public UserDTO getByLoginName(String LoginName) {
        String sql = "SELECT * FROM nguoidung WHERE MaND = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDTO(rs.getString("MaND"),
                        rs.getString("Ten"),
                        rs.getString("MatKhau"),
                        Enums.StatusValue.valueOf(rs.getString("TrangThai")),
                        roleDal.getByID(rs.getInt("MaNQ")));
            }
        } catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    public Boolean searchByLoginName(String LoginName) {
        String sql = "SELECT * FROM nguoidung WHERE MaND = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, LoginName);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Kết nối nguoidung thất bại!");
            e.printStackTrace();
        }
        return null;
    }

    //Add user
    public Boolean add(UserDTO a) {
    String sql = "INSERT INTO nguoidung (MaND, Ten, MatKhau, TrangThai, MaNQ) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, a.getLoginName());
        stmt.setString(2, a.getName());
        stmt.setString(3, a.getPass());
        stmt.setString(4, a.getStatus().name());
        stmt.setInt(5, a.getRole().getID());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Thêm người dùng thất bại!");
        e.printStackTrace();
    }
    return false;
}

    //Delete user
    public Boolean deleteByLoginName(String LoginName) {
    String sql = "DELETE FROM nguoidung WHERE MaND = ?";
    try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, LoginName);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Xóa người dùng thất bại!");
        e.printStackTrace();
    }
    return false;
}

    //update user
    public Boolean update(UserDTO a) {
    String sql = "UPDATE nguoidung SET Ten = ?, MatKhau = ?, TrangThai = ?, MaNQ = ? WHERE MaND = ?";
    try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, a.getName());
        stmt.setString(2, a.getPass());
        stmt.setString(3, a.getStatus().name());
        stmt.setInt(4, a.getRole().getID());
        stmt.setString(5, a.getLoginName());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Cập nhật người dùng thất bại!");
        e.printStackTrace();
    }
    return false;
}
    //searchUser
    public ArrayList<UserDTO> searchUser(String keyword) {
        ArrayList<UserDTO> results = new ArrayList<>();
        String sql = "SELECT * FROM nguoidung WHERE Ten LIKE ? OR MaND LIKE ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new UserDTO(rs.getString("MaND"),
                        rs.getString("Ten"),
                        rs.getString("MatKhau"),
                        Enums.StatusValue.valueOf(rs.getString("TrangThai")),
                        roleDal.getByID(rs.getInt("MaNQ"))));
            }
        } catch (SQLException e) {
            System.out.println("Tìm kiếm người dùng thất bại!");
            e.printStackTrace();
        }
        return results;
    }
    
   public UserDTO getByID(String LoginName) {
    String sql = "SELECT * FROM nguoidung WHERE MaND = ?";
    try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, LoginName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new UserDTO(rs.getString("MaND"),
                    rs.getString("Ten"),
                    rs.getString("MatKhau"),
                    Enums.StatusValue.valueOf(rs.getString("TrangThai")),
                    roleDal.getByID(rs.getInt("MaNQ")));
        }
    } catch (SQLException e) {
        System.out.println("Kết nối nguoidung thất bại!");
        e.printStackTrace();
    }
    return null;
}
    
}
