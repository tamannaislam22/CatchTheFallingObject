import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {

    // Player (Catcher)
    private int playerX = 250;
    private final int playerY = 620;
    private final int playerWidth = 100;
    private final int playerHeight = 20;

    // Show welcome message first
    private boolean showMessage = true;

    // Week 8: Difficulty Increase
    private int numberOfObjects = 5;
    private final int maxObjects = 10;

    private int[] objectX = new int[maxObjects];
    private int[] objectY = new int[maxObjects];

    private final int objectWidth = 30;
    private final int objectHeight = 30;

    // Different speed for each object
    private int[] objectSpeed = new int[maxObjects];

    // Different colors for objects
    private Color[] objectColors = {
        Color.RED,
        new Color(0, 119, 190),
        new Color(255, 20, 147),
        Color.YELLOW,
        Color.ORANGE,
        Color.CYAN,
        Color.MAGENTA,
        Color.PINK,
        Color.GREEN,
        Color.WHITE
    };

    // Week 5: Catch effect
    private boolean[] showCatchEffect = new boolean[maxObjects];
    private int[] effectX = new int[maxObjects];
    private int[] effectY = new int[maxObjects];

    // Week 6: Score System
    private int score = 0;

    // Week 7: Miss System (Lives)
    private int lives = 3;

    // Week 8: Difficulty timer
    private int difficultyTime = 0;

    // Week 9: Game Over
    private boolean gameOver = false;
    private JButton restartButton;

    // Random number generator
    private Random random = new Random();

    // Falling Object Timer
    private Timer fallingTimer;

    public GamePanel() {

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Week 9: Restart button
        setLayout(null);

        restartButton = new JButton("Restart");
        restartButton.setBounds(225, 400, 150, 40);
        restartButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());

        add(restartButton);

        // Random starting positions and different speeds
        for (int i = 0; i < maxObjects; i++) {
            objectX[i] = random.nextInt(570);
            objectY[i] = -random.nextInt(500);
            objectSpeed[i] = 1 + random.nextInt(4);
            showCatchEffect[i] = false;
        }

        // Show message for 3 seconds
        Timer timer = new Timer(3000, e -> {
            showMessage = false;
            repaint();
        });

        timer.setRepeats(false);
        timer.start();

        // Falling Object Timer
        fallingTimer = new Timer(30, e -> {

            if (!showMessage && !gameOver) {

                // Week 8: Increase difficulty over time
                difficultyTime++;

                // Increase difficulty approximately every 15 seconds
                if (difficultyTime % 500 == 0) {

                    // Increase number of objects
                    if (numberOfObjects < maxObjects) {
                        numberOfObjects++;
                    }

                    // Increase speed slowly
                    for (int i = 0; i < numberOfObjects; i++) {
                        if (objectSpeed[i] < 5) {
                            objectSpeed[i]++;
                        }
                    }
                }

                // Move all active objects downward
                for (int i = 0; i < numberOfObjects; i++) {

                    objectY[i] += objectSpeed[i];

                    // Week 5: Collision Detection
                    if (objectX[i] < playerX + playerWidth
                            && objectX[i] + objectWidth > playerX
                            && objectY[i] < playerY + playerHeight
                            && objectY[i] + objectHeight > playerY) {

                        // Save position for +1 effect
                        effectX[i] = objectX[i];
                        effectY[i] = playerY - 10;

                        // Show +1
                        showCatchEffect[i] = true;

                        // Week 6: Increase score on every catch
                        score++;

                        // Remove object
                        objectY[i] = -objectHeight;

                        // New random X position
                        objectX[i] = random.nextInt(
                                getWidth() - objectWidth);

                        // New random speed
                        objectSpeed[i] = 1 + random.nextInt(4);

                        // Hide +1 after 500 milliseconds
                        final int caughtObject = i;

                        Timer effectTimer = new Timer(500, event -> {
                            showCatchEffect[caughtObject] = false;
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

                        // Week 9: Game Over when lives = 0
                        if (lives == 0) {
                            gameOver = true;
                            fallingTimer.stop();
                            restartButton.setVisible(true);
                        }

                        // Reset object
                        objectY[i] = 0;

                        objectX[i] = random.nextInt(
                                getWidth() - objectWidth);

                        objectSpeed[i] = 1 + random.nextInt(4);
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
        numberOfObjects = 5;
        difficultyTime = 0;
        gameOver = false;

        playerX = 250;

        // Reset all objects
        for (int i = 0; i < maxObjects; i++) {

            objectX[i] = random.nextInt(
                    getWidth() - objectWidth);

            objectY[i] = -random.nextInt(500);

            objectSpeed[i] = 1 + random.nextInt(4);

            showCatchEffect[i] = false;
        }

        restartButton.setVisible(false);

        fallingTimer.start();

        requestFocusInWindow();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (showMessage) {

            String text = "Catch the Falling Objects Game Started!";

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            FontMetrics fm = g.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2
                    + fm.getAscent();

            g.drawString(text, x, y);

        } else if (gameOver) {

            // Week 9: Game Over Screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 35));

            String gameOverText = "GAME OVER";

            FontMetrics fm = g.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;

            g.drawString(gameOverText, x, 300);

            // Show final score
            g.setFont(new Font("Arial", Font.BOLD, 22));

            String finalScore = "Final Score: " + score;

            int scoreX = (getWidth() - g.getFontMetrics()
                    .stringWidth(finalScore)) / 2;

            g.drawString(finalScore, scoreX, 350);

        } else {

            // Week 6: Display Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 20, 30);

            // Week 7: Display Lives
            g.drawString("Lives: " + lives, 470, 30);

            // Draw Player (Catcher)
            g.setColor(Color.GREEN);

            g.fillRect(playerX, playerY,
                    playerWidth, playerHeight);

            // Draw Falling Objects
            for (int i = 0; i < numberOfObjects; i++) {

                if (objectY[i] >= 0) {

                    g.setColor(objectColors[i]);

                    g.fillOval(objectX[i], objectY[i],
                            objectWidth, objectHeight);
                }

                // Week 5: Draw +1 effect
                if (showCatchEffect[i]) {

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 20));

                    g.drawString("+1",
                            effectX[i],
                            effectY[i]);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!showMessage && !gameOver) {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                playerX -= 20;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                playerX += 20;
            }

            // Keep player inside screen
            if (playerX < 0) {
                playerX = 0;
            }

            if (playerX > getWidth() - playerWidth) {
                playerX = getWidth() - playerWidth;
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