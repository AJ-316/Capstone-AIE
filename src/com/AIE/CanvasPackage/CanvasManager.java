package com.AIE.CanvasPackage;

import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.FileMenu;
import com.AIE.WindowPackage.History;
import com.AIE.WindowPackage.ImageMenu;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;
import com.formdev.flatlaf.ui.FlatTabbedPaneUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasManager extends JTabbedPane {

    private static final int[] SIZES = new int[]{12, 24, 28, 34, 38, 44, 48, 54, 58, 64, 68, 75, 96, 100};
    private static int currentSizeIndex = 12;
    private static int TAB_ICON_SIZE = 96;
    public static CanvasManager GET;

    private CanvasManager() {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        setUI(new MaterialTabbedUI());
        addMouseWheelListener(e -> {
            int index = currentSizeIndex + e.getWheelRotation();
            currentSizeIndex = Math.max(0, Math.min(SIZES.length-1, index));
            TAB_ICON_SIZE = SIZES[currentSizeIndex];
            for(int i = 0; i < getTabCount(); i++) {
                ((VerticalTab)getTabComponentAt(i)).updateSize();
            }
            revalidate();
            repaint();
        });
    }

    public static void init(int width, int height) {
        GET = new CanvasManager();
        GET.setPreferredSize(new Dimension(width, height));
        GET.addChangeListener(e -> {
            int index = ((JTabbedPane) e.getSource()).getSelectedIndex();
            if(index < 0) return;
            History.GET.selectHistoryPanel(index);
        });
    }

    public void addCanvas(Canvas canvas) {
        addTab(null, canvas);
        canvas.addKeyListenerToRootPane(ImageMenu.getKeyAdapter(canvas));
        canvas.addKeyListenerToRootPane(FileMenu.getKeyAdapter(canvas));
        int index = getTabCount()-1;
        VerticalTab tabComponent = new VerticalTab(canvas, this, index);
        setTabComponentAt(index, tabComponent);
    }

    public void updateTabComponent(Canvas canvas) {
        int index = indexOfComponent(canvas);
        if(index != -1) {
            ((VerticalTab)getTabComponentAt(index)).update(canvas);
            revalidate();
            repaint();
        }
    }

    public static Canvas getCurrentCanvas() {
        return (Canvas) GET.getSelectedComponent();
    }

    public boolean isCanvasNotSelected(Canvas canvas) {
        return !getSelectedComponent().equals(canvas);
    }

    private static class VerticalTab extends JPanel {

        private final JLabel label;
        private final SmoothIcon icon;
        private final float scaledSize = 0.75f;

        public VerticalTab(Canvas canvas, JTabbedPane pane, int index) {
            setOpaque(false);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBorder(new BasicBorders.SplitPaneBorder(Color.red, Color.black));
            label = new JLabel(canvas.getName(), SwingConstants.CENTER);
            icon = new SmoothIcon().updateImage(canvas.getImage(), TAB_ICON_SIZE);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.SOUTH;
            gbc.insets.top = (int) (TAB_ICON_SIZE*scaledSize);
            gbc.gridy = 1;
            add(label, gbc);

            setPreferredSize(new Dimension(TAB_ICON_SIZE, TAB_ICON_SIZE));
            MouseAdapter adapter = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    pane.setSelectedIndex(index);
                    pane.setEnabledAt(index, true);
                    pane.requestFocus();
                }
                public void mouseDragged(MouseEvent e) {mousePressed(e);}

                @Override
                public void mouseMoved(MouseEvent e) {
                    pane.setEnabledAt(index, true);
                    pane.requestFocus();
                }
            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        }

        public void updateSize() {
            setPreferredSize(new Dimension(TAB_ICON_SIZE, TAB_ICON_SIZE));
            GridBagConstraints constraints = ((GridBagLayout)getLayout()).getConstraints(label);
            constraints.insets.top = (int) (TAB_ICON_SIZE*scaledSize);
            ((GridBagLayout)getLayout()).setConstraints(label, constraints);
            icon.updateImageSize(TAB_ICON_SIZE);
            label.setVisible(TAB_ICON_SIZE >= 48);
        }

        public void update(Canvas canvas) {
            label.setText(canvas.getName());
            icon.updateImage(canvas.getImage(), TAB_ICON_SIZE);
            setToolTipText(label.getText());
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int w = (int) (icon.getIconWidth()*0.75f);
            int h = (int) (icon.getIconHeight()*0.75f);
            g.drawImage(icon.getImage(), (getWidth()-w)/2, (int) ((TAB_ICON_SIZE*scaledSize-h)/2), w, h, null);
        }
    }

    public static class MaterialTabbedUI extends FlatTabbedPaneUI {
        private static final float[] fractions = new float[]{0, 0.5f, 1};
        private final Color[] border = new Color[]{new Color(0x505254), new Color(0x3C3F41), new Color(0x505254)};
        private final Color[] selection = new Color[]{MutableColor.TRANSPARENT, Color.white, MutableColor.TRANSPARENT};

        @Override
        protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
            super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new LinearGradientPaint(0, 0, 0, rects[tabIndex].height, fractions, border));
            g2.fillRect(tabIndex * rects[tabIndex].width + rects[tabIndex].width, 0, 1, rects[tabIndex].height);
            g2.fillRect(tabIndex * rects[tabIndex].width, 0, 1, rects[tabIndex].height);
        }

        @Override
        protected void paintTabSelection(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new LinearGradientPaint(0, 0, 0, rects[tabIndex].height, fractions, border));
            g2.fillRect(tabIndex * w + w, 0, 1, h);
            g2.fillRect(tabIndex * w, 0, 1, h);
            g2.setPaint(new LinearGradientPaint(x, 0, x+w, 0, fractions, selection));
            g2.fillRect(x, y + h - 2, w, 2);
            g2.fillRect(x, 0, w, 2);
            g2.dispose();
        }
    }
}
