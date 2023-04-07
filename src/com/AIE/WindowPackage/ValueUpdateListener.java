package com.AIE.WindowPackage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ValueUpdateListener extends MouseAdapter implements DocumentListener {

    private final JTextField textField;
    private final JSlider slider;

    public static void set(JTextField textField, JSlider slider) {
        ValueUpdateListener listener = new ValueUpdateListener(textField, slider);
        textField.getDocument().addDocumentListener(listener);
        slider.addMouseListener(listener);
        slider.addMouseMotionListener(listener);
    }

    private ValueUpdateListener(JTextField textField, JSlider slider) {
        this.textField = textField;
        this.slider = slider;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        textField.setText(String.valueOf(slider.getValue()));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mousePressed(e);
        ((JSlider)e.getSource()).repaint();
    }

    private void setSliderValue() {
        if(textField.getText().matches("\\d+")) {
            int value = Integer.parseInt(textField.getText());
            slider.setValue(Math.max(0, Math.min(slider.getMaximum(), value)));
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        setSliderValue();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        setSliderValue();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
