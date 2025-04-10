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
    public static UserDTO getByUsername(String Username){
        String sql = "SELECT * FROM nguoidung WHERE TenDangNhap = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, Username);
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

    public static Boolean checkUser(UserDTO a){
        Boolean result = false;
        
        return result;
    }
}
