package Components;

import javax.swing.*;
import java.awt.*;
import MainPack.MainFrame;

public class DrawerPanel extends JPanel {
    private boolean isOpen = false;
    private MainFrame mainFrame;

    public DrawerPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, mainFrame.getHeight()));
        setBackground(Color.PINK);

        JButton toggleButton = new JButton("☰");
        toggleButton.addActionListener(e -> toggleDrawer());

        // menu
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 1));

        JButton viewShips = new JButton("View Ships");
        viewShips.addActionListener(e -> mainFrame.showPage("View"));
        contentPanel.add(viewShips);

        JButton createPerson = new JButton("Create Person");
        createPerson.addActionListener(e -> mainFrame.showPage("Person"));
        contentPanel.add(createPerson);

        JButton createShip = new JButton("Create Ship");
        createShip.addActionListener(e -> mainFrame.showPage("add"));
        contentPanel.add(createShip);

        JButton comment = new JButton("Leave Comment");
        comment.addActionListener(e -> mainFrame.showPage("Comment"));
        contentPanel.add(comment);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(false);
    }

    public void toggleDrawer() {
        isOpen = !isOpen;
        setVisible(isOpen);
        revalidate();
        repaint();
    }
}
