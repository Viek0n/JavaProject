package MICS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static String url = "jdbc:mysql://localhost:3306/qlthitracnghiem";
    public static String user = "root";
    public static String pass = "";
    public static String img = "res/";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user, pass);
    }
}