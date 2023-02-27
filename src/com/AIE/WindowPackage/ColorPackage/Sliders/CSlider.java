package com.AIE.WindowPackage.ColorPackage.Sliders;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.PaletteElement;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.ColorSliderUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
//        ColorPalette.colorPickerWheel.updateColorPicker();
//        ColorPalette.hexInput.update(invoker);
//        Brush.getSelected().setColor(ColorChannelSlider.getColor());
public abstract class CSlider extends JPanel implements PaletteElement {

    private static final int PREFERRED_WIDTH = 100;
    private static final int PREFERRED_HEIGHT = 30;
    protected JLabel label;
    protected JSlider slider;
    protected JTextField inputField;
    protected final String ELEMENT_NAME;

    public CSlider(String name, String text, int maxVal, int defaultVal, ColorSliderUI ui) {
        this.ELEMENT_NAME = name;
        this.label = new JLabel(text, SwingConstants.RIGHT);
        add(label);

        SliderUpdate sliderUpdate = new SliderUpdate(this);
        this.slider = new JSlider(0, maxVal, defaultVal);
        this.slider.setUI(ui);
        this.slider.addMouseListener(sliderUpdate);
        this.slider.addMouseMotionListener(sliderUpdate);
        this.slider.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        add(slider);

        InputFieldUpdate inputFieldUpdate = new InputFieldUpdate(this);
        this.inputField = new JTextField(3);
        this.inputField.setText(String.valueOf(defaultVal));
        this.inputField.addFocusListener(inputFieldUpdate);
        this.inputField.addActionListener(inputFieldUpdate);
        this.inputField.getDocument().addDocumentListener(inputFieldUpdate);
        add(inputField);

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, -4));
        setVisible(true);

        ColorPalette.ELEMENTS.add(this);
    }

    public void updateInputValue() {
        int currentValue = Integer.parseInt(inputField.getText());
        if(currentValue == slider.getValue())
            return;

        inputField.setText(String.valueOf(slider.getValue()));
    }

    public void updateSliderValue() {
        int newValue = Integer.parseInt(inputField.getText());
        System.out.println(newValue + ", " + slider.getValue());
        if(slider.getValue() == newValue)
            return;

        slider.setValue(newValue);
    }

    public void setValue(int value) {
        slider.setValue(value);
        updateInputValue();
        repaint();
    }

    public void setText(String text) {
        inputField.setText(text);
        updateSliderValue();
        repaint();
    }

    public int getUnitVal() {
        return slider.getValue();
    }

    public float getVal() {
        return slider.getValue() / (float)slider.getMaximum();
    }

    public abstract void updateColor();

    private static class SliderUpdate extends MouseAdapter {
        private final CSlider cSlider;

        public SliderUpdate(CSlider cSlider) {
            this.cSlider = cSlider;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            cSlider.updateColor();
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            cSlider.updateColor();
        }
    }
    private static class InputFieldUpdate extends FocusAdapter implements DocumentListener, ActionListener {

        private String lastCorrectValue = "0";
        private final CSlider cSlider;

        public InputFieldUpdate(CSlider cSlider) {
            this.cSlider = cSlider;
        }

        private void verifyValue() throws NumberFormatException {
            String value = cSlider.inputField.getText();
            if(value.matches(".*[a-zA-Z]+.*")) {
                cSlider.inputField.setText(lastCorrectValue);
                return;
            }
            cSlider.setText(String.valueOf(Math.max(0,
                    Math.min(Integer.parseInt(value), cSlider.slider.getMaximum()))));
            lastCorrectValue = cSlider.inputField.getText();

            cSlider.updateColor();
        }

        private void verifyLength() {
            int length = cSlider.inputField.getText().length();
            if(length > 3 || length == 0)
                cSlider.inputField.setText(lastCorrectValue);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            Runnable doVerify = this::verifyLength;
            SwingUtilities.invokeLater(doVerify);
        }

        @Override
        public void focusLost(FocusEvent e) throws NumberFormatException {
            verifyValue();
        }
        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {
            verifyValue();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {}
        @Override
        public void changedUpdate(DocumentEvent e) {}
    }
}
