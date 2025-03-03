package Views;

import Database.Connect;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddShip extends JPanel {
    // Components of the Form
    private JLabel title, shipName, personA, personB, personFeedback, personAImage, personBImage;
    private JTextField tShipName, tPersonA, tPersonB;
    private JButton searchPersonA, searchPersonB, submit;
    private static Color backgroundColor = new Color(223, 190, 239);

    // Constructor, to initialize the components
    // with default values.
    public AddShip() {
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);
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

        searchPersonA.addActionListener(e -> {
            searchPersonFetchImage(tPersonA.getText(), personAImage);
        });

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

        searchPersonB.addActionListener(e -> {
            searchPersonFetchImage(tPersonB.getText(), personBImage);
        });

        // Person B Image
        personBImage = new JLabel();
        personBImage.setPreferredSize(new Dimension(100, 100));
        personBImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 3;
        add(personBImage, gbc);

        personFeedback = new JLabel();
        personFeedback.setPreferredSize(new Dimension(200, 20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(personFeedback, gbc);

        // Submit Button
        submit = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submit, gbc);

        submit.addActionListener(e -> {
            submit();
        });
    }

    public void submit() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        String query = String.format(
                "INSERT INTO Ships (username1, username2, date_posted, ship_name) VALUES ('%s', '%s', '%s', '%s');",
                tPersonA.getText(), tPersonB.getText(), formattedDate, tShipName.getText());
        try {
            Connection connect = Connect.createConnection();
            Statement statement = connect.createStatement();
            statement.addBatch(query);
            statement.executeBatch();
            clearFields();
            JOptionPane.showMessageDialog(this, "Submitted");
        } catch (BatchUpdateException bue) {
            if (bue.getErrorCode() == 1062) {
                personFeedback.setText("This ship name already exists!");
            } else {
                personFeedback.setText("Uh oh, this ship can't go through");
                System.err.println("Batch Update Error: " + bue.getMessage());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void searchPersonFetchImage(String person, JLabel personImage) {
        String query = "SELECT username, profile_pic from People where username = \"" + person + "\";";
        try {
            Connection connect = Connect.createConnection();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                personFeedback.setText("");
                Blob blob = rs.getBlob("profile_pic");
                if (blob != null) {
                    byte[] byteArr = blob.getBytes(1,(int)blob.length());

                    // Convert byte array to Image
                    ByteArrayInputStream bais = new ByteArrayInputStream(byteArr);
                    BufferedImage img = ImageIO.read(bais);

                    // Resize image to match JLabel size
                    Image scaledImg = img.getScaledInstance(personImage.getWidth(), personImage.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaledImg);

                    // Set the image to the JLabel
                    personImage.setIcon(icon);
                    personImage.setVisible(true);
                } else {
                    personImage.setText("No Image Found");
                    personImage.setFont(new Font("Arial", Font.ITALIC, 13));
                }
            } else {
                personFeedback.setText(person + " doesn't exist!");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        tShipName.setText("");
        tPersonA.setText("");
        tPersonB.setText("");

        // Clear images
        personAImage.setIcon(null);
        personAImage.setText(""); // Remove "No Image Found" text if present

        personBImage.setIcon(null);
        personBImage.setText("");

        // Clear feedback label
        personFeedback.setText("");
    }
}