package com.AIE.WindowPackage.ColorPackage.Sliders;

import com.AIE.WindowPackage.ColorPackage.Sliders.ColorChannelSlider;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class HEXInput extends JPanel {
    private final JTextField inputField;
    private final InputUpdate inputUpdate;

    public HEXInput(){
        super(new FlowLayout(FlowLayout.CENTER, 55, 0));
        setPreferredSize(new Dimension(295, 40));
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(80, 26));
        inputField.setText("FF0000");
        inputUpdate = new InputUpdate();
        inputField.getDocument().addDocumentListener(inputUpdate);
        inputField.addFocusListener(inputUpdate);
        inputField.addActionListener(inputUpdate);
        add(new JLabel("Hex:"));
        add(inputField);
    }

    public void update(String invoker) {
        if(invoker.equals("hex"))
            return;

        inputUpdate.updateHex(Integer.toHexString(
                ColorChannelSlider.getColor().getRGB()).substring(2).toUpperCase());
    }

    private class InputUpdate extends FocusAdapter implements DocumentListener, ActionListener {
        private String lastTyped = inputField.getText();

        public void updateHex(String hex) {
            inputField.setText(hex.toUpperCase());
            lastTyped = inputField.getText();
        }

        private void verifyHex() {
            if(inputField.getText().matches("^([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$") &&
                    inputField.getText().length() == 6) {
                inputField.setText(inputField.getText().toUpperCase());
                lastTyped = inputField.getText();
                ColorChannelSlider.setColor(Integer.decode("0x"+lastTyped));
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
        public void removeUpdate(DocumentEvent e) {}
        @Override
        public void changedUpdate(DocumentEvent e) {}
    }

}