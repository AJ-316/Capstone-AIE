package com.AIE.WindowPackage;

import com.AIE.HeadLabel;

import javax.swing.*;
import java.awt.*;

public class ColorPallet extends AbstractWindow {

    public static String NAME = "ColorPallet";
    public static ColorPickerWheel colorPickerWheel;
    public static HEXInput hexInput;
    private static JPanel sliderPanel;

    public ColorPallet(MainFrame mainFrame) {
        super(mainFrame, 480, 400, MainFrame.SCREEN_WIDTH - 500, 200);
        setTitle("ColorPallet");
        setFocusableWindowState(true);
        setResizable(true);

        sliderPanel = new JPanel();
        hexInput = new HEXInput();
        sliderPanel.setPreferredSize(new Dimension(200, 300));
        sliderPanel.add(new HeadLabel("RGB", 150));
        sliderPanel.add(ColorChannelSlider.create(0));
        sliderPanel.add(ColorChannelSlider.create(1));
        sliderPanel.add(ColorChannelSlider.create(2));
        sliderPanel.add(hexInput);
        sliderPanel.add(new HeadLabel("HSV", 150));
        sliderPanel.add(HSVSlider.create(0));
        sliderPanel.add(HSVSlider.create(1));
        sliderPanel.add(HSVSlider.create(2));

        colorPickerWheel = new ColorPickerWheel();
        add(colorPickerWheel);
        add(sliderPanel);

        setVisible(true);
    }


}
