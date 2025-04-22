package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.SubjectDTO;
import MICS.Connect;

public class SubjectDAL {
    public static List<SubjectDTO> getAllSubjects() {
        List<SubjectDTO> subjects = new ArrayList<>();
        String sql = "SELECT MaMH, TenMH FROM monhoc";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("MaMH");
                String name = rs.getString("TenMH");
                subjects.add(new SubjectDTO(id, name));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve subjects!");
            e.printStackTrace();
        }
        return subjects;
    }
    public static boolean addSubject(SubjectDTO subject) {
        String sql = "INSERT INTO monhoc (MaMH, TenMH) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subject.getID());
            stmt.setString(2, subject.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to add subject!");
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updateSubject(SubjectDTO subject) {
        String sql = "UPDATE monhoc SET TenMH = ? WHERE MaMH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update subject!");
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deleteSubject(String id) {
        String sql = "DELETE FROM monhoc WHERE MaMH = ?";
        try (Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete subject!");
            e.printStackTrace();
        }
        return false;
    }
}