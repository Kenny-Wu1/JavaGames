package game.pong.rungame;

import game.pong.entities.Ball;
import game.pong.entities.Player;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class GameBoard extends JPanel implements ActionListener{

    private Timer timer;
    private Player playerOne;
    private Player playerTwo;
    private Ball ball;
    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_UP = KeyEvent.VK_UP;
    private static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int PLAYER_WIDTH = 8;
    public static final int PLAYER_HEIGHT = 40;
    public static final int BALL_INIT_WIDTH = 5;
    public static final int BALL_INIT_HEIGHT = 5;
    public static final int BALL_INIT_X = 200;
    public static final int BALL_INIT_Y = 125;
    public static final int PLAYER_ONE_X = 30;
    public static final int PLAYER_ONE_Y = 125;
    public static final int PLAYER_TWO_X = 370;
    public static final int PLAYER_TWO_Y = 125;
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 300;
    private static final int DELAY = 15;
    private static final File sound = new File("src/game/sounds/Bounce.wav");
    private boolean isBallEdge;

    public GameBoard () {
        addKeyListener(new KeyReaction());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        ball = new Ball (BALL_INIT_X, BALL_INIT_Y, BALL_INIT_WIDTH,
                BALL_INIT_HEIGHT);

        playerOne = new Player(PLAYER_ONE_X, PLAYER_ONE_Y, PLAYER_WIDTH,
                PLAYER_HEIGHT, false);
        playerTwo = new Player(PLAYER_TWO_X, PLAYER_TWO_Y, PLAYER_WIDTH,
                PLAYER_HEIGHT, true);

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
        } catch (Exception e) {
            System.out.println("Could not get audio");
        }

        timer = new Timer(DELAY,this);
        timer.start();
    }

    public static void playSound () {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (Exception e) {
            System.out.println("Could not get audio");
        }
    }

    private void delay() {
        if (isBallEdge) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException s) {
                System.out.println("delay not accounted");
            }
        }
    }

    private void checkCollisions() {
        if(playerOne.getBounds().intersects(ball.getBounds())) {
            ball.bounceOffPlayerOne();
            playSound();
        } else if(playerTwo.getBounds().intersects(ball.getBounds())) {
            ball.bounceOffPlayerTwo();
            playSound();
        }
    }

    private void updateScore() {
        if (ball.isLeft()) {
            playerTwo.addScore();
        }else {
            playerOne.addScore();
        }
    }

    private boolean checkBallEdge () {
        if (ball.isAtEdge()) {
            updateScore();
            ball.resetBall();
            playerOne.resetPlayer();
            playerTwo.resetPlayer();
            return true;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(Color.WHITE);
        gg.fill(ball.getBounds());
        gg.fill(playerOne.getBounds());
        gg.fill(playerTwo.getBounds());
        gg.setColor(Color.GRAY);
        gg.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        gg.drawString(Integer.toString(playerOne.getScore()), 5, 285);
        gg.drawString(Integer.toString(playerTwo.getScore()), 380, 285);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ball.move();
        playerOne.move();
        playerTwo.move();
        checkCollisions();
        isBallEdge = checkBallEdge();
        repaint();
        delay();
    }

    private class KeyReaction extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KEY_W || e.getKeyCode() == KEY_S) {
                playerOne.keyPressed(e);
            }else if(e.getKeyCode() == KEY_UP || e.getKeyCode() == KEY_DOWN) {
                playerTwo.keyPressed(e);
            }
         }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KEY_W || e.getKeyCode() == KEY_S) {
                playerOne.keyReleased(e);
            }else if(e.getKeyCode() == KEY_UP || e.getKeyCode() == KEY_DOWN) {
                playerTwo.keyReleased(e);
            }
        }
    }
}
