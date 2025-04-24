package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import MICS.Connect;

public class ChapterDAL {
        public static String get(String ID){
        String sql = "SELECT * FROM chuong WHERE MaChuong = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,ID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString("TenChuong");
        }catch(SQLException e){
            System.out.println("Kết nối chuong thất bại!");
            e.printStackTrace();
        }
        return "";
    }
}
