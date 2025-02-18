package Components;

import javax.swing.*;
import java.awt.*;

public class DrawerPanel extends JPanel {
    private boolean isOpen = false;

    public DrawerPanel(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, parentFrame.getHeight()));
        setBackground(Color.PINK);

        JButton toggleButton = new JButton("☰");
        toggleButton.addActionListener(e -> toggleDrawer());

        // menu
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 1));

        JButton viewShips = new JButton("View Ships");
        //TODO: create handler function that creates view ships frame, closes current frame?
        contentPanel.add(viewShips);

        JButton createPerson = new JButton("Create Person");
        //TODO: create handler func create person
        contentPanel.add(createPerson);

        JButton createShip = new JButton("Create Ship");
        //TODO: handler func
        contentPanel.add(createShip);

        JButton comment = new JButton("Leave Comment");
        //TODO: handler func
        contentPanel.add(comment);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(false);

        //not sure if this is correct:
        parentFrame.add(toggleButton, BorderLayout.NORTH);
    }

    public void toggleDrawer() {
        isOpen = !isOpen;
        setVisible(isOpen);
        revalidate();
        repaint();
    }
}
