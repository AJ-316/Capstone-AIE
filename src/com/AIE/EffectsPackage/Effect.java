package com.AIE.EffectsPackage;

import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Effect extends JDialog implements ActionListener {

    protected JButton applyBtn;
    protected EffectProgress progress;

    public Effect(MainFrame frame, String title, int width, int height) {
        super(frame);
        setTitle(title);
        setLayout(new FlowLayout());
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(false);

        applyBtn = new JButton("Apply");
    }

    protected void setEffectProgress(MainFrame frame, EffectAction action) {
        progress = new EffectProgress(frame, action);
        applyBtn.addActionListener(this);
        add(applyBtn);
    }

    public static class EffectProgress extends JDialog implements Runnable {

        private final JProgressBar progressBar;
        private final EffectAction action;

        public EffectProgress(MainFrame frame, EffectAction action) {
            super(frame);
            setTitle("Applying Effect...");
            setLayout(new FlowLayout());
            setSize(200, 75);
            setLocationRelativeTo(null);
            setModal(true);

            this.action = action;
            progressBar = new JProgressBar(0, 100);
            add(progressBar);

            setVisible(false);
        }

        public void start() {
            new Thread(this).start();
            setVisible(true);
        }

        public void progress(int value) {
            progressBar.setValue(value);
        }

        @Override
        public void run() {
            action.doAction();
            setVisible(false);
        }
    }

    public interface EffectAction {
        void doAction();
    }

}
