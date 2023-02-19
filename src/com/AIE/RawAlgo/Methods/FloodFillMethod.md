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