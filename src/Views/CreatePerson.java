package Views;

import Database.Connect;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;

public class CreatePerson extends JPanel implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(223, 190, 239);
    private static final int MIN_USER_AGE = 13;
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;

    private JTextField nameField, majorField, ageField;
    private JTextArea personalDescriptionField;
    private JRadioButton maleButton, femaleButton, otherButton;
    private ButtonGroup genderGroup;
    private JLabel uploadedLabel;
    private File uploadedImage = null;
    private boolean visibility = false;

    public CreatePerson() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = createLabel("Create a Person!", 24, true);
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        gbc.gridy++;

        // Fields & Labels
        gbc.gridwidth = 1;
        addFieldWithLabel("Username:", gbc, nameField = new JTextField(20));
        addFieldWithLabel("Age:", gbc, ageField = new JTextField(20));
        addFieldWithLabel("Major:", gbc, majorField = new JTextField(20));

        // Gender Selection
        gbc.gridy++;
        gbc.gridx = 0;
        add(createLabel("Gender:"), gbc);
        gbc.gridx = 1;
        add(createGenderPanel(), gbc);

        // Image Upload
        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel("Image Upload:"), gbc);
        gbc.gridx = 1;
        JButton imageUploadButton = new JButton("Choose Image");
        imageUploadButton.addActionListener(this);
        add(imageUploadButton, gbc);

        uploadedLabel = new JLabel("Done!");
        uploadedLabel.setForeground(Color.GREEN);
        uploadedLabel.setVisible(false);
        gbc.gridx = 2;
        add(uploadedLabel, gbc);

        // Personal Description
        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel("Personal Description:"), gbc);
        gbc.gridx = 1;
        personalDescriptionField = new JTextArea(5, 20);
        personalDescriptionField.setLineWrap(true);
        personalDescriptionField.setWrapStyleWord(true);
        add(new JScrollPane(personalDescriptionField), gbc);

        // Submit Button
        gbc.gridy++;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton, gbc);
    }

    private JLabel createLabel(String text) {
        return new JLabel(text);
    }

    private JLabel createLabel(String text, int size, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, size));
        return label;
    }

    private void addFieldWithLabel(String labelText, GridBagConstraints gbc, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel(labelText), gbc);
        gbc.gridx = 1;
        add(textField, gbc);
    }

    private JPanel createGenderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);
        panel.add(maleButton);
        panel.add(femaleButton);
        panel.add(otherButton);
        maleButton.setBackground(BACKGROUND_COLOR);
        femaleButton.setBackground(BACKGROUND_COLOR);
        otherButton.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Submit".equals(command)) {
            handleFormSubmission();
        } else if ("Choose Image".equals(command)) {
            handleImageUpload();
        }
    }

    private void handleFormSubmission() {
        try {
            int age = Integer.parseInt(ageField.getText());
            if (age < MIN_USER_AGE) throw new Exception("User must be at least " + MIN_USER_AGE + " years old");
            String username = nameField.getText();
            if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
                throw new Exception("Username must be between " + MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + " characters");

            Connection connect = Connect.createConnection();
            String sqlInsert = getSQLInsert(username, age);
            if (uploadedImage == null) {
                connect.createStatement().executeUpdate(sqlInsert);
            } else {
                attachImageAndSend(sqlInsert, connect);
            }

            clearForm();
            JOptionPane.showMessageDialog(this, "Submitted successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleImageUpload() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG, JPG Images", "png", "jpg"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            uploadedImage = fileChooser.getSelectedFile();
            uploadedLabel.setVisible(true);
        }
    }

    private String getSQLInsert(String username, int age) {
        String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : otherButton.isSelected() ? "Other" : "None";
        return String.format("INSERT INTO People VALUES ('%s', %d, '%s', '%s', %s, '%s');",
                username, age, majorField.getText(), gender, (uploadedImage == null ? "null" : "?"), personalDescriptionField.getText());
    }

    private void attachImageAndSend(String sql, Connection connect) {
        try (PreparedStatement pst = connect.prepareStatement(sql); FileInputStream fis = new FileInputStream(uploadedImage)) {
            pst.setBinaryStream(1, fis);
            if (JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (pst.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(this, "Person added successfully!");
                } else {
                    throw new SQLException("Failed to add person.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error uploading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        majorField.setText("");
        personalDescriptionField.setText("");
        genderGroup.clearSelection();
        uploadedLabel.setVisible(false);
        uploadedImage = null;
    }
}
