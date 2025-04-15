package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DTO.AnswerDTO;
import mics.Connect;

public class AnswerDAL {
    //Add
    public static Boolean addAnswer(AnswerDTO a, int QID){
        String sql = "INSERT INTO dapan (MaCH, A, B, C, D, DapAnDung) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, QID);
            stmt.setString(2, a.getA());
            stmt.setString(3, a.getB());
            stmt.setString(4, a.getC());
            stmt.setString(5, a.getD());
            stmt.setInt(6, a.getRightAns());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối dapan thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Delete
    public static Boolean deleteAnsByQID(int ID){
        String sql = "DELETE FROM dapan WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối dapan thất bại!");
            e.printStackTrace();
        }
        return false;
    }
}
