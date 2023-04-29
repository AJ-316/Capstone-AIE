package com.AIE.EffectsPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ValueUpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Effect {

    private static final ArrayList<Effect> effects = new ArrayList<>();

    private final JDialog configWindow;
    private final JButton applyBtn;
    private final JButton cancelBtn;
    private final JProgressBar progressBar;
    private final PreviewThread previewThread;
    protected EffectListener effectListener;
    private final String name;
    protected int previewReq = 0;
    protected boolean forceStop = false;

    public Effect(String name, MainFrame frame, int width, int height) {
        this.name = name;
        configWindow = new JDialog(frame);
        configWindow.setTitle(name);
        configWindow.setIconImage(ImageLoader.loadIcon(name, ImageLoader.MENU_ICON_SIZE).getImage());
        configWindow.setSize(width, height);
        configWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        configWindow.setLocationRelativeTo(null);
        configWindow.setResizable(false);
        configWindow.setModal(true);
        configWindow.setVisible(false);
        configWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        effectListener = new EffectListener(this);

        applyBtn = new JButton("Apply");
        applyBtn.addActionListener((e) -> confirmEffect());
        applyBtn.setEnabled(false);

        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener((e) -> closeOperation());

        progressBar = new JProgressBar();
        progressBar.setSize(progressBar.getHeight(), (int) (configWindow.getWidth()/1.4f));
        previewThread = new PreviewThread(this::previewEffect);

        effects.add(this);
    }

    protected void previewEffect() {
        if (previewReq > 0) {
            applyBtn.setEnabled(false);
            Canvas canvas = CanvasManager.getCurrentCanvas();
            if (canvas == null)
                return;
            BufferedImage newImg = applyEffect(canvas.getImage());

            if(forceStop) {
                forceStop = false;
                previewReq--;
                return;
            }

            canvas.setPreviewImage(newImg);

            applyBtn.setEnabled(true);
            previewReq--;
        }
    }

    protected void finalizeComponents(Component... components) {

        for(Component component : components) {
            configWindow.add(component);
        }

        configWindow.add(applyBtn);
        configWindow.add(cancelBtn);
        configWindow.add(progressBar);
    }

    public void confirmEffect() {
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null) return;
        canvas.confirmPreview();
        closeOperation();
    }

    protected abstract BufferedImage applyEffect(BufferedImage source);

    public void updatePreview() {
        previewReq++;
        if(previewReq > 1)
            forceStop = true;
        previewReq = Math.min(2, previewReq);
    }

    protected void closeOperation() {
        forceStop = true;
        Objects.requireNonNull(CanvasManager.getCurrentCanvas()).disablePreviewMode();
        setProgress(0);
        configWindow.setVisible(false);
        previewThread.pauseThread();
        previewReq = 0;
    }

    private static class PreviewThread {
        private volatile boolean wait = false;
        private final Thread thread;

        public PreviewThread(Runnable task) {
            thread = new Thread(() -> {
                while (true) {
                    if(!wait)
                        task.run();

                    try {
                        if(wait) {
                            synchronized (this) {
                                this.wait();
                            }
                            continue;
                        }
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
        }

        public void pauseThread() {
            wait = true;
        }

        public void resumeThread() {
            synchronized (thread) {
                wait = false;
                thread.notify();
            }
        }
    }

    protected void progressEffect(int value, float divisor) {
        setProgress(Math.round(100f * value / divisor));
    }

    protected void setProgress(int value) {
        progressBar.setVisible(value != 0 && value != 100);
        progressBar.setValue(Math.min(value, progressBar.getMaximum()));
    }

    protected boolean checkForceStop() {
        if(!forceStop)
            return false;

        setProgress(0);
        return true;
    }

    public String getName() {
        return name;
    }

    public EffectListener getEffectListener() {
        return effectListener;
    }

    public static void show(String name) {
        for(Effect effect: effects) {
            if(effect.name.equals(name)) {
                effect.forceStop = false;
                effect.configWindow.setVisible(true);
                effect.previewThread.resumeThread();
                break;
            }
        }
    }

    protected JSlider getSlider(JTextField textField, int tickSpacing, int min, int max, int defaultVal) {
        JSlider slider = new JSlider(min, max, defaultVal);

        textField.addActionListener(effectListener);
        textField.setText(String.valueOf(defaultVal));

        ValueUpdateListener.set(textField, slider);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setMinorTickSpacing(tickSpacing/4);
        slider.addMouseListener(effectListener);
        return slider;
    }

    protected static float normalize(float value, int min, int max) {
        return (value - min) / (max - min);
    }
}
