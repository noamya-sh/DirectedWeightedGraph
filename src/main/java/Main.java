import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

public class Main {
    static final int WIDTH = 2000;
    static final int HEIGHT = (int) (WIDTH / 1.6);

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // mainFrame.add(new DrawPanel());

        PaintPanel paintPanel = new PaintPanel(Main.WIDTH,(Main.HEIGHT * 3) / 4);
        paintPanel.setPreferredSize(new Dimension(Main.WIDTH, (Main.HEIGHT * 3) / 4));
        paintPanel.setBackground(Color.BLACK);
        mainFrame.add(paintPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Noamya's Paint Program", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 32));
        title.setPreferredSize(new Dimension(Main.WIDTH / 2, 100));
        mainFrame.add(title, BorderLayout.PAGE_START);

        JButton creditsBtn = new JButton("Credits");
        mainFrame.add(creditsBtn, BorderLayout.PAGE_END);

        JDialog creditsDialog = new JDialog(mainFrame);
        creditsDialog.setSize(200, 200);
        JLabel creditsTxt = new JLabel(
                "<html><p>those are the credits if you want to see more visit my github account</p></html>");
        creditsDialog.add(creditsTxt, BorderLayout.PAGE_START);
        creditsBtn.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                creditsDialog.setVisible(true);
            }
        });

        mainFrame.setVisible(true);

    }
}