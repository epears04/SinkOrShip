package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CreatePerson implements ActionListener {
    private JFrame frame;
    private JTextField nameField;
    private JPanel panel;
    private boolean visibility = false;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField imageUpload;
    private JTextField personalDescriptionField;

    public CreatePerson() {
        frame = new JFrame("Create Person");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //ensures that the frame actually closes all the way when clicking X
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null); //centers on screen
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        genderField = new JTextField(20);
        imageUpload = new JTextField(20);
        personalDescriptionField = new JTextField(20);
        JLabel nameLabel = new JLabel("Username:");
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);


        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(submitButton);
        frame.add(panel);
    }

    public void toggleShow(){
        visibility = !visibility;
        frame.setVisible(visibility);

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
