package com.AIE.WindowPackage.ColorPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.*;
import com.AIE.WindowPackage.ColorPackage.Sliders.*;
import com.AIE.WindowPackage.ColorPackage.Sliders.HSVSliders.HSV;
import com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders.RGB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorPalette extends AbstractWindow {

    public static String NAME = "ColorPallet";
    private static JComboBox<String> brushSelection;
    private static JPanel sliderPanel;

    private static final int DEFAULT_WIDTH = 330;
    private static final int MORE_WIDTH = 520;
    private static final int DEFAULT_HEIGHT = 300;

    protected static Brush PRIMARY;
    protected static Brush SECONDARY;

    public static final ArrayList<PaletteElement> ELEMENTS = new ArrayList<>();
    private static final MutableColor COLOR = new MutableColor(255, 0, 0);

    public ColorPalette(MainFrame mainFrame) {
        super("palette", mainFrame, DEFAULT_WIDTH, DEFAULT_HEIGHT, MainFrame.SCREEN_WIDTH - 500, 200);
        setTitle("ColorPallet");
        setLayout(null);

        createBrush();
        createSliders();
        createColorPickerWheel();
        createSliderButton();

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
                setSize(MORE_WIDTH, DEFAULT_HEIGHT);
                setFocusableWindowState(true);
            } else {
                btn.setText(moreText);
                sliderPanel.setVisible(false);
                setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
        sliderPanel.setBounds(315, 0, 200, 300);

        sliderPanel.add(new HeadLabel("RGB", 150));
        RGB.create(sliderPanel);
        sliderPanel.add(new HEXInput());

        sliderPanel.add(new HeadLabel("HSV", 150));
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
    }

    public static void update(MutableColor color, String invoker) {
        COLOR.set(color);
        for(PaletteElement element : ELEMENTS) {
            element.updateElement(COLOR, invoker);
        }
    }

    public static void selectBrush(int id) {
        brushSelection.setSelectedIndex(id);

        String invoker = "brush";
        switch (id) {
            case 0 -> {
                PRIMARY.setSelected(true);
                ColorPalette.update(PRIMARY.getColor(), invoker);
            }
            case 1 -> {
                SECONDARY.setSelected(true);
                ColorPalette.update(SECONDARY.getColor(), invoker);
            }
        }
    }

    public static Brush getBrush(int id) {
        return id == 0 ? PRIMARY : SECONDARY;
    }
}
