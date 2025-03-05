package Views;

import MainPack.MainFrame;
import javax.swing.*;
import java.awt.*;

public class ViewPeople extends JPanel {

    private JPanel panel;
    private static Color backgroundColor = new Color(223, 190, 239);
    private static MainFrame mainFrame;

    public ViewPeople() {
        panel = new JPanel();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 20));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
