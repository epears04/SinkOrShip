package Views;

import Database.Connect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddShip extends JPanel implements ActionListener {
    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel shipName;
    private JTextField tShipName;
    private JLabel personA;
    private JTextField tPersonA;
    private JButton searchPersonA;
    private JLabel personAImage;
    private JLabel personB;
    private JTextField tPersonB;
    private JButton searchPersonB;
    private JLabel personBImage;
    private JButton submit;

    // Constructor, to initialize the components
    // with default values.
    public AddShip() {
        setBounds(300, 90, 900, 600);

        c = this;
        c.setLayout(new FlowLayout());

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

        searchPersonA = new JButton("Search");
        searchPersonA.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPersonA.setSize(75, 20);
        searchPersonA.setLocation(530, 155);
        c.add(searchPersonA);
        searchPersonA.addActionListener(this);

        personAImage = new JLabel();
        personAImage.setSize(150, 150);
        personAImage.setLocation(520, 300);
        c.add(personAImage);
        personAImage.setVisible(false);

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

        searchPersonB = new JButton("Search");
        searchPersonB.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPersonB.setSize(75, 20);
        searchPersonB.setLocation(530, 205);
        c.add(searchPersonB);
        searchPersonB.addActionListener(this);

        personBImage = new JLabel();
        personBImage.setSize(150, 150);
        personBImage.setLocation(350, 300);
        c.add(personBImage);
        personBImage.setVisible(false);

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
           JOptionPane.showMessageDialog(this, "Submitted");
        }
        else if (e.getSource() == searchPersonA || e.getSource() == searchPersonB) {
            searchPersonFetchImage(e);
        }
    }

    private Connection getConnection(){
        try {
            return Connect.createConnection();
        }
        catch (SQLException e) {
            System.out.println("Connection Error");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void searchPersonFetchImage(ActionEvent e ){
        Connection connect = getConnection();
        try {
            Statement statement = connect.createStatement();
            String nameQuery;
            if(e.getSource() == searchPersonA) {
                nameQuery= "SELECT username, profile_pic from People where username = \"" + tPersonA.getText() + "\";";
            }else{
                nameQuery= "SELECT username, profile_pic from People where username = \"" + tPersonB.getText() + "\";";
            }
            ResultSet rs = statement.executeQuery(nameQuery);

            InputStream input;
            FileOutputStream output;
            File theFile = new File("images/heartPixel.png");
            output = new FileOutputStream(theFile);
            if (rs.next()) {
                //TODO: send user message that the person was found in the database (variable based on the desired search)
                //TODO: Print out the person's profile_photo (if available)
                input = rs.getBinaryStream("profile_pic");
                byte buffer[] = new byte[1024];
                while(input.read(buffer)>0){
                    output.write(buffer);
                }
                String path = theFile.getAbsolutePath();
                ImageIcon image = new ImageIcon(path);
                Image img = image.getImage();
                Image newImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon newImageIcon = new ImageIcon(newImage);
                if (e.getSource() == searchPersonA) {
                    personAImage.setIcon(newImageIcon);
                    personA.setVisible(true);
                }else{
                    personBImage.setIcon(newImageIcon);
                    personB.setVisible(true);
                }
                System.out.println("success");
            }else {
                //TODO: send message that the person WAS NOT found
                JOptionPane.showMessageDialog(this, "No such person");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


}