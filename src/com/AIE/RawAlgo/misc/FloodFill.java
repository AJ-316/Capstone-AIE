import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FloodFill extends JPanel {
    private BufferedImage image;
    private int width;
    private int height;

    public FloodFill() {
        // Initialize the image and its size
        width = 400;
        height = 400;
        image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void floodFill(int x, int y, Color targetColor, Color replacementColor) {
        if (targetColor.equals(replacementColor)) {
            return;
        }
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x, y));
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int px = p.x;
            int py = p.y;

            if (px < 0 || px >= width || py < 0 || py >= height) {
                continue;
            }

            int pixelColor = image.getRGB(px, py);

            if (pixelColor == targetColor.getRGB()) {
                image.setRGB(px, py, replacementColor.getRGB());
                stack.push(new Point(px - 1, py));
                stack.push(new Point(px + 1, py));
                stack.push(new Point(px, py - 1));
                stack.push(new Point(px, py + 1));
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flood Fill Demo");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FloodFill panel = new FloodFill();
        frame.add(panel);
        frame.setVisible(true);
        panel.floodFill(0, 0, Color.WHITE, Color.GREEN);
    }
}