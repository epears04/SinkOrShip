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
    private ViewShips viewShips;
    private ViewPeople viewPeople;
    private AddShip addShip;
    private CreatePerson createPerson;

    public MainFrame(){
        setTitle("SinkorShip");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize CardLayout and Main Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different pages
        viewShips = new ViewShips(this);
        mainPanel.add(viewShips, "View");
        viewPeople = new ViewPeople(this);
        mainPanel.add(viewPeople, "ViewPeople");
        addShip = new AddShip();
        mainPanel.add(addShip, "Add");
        createPerson = new CreatePerson();
        mainPanel.add(createPerson, "Person");

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
        if (pageName.equals("View")) {
            mainPanel.remove(viewShips);
            viewShips = new ViewShips(this);
            mainPanel.add(viewShips, pageName);
        } else if (pageName.equals("ViewPeople")) {
            mainPanel.remove(viewPeople);
            viewPeople = new ViewPeople(this);
            mainPanel.add(viewPeople, pageName);
        }
        else if (pageName.equals("Add")){
            mainPanel.remove(addShip);
            addShip = new AddShip();
            mainPanel.add(addShip, pageName);
        }
        revalidate();                 // Refresh layout
        repaint();                    // Redraw UI
        cardLayout.show(mainPanel, pageName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
