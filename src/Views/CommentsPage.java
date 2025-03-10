package Views;

import Database.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CommentsPage extends JPanel implements ActionListener {
    private String shipName;
    private JPanel panel;
    private JPanel commenterNamePanel;
    private JTextField commenterTextField;
    private JPanel writtenCommentPanel;
    private JTextArea commentEntry;
    private JButton saveButton;
    private static Color backgroundColor = new Color(223, 190, 239);
    private boolean visibility = false;
    private JLabel noCommentsLabel = null;

    public CommentsPage(String shipName) {
        this.shipName = shipName;
        setLayout(new BorderLayout());

        // set up main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));

        commenterNamePanel = new JPanel();
        commenterNamePanel.setLayout(new BorderLayout());
        commenterNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Dimension namePanelSize = new Dimension(800, 40);

        commenterNamePanel.setPreferredSize(namePanelSize);
        commenterNamePanel.setMaximumSize(namePanelSize);
        commenterNamePanel.setMinimumSize(namePanelSize);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name: ", JLabel.RIGHT);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        commenterTextField = new JTextField();
        commenterTextField.setPreferredSize(new Dimension(400, 25)); // Limits width

        commenterTextField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(0, 2, 0, 0)));
//        commenterTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//        commenterTextField.setBorder(BorderFactory.createEmptyBorder(0,2,0,0));
        commenterNamePanel.add(commenterTextField, BorderLayout.CENTER);

        leftPanel.add(nameLabel);
        leftPanel.add(Box.createHorizontalStrut(5));
        leftPanel.add(commenterTextField);


        //Add name label and restructure
        commenterNamePanel.add(leftPanel, BorderLayout.WEST);
        commenterNamePanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER);

        panel.add(commenterNamePanel, BorderLayout.NORTH);

        writtenCommentPanel = new JPanel();
        writtenCommentPanel.setLayout(new BoxLayout(writtenCommentPanel, BoxLayout.Y_AXIS));
        writtenCommentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Dimension commentPanelSize = new Dimension(800, 150);

        writtenCommentPanel.setPreferredSize(commentPanelSize);
        writtenCommentPanel.setMaximumSize(commentPanelSize);
        writtenCommentPanel.setMinimumSize(commentPanelSize);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel commentEntryLabel = new JLabel("Comment: ", JLabel.RIGHT);
        commentEntryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        labelPanel.add(commentEntryLabel);
        writtenCommentPanel.add(labelPanel);

        commentEntry = new JTextArea(3, 40);
        commentEntry.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 2, 0, 0)));

        writtenCommentPanel.add(commentEntry);
        writtenCommentPanel.add(Box.createVerticalStrut(5));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        saveButton = new JButton("Post Comment");

        buttonPanel.add(saveButton);
        writtenCommentPanel.add(buttonPanel);

        panel.add(writtenCommentPanel, BorderLayout.CENTER);


        panel.add(Box.createVerticalStrut(30));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        fetchComments(shipName);
        saveButton.addActionListener(this);
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
        else if (s.equals("Post Comment")){
            try{
                Connection connect = Connect.createConnection();

                PreparedStatement statement = connect.prepareStatement("select sid from Ships where ship_name = \"" + shipName + "\"");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int sid = resultSet.getInt("sid");
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = currentDate.format(formatter);
                String sentInsert = "insert into Comments (sid, comment, commenter, date_posted) values (" + sid + ", \"" +  commentEntry.getText() + "\", \"" + commenterTextField.getText() +  "\", \"" + currentDate + "\");";
                int updateFromSQL = statement.executeUpdate(sentInsert);
                if (updateFromSQL > 0) {
                    addPostedComment(shipName, commenterTextField.getText(),commentEntry.getText(), String.valueOf(LocalDate.now()));
                    commenterTextField.setText("");
                    commentEntry.setText("");
                }else{
                    throw new Exception("error posting comment to database. Please Try Again.");
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void addPostedComment(String shipName, String commenter, String comment, String date) {
        if (noCommentsLabel != null && noCommentsLabel.isVisible())
                noCommentsLabel.setVisible(false);
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
        commentLabel.setForeground(Color.BLACK); // Set text color
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
            String query = "SELECT comment, commenter, date_posted FROM Comments WHERE Sid = (SELECT sid FROM Ships WHERE ship_name = \"" + shipName + "\") order by date_posted desc;";
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                noCommentsLabel = new JLabel("No comments found");
                noCommentsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                noCommentsLabel.setForeground(Color.GRAY);
                panel.add(noCommentsLabel, BorderLayout.NORTH);
            }
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
