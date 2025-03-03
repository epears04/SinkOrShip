package Views;

import Database.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreatePerson extends JPanel implements ActionListener {
    private Container c;
    private boolean visibility = false;
    private JTextField nameField;
    private JTextField majorField;
    private JTextField ageField;
    private JButton imageUpload;
    private JTextArea personalDescriptionField;
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

    public CreatePerson() {
        setLayout(new GridBagLayout()); // Use GridBagLayout
        setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Small padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expand components horizontally
        gbc.ipadx = 0; // No extra width
        gbc.weightx = 0.1; // Keep labels small
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Create title label
        JLabel titleLabel = new JLabel("Create a Person!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Large, bold font
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center text
        gbc.gridwidth = 2; // Span across both columns
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.weighty = 0.01;
        gbc.anchor = GridBagConstraints.CENTER; // Center in the grid
        add(titleLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        // Name Label
        JLabel nameLabel = new JLabel("Username:");
        add(nameLabel, gbc);

        // Name Field
        gbc.gridx = 1;
        nameField = new JTextField(20); // Set size via columns
        add(nameField, gbc);

        // Age Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel ageLabel = new JLabel("Age:");
        add(ageLabel, gbc);

        // Age Field
        gbc.gridx = 1;
        ageField = new JTextField(20);
        add(ageField, gbc);

        // Major Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel majorLabel = new JLabel("Major:");
        add(majorLabel, gbc);

        // Major Field
        gbc.gridx = 1;
        majorField = new JTextField(20);
        add(majorField, gbc);

        // Gender Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel genderLabel = new JLabel("Gender:");
        add(genderLabel, gbc);

        // Gender Buttons
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel();
        genderPanel.setBackground(backgroundColor);
        maleButton = new JRadioButton("Male");
        maleButton.setBackground(backgroundColor);
        femaleButton = new JRadioButton("Female");
        femaleButton.setBackground(backgroundColor);
        otherButton = new JRadioButton("Other");
        otherButton.setBackground(backgroundColor);
        group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(otherButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);
        add(genderPanel, gbc);

        // Image Upload Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel imageUploadLabel = new JLabel("Image Upload:");
        add(imageUploadLabel, gbc);

        // Image Upload Button
        gbc.gridx = 1;
        imageUpload = new JButton("Choose Image");
        imageUpload.addActionListener(this);
        add(imageUpload, gbc);

        // Uploaded Label (Hidden initially)
        gbc.gridx = 2;
        uploaded = new JLabel("Done!");
        uploaded.setForeground(Color.GREEN);
        uploaded.setVisible(false);
        add(uploaded, gbc);

        // Personal Description Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel personalDescriptionLabel = new JLabel("Personal Description:");
        add(personalDescriptionLabel, gbc);

        // Personal Description Field
        gbc.gridx = 1;
        personalDescriptionField = new JTextArea(5, 20); // 5 rows, 20 columns
        personalDescriptionField.setLineWrap(true);  // Enable text wrapping
        personalDescriptionField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(personalDescriptionField);
        scrollPane.setPreferredSize(new Dimension(300, 100)); // Set width & height
        add(scrollPane, gbc);

        // Submit Button
        gbc.gridx = 1;
        gbc.gridy++;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton, gbc);
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
                if (age <minUserAge)
                    throw new Exception("User must be at least " + minUserAge + " years  of age");
                String username = nameField.getText();
                if(username.length() < minUsernameLength|| username.length()>maxUsernameLength)
                    throw new Exception("Username must be between " + minUsernameLength + " and " + maxUsernameLength + " characters");

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
                        return; // something failed so don't move forward to the home page
                    }
                }
                // Clear text fields
                nameField.setText("");
                ageField.setText("");
                majorField.setText("");
                personalDescriptionField.setText("");
                // Clear radio button selection
                group.clearSelection();
                // Hide "Uploaded" label (if needed)
                uploaded.setVisible(false);
                JOptionPane.showMessageDialog(this, "Submitted");
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
