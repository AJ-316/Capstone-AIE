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