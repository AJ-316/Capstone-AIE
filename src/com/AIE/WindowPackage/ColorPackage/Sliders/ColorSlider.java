package com.AIE.WindowPackage.ColorPackage.Sliders;

import com.AIE.WindowPackage.ColorPackage.Brush;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class ColorSlider extends JPanel {

    private static final ArrayList<ColorSlider> allSliders = new ArrayList<>();
    private static final int PREFERRED_WIDTH = 100;
    private static final int PREFERRED_HEIGHT = 30;
    protected ColorSliderUI ui;
    protected JSlider slider;
    protected JTextField inputField;
    protected JLabel label;

    protected void init(ColorSliderUI ui, int maxVal, int defaultVal, String text) {
        this.ui = ui;
        SliderUpdate sliderUpdate = new SliderUpdate();
        InputFieldUpdate inputFieldUpdate = new InputFieldUpdate();

        this.label = new JLabel(text, SwingConstants.RIGHT);
        this.slider = new JSlider(0, maxVal, defaultVal);
        this.slider.setUI(ui);
        this.slider.addMouseListener(sliderUpdate);
        this.slider.addMouseMotionListener(sliderUpdate);
        this.slider.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        this.inputField = new JTextField(3);
        this.inputField.setText(String.valueOf(defaultVal));
        this.inputField.getDocument().addDocumentListener(inputFieldUpdate);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        add(label);
        add(slider);
        add(inputField);

        allSliders.add(this);
    }

    protected void update(String name) {
        slider.repaint();
        inputField.setText(String.valueOf(slider.getValue()));
    }

    public static void updateAll(String invoker) {
        for(ColorSlider colorSlider : allSliders) {
            colorSlider.update(invoker);
        }

        ColorPalette.colorPickerWheel.updateColorPicker();
        ColorPalette.hexInput.update(invoker);
        Brush.getSelected().setColor(ColorChannelSlider.getColor());
    }

    private class InputFieldUpdate implements DocumentListener {

        private String lastTyped = "0";

        @Override
        public void insertUpdate(DocumentEvent e) {
            Runnable doVerify = () -> {
                if(verify())
                    slider.setValue(Integer.parseInt(inputField.getText()));
            };
            SwingUtilities.invokeLater(doVerify);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {}

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changed");
        }

        private boolean verify() {
            if(inputField.getText().isEmpty())
                return false;
            if(inputField.getText().matches(".*[a-zA-Z]+.*")) {
                inputField.setText(lastTyped);
                return false;
            } else {
                if(inputField.getText().length() > 3)
                    inputField.setText(inputField.getText().substring(0, 3));
                else {
                    inputField.setText(String.valueOf(Math.max(0, Math.min(
                            Integer.parseInt(inputField.getText()), slider.getMaximum()))));
                }
                lastTyped = inputField.getText();
                return true;
            }
        }
    }

    private class SliderUpdate extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            updateAll(slider.getName());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            updateAll(slider.getName());
        }
    }

    public int getValue() {
        return slider.getValue();
    }
    
    public float getUnitValue() {
        return slider.getValue()/(float)slider.getMaximum();
    }

}
