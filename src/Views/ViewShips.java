package Views;

import Database.Connect;
import MainPack.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewShips extends JPanel {
    private class ButtonActionListener implements ActionListener{
        private JRadioButton prevSelectedButton;
        private JRadioButton upButton;
        private JRadioButton downButton;
        private int sid;
        private JLabel votesLabel;
        private ImageIcon downSelectedIcon;
        private ImageIcon upSelectedIcon;
        private ImageIcon upIcon;
        private ImageIcon downIcon;

        public ButtonActionListener(JRadioButton prevSelectedButton, JRadioButton upButton, JRadioButton downButton, int sid, JLabel votesLabel, ImageIcon downSelectedIcon, ImageIcon upSelectedIcon, ImageIcon downIcon, ImageIcon upIcon) {
            this.prevSelectedButton = prevSelectedButton;
            this.upButton = upButton;
            this.downButton = downButton;
            this.sid = sid;
            this.votesLabel = votesLabel;
            this.downSelectedIcon = downSelectedIcon;
            this.upSelectedIcon = upSelectedIcon;
            this.downIcon = downIcon;
            this.upIcon = upIcon;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int voteQuant = 0;
            JRadioButton sourceButton = (JRadioButton) e.getSource(); // Get the clicked button

            if (prevSelectedButton == null) {
                // No button selected -> Select the clicked button
                prevSelectedButton = sourceButton;
                voteQuant = (sourceButton == upButton) ? 1 : -1;
            } else if (prevSelectedButton == upButton && sourceButton == downButton) {
                // Switching from up to down
                prevSelectedButton = downButton;
                voteQuant = -2;
            } else if (prevSelectedButton == downButton && sourceButton == upButton) {
                // Switching from down to up
                prevSelectedButton = upButton;
                voteQuant = 2;
            } else if (prevSelectedButton == sourceButton) {
                // Deselecting the currently selected button
                prevSelectedButton = null;
                voteQuant = (sourceButton == upButton) ? -1 : 1;
            }

           // Update icons accordingly
            upButton.setIcon(prevSelectedButton == upButton ? upSelectedIcon : upIcon);
            downButton.setIcon(prevSelectedButton == downButton ? downSelectedIcon : downIcon);

            // Call vote handling method
            handleVote(sid, voteQuant, votesLabel);
        }
    }

    private JPanel panel;
    private static Color backgroundColor = new Color(223, 190, 239);
    private static MainFrame mainFrame;

    public ViewShips(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // set up main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        fetchShips();
    }

    // make a ship component
    private void addShip(int sid, String shipName, String p1, String p2, String date, int votes) {
        JPanel shipPanel = new JPanel(new BorderLayout());
        shipPanel.setLayout(new BoxLayout(shipPanel, BoxLayout.X_AXIS));
        shipPanel.setPreferredSize(new Dimension(800, 100));
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

        JPanel commentConnectionPanel = new JPanel();
        commentConnectionPanel.setLayout(new BoxLayout(commentConnectionPanel, BoxLayout.Y_AXIS));

        ImageIcon commentIcon = new ImageIcon("images/comment_icon.png");
        Image scaledCommentImage = commentIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        commentIcon = new ImageIcon(scaledCommentImage);
        JButton commentButton = new JButton(commentIcon);
        commentConnectionPanel.add(commentButton);

        commentButton.setOpaque(false);
        commentButton.setContentAreaFilled(false);
        commentButton.setBorderPainted(false);
        commentButton.setFocusPainted(false);
        commentButton.addActionListener(e->{
            mainFrame.getMainPanel().add(new CommentsPage(shipName), "Comments");
            mainFrame.showPage("Comments");

        });

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
        JRadioButton downButton = new JRadioButton(downIcon);

        JRadioButton prevSelectedButton = null;

        ViewShips.ButtonActionListener actionListener = new ButtonActionListener(prevSelectedButton, upButton, downButton, sid, votesLabel, downSelectedIcon, upSelectedIcon, downIcon, upIcon);

        upButton.addActionListener(actionListener);
        downButton.addActionListener(actionListener);

//        final Map<JRadioButton, JRadioButton> prevSelectedButtonMap = new HashMap<>();
//        prevSelectedButtonMap.put(upButton, null);
//        prevSelectedButtonMap.put(downButton, null);

//        ActionListener listener = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JRadioButton selectedButton = (JRadioButton) e.getSource();
//                JRadioButton prevSelectedButton = prevSelectedButtonMap.get(selectedButton);
//
//                if (selectedButton == upButton) {
//                    if (prevSelectedButton != upButton) {
//                        handleVote(sid, 1, votesLabel);
//                        prevSelectedButtonMap.put(upButton, upButton);
//                        prevSelectedButtonMap.put(downButton, null); // Reset other button
//                    } else {
//                        handleVote(sid, 0, votesLabel);
//                        prevSelectedButtonMap.put(upButton, null);
//                    }
//                } else if (selectedButton == downButton) {
//                    if (prevSelectedButton != downButton) {
//                        handleVote(sid, -1, votesLabel);
//                        prevSelectedButtonMap.put(downButton, downButton);
//                        prevSelectedButtonMap.put(upButton, null); // Reset other button
//                    } else {
//                        handleVote(sid, 0, votesLabel);
//                        prevSelectedButtonMap.put(downButton, null);
//                    }
//                }
//            }
//        };

//        ImageIcon finalUpSelectedIcon = upSelectedIcon;
//        upButton.addActionListener(e->{
//            if(!downButton.isEnabled()) handleVote(sid, 2, votesLabel);
//            else handleVote(sid, 1, votesLabel);
//            downButton.setEnabled(true);
//            upButton.setEnabled(false);
//            upButton.setDisabledIcon(finalUpSelectedIcon);
//        });
//        ImageIcon finalDownSelectedIcon = downSelectedIcon;
//        downButton.addActionListener(e->{
//            if(!upButton.isEnabled()) handleVote(sid, -2, votesLabel);
//            else handleVote(sid, -1, votesLabel);
//            downButton.setEnabled(false);
//            upButton.setEnabled(true);
//            downButton.setDisabledIcon(finalDownSelectedIcon);
//        });

        ButtonGroup shipButtonGroup = new ButtonGroup();
        shipButtonGroup.add(upButton);
        shipButtonGroup.add(downButton);

        votePanel.add(upButton);
        votePanel.add(Box.createHorizontalStrut(5));
        votePanel.add(downButton);

        // set up ship component
        shipPanel.add(labelPanel, BorderLayout.WEST);
        shipPanel.add(commentConnectionPanel, BorderLayout.CENTER);
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
            String query = "SELECT s.sid, s.username1, s.username2, s.date_posted, s.ship_name, COALESCE(sum(v.vote),0) AS votes " +
                    "FROM Ships s LEFT JOIN Votes v ON s.sid = v.sid " +
                    "GROUP BY s.sid, s.username1, s.username2, s.date_posted, s.ship_name " +
                    "ORDER BY sum(v.vote) DESC;";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int sid = resultSet.getInt("sid");
                String shipName = resultSet.getString("ship_name");
                String username1 = resultSet.getString("username1");
                String username2 = resultSet.getString("username2");
                String date = resultSet.getString("date_posted");
                int votes = resultSet.getInt("votes");

                addShip(sid, shipName, username1, username2, date, votes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleVote(int sid, int votes, JLabel votesLabel) {
        String query = "INSERT INTO Votes (sid, vote, vDate) VALUES (?, ?, CURDATE())";

        try {
            Connection connect = Connect.createConnection();
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setInt(1, sid); //set sid
            statement.setInt(2, votes); //set vote
            statement.executeUpdate();

            updateVoteCount(sid, votesLabel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateVoteCount(int sid, JLabel votesLabel) {
        String query = "SELECT COALESCE(sum(vote),0) AS total_votes, sid" +
                        " FROM Votes" +
                        " WHERE sid = ?;";
        try {
            Connection connect = Connect.createConnection();
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setInt(1, sid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int updatedVotes = resultSet.getInt("total_votes");
                votesLabel.setText("Votes: " + updatedVotes);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}