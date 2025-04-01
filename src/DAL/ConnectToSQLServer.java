package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectToSQLServer {
    private static final Logger logger = Logger.getLogger(ConnectToSQLServer.class.getName());

    public static void main(String[] args) {
        var server = "LAPTOP-28582O6R\\SQLEXPRESS";
        var user = "sa";
        var password = "123456";
        var db = "ExamSystem";
        var port = 1433;
        
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);
        ds.setTrustServerCertificate(true);
      
        
        try (Connection connection = ds.getConnection()) {
            System.out.println("Ket noi voi SQLServer thanh cong!");
            System.out.println("Catalog: " + connection.getCatalog());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Connection failed. Error: " + ex.getMessage(), ex);
        }
    }
}
