package Views;

import Database.Connect;
import MainPack.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

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

        fetchPeople();
    }

    // username, age, major, gender, profile_pic (blob), description
    public void fetchPeople() {
        try {
            Connection connect = Connect.createConnection();
            String query = "SELECT * FROM People";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                Blob imgBlob = resultSet.getBlob("profile_pic");
                String description = resultSet.getString("description");

                addPerson(username, age, gender, imgBlob, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPerson(String username, int age, String gender, Blob blob, String description) {
        JPanel personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));
        personPanel.setPreferredSize(new Dimension(800, 100));
        personPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 30));

        JLabel nameLabel = new JLabel(username);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 20));
        JLabel ageLabel = new JLabel("Age: " + age, JLabel.CENTER);
        JLabel genderLabel = new JLabel("Gender: " + gender, JLabel.CENTER);
        JLabel descriptionLabel = new JLabel("Description: " + description, JLabel.CENTER );

        personPanel.add(nameLabel);
        personPanel.add(Box.createVerticalStrut(5));
        personPanel.add(ageLabel);
        personPanel.add(Box.createVerticalStrut(5));
        personPanel.add(genderLabel);
        personPanel.add(Box.createVerticalStrut(5));
        personPanel.add(descriptionLabel);
        personPanel.add(Box.createVerticalStrut(5));

        panel.add(personPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.revalidate();
        panel.repaint();
    }
}
