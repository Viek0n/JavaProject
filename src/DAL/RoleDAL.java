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
                return new RoleDTO(rs.getInt("MaNhom"),
                                rs.getString("TenNhom"));
            }
        }catch(SQLException e){
            System.out.println("Kết nối nhomquyen thất bại!");
            e.printStackTrace();
        }
        return null;
    }
}
