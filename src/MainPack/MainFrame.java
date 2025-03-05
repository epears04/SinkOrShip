package MainPack;

import Components.DrawerPanel;
import Views.AddShip;
import Views.CreatePerson;
import Views.ViewShips;
import Views.ViewPeople;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DrawerPanel drawerPanel;

    public MainFrame(){
        setTitle("SinkorShip");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize CardLayout and Main Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different pages
        mainPanel.add(new ViewShips(this), "View");
        mainPanel.add(new AddShip(), "Add");
        mainPanel.add(new CreatePerson(), "Person");
        mainPanel.add(new ViewPeople(), "People");
//        mainPanel.add(new CommentsPage("Sobear"), "Comments");

        // Show View Ships by default
        cardLayout.show(mainPanel, "View");

        // Add drawer menu
        drawerPanel = new DrawerPanel(this);
        add(drawerPanel, BorderLayout.WEST);

        // Toggle button (outside drawer)
        JButton toggleButton = new JButton("MENU");
        toggleButton.setBackground(Color.PINK);
        toggleButton.addActionListener(e -> drawerPanel.toggleDrawer());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.white);
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
