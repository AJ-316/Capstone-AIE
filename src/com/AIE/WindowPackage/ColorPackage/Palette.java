package com.AIE.WindowPackage.ColorPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Palette extends JPanel implements MouseListener {

    private static final ArrayList<PaletteChip> defaultChips = new ArrayList<>();
    public static ArrayList<PaletteChip> chips;
    private static int PANEL_HEIGHT = 300;

    public static void savePalette() {
        for(int i = 0; i < chips.size(); i++) {
            if(chips.get(i).isNewChip) chips.remove(i--);
        }
        PaletteChipSerialization.save(chips);
    }

    public Palette() {
        setLayout(new FlowLayout(FlowLayout.LEFT, -1, 0));
        chips = new ArrayList<>();

        createDefaultChips();
        ArrayList<PaletteChip> loadedChips = PaletteChipSerialization.load(defaultChips);
        for(PaletteChip button : loadedChips)
            addChip(new PaletteChip(button, this));
    }

    private void createDefaultChips() {
        addDefaultChips(
                new MutableColor(0, 0, 0),
                new MutableColor(64, 64, 64),
                new MutableColor(255, 0, 0),
                new MutableColor(255, 106, 0),
                new MutableColor(255, 216, 0),
                new MutableColor(182, 255, 0),
                new MutableColor(76, 255, 0),
                new MutableColor(0, 255, 33),
                new MutableColor(0, 255, 144),
                new MutableColor(0, 255, 255),
                new MutableColor(0, 148, 255),
                new MutableColor(0, 38, 255),
                new MutableColor(72, 0, 255),
                new MutableColor(178, 0, 255),
                new MutableColor(255, 0, 220),
                new MutableColor(255, 0, 110),

                new MutableColor(255, 255, 255),
                new MutableColor(128, 128, 128),
                new MutableColor(127, 0, 0),
                new MutableColor(127, 51, 0),
                new MutableColor(127, 106, 0),
                new MutableColor(91, 127, 0),
                new MutableColor(38, 127, 0),
                new MutableColor(0, 127, 14),
                new MutableColor(0, 127, 70),
                new MutableColor(0, 127, 127),
                new MutableColor(0, 74, 127),
                new MutableColor(0, 19, 127),
                new MutableColor(33, 0, 127),
                new MutableColor(87, 0, 127),
                new MutableColor(127, 0, 110),
                new MutableColor(127, 0, 55)
        );
    }

    private void addDefaultChips(MutableColor... colors) {
        for(MutableColor color : colors) {
            PaletteChip button = new PaletteChip(color, this);
            button.locked = true;
            defaultChips.add(button);
        }
    }

    private void addChip(PaletteChip paletteChip) {
        add(paletteChip);
        chips.add(paletteChip);
        PANEL_HEIGHT += paletteChip.getWidth();
        Dimension size = getPreferredSize();
        size.height = PANEL_HEIGHT/16;
        setPreferredSize(size);
    }

    public void addToWindow(ColorPaletteWindow colorPalette, int x, int y, int width, int height) {
        JScrollPane pane = new JScrollPane(this);
        pane.setLocation(x, y);
        pane.setSize(width, height);
        pane.getVerticalScrollBar().setPreferredSize(new Dimension(4, 4));
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.getVerticalScrollBar().setUnitIncrement(10);
        setPreferredSize(new Dimension(pane.getSize().width - 10, 0));
        addChip(new PaletteChip(this));

        pane.setVisible(true);
        colorPalette.add(pane);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        PaletteChip button = (PaletteChip) e.getSource();
        if(button.isNewChip) {
            addChip(new PaletteChip(this));
            button.isNewChip = false;
            button.color = new MutableColor(255, 255, 255);
            revalidate();
            repaint();
        }
        if (!button.isSelected()) {
            if(SwingUtilities.isRightMouseButton(e)) {
                ColorPaletteWindow.selectBrush(1);
                button.selectedBrush = 1;
                ColorPaletteWindow.update(button.color, "chip");
                button.setToolTipText("Secondary" + (button.locked ? "" : "\nDelete Chip (Middle Mouse Click)"));
                button.setSelected(true);
                clearChips(1, button);
            } else if(SwingUtilities.isLeftMouseButton(e)) {
                ColorPaletteWindow.selectBrush(0);
                button.selectedBrush = 0;
                ColorPaletteWindow.update(button.color, "chip");
                button.setToolTipText("Primary" + (button.locked ? "" : "\nDelete Chip (Middle Mouse Click)"));
                clearChips(0, button);
            }
        } else if(SwingUtilities.isMiddleMouseButton(e)) {
            if(!button.locked && ColorPaletteWindow.isBrushSelected(button.selectedBrush)) {
                chips.remove(button);
                ColorPaletteWindow.ELEMENTS.remove(button);
                remove(button);
                revalidate();
                repaint();
            }
        }
    }

    private void clearChips(int id, PaletteChip check) {
        for(PaletteChip button : chips) {
            if (!button.equals(check) && button.selectedBrush == id)
                button.setSelected(false);
        }
    }

    public static class PaletteChip extends JCheckBox implements PaletteElement {
        private MutableColor color;
        private int selectedBrush = -1;
        private boolean isNewChip;
        private boolean locked;
        private static final BasicStroke[] strokes = new BasicStroke[] {
                new BasicStroke(1), new BasicStroke(2), new BasicStroke(8)};

        public PaletteChip(PaletteChip chip, Palette palette) {
            this(chip.color, palette);
        }

        public PaletteChip(MutableColor color, Palette palette) {
            this.color = color;
            if(color == null) {
                isNewChip = true;
                setToolTipText("Add Chip");
            }

            addMouseListener(palette);
            setSize(new Dimension(18, 18));
            setPreferredSize(getSize());
            ColorPaletteWindow.ELEMENTS.add(this);

        }

        public PaletteChip(Palette palette) {
            this((MutableColor) null, palette);
        }



        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(getModel().isRollover() || isSelected()) {
                g2.setColor(Color.white);
                g2.setStroke(strokes[1]);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
                g2.setStroke(strokes[0]);
                g2.setColor(Color.black);
                g2.fillRoundRect(1, 1, getWidth()-3, getHeight()-3, 6, 6);
            } else if(color != null) {
                if(color.getAlpha() < 255) {
                    g2.setStroke(strokes[0]);
                    g2.setColor(Color.black);
                    g2.fillRoundRect(1, 1, getWidth()-3, getHeight()-3, 6, 6);
                    g2.setStroke(strokes[0]);
                    g2.setColor(Color.white);
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 6, 6);
                } else {
                    g2.setStroke(strokes[2]);
                    g2.setColor(Color.white);
                    g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 6, 6);
                }
            }
            if(color == null) {
                g2.setColor(Color.white);
                g2.fillRoundRect(getWidth()/2 - 1, 2, 2, getHeight()-4, 4, 4);
                g2.fillRoundRect(2, getHeight()/2 - 1, getWidth()-4, 2, 4, 4);
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 4, 4);
                return;
            }

            g2.setStroke(strokes[1]);
            g2.setColor(color);
            g2.fillRoundRect(2, 2, getWidth()-5, getHeight()-5, 6, 6);
        }

        @Override
        public void updateElement(MutableColor color, String invoker) {
            if(locked || invoker.equals("chip")) return;
            if(isSelected() && ColorPaletteWindow.isBrushSelected(selectedBrush)) {
                this.color.set(color);
                repaint();
            }
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
