package Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CreatePerson extends JFrame implements ActionListener {
    private Container c;
    private JPanel panel;
    private boolean visibility = false;
    private JTextField nameField;
    private JTextField majorField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField imageUpload;
    private JTextField personalDescriptionField;

    public CreatePerson() {
        setTitle("Create Person");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //ensures that the frame actually closes all the way when clicking X
        setBounds(30, 60, 1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null); //centers on screen

        c= getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(223, 190, 239));

        panel = new JPanel();
        panel.setBackground(new Color(236, 152, 152));

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

        genderField = new JTextField();
        genderField.setSize(300, 30);
        genderField.setFont(new Font("Arial", Font.PLAIN, 15));
        genderField.setLocation(350, 280);
        c.add(genderField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setLocation(350, genderField.getY()-35);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        genderLabel.setSize(300, 40);
        c.add(genderLabel);

        imageUpload = new JTextField();
        imageUpload.setSize(300, 30);
        imageUpload.setFont(new Font("Arial", Font.PLAIN, 15));
        imageUpload.setLocation(350, 340);
        c.add(imageUpload);

        JLabel imageUploadLabel = new JLabel("Image Upload:");
        imageUploadLabel.setLocation(350, imageUpload.getY()-35);
        imageUploadLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        imageUploadLabel.setSize(300, 40);
        c.add(imageUploadLabel);

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
            String inputUsername = nameField.getText();
            System.out.println(inputUsername);
            toggleShow();
        }
    }
}
