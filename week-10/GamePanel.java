import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {

    // Week 2: Player / Catcher
    private int playerX = 200;
    private final int playerY = 520;
    private final int playerWidth = 100;
    private final int playerHeight = 20;

    // Week 1: Show welcome message first
    private boolean showMessage = true;

    // Week 4: Multiple falling objects
    private int numberOfObjects = 5;
    private final int maxObjects = 10;

    private int[] objectX = new int[maxObjects];
    private int[] objectY = new int[maxObjects];

    private final int objectWidth = 30;
    private final int objectHeight = 30;

    // Week 4: Different speed for each object
    private int[] objectSpeed = new int[maxObjects];

    // Week 10: Improved object colors
    private Color[] objectColors = {
        new Color(255, 80, 80),       // Light Red
        new Color(0, 170, 255),       // Sky Blue
        new Color(255, 60, 170),      // Deep Pink
        new Color(255, 220, 50),      // Yellow
        new Color(255, 150, 50),      // Deep Orange
        new Color(50, 220, 220),      // Cyan
        new Color(220, 80, 255),      // Purple
        new Color(255, 130, 180),     // Light Pink
        new Color(80, 220, 100),      // Green
        Color.WHITE                    // White
    };

    // Week 5: Catch effect
    private boolean[] showCatchEffect = new boolean[maxObjects];
    private int[] effectX = new int[maxObjects];
    private int[] effectY = new int[maxObjects];

    // Week 6: Score System
    private int score = 0;

    // Week 7: Lives System
    private int lives = 3;

    // Week 8: Difficulty Increase
    private int difficultyTime = 0;

    // Week 9: Game Over
    private boolean gameOver = false;
    private JButton restartButton;

    // Week 10: Preloaded sound clips
    private Clip[] catchSounds = new Clip[5];
    private Clip gameOverSound;
    private int catchSoundIndex = 0;

    // Random number generator
    private Random random = new Random();

    // Main game timer
    private Timer fallingTimer;

    public GamePanel() {

        // Week 10: Improved background
        setBackground(new Color(15, 20, 35));

        setFocusable(true);
        addKeyListener(this);

        // Week 9: Restart button
        setLayout(null);

        restartButton = new JButton("RESTART");
        restartButton.setBounds(175, 350, 150, 45);
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setBackground(new Color(50, 150, 255));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());

        add(restartButton);

        // Week 10: Load sounds once
        loadSounds();

        // Week 4: Different starting positions and slow speeds
        for (int i = 0; i < maxObjects; i++) {

            objectX[i] = random.nextInt(470);
            objectY[i] = -30 - random.nextInt(300);

            // Start with slow random speed
            objectSpeed[i] = 1 + random.nextInt(2);

            showCatchEffect[i] = false;
        }

        // Week 1: Show welcome message for 3 seconds
        Timer timer = new Timer(3000, e -> {

            showMessage = false;

            requestFocusInWindow();

            repaint();
        });

        timer.setRepeats(false);
        timer.start();

        // Week 3: Falling Object Timer
        fallingTimer = new Timer(30, e -> {

            if (!showMessage && !gameOver) {

                // Week 8: Increase number of objects over time
                difficultyTime++;

                if (difficultyTime % 1000 == 0) {

                    if (numberOfObjects < maxObjects) {
                        numberOfObjects++;
                    }
                }

                // Week 3: Move all active objects
                for (int i = 0; i < numberOfObjects; i++) {

                    objectY[i] += objectSpeed[i];

                    // Week 5: Collision Detection
                    if (objectX[i] < playerX + playerWidth
                            && objectX[i] + objectWidth > playerX
                            && objectY[i] < playerY + playerHeight
                            && objectY[i] + objectHeight > playerY) {

                        // Week 5: Save position for +1 effect
                        effectX[i] = objectX[i];
                        effectY[i] = playerY - 10;

                        // Week 5: Show +1 effect
                        showCatchEffect[i] = true;

                        // Week 6: Increase score
                        score++;

                        // Week 10: Play preloaded catch sound
                        playCatchSound();

                        // Week 8: Difficulty starts after 30 catches
                        int minSpeed = 1;
                        int maxSpeed = 2;

                        if (score >= 30) {
                            maxSpeed = 3;
                        }

                        if (score >= 40) {
                            minSpeed = 2;
                        }

                        if (score >= 50) {
                            maxSpeed = 4;
                        }

                        if (score >= 60) {
                            minSpeed = 3;
                            maxSpeed = 5;
                        }

                        // Week 4 & 8: New random speed
                        objectSpeed[i] =
                                minSpeed + random.nextInt(
                                        maxSpeed - minSpeed + 1);

                        // Week 3: Remove object from screen
                        objectY[i] =
                                -objectHeight - random.nextInt(150);

                        // Week 4: New X position inside screen
                        objectX[i] =
                                random.nextInt(470);

                        // Week 5: Hide +1 after 500 milliseconds
                        final int caughtObject = i;

                        Timer effectTimer =
                                new Timer(500, event -> {

                                    showCatchEffect[caughtObject] =
                                            false;

                                    repaint();
                                });

                        effectTimer.setRepeats(false);
                        effectTimer.start();
                    }

                    // Week 7: Reduce life when object is missed
                    if (objectY[i] >= getHeight()) {

                        if (lives > 0) {
                            lives--;
                        }

                        // Week 9: Game Over
                        if (lives == 0) {

                            gameOver = true;

                            fallingTimer.stop();

                            restartButton.setVisible(true);

                            // Week 10: Play game over sound
                            playGameOverSound();
                        }

                        // Week 8: Difficulty starts after 30 catches
                        int minSpeed = 1;
                        int maxSpeed = 2;

                        if (score >= 30) {
                            maxSpeed = 3;
                        }

                        if (score >= 40) {
                            minSpeed = 2;
                        }

                        if (score >= 50) {
                            maxSpeed = 4;
                        }

                        if (score >= 60) {
                            minSpeed = 3;
                            maxSpeed = 5;
                        }

                        // Week 4 & 8: New random speed
                        objectSpeed[i] =
                                minSpeed + random.nextInt(
                                        maxSpeed - minSpeed + 1);

                        // Week 3: Reset object above screen
                        objectY[i] =
                                -objectHeight - random.nextInt(250);

                        // Week 4: New X position inside screen
                        objectX[i] =
                                random.nextInt(470);
                    }
                }

                repaint();
            }
        });

        fallingTimer.start();
    }

    // Week 9: Restart Game
    private void restartGame() {

        score = 0;
        lives = 3;
        difficultyTime = 0;
        numberOfObjects = 5;
        gameOver = false;
        playerX = 200;
        catchSoundIndex = 0;

        // Week 3: Reset all objects
        for (int i = 0; i < maxObjects; i++) {

            objectX[i] =
                    random.nextInt(470);

            objectY[i] =
                    -30 - random.nextInt(300);

            // Week 4: Start with slow random speed
            objectSpeed[i] =
                    1 + random.nextInt(2);

            showCatchEffect[i] = false;
        }

        restartButton.setVisible(false);

        fallingTimer.start();

        requestFocusInWindow();

        repaint();
    }

    // Week 10: Load sounds once at the beginning
    private void loadSounds() {

        try {

            // Load 5 copies of catch sound
            for (int i = 0; i < catchSounds.length; i++) {

                AudioInputStream audio =
                        AudioSystem.getAudioInputStream(
                                new File("sounds/catch.wav"));

                catchSounds[i] =
                        AudioSystem.getClip();

                catchSounds[i].open(audio);

                audio.close();
            }

            // Load game over sound
            AudioInputStream gameOverAudio =
                    AudioSystem.getAudioInputStream(
                            new File("sounds/gameover.wav"));

            gameOverSound =
                    AudioSystem.getClip();

            gameOverSound.open(gameOverAudio);

            gameOverAudio.close();

        } catch (Exception e) {

            System.out.println(
                    "Sound could not be loaded.");
        }
    }

    // Week 10: Play catch sound smoothly
    private void playCatchSound() {

        Clip sound =
                catchSounds[catchSoundIndex];

        catchSoundIndex++;

        if (catchSoundIndex >= catchSounds.length) {
            catchSoundIndex = 0;
        }

        if (sound != null) {

            if (sound.isRunning()) {
                sound.stop();
            }

            sound.setFramePosition(0);
            sound.start();
        }
    }

    // Week 10: Play game over sound
    private void playGameOverSound() {

        if (gameOverSound != null) {

            if (gameOverSound.isRunning()) {
                gameOverSound.stop();
            }

            gameOverSound.setFramePosition(0);
            gameOverSound.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Week 10: Smooth graphics
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (showMessage) {

            // Week 1: Welcome screen
            g2.setColor(
                    new Color(25, 35, 60));

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight());

            String title =
                    "CATCH THE FALLING OBJECTS";

            String subtitle =
                    "Get ready!";

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            25));

            g2.setColor(Color.WHITE);

            FontMetrics fm =
                    g2.getFontMetrics();

            int titleX =
                    (getWidth()
                    - fm.stringWidth(title)) / 2;

            g2.drawString(
                    title,
                    titleX,
                    280);

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            18));

            fm =
                    g2.getFontMetrics();

            int subtitleX =
                    (getWidth()
                    - fm.stringWidth(subtitle)) / 2;

            g2.drawString(
                    subtitle,
                    subtitleX,
                    315);

        } else if (gameOver) {

            // Week 9: Game Over Screen
            g2.setColor(
                    new Color(25, 20, 35));

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight());

            g2.setColor(
                    new Color(255, 80, 80));

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            42));

            String gameOverText =
                    "GAME OVER";

            FontMetrics fm =
                    g2.getFontMetrics();

            int x =
                    (getWidth()
                    - fm.stringWidth(
                            gameOverText)) / 2;

            g2.drawString(
                    gameOverText,
                    x,
                    260);

            // Week 6: Display final score
            g2.setColor(Color.WHITE);

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            22));

            String finalScore =
                    "Final Score: " + score;

            int scoreX =
                    (getWidth()
                    - g2.getFontMetrics()
                            .stringWidth(
                                    finalScore)) / 2;

            g2.drawString(
                    finalScore,
                    scoreX,
                    310);

        } else {

            // Week 10: Improved top information bar
            g2.setColor(
                    new Color(25, 35, 55));

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    55);

            // Week 6: Display Score
            g2.setColor(Color.WHITE);

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            20));

            g2.drawString(
                    "Score: " + score,
                    20,
                    35);

            // Week 7: Display Lives
            g2.setColor(
                    new Color(255, 100, 100));

            g2.drawString(
                    "Lives: " + lives,
                    380,
                    35);

            // Week 2: Draw Player / Catcher
            g2.setColor(
                    new Color(50, 220, 120));

            g2.fillRoundRect(
                    playerX,
                    playerY,
                    playerWidth,
                    playerHeight,
                    10,
                    10
            );

            // Week 3: Draw Falling Objects
            for (int i = 0;
                    i < numberOfObjects;
                    i++) {

                if (objectY[i] >= 0) {

                    // Week 10: Different object colors
                    g2.setColor(
                            objectColors[i]);

                    g2.fillOval(
                            objectX[i],
                            objectY[i],
                            objectWidth,
                            objectHeight
                    );
                }

                // Week 5: Draw +1 catch effect
                if (showCatchEffect[i]) {

                    g2.setColor(
                            new Color(
                                    100,
                                    255,
                                    150));

                    g2.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    22));

                    g2.drawString(
                            "+1",
                            effectX[i],
                            effectY[i]
                    );
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!showMessage && !gameOver) {

            // Week 2: Move player left
            if (e.getKeyCode() ==
                    KeyEvent.VK_LEFT) {

                playerX -= 20;
            }

            // Week 2: Move player right
            if (e.getKeyCode() ==
                    KeyEvent.VK_RIGHT) {

                playerX += 20;
            }

            // Week 2: Keep player inside screen
            if (playerX < 0) {
                playerX = 0;
            }

            if (playerX >
                    getWidth() - playerWidth) {

                playerX =
                        getWidth() - playerWidth;
            }

            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}