package DAL;

import DTO.ExamStructSelectedDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExamStructSelectedDAL {
        public Boolean add(ExamStructSelectedDTO a){
        String sql = "INSERT INTO cauhoituychon (MaCT, MaCH) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getExamStructID());
            stmt.setString(2, a.getQuestID());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối cauhoituychon thất bại! Thêm đáp án thất bại");
            e.printStackTrace();
        }
        return false;
    }

    public  ArrayList<ExamStructSelectedDTO> getAll(String examStructID){
        ArrayList<ExamStructSelectedDTO> array = null;
        String sql = "SELECT * FROM cauhoituychon WHERE MaCT = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, examStructID);
            ResultSet rs = stmt.executeQuery();
            array = new ArrayList<>();
            while(rs.next()){
                array.add(new ExamStructSelectedDTO(rs.getString("MaCT"), rs.getString("MaCH")));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối cauhoituychon thất bại!");
            e.printStackTrace();
        }
        return array;
    }

    public Boolean deleteAll(String examStructID){
        String sql = "DELETE FROM cauhoituychon WHERE MaCT = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, examStructID);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối cauhoituychon thất bại! Thêm đáp án thất bại");
            e.printStackTrace();
        }
        return false;
    }
}
