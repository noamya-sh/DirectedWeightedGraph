import javax.swing.*;
import java.awt.*;

public class GUI {
    public static final int WIDTH = 1000,HEIGHT=1000;

    public static void main(String[] args) {
        JFrame jf = new JFrame("one");
        jf.setSize(WIDTH,HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setBackground(Color.BLACK);
        jf.setVisible(true);
    }


}
