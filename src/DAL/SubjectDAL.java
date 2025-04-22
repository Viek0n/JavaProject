package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import MICS.Connect;
public class SubjectDAL {
    public static String getCourseByChapID(String ID){
        String sql = "SELECT chuong.MaChuong, chuong.TenChuong, monhoc.TenMH FROM chuong LEFT JOIN monhoc ON chuong.MonHoc = monhoc.MaMH WHERE MaChuong = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,ID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString("TenMH");
        }catch(SQLException e){
            System.out.println("Kết nối monhoc thất bại!");
            e.printStackTrace();
        }
        return "";
    }
}
