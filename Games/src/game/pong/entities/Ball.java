package game.pong.entities;

import game.entity.Sprite;
import game.pong.rungame.GameBoard;

import java.util.Random;

public class Ball extends Sprite {

    private static final int RANDOM_BOUND = 4;
    private static final int MIN_SPEED = 2;
    private int dy;
    private int dx;
    private boolean isLeft;
    private boolean isUp;
    private Random random;


    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        initBallMove();

    }

    public boolean isAtEdge () {
        if(getX() < -10 || getX() > GameBoard.BOARD_WIDTH + 10) {
            return true;
        }
        return false;
    }

    public void resetBall () {
        setX(GameBoard.BALL_INIT_X);
        setY(GameBoard.BALL_INIT_Y);
        initBallMove();
    }


    private void initBallMove() {
        isLeft = random.nextBoolean();
        isUp = random.nextBoolean();
        dx = random.nextInt(RANDOM_BOUND-2) + MIN_SPEED;
        dy = random.nextInt(RANDOM_BOUND-2) + MIN_SPEED;
        if (isLeft) {
            dx *= -1;
        }
        if(isUp) {
            dy *= -1;
        }
    }

    public void move() {

        setX(getX() + dx);
        setY(getY() + dy);

        if(touchingWall()) {
            dy *= -1;
            GameBoard.playSound();
        }
    }

    private boolean touchingWall () {
        if(getY() < 1) {
            isUp = false;
            setY(1);
            return true;
        }
        if(getY() > GameBoard.BOARD_HEIGHT - GameBoard.BALL_INIT_HEIGHT) {
            isUp = true;
            setY(GameBoard.BOARD_HEIGHT - GameBoard.BALL_INIT_HEIGHT);
            return true;
        }
        return false;
    }

    public void bounceOffPlayerOne() {
        if (isUp) {
            dx = random.nextInt(RANDOM_BOUND) + MIN_SPEED;
            dy = (random.nextInt(RANDOM_BOUND) + MIN_SPEED) * -1;
        }else if (!isUp){
            dx = random.nextInt(RANDOM_BOUND) + MIN_SPEED;
            dy = random.nextInt(RANDOM_BOUND + MIN_SPEED);
        }
        isLeft = false;
    }

    public void bounceOffPlayerTwo() {
        if (isUp) {
            dx = (random.nextInt(RANDOM_BOUND) + MIN_SPEED) * -1;
            dy = (random.nextInt(RANDOM_BOUND) + MIN_SPEED) * -1;

        } else if (!isUp) {
            dx = (random.nextInt(RANDOM_BOUND) + MIN_SPEED) * -1;
            dy = random.nextInt(RANDOM_BOUND) + MIN_SPEED;
        }
        isLeft = true;
    }

    public boolean isLeft() {
        return isLeft;
    }
}
