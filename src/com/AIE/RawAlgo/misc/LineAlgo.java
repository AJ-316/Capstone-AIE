import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;

public class LineAlgo extends JPanel {
    Point start;
    Point end;
    public LineAlgo() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                start = new Point(e.getPoint().x, e.getPoint().y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                end = new Point(e.getPoint().x, e.getPoint().y);
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                end = new Point(e.getPoint().x, e.getPoint().y);
                repaint();
            }
        });
    }
    private void drawLine(int x1, int y1, int x2, int y2, Graphics g) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            g.drawRect(x1, y1, 1, 1);
            float e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLine(start.x, start.y, end.x, end.y, g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bresenham Line Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new LineAlgo());
        frame.setVisible(true);
    }
}

