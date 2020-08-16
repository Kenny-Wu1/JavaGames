package game.pong.rungame;

import javax.swing.*;

public class GameSpace extends JFrame{
    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 450;
    private static final int MAX_WIDTH = 1280;
    private static final int MAX_HEIGHT = 720;

    public GameSpace(String title) {
        setTitle(title);
        setResizable(false);
        add(new GameBoard());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
