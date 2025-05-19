package DAL;

import DTO.ExamDTO;
import DTO.QuestionDTO;
import MICS.Connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamDAL {
    
    private  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
    }

    public String getNextId(){
        String sql = "SELECT * FROM baikiemtra ORDER BY CAST(SUBSTRING(MaBaiKT, 3) AS UNSIGNED) DESC LIMIT 1";
        try(Connection conn = DriverManager.getConnection(Connect.url, Connect.user, Connect.pass);
        PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString(1);
                int number = Integer.parseInt(lastId.substring(2)); 
                String nextId = "KT"+(number+1);
                return nextId;
            }else{
                return "KT1";
            }
        }catch(SQLException e){
            System.out.println("Kết nối baikiemtra thất bại! Không tìm được Id tiếp theo");
            e.printStackTrace();
        }
        return "";
    }

    public  boolean add(ExamDTO exam) {
        String query = "INSERT INTO baikiemtra (MaBaiKT, MaCT, MaND, Diem, ThoiGianLam) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            
            pstmt.setString(1, exam.getExamId());
            pstmt.setString(2, exam.getExamStructID());
            pstmt.setString(3, exam.getUserID());
            pstmt.setDouble(4,  exam.getScore());
            pstmt.setTime(5, new java.sql.Time(exam.getRemainingTime().getTime()));

            int result = pstmt.executeUpdate();
            addDetail(exam);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addDetail(ExamDTO exam){
        String query = "INSERT INTO chitietbai (MaCH, MaBaiKT, LuaChon) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            
            ArrayList<QuestionDTO> question = exam.getQuestion();
            ArrayList<Integer> choice = exam.getChoice();
            int result = 0;
            for(int i = 0; i < question.size(); i++){
                pstmt.setString(1, question.get(i).getID());
                pstmt.setString(2, exam.getExamId());
                pstmt.setInt(3, choice.get(i));

                result+=pstmt.executeUpdate();
            }

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ExamDTO> getAll(String id) {
        ArrayList<ExamDTO> list = new ArrayList<>();
        String query = "SELECT * FROM baikiemtra WHERE MaND = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExamDTO exam = new ExamDTO();
                exam.setExamId(rs.getString("MaBaiKT"));
                exam.setExamStructID(rs.getString("MaCT"));
                exam.setUserID(rs.getString("MaND"));
                exam.setRemainingTime(rs.getTime("ThoiGianLam"));
                exam.setScore(rs.getFloat("Diem"));
                getDetail(exam, exam.getExamId());
                list.add(exam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExamDTO getDetail(ExamDTO exam, String id) {
        String query = "SELECT * FROM chitietbai WHERE MaBaiKT = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            
            pstmt.setString(1, id);
            ArrayList<QuestionDTO> question = new ArrayList<>();
            ArrayList<Integer> choice = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                question.add(new QuestionDAL().getByID(rs.getString("MaCH")));
                choice.add(rs.getInt("LuaChon"));
            }
            exam.setQuestion(question);
            exam.setChoice(choice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ExamDTO get(String id) {
        String query = "SELECT * FROM baikiemtra WHERE MaBaiKT = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ExamDTO exam = new ExamDTO();
                    exam.setExamId(rs.getString("MaBaiKT"));
                    exam.setExamStructID(rs.getString("MaCT"));
                    exam.setUserID(rs.getString("MaND"));
                    exam.setRemainingTime(rs.getTime("ThoiGianLam"));
                    exam.setScore(rs.getFloat("Diem"));
                    getDetail(exam, exam.getExamId());
                    return exam;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
