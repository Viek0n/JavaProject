import java.sql.*;
public class main {
    public static void main(String[] args) {
        // Thông tin kết nối
        String url = "jdbc:mysql://localhost:3306/qlthitracnghiem";
        String user = "root";
        String pass = ""; // Mặc định XAMPP không có mật khẩu

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Kết nối thành công!");

            String sql = "SELECT MaMH, TenMH FROM monhoc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("📘 Danh sách môn học:");
            while (rs.next()) {
                String maMH = rs.getString("MaMH");
                String tenMH = rs.getString("TenMH");
                System.out.println("- " + maMH + ": " + tenMH);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }
    }
}
