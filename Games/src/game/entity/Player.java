package game.entity;

import java.awt.event.KeyEvent;

public abstract class Player extends Sprite{

    public static final int KEY_W = KeyEvent.VK_W;
    public static final int KEY_S = KeyEvent.VK_S;
    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int KEY_A = KeyEvent.VK_A;
    public static final int KEY_D = KeyEvent.VK_D;
    public static final int SPEED = 8;
    public static final int KEY_RELEASED_SPEED = 0;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected abstract void move ();
    protected abstract void keyPressed(KeyEvent e);
    protected abstract void keyReleased(KeyEvent e);
}
