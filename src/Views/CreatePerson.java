package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CreatePerson implements ActionListener {
    private JFrame frame;
    private JTextField nameField;
    private JPanel panel;
    private boolean visibility = false;

    public CreatePerson() {
        frame = new JFrame("Create Person");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //ensures that the frame actually closes all the way when clicking X
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null); //centers on screen
        nameField = new JTextField(20);
        JLabel nameLabel = new JLabel("Username:");
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

//        JTextField ageField = new JTextField();
//        JTextField genderField = new JTextField();
//        JTextField imageUpload = new JTextField();
//        JTextField personalDescriptionField = new JTextField();
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
