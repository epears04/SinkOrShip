package Views;

import Database.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CommentsPage extends JFrame implements ActionListener {
    private Container c;
    private boolean visibility = false;
    private JTextField nameField;
    private JTextField majorField;
    private JTextField ageField;
    private JButton imageUpload;
    private JTextField personalDescriptionField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JRadioButton otherButton;
    private ButtonGroup group;
    private static final Color backgroundColor = new Color(223, 190, 239);
    private JLabel uploaded;
    private File uploadedImage = null;
    private static final int minUserAge = 13;
    private static final int minUsernameLength = 3;
    private static final int maxUsernameLength = 20;
    private JButton searchShip;

    public CommentsPage(String shipName) {
        setTitle("Write Comment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //ensures that the frame actually closes all the way when clicking X
        setBounds(30, 60, 1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null); //centers on screen

        c= getContentPane();
        c.setLayout(null);
        c.setBackground(backgroundColor);

        nameField = new JTextField();
        nameField.setSize(300, 30);
        nameField.setLocation(350, 100);
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        c.add(nameField);

        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setLocation(350, nameField.getY()-35);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setSize(300, 40);
        c.add(nameLabel);

        ageField = new JTextField();
        ageField.setSize(300, 30);
        ageField.setLocation(350, 160);
        ageField.setFont(new Font("Arial", Font.PLAIN, 15));
        c.add(ageField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setLocation(350, ageField.getY()-35);
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        ageLabel.setSize(300, 40);
        c.add(ageLabel);

        majorField = new JTextField();
        majorField.setSize(300, 30);
        majorField.setLocation(350, 220);
        majorField.setFont(new Font("Arial", Font.PLAIN, 15));
        c.add(majorField);

        JLabel majorLabel = new JLabel("Major:");
        majorLabel.setLocation(350, majorField.getY()-35);
        majorLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        majorLabel.setSize(300, 40);
        c.add(majorLabel);

        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");
        maleButton.setSize(90, 30);
        femaleButton.setSize(90, 30);
        otherButton.setSize(90, 30);
        int buttonDistance = 93;
        maleButton.setLocation(360,280);
        femaleButton.setLocation(maleButton.getX()+buttonDistance,280);
        otherButton.setLocation(femaleButton.getX()+buttonDistance,280);
        maleButton.setFont(new Font("Arial", Font.PLAIN, 15));
        femaleButton.setFont(new Font("Arial", Font.PLAIN, 15));
        otherButton.setFont(new Font("Arial", Font.PLAIN, 15));
        maleButton.setBackground(backgroundColor);
        femaleButton.setBackground(backgroundColor);
        otherButton.setBackground(backgroundColor);
        group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(otherButton);
        c.add(maleButton);
        c.add(femaleButton);
        c.add(otherButton);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setLocation(350, maleButton.getY()-35);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        genderLabel.setSize(300, 40);
        c.add(genderLabel);

//        imageUpload = new JTextField();
//        imageUpload.setSize(300, 30);
//        c.add(imageUpload);

        imageUpload = new JButton("Choose Image");
        imageUpload.setSize(100, 30);
        imageUpload.setFont(new Font("Arial", Font.PLAIN, 15));
        imageUpload.setLocation(350, 340);
        imageUpload.addActionListener(this);
        c.add(imageUpload);


        JLabel imageUploadLabel = new JLabel("Image Upload:");
        imageUploadLabel.setLocation(350, imageUpload.getY()-35);
        imageUploadLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        imageUploadLabel.setSize(300, 40);
        c.add(imageUploadLabel);

        uploaded = new JLabel("Done!");
        uploaded.setFont(new Font("Arial", Font.PLAIN, 15));
        uploaded.setForeground(Color.GREEN);
        uploaded.setSize(300, 40);
        uploaded.setLocation(480, 340);
        uploaded.setVisible(false);
        c.add(uploaded);

        personalDescriptionField = new JTextField();
        personalDescriptionField.setSize(300, 30);
        personalDescriptionField.setFont(new Font("Arial", Font.PLAIN, 15));
        personalDescriptionField.setLocation(350, 400);
        c.add(personalDescriptionField);

        JLabel personalDescriptionLabel = new JLabel("Personal Description:");
        personalDescriptionLabel.setLocation(350, personalDescriptionField.getY()-35);
        personalDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        personalDescriptionLabel.setSize(300, 40);
        c.add(personalDescriptionLabel);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setSize(100, 30);
        submitButton.setLocation(450, 450);
        c.add(submitButton);


        searchShip = new JButton("Search");
        searchShip.setFont(new Font("Arial", Font.PLAIN, 12));
        searchShip.setSize(75, 20);
        searchShip.setLocation(100, 100);
        c.add(searchShip);
        searchShip.addActionListener(this);
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
        // used to handle the addition of an image to the profile
        else if (s.equals("Choose Image")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png", "jpg"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) { // 0 if successful
                uploadedImage = fileChooser.getSelectedFile();
                uploaded.setVisible(true);
            }else {
                uploaded.setText("Failed");
                uploaded.setForeground(Color.RED);
                uploaded.setVisible(true);
            }
            JOptionPane.showMessageDialog(null, uploadedImage);

        }
    }




}
