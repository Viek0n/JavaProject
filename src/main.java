import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class main {
    public static void main(String[] args) {
        // Thông tin kết nối
        String url = "jdbc:mysql://localhost:3306/qlthitracnghiem";
        String username = "root";
        String password = ""; // Mặc định XAMPP không có mật khẩu

        try {
            // Kết nối đến cơ sở dữ liệu
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công đến MySQL!");
            // Đóng kết nối
            conn.close();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }
    }
}
