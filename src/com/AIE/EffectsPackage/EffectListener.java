package com.AIE.EffectsPackage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class EffectListener extends MouseAdapter implements  MouseMotionListener, ActionListener, ChangeListener {

    private final Effect effect;

    public EffectListener(Effect effect) {
        this.effect = effect;
    }

    public void mouseDragged(MouseEvent e) {
        effect.updatePreview();
    }
    public void mouseReleased(MouseEvent e) {
        effect.updatePreview();
    }
    public void actionPerformed(ActionEvent e) {
        effect.updatePreview();
    }
    public void stateChanged(ChangeEvent e) {
        effect.updatePreview();
    }
}
