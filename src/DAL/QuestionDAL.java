package DAL;

import DTO.QuestionDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionDAL {
    //Search
    public static Boolean searchQuestByID(int i){
        String sql = "SELECT * FROM cauhoi WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, i);

                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch(SQLException e){
                System.out.println("Kết nối cauhoi thất bại!");
                e.printStackTrace();
            }
        return false;
    }
    //Update
    public static Boolean updateQuestion(QuestionDTO a){
        String sql = "UPDATE cauhoi SET NoiDung = ?, DoKho = ?, MaChuong = ? WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getText());
            stmt.setString(2, a.getDifficult().name());
            stmt.setString(3, a.getChapterID());
            stmt.setInt(4, a.getID());
            AnswerDAL.updateAns(a.getAns(), a.getID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối cauhoi thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Add
    public static Boolean addQuestion(QuestionDTO a){
        String sql = "INSERT INTO cauhoi (NoiDung, DoKho, MaChuong, MaND) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getText());
            stmt.setString(2, a.getDifficult().name());
            stmt.setString(3, a.getChapterID());
            stmt.setString(4, a.getCreatedBy());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
                AnswerDAL.addAnswer(a.getAns(),rs.getInt(1));
            return true;
        } catch (SQLException e) {
            System.out.println("Kết nối cauhoi thất bại!");
            e.printStackTrace();
        }
        return false;
    }
    //Delete
    public static Boolean deleteQuestByID(int ID){
        String sql = "DELETE FROM cauhoi WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);
            AnswerDAL.deleteAnsByQID(ID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối cauhoi thất bại!");
            e.printStackTrace();
        }
        return false;
    }
}
