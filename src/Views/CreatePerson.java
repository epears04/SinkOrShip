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

        panel = new JPanel();
        panel.setBackground(new Color(236, 152, 152));

        nameField = new JTextField(20);
        ageField = new JTextField(20);
        majorField = new JTextField(20);
        genderField = new JTextField(20);
        imageUpload = new JTextField(20);
        personalDescriptionField = new JTextField(20);
        Dimension textFieldSize = new Dimension(200, 20);
        nameField.setMaximumSize(textFieldSize);
        ageField.setMaximumSize(textFieldSize);
        majorField.setMaximumSize(textFieldSize);
        genderField.setMaximumSize(textFieldSize);
        imageUpload.setMaximumSize(textFieldSize);
        personalDescriptionField.setMaximumSize(textFieldSize);

        JLabel nameLabel = new JLabel("Username:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel majorLabel = new JLabel("Major:");
        JLabel genderLabel = new JLabel("Gender:");
        JLabel imageUploadLabel = new JLabel("Image Upload:");
        JLabel personalDescriptionLabel = new JLabel("Personal Description:");
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);


        c.add(nameLabel);
        c.add(nameField);
        c.add(ageLabel);
        c.add(ageField);
        c.add(majorLabel);
        c.add(majorField);
        c.add(genderLabel);
        c.add(genderField);
        c.add(imageUploadLabel);
        c.add(imageUpload);
        c.add(personalDescriptionLabel);
        c.add(personalDescriptionField);
        c.add(submitButton);
        c.add(panel);
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
