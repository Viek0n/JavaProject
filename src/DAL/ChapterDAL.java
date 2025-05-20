package DAL;

import DTO.ChapterDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAL {
    public  ChapterDTO get(String ID) {
        String sql = "SELECT * FROM chuong WHERE MaChuong = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ChapterDTO(rs.getString("MaChuong"), rs.getString("TenChuong"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving chapter with ID: " + ID);
            e.printStackTrace();
        }
        return null;
    }

    public  List<ChapterDTO> getBySubject(String ID) {
        List<ChapterDTO> chapters = new ArrayList<>();
        String sql = "SELECT * FROM chuong WHERE MonHoc = ?";
        try (Connection conn = Connect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ID);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                chapters.add(new ChapterDTO(rs.getString("MaChuong"), rs.getString("TenChuong")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all chapters!");
            e.printStackTrace();
        }
        return chapters;
    }

    public  List<ChapterDTO> getAll() {
        List<ChapterDTO> chapters = new ArrayList<>();
        String sql = "SELECT * FROM chuong";
        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                chapters.add(new ChapterDTO(rs.getString("MaChuong"), rs.getString("TenChuong")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all chapters!");
            e.printStackTrace();
        }
        return chapters;
    }

    public  boolean add(ChapterDTO chapter, String subjectId) {
        String sql = "INSERT INTO chuong (MaChuong, TenChuong, MonHoc) VALUES (?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chapter.getID());
            stmt.setString(2, chapter.getName());
            stmt.setString(3, subjectId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding chapter: " + chapter);
            e.printStackTrace();
        }
        return false;
    }

    public  boolean update(ChapterDTO chapter) {
        String sql = "UPDATE chuong SET TenChuong = ? WHERE MaChuong = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chapter.getName());
            stmt.setString(2, chapter.getID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating chapter: " + chapter);
            e.printStackTrace();
        }
        return false;
    }

    public  boolean delete(String ID) {
        String sql = "DELETE FROM chuong WHERE MaChuong = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting chapter with ID: " + ID);
            e.printStackTrace();
        }
        return false;
    }
}