package Views;

import javax.swing.*;
import java.awt.*;

public class ViewShips extends JFrame {

    private JPanel panel;

    public ViewShips() {
        setTitle("View Ships");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.PINK);
        panel.setBorder(BorderFactory.createLineBorder(Color.PINK, 20));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        addShip("Jelena", "Justin", "Selena", 10);
        addShip("Bennifer", "Jennifer", "Ben", 11);
        addShip("Brangelina", "Angelina Jolie", "Brad Pitt", 8);
        addShip("Speidi", "Heidi Montag", "Spencer Pratt", 9);
        setVisible(true);
    }

    private void addShip(String shipName, String p1, String p2, int votes) {
        JPanel shipPanel = new JPanel(new BorderLayout());
        shipPanel.setLayout(new BoxLayout(shipPanel, BoxLayout.X_AXIS));
        shipPanel.setSize(new Dimension(800, 100));
        shipPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 30));

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


        JPanel votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.X_AXIS));
        ImageIcon upIcon = new ImageIcon("images/up-arrow.png");
        Image scaledUpImage = upIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        upIcon = new ImageIcon(scaledUpImage);
        ImageIcon downIcon = new ImageIcon("images/down-arrow.png");
        Image scaledDownImage = downIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        downIcon = new ImageIcon(scaledDownImage);
        JButton upButton = new JButton(upIcon);
        JButton downButton = new JButton(downIcon);
        votePanel.add(upButton);
        votePanel.add(Box.createHorizontalStrut(5));
        votePanel.add(downButton);

        shipPanel.add(labelPanel, BorderLayout.WEST);
        shipPanel.add(votePanel, BorderLayout.EAST);

        panel.add(shipPanel);
        panel.add(Box.createVerticalStrut(30));
        panel.revalidate();
        panel.repaint();
    }

}