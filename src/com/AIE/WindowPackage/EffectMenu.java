package com.AIE.WindowPackage;

import com.AIE.EffectsPackage.*;
import com.AIE.ImageLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EffectMenu extends JMenu implements ActionListener {

    public EffectMenu(MainFrame frame) {
        super("Effects");
        JMenu blurMenu = new JMenu("Blurs");
        addMenuItemTo(blurMenu, new GaussianBlur(frame));
        addMenuItemTo(blurMenu, new AverageBlur(frame));
        addMenuItemTo(blurMenu, new MotionBlur(frame));
        add(blurMenu);

        JMenu correctionMenu = new JMenu("Color Corrections");
        addMenuItemTo(correctionMenu, new BrightnessContrast(frame));
        addMenuItemTo(correctionMenu, new Sepia(frame));
        addMenuItemTo(correctionMenu, new Exposure(frame));
        addMenuItemTo(correctionMenu, new HSVEffect(frame));
        add(correctionMenu);

        addMenuItemTo(this, new BlackAndWhite(frame));
        addMenuItemTo(this, new InvertColor(frame));
    }

    private void addMenuItemTo(JMenu menu, Effect effect) {
        JMenuItem item = new JMenuItem(effect.getName());
        item.setIcon(ImageLoader.loadIcon(effect.getName(), ImageLoader.MENU_ICON_SIZE));
        item.addActionListener(this);
        item.addActionListener(effect.getEffectListener());
        menu.add(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Effect.show(((JMenuItem)e.getSource()).getActionCommand());
    }

}
