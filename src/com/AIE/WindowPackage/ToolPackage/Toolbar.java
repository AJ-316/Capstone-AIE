package com.AIE.WindowPackage.ToolPackage;

import com.AIE.WindowPackage.AbstractWindow;
import com.AIE.WindowPackage.PanelsPackage.ToolEditor;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ToolPackage.Shapes.ShapesTool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends AbstractWindow {

    private static ButtonGroup toolGroup;
    private static ArrayList<AbstractTool> toolList;
    public static ToolEditor EDITOR;
    public static boolean LOCKED;

    public Toolbar(MainFrame mainFrame) {
        super("ToolBar", mainFrame, 0, 500, 130, 250);
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));

        EDITOR = new ToolEditor();
        mainFrame.add(EDITOR, BorderLayout.NORTH);

        createTools();
        setVisible(true);
    }

    public static void locked(boolean locked) {
        LOCKED = locked;
        for(AbstractTool tool : toolList) {
            tool.setEnabled(!locked);
        }
    }

    private void createTools() {
        toolGroup = new ButtonGroup();
        toolList = new ArrayList<>();

        addTool(new PencilTool());
        addTool(new EraserTool());
        addTool(new BrushTool());
        addTool(new BucketTool());
        addTool(new DropperTool());
        addTool(new ShapesTool());
    }

    private void addTool(AbstractTool tool) {
        toolGroup.add(tool);
        add(tool);
        toolList.add(tool);
    }

    public static AbstractTool getCurrentTool() {
        for(AbstractTool tool : toolList) {
            if(tool.isSelected())
                return tool;
        }
        return null;
    }

}
