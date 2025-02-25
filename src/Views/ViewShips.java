package Views;

import javax.swing.*;
import java.awt.*;

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
        addShip("Jelena", "Justin", "Selena", 10);
        addShip("Bennifer", "Jennifer", "Ben", 11);
        addShip("Brangelina", "Angelina Jolie", "Brad Pitt", 8);
        addShip("Speidi", "Heidi Montag", "Spencer Pratt", 9);
        setVisible(true);
    }

    // make a ship component
    private void addShip(String shipName, String p1, String p2, int votes) {
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
        JLabel votesLabel = new JLabel("Votes: " + votes, JLabel.CENTER);
        labelPanel.add(shipNameLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(namesLabel);
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
}