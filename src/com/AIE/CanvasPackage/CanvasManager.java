package com.AIE.CanvasPackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CanvasManager extends JPanel {

    private static ArrayList<Canvas> canvasList;
    public static int currentCanvasIndex;

    public CanvasManager(int width, int height) {
        super(null);
        setPreferredSize(new Dimension(width, height));
        canvasList = new ArrayList<>();
        Font font = getFont().deriveFont(14f);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("List.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("ToolTip.font", font);
    }

    public void addCanvas(Canvas... canvasesToAdd) {

        canvasList.addAll(Arrays.asList(canvasesToAdd));

        currentCanvasIndex = canvasesToAdd.length-1;

        setCurrentCanvas(currentCanvasIndex);
    }

    public void setCurrentCanvas(int i) {

        remove(canvasList.get(currentCanvasIndex));
        add(canvasList.get(i));
        currentCanvasIndex = i;
        repaint();
    }

    // Need to change method
    public static Canvas getCurrentCanvas() {
        if(canvasList.size() == 0) return null;

        return canvasList.get(currentCanvasIndex);
    }

}
