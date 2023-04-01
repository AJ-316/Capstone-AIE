package com.AIE.WindowPackage;

import com.AIE.EffectsPackage.AverageBlur;
import com.AIE.EffectsPackage.Effect;
import com.AIE.ImageLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EffectMenu extends JMenu implements ActionListener {

    public EffectMenu(MainFrame frame) {
        super("Effects");
        JMenu blurMenu = new JMenu("Blurs");
        addMenuItemTo(blurMenu, new AverageBlur(frame));
        add(blurMenu);
    }

    private void addMenuItemTo(JMenu menu, Effect effect) {
        JMenuItem item = new JMenuItem(effect.getName());
        item.setIcon(ImageLoader.loadIcon(effect.getName(), ImageLoader.MENU_ICON_SIZE));
        item.addActionListener(this);
        menu.add(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Effect.show(((JMenuItem)e.getSource()).getActionCommand());
    }

}
