package com.AIE.CanvasPackage;

import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.History;
import com.AIE.WindowPackage.ImageMenu;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasManager extends JTabbedPane {

    private static final int TAB_ICON_SIZE = 96;
    public static CanvasManager GET;

    private CanvasManager() {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        setUI(new MaterialTabbedUI());
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

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {pane.setSelectedIndex(index);}
                public void mouseDragged(MouseEvent e) {pane.setSelectedIndex(index);}
            });
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

    public static class MaterialTabbedUI extends MetalTabbedPaneUI {

        private Animator animator;
        private Rectangle currentRectangle;
        private TimingTarget target;

        @Override
        public void installUI(JComponent jc) {
            super.installUI(jc);
            animator = new Animator(150);
            animator.setResolution(0);
            animator.setAcceleration(.5f);
            animator.setDeceleration(.5f);
            tabPane.addChangeListener(ce -> {
                int selected = tabPane.getSelectedIndex();
                if (selected != -1) {
                    if (currentRectangle != null) {
                        if (animator.isRunning()) {
                            animator.stop();
                        }
                        animator.removeTarget(target);
                        target = new PropertySetter(MaterialTabbedUI.this, "currentRectangle", currentRectangle, getTabBounds(selected, calcRect));
                        animator.addTarget(target);
                        animator.start();
                    }
                }
            });
        }

        @Override
        protected Insets getTabInsets(int tabPlacement, int tabIndex) {
            return new Insets(5, 5, 5, 5);
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (currentRectangle == null || !animator.isRunning()) {
                if (isSelected) {
                    currentRectangle = new Rectangle(x, y, w, h);
                }
            }
            LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, h,
                    new float[]{0, 0.5f, 1},
                    new Color[]{new Color(0x505254), tabPane.getBackground(), new Color(0x505254)});
            g2.setPaint(paint);
            g2.fillRect(tabIndex * w + w, 0, 1, h);
            g2.fillRect(tabIndex * w, 0, 1, h);
            if (currentRectangle != null) {
                paint = new LinearGradientPaint(currentRectangle.x, 0, currentRectangle.x+currentRectangle.width, 0,
                        new float[]{0, 0.5f, 1},
                        new Color[]{MutableColor.TRANSPARENT, Color.white, MutableColor.TRANSPARENT});
                g2.setPaint(paint);
                g2.fillRect(currentRectangle.x, currentRectangle.y + currentRectangle.height - 2, currentRectangle.width, 2);
                g2.fillRect(currentRectangle.x, 0, currentRectangle.width, 2);
            }
            g2.dispose();
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0x505254));
            Insets insets = getTabAreaInsets(tabPlacement);
                int tabHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                g2.drawLine(insets.left, tabHeight, tabPane.getWidth() - insets.right - 1, tabHeight);
            g2.dispose();
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int i, Rectangle[] rctngls, int i1, Rectangle rctngl, Rectangle rctngl1, boolean bln) {}

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            if(tabPane.isOpaque())
                super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
        }

        public void setCurrentRectangle(Rectangle currentRectangle) {
            this.currentRectangle = currentRectangle;
            tabPane.repaint();
        }
    }
}
