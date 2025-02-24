package Views;

import Database.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreatePerson extends JPanel implements ActionListener {
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
    private static Color backgroundColor = new Color(223, 190, 239);
    private JLabel uploaded;
    private File uploadedImage = null;

    public CreatePerson() {
        setBounds(30, 60, 1000, 1000);


        c= this;
        c.setLayout(new FlowLayout());
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

    }

    public void toggleShow(){
        visibility = !visibility;
        setVisible(visibility);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Submit")) {
            try{
                // Handling all constraints for people form
                int age = Integer.parseInt(ageField.getText());
                if (age <13)
                    throw new Exception("User must be at least 13 years of age");
                String username = nameField.getText();
                if(username.length() < 3)
                    throw new Exception("Username must be at least 8 characters");

                // since the inputs fit the integrity constraints, we can make the sql insert
                Connection connect = Connect.createConnection();
                Statement statement = connect.createStatement();
                String sqlInsert = getSQLInsert(username, age);
                int rowsAffected;
                if (uploadedImage == null) {
                    rowsAffected = statement.executeUpdate(sqlInsert);
                }
                else{
                    if(attachImageAndSend(sqlInsert, connect) == 0){
                        return; // something failed so dont move forward to the home page
                    }
                }

            }catch (Exception ex){

                //can use "JOptionPane" to help display error messages and breaking of integrity constraints
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return;
            }



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

    private String getSQLInsert(String username, int age) {
        String sqlInsert;
        if (uploadedImage == null){
             sqlInsert= "INSERT INTO People VALUES (\"%s\", %d, \"%s\", %s, null, \"%s\");";
        }else{
            sqlInsert = "INSERT INTO People VALUES (\"%s\", %d, \"%s\", %s, ?, \"%s\");";
        }

        // handles the gender related radio buttons
        String gender;
        if(maleButton.isSelected()){
            gender = "\"male\"";
        }
        else if(femaleButton.isSelected()){
            gender = "\"female\"";
        }
        else if(otherButton.isSelected()){
            gender = "\"other\"";
        }else{
            gender = "\"none\"";
        }
        sqlInsert = String.format(sqlInsert, username, age, majorField.getText(), gender, personalDescriptionField.getText());
        return sqlInsert;
    }

    private int attachImageAndSend(String preparedSql, Connection connect) {
//        String selectedFileName = uploadedImage.getName();
        String imagePath = uploadedImage.getAbsolutePath();
//        ImageIcon imageIcon = new ImageIcon(imagePath);

//        Image img = imageIcon.getImage();
//        Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        //JLabel.setIcon(new Image(scaledImage); -> just needs an actual JLabel to work in theory
        //now to actually store in database
        try{
            FileInputStream fis = new FileInputStream(imagePath);
            PreparedStatement pst = connect.prepareStatement(preparedSql);
            pst.setBinaryStream(1, fis);
            System.out.println(pst);
            int response = JOptionPane.showConfirmDialog(this, "Are u sure", "Confirm...", JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION){//==0
                int record = pst.executeUpdate();
                if(record == 1){
                    JOptionPane.showMessageDialog(this, "You have successfully added person");
                    return record;
                }else {
                    throw new SQLException();
                }
            }else if (response == JOptionPane.NO_OPTION){
                System.out.println("cancelled by user");
                return 0;
            }

        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
        return 0;
    }
}
