package DAL;

import DTO.ExamStructDTO;
import DTO.SubjectDTO;

import java.sql.*;
import java.util.ArrayList;
import MICS.Connect;

public class ExamStructDAL {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
    }

    public ArrayList<ExamStructDTO> getAllExamStructs() {
        ArrayList<ExamStructDTO> list = new ArrayList<>();
        String query = "SELECT * FROM ExamStruct";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ExamStructDTO examStruct = new ExamStructDTO();
                examStruct.setID(rs.getString("ID"));
                examStruct.setName(rs.getString("Name"));
                examStruct.setDesc(rs.getString("Desc"));
                examStruct.setStart(rs.getDate("Start"));
                examStruct.setEnd(rs.getDate("End"));
                examStruct.setExamTime(rs.getTime("ExamTime"));
                SubjectDTO subject = new SubjectDTO();
                subject.setID(rs.getString("SubjectID"));
                examStruct.setSubject(subject);
                list.add(examStruct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addExamStruct(ExamStructDTO examStruct) {
        String query = "INSERT INTO ExamStruct (Name, Desc, Start, End, ExamTime, SubjectID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, examStruct.getName());
            pstmt.setString(2, examStruct.getDesc());
            pstmt.setDate(3, new java.sql.Date(examStruct.getStart().getTime()));
            pstmt.setDate(4, new java.sql.Date(examStruct.getEnd().getTime()));
            pstmt.setTime(5, examStruct.getExamTime());
            pstmt.setString(6, examStruct.getSubject().getID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateExamStruct(ExamStructDTO examStruct) {
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

    public boolean deleteExamStruct(int id) {
        String query = "DELETE FROM ExamStruct WHERE ID = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ExamStructDTO getExamStructById(int id) {
        String query = "SELECT * FROM ExamStruct WHERE ID = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ExamStructDTO examStruct = new ExamStructDTO();
                    examStruct.setID(rs.getString("ID"));
                    examStruct.setName(rs.getString("Name"));
                    examStruct.setDesc(rs.getString("Desc"));
                    examStruct.setStart(rs.getDate("Start"));
                    examStruct.setEnd(rs.getDate("End"));
                    examStruct.setExamTime(rs.getTime("ExamTime"));
                    SubjectDTO subject = new SubjectDTO();
                    subject.setID(rs.getString("SubjectID"));
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