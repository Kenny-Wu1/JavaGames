package game.breakout.entities;

import game.entity.Player;
import game.breakout.rungame.BreakoutPanel;

import java.awt.event.KeyEvent;

public class BreakoutPlayer extends Player {

    private int dx;
    public static final int INIT_LIVES = 3;
    private static int lives = INIT_LIVES;

    public BreakoutPlayer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public static int getLives() {
        return lives;
    }

    public boolean checkLives(){
       return lives <= 0;
    }

    public static void loseLife() {
        lives--;
    }

    public void resetPlayer(int x, int y) {
        setX(x);
        setY(y);
        lives = INIT_LIVES;
    }

    @Override
    public void move() {
        setX(getX() + dx);
        if(getX() < 0) {
            setX(1);
        }
        if(getX() > BreakoutPanel.WIDTH-BreakoutPanel.PLAYER_WIDTH) {
            setX(BreakoutPanel.WIDTH-BreakoutPanel.PLAYER_WIDTH);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KEY_A:
                dx = SPEED*-1;
                break;
            case KEY_D:
                dx = SPEED;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KEY_A || e.getKeyCode() == KEY_D) {
            dx = KEY_RELEASED_SPEED;
        }
    }


}
