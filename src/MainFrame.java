import Components.DrawerPanel;
import Views.AddShip;
import Views.CreatePerson;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DrawerPanel drawerPanel;

    public MainFrame(){
        setTitle("SinkorShip");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize CardLayout and Main Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different pages
        //mainPanel.add(new ViewShips(), "View");
        mainPanel.add(new AddShip(), "Add");
        mainPanel.add(new CreatePerson(), "Person");

        // Show View Ships by default
        //cardLayout.show(mainPanel, "View");

        // Add drawer menu
        drawerPanel = new DrawerPanel(this);
        add(drawerPanel, BorderLayout.WEST);

        // Toggle button (outside drawer)
        JButton toggleButton = new JButton("☰");
        toggleButton.addActionListener(e -> drawerPanel.toggleDrawer());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(toggleButton);
        add(topPanel, BorderLayout.NORTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Method to switch pages
    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
