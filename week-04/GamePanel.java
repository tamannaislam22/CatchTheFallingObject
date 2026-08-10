import javax.swing.JPanel;
import javax.swing.Timer;
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

    // Week 4: Multiple Falling Objects
    private final int numberOfObjects = 5;

    private int[] objectX = new int[numberOfObjects];
    private int[] objectY = new int[numberOfObjects];

    private final int objectWidth = 30;
    private final int objectHeight = 30;

    // Different speed for each object
    private int[] objectSpeed = new int[numberOfObjects];

    // Different colors for objects
    private Color[] objectColors = {
        Color.RED,
        new Color(0, 119, 190),    // Ocean Blue
        new Color(255, 20, 147),   // Deep Pink
        Color.YELLOW,
        Color.ORANGE
    };

    // Random number generator
    private Random random = new Random();

    public GamePanel() {
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        // Random starting positions and different speeds
        for (int i = 0; i < numberOfObjects; i++) {

            // Random X position for 600px wide window
            objectX[i] = random.nextInt(570);

            // Different starting heights
            objectY[i] = -random.nextInt(500);

            // Random speed between 1 and 4
            objectSpeed[i] = 1 + random.nextInt(4);
        }

        // Show message for 3 seconds
        Timer timer = new Timer(3000, e -> {
            showMessage = false;
            repaint();
        });

        timer.setRepeats(false);
        timer.start();

        // Falling Object Timer
        Timer fallingTimer = new Timer(30, e -> {

            if (!showMessage) {

                // Move all objects downward
                for (int i = 0; i < numberOfObjects; i++) {

                    // Each object moves with its own speed
                    objectY[i] += objectSpeed[i];

                    // Reset object when it reaches bottom
                    if (objectY[i] >= getHeight()) {

                        objectY[i] = 0;

                        // New random X position
                        objectX[i] = random.nextInt(
                                getWidth() - objectWidth);

                        // Give new random speed between 1 and 4
                        objectSpeed[i] = 1 + random.nextInt(4);
                    }
                }

                repaint();
            }
        });

        fallingTimer.start();
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

        } else {

            // Draw Player (Catcher)
            g.setColor(Color.GREEN);

            g.fillRect(playerX, playerY,
                    playerWidth, playerHeight);

            // Draw Multiple Falling Objects
            for (int i = 0; i < numberOfObjects; i++) {

                // Set different color for each object
                g.setColor(objectColors[i]);

                g.fillOval(objectX[i], objectY[i],
                        objectWidth, objectHeight);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!showMessage) {

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