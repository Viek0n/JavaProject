package DAL;

import DTO.ExamStructDetailDTO;
import MICS.Connect;
import MICS.Enums;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExamStructDetailDAL {
    public Boolean add(ExamStructDetailDTO a){
        String sql = "INSERT INTO chitietde (MaCT, MaChuong, DoKho, SoLuong) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getExamStructID());
            stmt.setString(2, a.getChapID());
            stmt.setString(3, a.getDiff().toString());
            stmt.setInt(4, a.getQuantity());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối chitietde thất bại! Thêm đáp án thất bại");
            e.printStackTrace();
        }
        return false;
    }

    public  ArrayList<ExamStructDetailDTO> getAll(String examStructID){
        ArrayList<ExamStructDetailDTO> array = null;
        String sql = "SELECT * FROM chitietde WHERE MaCT = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, examStructID);
            ResultSet rs = stmt.executeQuery();
            array = new ArrayList<>();
            while(rs.next()){
                array.add(new ExamStructDetailDTO(rs.getString("MaChuong"), Enums.DifficultValue.valueOf(rs.getString("DoKho")), rs.getString("MaCT"),rs.getInt("SoLuong")));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối dapan thất bại!");
            e.printStackTrace();
        }
        return array;
    }

    public Boolean deleteAll(String examStructID){
        String sql = "DELETE FROM chitietde WHERE MaCT = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, examStructID);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối chitietde thất bại! Thêm đáp án thất bại");
            e.printStackTrace();
        }
        return false;
    }
}
