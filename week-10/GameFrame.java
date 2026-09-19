import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Catch the Falling Objects");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        add(new GamePanel());

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}