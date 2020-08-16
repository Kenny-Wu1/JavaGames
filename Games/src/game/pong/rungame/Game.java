package game.pong.rungame;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameSpace gameSpace = new GameSpace("Pong");
                gameSpace.setVisible(true);
            }
        });
    }
}
