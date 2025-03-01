package Views;

import Database.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class CommentsPage extends JPanel implements ActionListener {
    private JPanel panel;
    private JPanel commenterNamePanel;
    private JTextField commenterTextField;
    private JPanel writtenCommentPanel;
    private JTextArea commentEntry;
    private JButton saveButton;
    private static Color backgroundColor = new Color(223, 190, 239);
    private boolean visibility = false;

    public CommentsPage(String shipName) {
        setLayout(new BorderLayout());

        // set up main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));

        commenterNamePanel = new JPanel(new BorderLayout());
        commenterNamePanel.setLayout(new BoxLayout(commenterNamePanel, BoxLayout.X_AXIS));
        commenterNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 400));
        commenterNamePanel.setMinimumSize(new Dimension(800, 40));
        commenterNamePanel.setMaximumSize(new Dimension(800, 40));
        commenterNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel("Name: ", JLabel.LEFT);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        //Add name label and restructure
        commenterNamePanel.add(nameLabel, BorderLayout.WEST);
        commenterNamePanel.add(Box.createHorizontalStrut(5));

        commenterTextField = new JTextField();
        commenterTextField.setMaximumSize(new Dimension(300, 25)); // Limits width
        commenterTextField.setPreferredSize(new Dimension(200, 25)); // Preferred width
        commenterTextField.setMinimumSize(new Dimension(200, 25)); // Ensures it doesn't shrink too much
        commenterNamePanel.add(commenterTextField, BorderLayout.WEST);

        panel.add(commenterNamePanel, BorderLayout.WEST);

        writtenCommentPanel = new JPanel();
        writtenCommentPanel.setLayout(new BoxLayout(writtenCommentPanel, BoxLayout.Y_AXIS));
//        writtenCommentPanel.add(commenterTextField);


        panel.add(Box.createVerticalStrut(30));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        fetchComments(shipName);
        setVisible(true);
    }

    public void toggleShow(){
        visibility = !visibility;
        setVisible(visibility);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Submit")) {
            // needs to link back to home page
            //going to force link to Add SHip page for proof of functionality

            this.toggleShow();
            AddShip connectedFrame = new AddShip();

        }
    }

    private void addPostedComment(String shipName, String commenter, String comment, String date) {
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.X_AXIS));
        commentPanel.setSize(new Dimension(800, 100));
        commentPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 30));

        // add ship name, people, and number of votes
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JLabel shipNameLabel = new JLabel(shipName, JLabel.LEFT);
        shipNameLabel.setFont(shipNameLabel.getFont().deriveFont(Font.BOLD, 20));

        JLabel dateLabel = new JLabel(date, JLabel.LEFT);
        dateLabel.setFont(dateLabel.getFont().deriveFont(Font.PLAIN, 13));

        JLabel commenterLabel = new JLabel("@" + commenter + ": " , JLabel.LEFT);
        commenterLabel.setFont(commenterLabel.getFont().deriveFont(Font.PLAIN, 15));
        commenterLabel.setForeground(Color.BLUE);

//        JLabel commentLabel = new JLabel(comment, JLabel.CENTER);
//        commentLabel.setForeground(new Color(255, 169, 215));
        JTextArea commentLabel = new JTextArea(comment);
        commentLabel.setLineWrap(true);
        commentLabel.setWrapStyleWord(true);
        commentLabel.setOpaque(false); // Make background transparent
        commentLabel.setEditable(false); // Prevent user editing
        commentLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        commentLabel.setForeground(new Color(255, 169, 215)); // Set text color
        commentLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 45));


        JPanel commentBox = new JPanel();
        commentBox.setLayout(new BoxLayout(commentBox, BoxLayout.Y_AXIS));
        commentBox.setSize(new Dimension(300, 75));
        commentBox.add(Box.createHorizontalStrut(100)); // Indentation (adjust value as needed) -> might replace with images of ship ??
        commentBox.add(commentLabel);
        commentBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left within Y_AXIS layout

        labelPanel.add(shipNameLabel);
        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(dateLabel);
        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(commenterLabel);
        labelPanel.add(Box.createVerticalStrut(5));

        labelPanel.add(commentBox, BorderLayout.NORTH);
        labelPanel.add(Box.createVerticalStrut(5));

        // set up ship component
        commentPanel.add(labelPanel, BorderLayout.WEST);

        // add component to main panel
        panel.add(commentPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.revalidate();
        panel.repaint();
    }


    private void fetchComments(String shipName) {
        try {
            Connection connect = Connect.createConnection();
            String query = "SELECT comment, commenter, date_posted FROM Comments WHERE Sid = (SELECT sid FROM Ships WHERE ship_name = \"" + shipName + "\");";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String commenter = resultSet.getString("commenter");
                String comment = resultSet.getString("comment");
                String date = resultSet.getString("date_posted");

                addPostedComment(shipName, commenter, comment, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
