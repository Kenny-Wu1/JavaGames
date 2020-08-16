package game.breakout.entities;

import game.entity.Sprite;
import game.breakout.rungame.BreakoutPanel;

import java.util.Random;

public class Ball extends Sprite {

    private int dx;
    private int dy;
    private boolean isLeft;
    private boolean isUp;
    private boolean isStart;
    private Random random;
    public static final int MIN_SPEED = 3;
    public static final int RANDOM_SPEED = 2;
    public static final int LEFT_WALL = 1;
    public static final int TOP_WALL = 1;
    public static final int BOT_WALL = BreakoutPanel.HEIGHT;
    public final int rightWall;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        rightWall = BreakoutPanel.WIDTH - width;
        isStart = false;
        dx = 0;
        dy = 0;
    }

    public void move () {
        setX(getX() + dx);
        setY(getY() + dy);

        touchingWall();
    }


    private void touchingWall() {
        if(getY() < TOP_WALL) {
            dy *= -1;
            isUp = false;
            BreakoutPanel.playBounceSound();
        }else if (getX() > rightWall){
            dx *= -1;
            isLeft = true;
            BreakoutPanel.playBounceSound();
        }else if (getX() < LEFT_WALL) {
            dx *= -1;
            isLeft = false;
            BreakoutPanel.playBounceSound();
        }else if(getY() > BOT_WALL) {
            BreakoutPanel.resetBall();
            isStart = false;
        }
    }

    public void launchBall() {
        if(!isStart) {
            isLeft = random.nextBoolean();
        }
        isUp = true;
        dx = random.nextInt(RANDOM_SPEED) + MIN_SPEED;
        dy = (random.nextInt(RANDOM_SPEED) + MIN_SPEED) * -1;
        if(isLeft) {
            dx *= -1;
        }
        isStart = true;
    }

    public void hitLeftSide(){
        if(isLeft) {
            hitMidSide();
        }else {
            isLeft = true;
            dx *= -1;
            BreakoutPanel.playHitSound();
        }
    }

    public void hitRightSide(){
        if(!isLeft) {
            hitMidSide();
        }else {
            isLeft = false;
            dx *= -1;
            BreakoutPanel.playHitSound();
        }
    }

    public void hitMidSide(){
        isUp = !isUp;
        dy *= -1;
        BreakoutPanel.playHitSound();
    }

    public void setisStart(boolean isStart) {
        this.isStart = isStart;
    }


}
