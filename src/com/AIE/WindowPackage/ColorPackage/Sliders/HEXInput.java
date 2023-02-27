package com.AIE.WindowPackage.ColorPackage.Sliders;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.PaletteElement;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class HEXInput extends JPanel implements PaletteElement, DocumentListener, ActionListener, FocusListener {
    private static final int PREFERRED_WIDTH = 80;
    private static final int PREFERRED_HEIGHT = 26;
    private String lastTyped;
    private final MutableColor color = new MutableColor(0,0,0);
    private final JTextField inputField;
    private final static String ELEMENT_NAME = "hex";

    public HEXInput() {
        super(new FlowLayout(FlowLayout.CENTER, 55, 0));
        add(new JLabel("Hex:"));

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        inputField.getDocument().addDocumentListener(this);
        inputField.addFocusListener(this);
        inputField.addActionListener(this);
        updateHex("FF0000");
        add(inputField);

        ColorPalette.ELEMENTS.add(this);
    }

    @Override
    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME))
            return;

        updateHex(Integer.toHexString(
                color.getRGB()).substring(2).toUpperCase());
    }

    public void updateHex(String hex) {
        inputField.setText(hex.toUpperCase());
        lastTyped = inputField.getText();
    }

    private void verifyHex() {
        if(inputField.getText().matches("^([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$") &&
                inputField.getText().length() == 6) {
            inputField.setText(inputField.getText().toUpperCase());
            lastTyped = inputField.getText();
            color.setRGB(Integer.decode("0x"+lastTyped));
            ColorPalette.update(color, ELEMENT_NAME);
        } else {
            inputField.setText(lastTyped);
        }
    }
    private void verifyLength() {
        if(inputField.getText().length() > 6) {
            if(inputField.getText().startsWith("#")) {
                inputField.setText(inputField.getText().substring(1));
                return;
            }
            inputField.setText(lastTyped);
        }
    }
    @Override
    public void insertUpdate(DocumentEvent e) {
        Runnable doVerify = this::verifyLength;
        SwingUtilities.invokeLater(doVerify);
    }

    @Override
    public void focusLost(FocusEvent e) {
        verifyHex();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        verifyHex();
    }
    @Override
    public void focusGained(FocusEvent e) {}
    @Override
    public void removeUpdate(DocumentEvent e) {}
    @Override
    public void changedUpdate(DocumentEvent e) {}

}