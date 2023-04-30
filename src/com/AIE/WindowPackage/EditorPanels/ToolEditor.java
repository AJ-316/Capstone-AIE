package com.AIE.WindowPackage.EditorPanels;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ToolEditor extends JPanel {

    public ToolEditor() {
        super(new CardLayout());
        setPreferredSize(new Dimension(0, 35));
        setBorder(new MatteBorder(0, 0, 1, 0, new Color(0x505254)));
        setBackground(new Color(0x303234));
    }

    public void addToolEdits(String name, EditorComponent... components) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        for (EditorComponent editorComponent : components) {
            constraints.gridx++;
            constraints.insets.right = editorComponent.insetLeft;
            panel.add(editorComponent.component, constraints);
        }
        add(panel, name);
    }

    public static EditorComponent create(Component component, int insetLeft) {
        return new EditorComponent(component, insetLeft);
    }

    public void setCurrentEditor(String name) {
        ((CardLayout)getLayout()).show(this, name);
    }

    public record EditorComponent(Component component, int insetLeft) {}

}
