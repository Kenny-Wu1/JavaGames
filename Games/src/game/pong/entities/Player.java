package game.pong.entities;

import game.entity.Sprite;
import game.pong.rungame.GameBoard;

import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_UP = KeyEvent.VK_UP;
    private static final int KEY_DOWN = KeyEvent.VK_DOWN;
    private static final int SPEED = 6;
    private static final int KEY_RELEASED_SPEED = 0;
    private int dy;
    private boolean isSecondPlyr;
    private int score;

    public Player(int x, int y, int width, int height, boolean isSecondPlyr) {
        super(x,y,width,height);
        this.isSecondPlyr = isSecondPlyr;
        score = 0;

    }

    public void move() {
        setY(getY() + dy);

        if (getY() < 1) {
            setY(1);
        }

        if(getY() > GameBoard.BOARD_HEIGHT-GameBoard.PLAYER_HEIGHT) {
            setY(GameBoard.BOARD_HEIGHT-GameBoard.PLAYER_HEIGHT);
        }
    }

    public void resetPlayer () {
        if (isSecondPlyr) {
            setY(GameBoard.PLAYER_TWO_Y);
        } else {
            setY(GameBoard.PLAYER_ONE_Y);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        setDY(e, key, SPEED);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        setDY(e, key, KEY_RELEASED_SPEED);
    }

    private void setDY(KeyEvent e, int key, int setValue) {
        if (isSecondPlyr) {
            switch(key) {
                case KEY_UP:
                    dy = -1 * setValue;
                    break;
                case KEY_DOWN:
                    dy = setValue;
                    break;
            }
        } else {
            switch(key) {
                case KEY_W:
                    dy = -1 * setValue;
                    break;
                case KEY_S:
                    dy = setValue;
                    break;
            }
        }
    }

    public void addScore () {
        score++;
    }

    public int getScore() {
        return score;
    }
}
