package game.breakout.entities;

import game.entity.Sprite;
import game.breakout.rungame.BreakoutPanel;

import java.awt.*;

public class Block extends Sprite {

    private boolean isActive;
    private Rectangle leftSide;
    private Rectangle rightSide;
    private Rectangle midSide;
    private Rectangle topSide;
    private Rectangle botSide;
    public static final int LEFT_RIGHT_WIDTH_MOD = 1;
    public static final int MID_WIDTH_MOD = LEFT_RIGHT_WIDTH_MOD*2;
    public final int sideHeight;

    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        sideHeight = height - 2;
        leftSide = new Rectangle(x, y+1, LEFT_RIGHT_WIDTH_MOD, sideHeight);
        midSide = new Rectangle(x+LEFT_RIGHT_WIDTH_MOD, y,
                width-MID_WIDTH_MOD, height);
        rightSide = new Rectangle(x+width-LEFT_RIGHT_WIDTH_MOD, y+1,
                LEFT_RIGHT_WIDTH_MOD, sideHeight);
        isActive = true;
    }

    public void checkBallCollision(Ball ball) {
        if(isActive) {
            if (ball.getBounds().intersects(leftSide)) {
                ball.hitLeftSide();
                isActive = false;
                BreakoutPanel.addScore();
            } else if (ball.getBounds().intersects(rightSide)) {
                ball.hitRightSide();
                isActive = false;
                BreakoutPanel.addScore();
            } else if (ball.getBounds().intersects(midSide)) {
                ball.hitMidSide();
                isActive = false;
                BreakoutPanel.addScore();
            }
        }
    }

    public void resetBlock() {
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public Rectangle getLeftSide() {
        return leftSide;
    }

    public Rectangle getRightSide() {
        return rightSide;
    }

    public Rectangle getMidSide() {
        return midSide;
    }

}
