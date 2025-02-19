import Views.AddShip;
import Views.CreatePerson;

import javax.swing.*;

public class Main {
//    public static final AddShip ADD_SHIP = new AddShip();
    public static final CreatePerson CREATE_PERSON = new CreatePerson();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CREATE_PERSON.toggleShow();
            }
        });
    }

//    public void pastedSQLConnection(){
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connect = DriverManager.getConnection(
//                    DatabaseAccess.databaseURL, DatabaseAccess.user, DatabaseAccess.password); // Replace with database name (username), username, and password
//            Statement statement = connect.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM TEST;");
//            while (rs.next()) {
//                String studentName = rs.getString(1);
//                System.out.println("Student name = " +
//                        studentName);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}