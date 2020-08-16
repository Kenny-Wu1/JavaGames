package game.breakout.rungame;

import game.breakout.entities.*;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;

public class BreakoutPanel extends JPanel implements ActionListener {

    private Timer timer;
    private BreakoutPlayer breakoutPlayer;
    private Ball ball;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    public static final int PLAYER_HEIGHT = 7;
    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_INIT_X = 250;
    public static final int PLAYER_INIT_Y = 350;
    public static final int BALL_SIDE_LENGTH = 5;
    public static final int BALL_INIT_X = 275;
    public static final int BALL_INIT_Y = 342;
    public static final int BLOCK_WIDTH = 42;
    public static final int BLOCK_HEIGHT = 10;
    public static final int BLOCK_COL = 11;
    public static final int BLOCK_ROW = 10;
    public static final int STARTING_X_BLOCK = 7;
    public static final int STARTING_Y_BLOCK = 40;
    public static final int BLOCK_X_INTERVAL = 44;
    public static final int BLOCK_Y_INTERVAL = 15;
    public static final int TOTAL_SCORE = BLOCK_COL * BLOCK_ROW;
    private static boolean isSpacedPressed;
    private int[] blockXCoordinates;
    private LinkedList<Block> blocks;
    private boolean isDead;
    private static int score = 0;
    private JButton button;
    private static final File HIT_SOUND = new File("src/game/sounds/BlockHit.aiff");
    private static final File BOUNCE_SOUND = new File("src/game/sounds/Bounce.wav");
    private static Clip hitClip = null;
    private static Clip bounceClip = null;

    public BreakoutPanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this.new KeyListener());
        setFocusable(true);
        setBackground(Color.BLACK);
        setLayout(null);

        initializeBounceSound();
        initializeHitSound();

        blocks = new LinkedList<>();

        breakoutPlayer = new BreakoutPlayer(PLAYER_INIT_X, PLAYER_INIT_Y,
                PLAYER_WIDTH, PLAYER_HEIGHT);

        ball = new Ball(BALL_INIT_X, BALL_INIT_Y,
                BALL_SIDE_LENGTH, BALL_SIDE_LENGTH);

        button = new JButton("Click here to Reset");
        button.setBackground(Color.GRAY);
        button.setForeground(Color.BLACK);
        button.setBounds(190, 200, 120,20);
        button.setFont(new Font("TimesRoman", Font.PLAIN,12));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(button);
        button.setVisible(false);

        blockXCoordinates = new int[BLOCK_COL];
        initXCoordinates();
        initBlocks();

        isSpacedPressed = false;
        isDead = false;

        timer = new Timer(15, this);
        timer.start();
    }

    private static void initializeHitSound() {
        try {
            hitClip = AudioSystem.getClip();
            hitClip.open(AudioSystem.getAudioInputStream(HIT_SOUND));
        } catch (Exception e) {
            System.out.println("Could not get audio");
        }
    }

    private static void initializeBounceSound() {
        try {
            bounceClip = AudioSystem.getClip();
            bounceClip.open(AudioSystem.getAudioInputStream(BOUNCE_SOUND));
        } catch (Exception e) {
            System.out.println("Could not get audio");
        }
    }

    public static void playHitSound() {
        initializeHitSound();
        hitClip.start();
    }

    public static void playBounceSound () {
        initializeBounceSound();
        bounceClip.start();
    }

    public void resetGame() {
        button.setVisible(false);
        isSpacedPressed = false;
        ball.setisStart(false);
        resetBlocks();
        breakoutPlayer.resetPlayer(PLAYER_INIT_X, PLAYER_INIT_Y);
        isDead = false;
        score= 0;
    }

    private void resetBlocks() {
        for (Block block: blocks) {
            block.resetBlock();
        }
    }

    public static void addScore() {
        score++;
    }

    public void isDead() {
        if(breakoutPlayer.checkLives() || score >= TOTAL_SCORE) {
            isDead = true;
        }
    }

    public static void resetBall() {
        isSpacedPressed = false;
        BreakoutPlayer.loseLife();
    }

    public void initXCoordinates() {
        int currentNumber = STARTING_X_BLOCK;
        for (int i = 0; i < blockXCoordinates.length; i++) {
            blockXCoordinates[i] = currentNumber;
            currentNumber += BLOCK_X_INTERVAL;
        }
    }

    public void collideWithPlayer() {
        if(breakoutPlayer.getBounds().intersects(ball.getBounds())){
            playBounceSound();
            ball.launchBall();
        }
    }

    private void followPlayer() {
        if(!isSpacedPressed) {
            ball.setY(BALL_INIT_Y);
            ball.setX(breakoutPlayer.getX()+PLAYER_WIDTH/2);
        }
    }

    private void checkBlockCollsion() {
        for(int i = 0; i < blocks.size(); i++) {
            blocks.get(i).checkBallCollision(ball);
        }
    }

    private void drawBlocks(Graphics2D gg) {
        for(int i = 0; i < blocks.size(); i++) {
            switch(i) {
                case 0:
                    gg.setColor(Color.RED);
                    break;
                case BLOCK_COL*2:
                    gg.setColor(Color.BLUE);
                    break;
                case BLOCK_COL*4:
                    gg.setColor(Color.YELLOW);
                    break;
                case BLOCK_COL*6:
                    gg.setColor(Color.ORANGE);
                    break;
                case BLOCK_COL*8:
                    gg.setColor(Color.CYAN);
                    break;
            }
            if(blocks.get(i).isActive()) {
                gg.fill(blocks.get(i).getBounds());
            }
        }
    }

    private void initBlocks() {
        int currentY = STARTING_Y_BLOCK;
        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COL; j++) {
                blocks.add(new Block(blockXCoordinates[j], currentY,
                        BLOCK_WIDTH, BLOCK_HEIGHT));
            }
            currentY += BLOCK_Y_INTERVAL;
        }

    }

    private void onDeath(Graphics2D gg) {
        if(isDead) {
            gg.fill(new Rectangle(170, 120, 170, 30));
            gg.setColor(Color.BLACK);
            gg.setFont(new Font("Arial", Font.PLAIN, 12));
            gg.drawString(String.format("               " +
                    "You scored: %d  ", score), 170,140);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(Color.WHITE);
        gg.fill(breakoutPlayer.getBounds());
        gg.fill(ball.getBounds());
        drawBlocks(gg);
        gg.setColor(Color.WHITE);
        gg.setFont(new Font("Arial", Font.PLAIN,20));
        gg.drawString(String.format("Lives: %d", BreakoutPlayer.getLives()), 20, 20);
        gg.drawString(String.format("Score: %d", score), 400, 20);
        onDeath(gg);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isDead) {
            breakoutPlayer.move();
            followPlayer();
            ball.move();
            checkBlockCollsion();
            if(isSpacedPressed)
                collideWithPlayer();
            isDead();
        }else{
            button.setVisible(true);
        }
        repaint();
    }


    private class KeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            breakoutPlayer.keyPressed(e);
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (!isSpacedPressed) {
                    isSpacedPressed = true;
                    ball.launchBall();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            breakoutPlayer.keyReleased(e);
        }

    }
 }


