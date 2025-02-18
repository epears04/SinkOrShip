package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection CONNECT;

    public static Connection createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONNECT = DriverManager.getConnection(
                    DatabaseAccess.databaseURL, DatabaseAccess.user, DatabaseAccess.password);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("Database driver not found", e);
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            throw e;
        }
        return CONNECT;
    }
}
