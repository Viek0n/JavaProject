package DAL;

import DTO.AnswerDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnswerDAL {
    //Search
    public static Boolean searchAnsByQID(int i){
        String sql = "SELECT * FROM dapan WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, i);

                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch(SQLException e){
                System.out.println("Kết nối dapan thất bại!");
                e.printStackTrace();
            }
        return false;
    }
    //Update
    public static Boolean updateAns(AnswerDTO a, int ID){
        String sql = "UPDATE dapan SET A = ?, B = ?, C = ?, D = ?, DapAnDung = ? WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getA());
            stmt.setString(2, a.getB());
            stmt.setString(3, a.getC());
            stmt.setString(4, a.getD());
            stmt.setInt(5, a.getRightAns());
            stmt.setInt(6, ID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối cauhoi thất bại!");
            e.printStackTrace();
        }
        return false;
    }
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
