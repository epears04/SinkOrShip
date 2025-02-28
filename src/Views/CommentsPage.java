package Views;

import Database.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CommentsPage extends JPanel implements ActionListener {
    private JPanel panel;
    private static Color backgroundColor = new Color(223, 190, 239);
    private boolean visibility = false;

    public CommentsPage() {
        setLayout(new BorderLayout());

        // set up main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        // testing ships - will be connected to db soon!
        addShip("Jelena", "Justin", "Selena", 10);
        addShip("Bennifer", "Jennifer", "Ben", 11);
        addShip("Brangelina", "Angelina Jolie", "Brad Pitt", 8);
        addShip("Speidi", "Heidi Montag", "Spencer Pratt", 9);
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

    private void addShip(String shipName, String p1, String p2, int votes) {
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.X_AXIS));
        commentPanel.setSize(new Dimension(800, 100));
        commentPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 30));

        // add ship name, people, and number of votes
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        JLabel shipNameLabel = new JLabel(shipName, JLabel.LEFT);
        shipNameLabel.setFont(shipNameLabel.getFont().deriveFont(Font.BOLD, 20));
        JLabel namesLabel = new JLabel(p1 + " and " + p2, JLabel.LEFT);
        JLabel votesLabel = new JLabel("Votes: " + votes, JLabel.CENTER);
        labelPanel.add(shipNameLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(namesLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(votesLabel);

        // make a panel for vote buttons
        JPanel votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.X_AXIS));

        // add buttons to vote panel

        // set up ship component
        commentPanel.add(labelPanel, BorderLayout.WEST);
        commentPanel.add(votePanel, BorderLayout.EAST);

        // add component to main panel
        panel.add(commentPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.revalidate();
        panel.repaint();
    }




}
