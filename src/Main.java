import Views.AddShip;
import javax.swing.*;

public class Main {
    public static final AddShip ADD_SHIP = new AddShip();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ADD_SHIP.setVisible(true);
            }
        });
    }
}