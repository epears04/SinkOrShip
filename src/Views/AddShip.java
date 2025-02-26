package Views;

import Database.Connect;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Blob;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddShip extends JFrame implements ActionListener {
    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel shipName;
    private JTextField tShipName;
    private JLabel personA;
    private JTextField tPersonA;
    private JButton searchPersonA;
    private JLabel personAImage;
    private JLabel personB;
    private JTextField tPersonB;
    private JButton searchPersonB;
    private JLabel personBImage;
    private JButton submit;

    // Constructor, to initialize the components
    // with default values.
    public AddShip() {
        setTitle("Add Ships");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Add a Ship!");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        shipName = new JLabel("Ship Name");
        shipName.setFont(new Font("Arial", Font.PLAIN, 20));
        shipName.setSize(100, 20);
        shipName.setLocation(100, 100);
        c.add(shipName);

        tShipName = new JTextField();
        tShipName.setFont(new Font("Arial", Font.PLAIN, 15));
        tShipName.setSize(300, 30);
        tShipName.setLocation(200, 100);
        c.add(tShipName);

        personA = new JLabel("Person 1");
        personA.setFont(new Font("Arial", Font.PLAIN, 20));
        personA.setSize(100, 20);
        personA.setLocation(100, 150);
        c.add(personA);

        tPersonA = new JTextField();
        tPersonA.setFont(new Font("Arial", Font.PLAIN, 15));
        tPersonA.setSize(300, 30);
        tPersonA.setLocation(200, 150);
        c.add(tPersonA);

        searchPersonA = new JButton("Search");
        searchPersonA.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPersonA.setSize(75, 20);
        searchPersonA.setLocation(530, 155);
        c.add(searchPersonA);
        searchPersonA.addActionListener(this);

        personAImage = new JLabel();
        personAImage.setSize(150, 150);
        personAImage.setLocation(200, 250);
        c.add(personAImage);
        personAImage.setVisible(false);

        personB = new JLabel("Person 2");
        personB.setFont(new Font("Arial", Font.PLAIN, 20));
        personB.setSize(100, 20);
        personB.setLocation(100, 200);
        c.add(personB);

        tPersonB = new JTextField();
        tPersonB.setFont(new Font("Arial", Font.PLAIN, 15));
        tPersonB.setSize(300, 30);
        tPersonB.setLocation(200, 200);
        c.add(tPersonB);

        searchPersonB = new JButton("Search");
        searchPersonB.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPersonB.setSize(75, 20);
        searchPersonB.setLocation(530, 205);
        c.add(searchPersonB);
        searchPersonB.addActionListener(this);

        personBImage = new JLabel();
        personBImage.setSize(150, 150);
        personBImage.setLocation(350, 250);
        c.add(personBImage);
        personBImage.setVisible(false);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(150, 450);
        submit.addActionListener(this);
        c.add(submit);
    }

    // Method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            JOptionPane.showMessageDialog(this, "Submitted");
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            String shipName = tShipName.getText();
            try {
                Connection connect = Connect.createConnection();
                Statement statement = connect.createStatement();
                String query = String.format(
                        "INSERT INTO Ships (username1, username2, date_posted, ship_name) VALUES ('%s', '%s', '%s', '%s');",
                        tPersonA.getText(), tPersonB.getText(), formattedDate, shipName);
                statement.addBatch(query);
                statement.executeBatch();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == searchPersonA) {
            searchPersonFetchImage(tPersonA.getText(), personAImage);
        } else if (e.getSource() == searchPersonB) {
            searchPersonFetchImage(tPersonB.getText(), personBImage);
        }
    }

    private Connection getConnection(){
        try {
            return Connect.createConnection();
        }
        catch (SQLException e) {
            System.out.println("Connection Error");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void searchPersonFetchImage(String person, JLabel personImage) {
        Connection connect = getConnection();
        try {
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT username, profile_pic from People where username = \"" + person + "\";");

            if (rs.next()) {
                //TODO: send user message that the person was found in the database (variable based on the desired search)
                //TODO: Print out the person's profile_photo (if available)
                Blob blob = rs.getBlob("profile_pic");
                byte[] byteArr = blob.getBytes(1,(int)blob.length());

                // Convert byte array to Image
                ByteArrayInputStream bais = new ByteArrayInputStream(byteArr);
                BufferedImage img = ImageIO.read(bais);

                if (img != null) {
                    // Resize image to match JLabel size
                    Image scaledImg = img.getScaledInstance(personImage.getWidth(), personImage.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaledImg);

                    // Set the image to the JLabel
                    personImage.setIcon(icon);
                    personImage.setVisible(true);
                } else {
                    System.out.println("Failed to read image from byte array.");
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


}