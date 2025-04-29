package DAL;

import DTO.ExamStructDTO;
import DTO.SubjectDTO;
import MICS.Connect;
import java.sql.*;
import java.util.ArrayList;

public class ExamStructDAL {
    private SubjectDAL subjectDAL;
    
    
    public ExamStructDAL() {
        subjectDAL = new SubjectDAL();
    }

    private  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
    }

    public ArrayList<ExamStructDTO> getAll() {
        ArrayList<ExamStructDTO> list = new ArrayList<>();
        String query = "SELECT * FROM cautrucde";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ExamStructDTO examStruct = new ExamStructDTO();
                examStruct.setID(rs.getString("MaCT"));
                examStruct.setName(rs.getString("TenCT"));
                examStruct.setDesc(rs.getString("MoTa"));
                examStruct.setStart(rs.getDate("ThoiGianBD"));
                examStruct.setEnd(rs.getDate("ThoiGianKT"));
                examStruct.setExamTime(rs.getTime("ThoiGianLamBai"));
                SubjectDTO subject = new SubjectDTO();
                subject.setID(rs.getString("MonHoc"));
                examStruct.setSubject(subject);
                list.add(examStruct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public  String getNextId(){
        String sql = "SELECT * FROM cautrucde ORDER BY CAST(SUBSTRING(MaCT, 3) AS UNSIGNED) DESC LIMIT 1";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString(1);
                int number = Integer.parseInt(lastId.substring(2)); 
                String nextId = "CT"+(number+1);
                return nextId;
            }
        }catch(SQLException e){
            System.out.println("Kết nối cautrucde thất bại! Không tìm được Id tiếp theo");
            e.printStackTrace();
        }
        return "";
    }

    public  boolean add(ExamStructDTO examStruct) {
        String query = "INSERT INTO cautrucde (MaCT, TenCT, MoTa, ThoiGianBD, ThoiGianKT, ThoiGianLamBai, MonHoc) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            examStruct.setID(getNextId());
            pstmt.setString(1, examStruct.getID());
            pstmt.setString(2, examStruct.getName());
            pstmt.setString(3, examStruct.getDesc());
            pstmt.setDate(4, new java.sql.Date(examStruct.getStart().getTime()));
            pstmt.setDate(5, new java.sql.Date(examStruct.getEnd().getTime()));
            pstmt.setTime(6, examStruct.getExamTime());
            pstmt.setString(7, examStruct.getSubject().getID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  boolean update(ExamStructDTO examStruct) {
        String query = "UPDATE ExamStruct SET Name = ?, Desc = ?, Start = ?, End = ?, ExamTime = ?, SubjectID = ? WHERE ID = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, examStruct.getName());
            pstmt.setString(2, examStruct.getDesc());
            pstmt.setDate(3, new java.sql.Date(examStruct.getStart().getTime()));
            pstmt.setDate(4, new java.sql.Date(examStruct.getEnd().getTime()));
            pstmt.setTime(5, examStruct.getExamTime());
            pstmt.setString(6, examStruct.getSubject().getID());
            pstmt.setString(7, examStruct.getID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  boolean deleteExamStruct(String id) {
        String query = "DELETE FROM cautrucde WHERE MaCT = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  ExamStructDTO getExamStructById(String id) {
        String query = "SELECT * FROM cautrucde WHERE MaCT = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ExamStructDTO examStruct = new ExamStructDTO();
                    examStruct.setID(rs.getString("MaCT"));
                    examStruct.setName(rs.getString("TenCT"));
                    examStruct.setDesc(rs.getString("MoTa"));
                    examStruct.setStart(rs.getDate("ThoiGianBD"));
                    examStruct.setEnd(rs.getDate("ThoiGianKT"));
                    examStruct.setExamTime(rs.getTime("ThoiGianLamBai"));
                    SubjectDTO subject = subjectDAL.get(rs.getString("MonHoc"));
                    examStruct.setSubject(subject);
                    return examStruct;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}