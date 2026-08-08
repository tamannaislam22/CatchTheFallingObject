import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {

    // Player (Catcher)
    private int playerX = 250;
    private final int playerY = 620;
    private final int playerWidth = 100;
    private final int playerHeight = 20;

    // Show welcome message first
    private boolean showMessage = true;

    // Week 3: Falling Object
    private int objectX = 280;
    private int objectY = 0;
    private final int objectWidth = 30;
    private final int objectHeight = 30;
    private final int objectSpeed = 5;

    public GamePanel() {
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        // Show message for 3 seconds
        Timer timer = new Timer(3000, e -> {
            showMessage = false;
            repaint();
        });

        timer.setRepeats(false);
        timer.start();

        // Week 3: Falling Object Timer
        Timer fallingTimer = new Timer(30, e -> {

            if (!showMessage) {

                // Move object downward
                objectY += objectSpeed;

                // Reset object when it reaches bottom
                if (objectY >= getHeight()) {
                    objectY = 0;
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

            // Week 3: Draw Falling Object
            g.setColor(Color.RED);
            g.fillOval(objectX, objectY,
                    objectWidth, objectHeight);
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