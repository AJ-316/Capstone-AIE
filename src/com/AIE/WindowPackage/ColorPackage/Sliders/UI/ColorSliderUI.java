package com.AIE.WindowPackage.ColorPackage.Sliders.UI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ColorSliderUI extends BasicSliderUI {

    private boolean thumbHover;
    private boolean thumbPressed;

    private final int[] thumbPointsX = new int[3];
    private final int[] thumbPointsY = new int[3];
    private static final int thumbOffset = 7;
    private static final int UNIVERSAL_SIZE = 20;
    private static final BasicStroke trackStroke = new BasicStroke(0.6f);
    private static final BasicStroke thumbStroke = new BasicStroke(1);

    protected BasicSliderUI.TrackListener createTrackListener(JSlider slider) {
        return new ColorTrackListener();
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(UNIVERSAL_SIZE, UNIVERSAL_SIZE);
    }

    private void updateThumbPoints() {
        thumbPointsX[0] = thumbRect.x+thumbOffset;
        thumbPointsX[1] = (int) (thumbRect.x+UNIVERSAL_SIZE/5.5f+thumbOffset);
        thumbPointsX[2] = (int) (thumbRect.x+UNIVERSAL_SIZE/2.75f+thumbOffset);

        thumbPointsY[0] = (int) (thumbRect.y+UNIVERSAL_SIZE/1.8f+thumbOffset);
        thumbPointsY[1] = (int) (thumbRect.y+UNIVERSAL_SIZE/3.6f+thumbOffset);
        thumbPointsY[2] = thumbPointsY[0];
    }

    @Override
    public void paintFocus(Graphics g) {}

    @Override
    public void paint(Graphics g, JComponent c) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintThumb(Graphics g) {
        ((Graphics2D) g).setStroke(thumbStroke);
        updateThumbPoints();
        g.setColor(Color.black);
        g.fillPolygon(thumbPointsX, thumbPointsY, thumbPointsX.length);
        g.setColor(Color.white);
        g.drawPolygon(thumbPointsX, thumbPointsY, thumbPointsX.length);
    }

    public class ColorTrackListener extends BasicSliderUI.TrackListener {
        private ColorTrackListener() {
            super();
        }

        public void mouseEntered(MouseEvent e) {
            this.setThumbHover(this.isOverThumb(e));
            super.mouseEntered(e);
        }

        public void mouseExited(MouseEvent e) {
            this.setThumbHover(false);
            super.mouseExited(e);
        }

        public void mouseMoved(MouseEvent e) {
            this.setThumbHover(this.isOverThumb(e));
            super.mouseMoved(e);
        }

        public void mousePressed(MouseEvent e) {
            this.setThumbPressed(this.isOverThumb(e));
            if (slider.isEnabled()) {
                if (UIManager.getBoolean("Slider.scrollOnTrackClick")) {
                    super.mousePressed(e);
                } else {
                    int x = e.getX();
                    int y = e.getY();
                    calculateGeometry();
                    if (thumbRect.contains(x, y)) {
                        super.mousePressed(e);
                    } else if (!UIManager.getBoolean("Slider.onlyLeftMouseButtonDrag") || SwingUtilities.isLeftMouseButton(e)) {
                        int tx = thumbRect.x + thumbRect.width / 2 - x;
                        int ty = thumbRect.y + thumbRect.height / 2 - y;
                        e.translatePoint(tx, ty);
                        super.mousePressed(e);
                        e.translatePoint(-tx, -ty);
                        this.mouseDragged(e);
                        this.setThumbPressed(true);
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            this.setThumbPressed(false);
            super.mouseReleased(e);
        }

        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            if (isDragging() && slider.getSnapToTicks() && slider.isEnabled() && !UIManager.getBoolean("Slider.snapToTicksOnReleased")) {
                calculateThumbLocation();
            }
            slider.repaint();
        }

        private void setThumbHover(boolean hover) {
            if (hover != thumbHover) {
                thumbHover = hover;
                slider.repaint(thumbRect);
            }
        }

        private void setThumbPressed(boolean pressed) {
            if (pressed != thumbPressed) {
                thumbPressed = pressed;
                slider.repaint(thumbRect);
            }
        }

        private boolean isOverThumb(MouseEvent e) {
            return e != null && slider.isEnabled() && thumbRect.contains(e.getX(), e.getY());
        }
    }

    @Override
    public void paintTrack(Graphics g) {
        g.fillRect(trackRect.x, trackRect.y+UNIVERSAL_SIZE/4, trackRect.width, trackRect.height/2);
        g.setColor(Color.white);
        ((Graphics2D) g).setStroke(trackStroke);
        g.drawRect(trackRect.x, trackRect.y+UNIVERSAL_SIZE/4, trackRect.width, trackRect.height/2);
    }
}