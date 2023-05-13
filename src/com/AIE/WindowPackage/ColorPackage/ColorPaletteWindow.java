package com.AIE.WindowPackage.ColorPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.*;
import com.AIE.WindowPackage.ColorPackage.Sliders.*;
import com.AIE.WindowPackage.ColorPackage.Sliders.HSVSliders.HSV;
import com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders.RGBA;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorPaletteWindow extends AbstractWindow {

    private static JComboBox<String> brushSelection;
    private static JPanel sliderPanel;

    private static final int DEFAULT_WIDTH = 330;
    private static final int MORE_WIDTH = 520;
    private static final int DEFAULT_HEIGHT = 400;
    private static final int MORE_HEIGHT = 400;//320;

    protected static Brush PRIMARY;
    protected static Brush SECONDARY;

    public static final ArrayList<PaletteElement> ELEMENTS = new ArrayList<>();
    private static final MutableColor COLOR = new MutableColor(255, 0, 0);

    public ColorPaletteWindow(MainFrame mainFrame) {
        super("Color Palette", mainFrame, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(null);

        createBrush();
        createSliders();
        createColorPickerWheel();
        createSliderButton();
        Palette palette = new Palette();
        palette.addToWindow(this, 15, 280, DEFAULT_WIDTH-40, 80);


    }

    public void setRelativeLocation(MainFrame frame) {
        //mainFrame.getLocation().x + MainFrame.WINDOW_WIDTH - DEFAULT_WIDTH, 350
        setLocation(frame.getLocation().x + MainFrame.WINDOW_WIDTH - DEFAULT_WIDTH,
                frame.getLocation().y + MainFrame.WINDOW_HEIGHT - DEFAULT_HEIGHT);
        setVisible(true);
    }

    private void createSliderButton() {
        String moreText = "More >>";
        String lessText = "<< Less";
        JButton btn = new JButton(moreText);
        btn.setBounds(205, 5, 100, 25);
        btn.addActionListener(e -> {
            if(btn.getText().equals(moreText)) {
                btn.setText(lessText);
                sliderPanel.setVisible(true);
                setSize(MORE_WIDTH, MORE_HEIGHT);
                setLocation(getLocation().x - (MORE_WIDTH-DEFAULT_WIDTH), getLocation().y);
                setFocusableWindowState(true);
            } else {
                btn.setText(moreText);
                sliderPanel.setVisible(false);
                setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                setLocation(getLocation().x + (MORE_WIDTH-DEFAULT_WIDTH), getLocation().y);
                setFocusableWindowState(false);
            }
        });
        add(btn);
    }

    private void createColorPickerWheel() {
        ColorPickerWheel colorPickerWheel = new ColorPickerWheel(105, 40);
        add(colorPickerWheel);
    }

    private void createSliders() {
        sliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        sliderPanel.setBounds(315, 0, 200, 320);

        HeadLabel rgb = new HeadLabel("RGBA", 150);
        rgb.setPreferredSize(new Dimension(rgb.getPreferredSize().width, rgb.getPreferredSize().height + 20));
        rgb.setVerticalAlignment(SwingConstants.BOTTOM);
        sliderPanel.add(rgb);
        RGBA.create(sliderPanel);
        sliderPanel.add(new HEXInput());

        HeadLabel hsv = new HeadLabel("HSV", 150);
        hsv.setPreferredSize(new Dimension(hsv.getPreferredSize().width, hsv.getPreferredSize().height + 20));
        hsv.setVerticalAlignment(SwingConstants.BOTTOM);
        sliderPanel.add(hsv);
        HSV.create(sliderPanel);
        add(sliderPanel);
    }

    private void createBrush() {
        PRIMARY = new Brush(0, new MutableColor(255, 0, 0));
        SECONDARY = new Brush(1, new MutableColor(0, 255, 0));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(PRIMARY);
        buttonGroup.add(SECONDARY);

        brushSelection = new JComboBox<>(new String[]{"Primary", "Secondary"});
        brushSelection.addActionListener(e ->
                selectBrush(((JComboBox<?>)e.getSource()).getSelectedIndex()));
        brushSelection.setBounds(10, 5, 100, 25);

        PRIMARY.setLocation(20, 40);
        SECONDARY.setLocation(10 + (int) (Brush.SIZE/1.2f), (int) (Brush.SIZE/1.2f) + 30);
        add(PRIMARY);
        add(SECONDARY);
        add(brushSelection);

        PRIMARY.setSelected(true);
    }

    public static void update(MutableColor color, String invoker) {
        COLOR.set(color);
        if(!invoker.equals("alpha"))
            COLOR.setRGBA(COLOR.getRed(), COLOR.getGreen(), COLOR.getBlue(), RGBA.getAlphaUnit());
        for(PaletteElement element : ELEMENTS) {
            element.updateElement(COLOR, invoker);
        }
    }

    public static boolean isBrushSelected(int id) {
        if(id == 0 && PRIMARY.isSelected()) return true;
        return id == 1 && SECONDARY.isSelected();
    }

    public static void selectBrush(int id) {
        brushSelection.setSelectedIndex(id);

        String invoker = "brush";
        switch (id) {
            case 0 -> {
                PRIMARY.setSelected(true);
                ColorPaletteWindow.update(PRIMARY.getColor(), invoker);
            }
            case 1 -> {
                SECONDARY.setSelected(true);
                ColorPaletteWindow.update(SECONDARY.getColor(), invoker);
            }
        }
    }

    public static Brush getBrush(int id) {
        return id == 0 ? PRIMARY : SECONDARY;
    }
}
