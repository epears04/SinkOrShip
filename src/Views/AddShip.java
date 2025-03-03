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

public class AddShip extends JPanel implements ActionListener {
    // Components of the Form
    private JLabel title, shipName, personA, personB, personAImage, personBImage;
    private JTextField tShipName, tPersonA, tPersonB;
    private JButton searchPersonA, searchPersonB, submit;

    // Constructor, to initialize the components
    // with default values.
    public AddShip() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        title = new JLabel("Add a Ship!");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        // Ship Name Row
        shipName = new JLabel("Ship Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(shipName, gbc);

        tShipName = new JTextField(20);
        gbc.gridx = 1;
        add(tShipName, gbc);

        // Person A Row
        personA = new JLabel("Person 1:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(personA, gbc);

        tPersonA = new JTextField(15);
        gbc.gridx = 1;
        add(tPersonA, gbc);

        searchPersonA = new JButton("Search");
        gbc.gridx = 2;
        add(searchPersonA, gbc);

        // Person A Image
        personAImage = new JLabel();
        personAImage.setPreferredSize(new Dimension(100, 100));
        personAImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 3;
        add(personAImage, gbc);

        // Person B Row
        personB = new JLabel("Person 2:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(personB, gbc);

        tPersonB = new JTextField(15);
        gbc.gridx = 1;
        add(tPersonB, gbc);

        searchPersonB = new JButton("Search");
        gbc.gridx = 2;
        add(searchPersonB, gbc);

        // Person B Image
        personBImage = new JLabel();
        personBImage.setPreferredSize(new Dimension(100, 100));
        personBImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 3;
        add(personBImage, gbc);

        // Submit Button
        submit = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submit, gbc);
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