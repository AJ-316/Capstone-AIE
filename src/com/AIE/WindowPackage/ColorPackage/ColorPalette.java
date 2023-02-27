package com.AIE.WindowPackage.ColorPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.*;
import com.AIE.WindowPackage.ColorPackage.Sliders.ColorChannelSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.HEXInput;
import com.AIE.WindowPackage.ColorPackage.Sliders.HSVSlider;

import javax.swing.*;

public class ColorPalette extends AbstractWindow {

    public static String NAME = "ColorPallet";
    public static ColorPickerWheel colorPickerWheel;
    public static HEXInput hexInput;
    private static JPanel sliderPanel;

    private static final int DEFAULT_WIDTH = 330;
    private static final int MORE_WIDTH = 520;
    private static final int DEFAULT_HEIGHT = 360;

    public ColorPalette(MainFrame mainFrame) {
        super(mainFrame, DEFAULT_WIDTH, DEFAULT_HEIGHT, MainFrame.SCREEN_WIDTH - 500, 200);
        setTitle("ColorPallet");
        setLayout(null);

        sliderPanel = new JPanel();
        sliderPanel.add(new HeadLabel("RGB", 150));
        sliderPanel.add(ColorChannelSlider.create(0));
        sliderPanel.add(ColorChannelSlider.create(1));
        sliderPanel.add(ColorChannelSlider.create(2));
        hexInput = new HEXInput();
        sliderPanel.add(hexInput);
        sliderPanel.add(new HeadLabel("HSV", 150));
        sliderPanel.add(HSVSlider.create(0));
        sliderPanel.add(HSVSlider.create(1));
        sliderPanel.add(HSVSlider.create(2));
        sliderPanel.setBounds(315, 10, 200, 300);
        sliderPanel.setVisible(false);
        add(sliderPanel);

        Brush.createBrushes(10, 5);
        add(Brush.brushContainer);

        colorPickerWheel = new ColorPickerWheel(105, 40);
        add(colorPickerWheel);

        JButton btn = new JButton("More >>");
        btn.setBounds(190, 5, 100, 25);
        btn.addActionListener(e -> {
            if(btn.getText().equals("More >>")) {
                btn.setText("<< Less");
                sliderPanel.setVisible(true);
                setSize(MORE_WIDTH, DEFAULT_HEIGHT);
                setFocusableWindowState(true);
            } else {
                btn.setText("More >>");
                sliderPanel.setVisible(false);
                setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                setFocusableWindowState(false);
            }
        });
        add(btn);

        setVisible(true);
    }
}
