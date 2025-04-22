package DAL;

import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MiscDAL {
    public static String getChapter(String ID){
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

    public static String getNextId(String table){
        String sql = "SELECT * FROM cauhoi ORDER BY CAST(SUBSTRING(MaCH, 3) AS UNSIGNED) DESC LIMIT 1";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString(1);
                int number = Integer.parseInt(lastId.substring(2)); 
                String nextId = "CH"+(number+1);
                return nextId;
            }
        }catch(SQLException e){
            System.out.println("Kết nối" + table + "thất bại! Không tìm được Id tiếp theo");
            e.printStackTrace();
        }
        return "";
    }
}
