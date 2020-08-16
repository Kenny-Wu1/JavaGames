package game.breakout.rungame;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public Game(String title) {
        setTitle(title);
        setResizable(true);
        setPreferredSize(new Dimension(500,400));
        add(new BreakoutPanel());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game("Breakout");
                game.setVisible(true);
            }
        });
    }
}
