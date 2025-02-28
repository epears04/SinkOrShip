package Views;

import Database.Connect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewShips extends JPanel {

    private JPanel panel;
    private static Color backgroundColor = new Color(223, 190, 239);

    public ViewShips() {
        setLayout(new BorderLayout());

        // set up main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        // testing ships - will be connected to db soon!
//        addShip("Jelena", "Justin", "Selena", "10-2-2024", 10);
//        addShip("Bennifer", "Jennifer", "Ben", "2-3-2025", 11);
//        addShip("Brangelina", "Angelina Jolie", "8-23-2012", "Brad Pitt", 8);
//        addShip("Speidi", "Heidi Montag", "Spencer Pratt", "3-31-2025", 9);
//        setVisible(true);
        fetchShips();
    }

    // make a ship component
    private void addShip(String shipName, String p1, String p2, String date, int votes) {
        JPanel shipPanel = new JPanel(new BorderLayout());
        shipPanel.setLayout(new BoxLayout(shipPanel, BoxLayout.X_AXIS));
        shipPanel.setSize(new Dimension(800, 100));
        shipPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 30));

        // add ship name, people, and number of votes
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        JLabel shipNameLabel = new JLabel(shipName, JLabel.LEFT);
        shipNameLabel.setFont(shipNameLabel.getFont().deriveFont(Font.BOLD, 20));
        JLabel namesLabel = new JLabel(p1 + " and " + p2, JLabel.LEFT);
        JLabel dateLabel = new JLabel(date, JLabel.LEFT);
        JLabel votesLabel = new JLabel("Votes: " + votes, JLabel.CENTER);
        labelPanel.add(shipNameLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(namesLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(dateLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(votesLabel);

        // make a panel for vote buttons
        JPanel votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.X_AXIS));

        // set up button icons
        ImageIcon upIcon = new ImageIcon("images/up-arrow.png");
        Image scaledUpImage = upIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        upIcon = new ImageIcon(scaledUpImage);
        ImageIcon downIcon = new ImageIcon("images/down-arrow.png");
        Image scaledDownImage = downIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        downIcon = new ImageIcon(scaledDownImage);

        // set up selected button icons
        ImageIcon upSelectedIcon = new ImageIcon("images/selected-up-arrow.png");
        Image scaledUpSelectedIcon = upSelectedIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        upSelectedIcon = new ImageIcon(scaledUpSelectedIcon);
        ImageIcon downSelectedIcon = new ImageIcon("images/selected-down-arrow.png");
        Image scaledDownSelectedIcon = downSelectedIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        downSelectedIcon = new ImageIcon(scaledDownSelectedIcon);

        // add buttons to vote panel
        JRadioButton upButton = new JRadioButton(upIcon);
        upButton.setSelectedIcon(upSelectedIcon);
        JRadioButton downButton = new JRadioButton(downIcon);
        downButton.setSelectedIcon(downSelectedIcon);

        ButtonGroup shipButtonGroup = new ButtonGroup();
        shipButtonGroup.add(upButton);
        shipButtonGroup.add(downButton);

        votePanel.add(upButton);
        votePanel.add(Box.createHorizontalStrut(5));
        votePanel.add(downButton);

        // set up ship component
        shipPanel.add(labelPanel, BorderLayout.WEST);
        shipPanel.add(votePanel, BorderLayout.EAST);

        // add component to main panel
        panel.add(shipPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.revalidate();
        panel.repaint();
    }

    private void fetchShips() {
        try {
            Connection connect = Connect.createConnection();
            String query = "SELECT s.username1, s.username2, s.date_posted, s.ship_name, COALESCE(sum(vote),0) AS votes " +
                    "FROM Ships s LEFT JOIN Votes v ON s.sid = v.sid " +
                    "GROUP BY s.sid, s.username1, s.username2, s.date_posted, s.ship_name;";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String shipName = resultSet.getString("ship_name");
                String username1 = resultSet.getString("username1");
                String username2 = resultSet.getString("username2");
                String date = resultSet.getString("date_posted");
                int votes = resultSet.getInt("votes");

                addShip(shipName, username1, username2, date, votes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}