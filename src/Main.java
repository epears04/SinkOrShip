import java.sql.*;

public class Main {
    static   Connection connect;
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    DatabaseAccess.databaseURL, DatabaseAccess.user, DatabaseAccess.password); // Replace with database name (username), username, and password

            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM TEST;");
            while (rs.next()) {
                String studentName = rs.getString(1);
                System.out.println("Student name = " +
                        studentName);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}