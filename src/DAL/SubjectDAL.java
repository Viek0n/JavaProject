package DAL;

import DTO.SubjectDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAL {
    public ArrayList<SubjectDTO> getAll() {
        ArrayList<SubjectDTO> subjects = new ArrayList<>();
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
        return subjects; // Ensure the list is returned
    }
    public  boolean add(SubjectDTO subject) {
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
    public  boolean update(SubjectDTO subject) {
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
    public  boolean delete(String id) {
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
    public  SubjectDTO getByChapID(String ID){
        String sql = "SELECT chuong.MaChuong, chuong.TenChuong, monhoc.TenMH, monhoc.MaMH FROM chuong LEFT JOIN monhoc ON chuong.MonHoc = monhoc.MaMH WHERE MaChuong = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,ID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                
                return new SubjectDTO(rs.getString("MaMH"), rs.getString("TenMH"));
            }
                
        }catch(SQLException e){
            System.out.println("Failed to retrieve subjects!");
            e.printStackTrace();
        }
        return null;
    }

    public  SubjectDTO get(String ID){
        String sql = "SELECT * FROM monhoc WHERE MaMH = ?";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,ID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return new SubjectDTO(rs.getString("MaMH"), rs.getString("TenMH"));
        }catch(SQLException e){
            System.out.println("Failed to retrieve subjects!");
            e.printStackTrace();
        }
        return null;
    }
    
}
