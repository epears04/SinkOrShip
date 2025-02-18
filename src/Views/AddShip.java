package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddShip extends JFrame implements ActionListener {
    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel shipName;
    private JTextField tShipName;
    private JLabel personA;
    private JTextField tPersonA;
    private JLabel personB;
    private JTextField tPersonB;
    private JButton submit;

    // Constructor, to initialize the components
    // with default values.
    public AddShip() {
        setTitle("Add Ships");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Add a Ship!");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        shipName = new JLabel("Ship Name");
        shipName.setFont(new Font("Arial", Font.PLAIN, 20));
        shipName.setSize(100, 20);
        shipName.setLocation(100, 100);
        c.add(shipName);

        tShipName = new JTextField();
        tShipName.setFont(new Font("Arial", Font.PLAIN, 15));
        tShipName.setSize(300, 30);
        tShipName.setLocation(200, 100);
        c.add(tShipName);

        personA = new JLabel("Person 1");
        personA.setFont(new Font("Arial", Font.PLAIN, 20));
        personA.setSize(100, 20);
        personA.setLocation(100, 150);
        c.add(personA);

        tPersonA = new JTextField();
        tPersonA.setFont(new Font("Arial", Font.PLAIN, 15));
        tPersonA.setSize(300, 30);
        tPersonA.setLocation(200, 150);
        c.add(tPersonA);

        personB = new JLabel("Person 2");
        personB.setFont(new Font("Arial", Font.PLAIN, 20));
        personB.setSize(100, 20);
        personB.setLocation(100, 200);
        c.add(personB);

        tPersonB = new JTextField();
        tPersonB.setFont(new Font("Arial", Font.PLAIN, 15));
        tPersonB.setSize(300, 30);
        tPersonB.setLocation(200, 200);
        c.add(tPersonB);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(150, 300);
        submit.addActionListener(this);
        c.add(submit);

        setVisible(true);
    }

    // Method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
           System.out.println("Submitted");
        }
    }
}