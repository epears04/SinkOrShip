package Views;

import Database.Connect;
import MainPack.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;

public class ViewPeople extends JPanel {

    private JPanel panel;
    private static Color backgroundColor = new Color(223, 190, 239);
    private static MainFrame mainFrame;

    public ViewPeople(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        //add title
        JLabel title = new JLabel("People");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.CENTER); //need to make this align center
        panel.add(Box.createVerticalStrut(30));

        fetchPeople();
    }

    // get person's username, age, major, gender, profile_pic (blob), description from database
    public void fetchPeople() {
        try {
            Connection connect = Connect.createConnection();
            String query = "SELECT * FROM People";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                int age = resultSet.getInt(2);
                String major = resultSet.getString(3);
                String gender = resultSet.getString(4);
                Blob imgBlob = resultSet.getBlob(5);
                String description = resultSet.getString(6);

                addPerson(username, age, gender, major, imgBlob, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // get image from Blob or default image
    private ImageIcon getProfileImage(Blob blob) {
        try {
            ImageIcon profileIcon;

            if (blob != null) {
                byte[] imgData = blob.getBytes(1, (int) blob.length());
                profileIcon = new ImageIcon(imgData);
            } else {
                profileIcon = new ImageIcon("images/default_profile.png");
            }

            // Resize the image to 64x64
            Image scaledImage = profileIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageIcon fallbackIcon = new ImageIcon("images/default_profile.png");
        Image scaledFallback = fallbackIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledFallback);
    }

    // add Person data to page
    public void addPerson(String username, int age, String gender, String major, Blob blob, String description) {
        // create person box
        JPanel personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.X_AXIS));
        personPanel.setPreferredSize(new Dimension(800, 150));
        personPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 30));

        // Profile Image Handling
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        JLabel imageLabel = new JLabel();
        ImageIcon profileImage = getProfileImage(blob);
        imageLabel.setIcon(profileImage);
        imagePanel.add(imageLabel);

        // create info labels
        JLabel nameLabel = new JLabel(username);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 20));
        JLabel majorLabel = new JLabel("Major: " + major, JLabel.CENTER);
        JLabel ageLabel = new JLabel("Age: " + age, JLabel.CENTER);
        JLabel genderLabel = new JLabel("Gender: " + gender, JLabel.CENTER);
        JLabel descriptionLabel = new JLabel("Description: " + description, JLabel.CENTER );

        // add info labels to box
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(ageLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(genderLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(majorLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(descriptionLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        personPanel.add(imagePanel, BorderLayout.WEST);
        personPanel.add(Box.createHorizontalStrut(30));
        personPanel.add(infoPanel, BorderLayout.CENTER);

        // update people panel
        panel.add(personPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.revalidate();
        panel.repaint();
    }
}
