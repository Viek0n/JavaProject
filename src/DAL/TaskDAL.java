package DAL;

import BLL.SubjectBLL;
import BLL.UserBLL;
import DTO.SubjectDTO;
import DTO.TaskDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskDAL {
    public ArrayList<TaskDTO> get(String userId){
        ArrayList<TaskDTO> task = new ArrayList<>();
        String sql = "SELECT * FROM bangphancong WHERE MaND = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                task.add(new TaskDTO(new UserBLL().getByLoginName(rs.getString(1)), new SubjectBLL().get(rs.getString(2))));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối bangphancong thất bại!");
            e.printStackTrace();
        }
        return task;
    }

        public ArrayList<TaskDTO> getAll(){
        ArrayList<TaskDTO> task = new ArrayList<>();
        String sql = "SELECT * FROM bangphancong";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                task.add(new TaskDTO(new UserBLL().getByLoginName(rs.getString(1)), new SubjectBLL().get(rs.getString(2))));
            }
        }catch (SQLException e) {
            System.out.println("Kết nối bangphancong thất bại!");
            e.printStackTrace();
        }
        return task;
    }

    public  boolean delete(String userId, String subjectId) {
        String sql = "DELETE FROM bangphancong WHERE MaND = ? AND MaMH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.setString(2, subjectId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Không thể kết nối tới bangphancong!");
            e.printStackTrace();
        }
        return false;
    }

    public  boolean add(String userId, String subjectId) {
        String sql = "INSERT INTO bangphancong (MaND, MaMH) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.setString(2, subjectId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Không thể kết nối tới bangphancong!");
            e.printStackTrace();
        }
        return false;
    }
}
