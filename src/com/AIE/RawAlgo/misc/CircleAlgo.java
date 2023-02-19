import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CircleAlgo extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private int centerX;
    private int centerY;
    private int radius;
    private boolean drawingCircle;

    public CircleAlgo() {
        // add mouse listener to panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // set center point and start drawing
                centerX = e.getX();
                centerY = e.getY();
                radius = 0;
                drawingCircle = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // stop drawing and repaint
                drawingCircle = false;
//                repaint();
            }
        });

        // add mouse motion listener to panel
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // update radius while drawing
                radius = (int) Math.sqrt(Math.pow(e.getX() - centerX, 2) + Math.pow(e.getY() - centerY, 2));
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(drawingCircle, radius, g);
    }
    public void drawCircle(boolean drawingCircle, int radius, Graphics g){
        if (drawingCircle) {
            // set the color to black
            g.setColor(Color.BLACK);
            // initialize variables
            int x = 0;
            int y = radius;
            int d = 3 - 2 * radius;
            // draw the first set of points
            drawPoints(g, x, y);
            // iterate through the remaining points
            while (y >= x) {
                x++;

                // check if the decision variable is less than 0
                if (d < 0) {
                    d = d + 4 * x + 6;
                } else {
                    y--;
                    d = d + 4 * (x - y) + 10;
                }
                // draw the next set of points
                drawPoints(g, x, y);
            }
        }
    }

    private void drawPoints(Graphics g, int x, int y) {
        // draw points in all eight octants
        g.drawRect(centerX + x, centerY + y, 1, 1);
        g.drawRect(centerX - x, centerY + y, 1, 1);
        g.drawRect(centerX + x, centerY - y, 1, 1);
        g.drawRect(centerX - x, centerY - y, 1, 1);
        g.drawRect(centerX + y, centerY + x, 1, 1);
        g.drawRect(centerX - y, centerY + x, 1, 1);
        g.drawRect(centerX + y, centerY - x, 1, 1);
        g.drawRect(centerX - y, centerY - x, 1, 1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Bresenham's Circle Drawing Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CircleAlgo circle = new CircleAlgo();
        frame.add(circle);

        frame.setVisible(true);
    }
}