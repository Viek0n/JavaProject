package DAL;

import DTO.AnswerDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AnswerDAL {
    public static ArrayList<AnswerDTO> getAllByQId(String ID){
        ArrayList<AnswerDTO> array = null;
        String sql = "SELECT * FROM dapan WHERE MaCH = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            array = new ArrayList<>();
            while(rs.next()){
                array.add(new AnswerDTO(rs.getString("NoiDung"), rs.getBoolean("Dung")));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối dapan thất bại!");
            e.printStackTrace();
        }
        return array;
    }
    //Search
    public static Boolean searchByQID(String id){
        String sql = "SELECT * FROM dapan WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);

                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch(SQLException e){
                System.out.println("Kết nối dapan thất bại! Tìm đáp án thất bại");
                e.printStackTrace();
            }
        return false;
    }
    //Update 
    //Add
    public static Boolean add(AnswerDTO a, String ID){
        String sql = "INSERT INTO dapan (MaCH, NoiDung, Dung) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ID);
            stmt.setString(2, a.getText());
            stmt.setInt(3, a.getRight() ? 1 : 0);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối dapan thất bại! Thêm đáp án thất bại");
            e.printStackTrace();
        }
        return false;
    }
    //Delete
    public static Boolean deleteByQID(String Id){
        String sql = "DELETE FROM dapan WHERE MaCH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kết nối dapan thất bại! Xóa đáp án thất bại");
            e.printStackTrace();
        }
        return false;
    }
}
