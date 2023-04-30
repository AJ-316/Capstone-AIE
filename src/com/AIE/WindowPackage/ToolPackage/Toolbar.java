package com.AIE.WindowPackage.ToolPackage;

import com.AIE.WindowPackage.AbstractWindow;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends AbstractWindow {

    public static final String NAME = "ToolBar";
    private static ButtonGroup toolGroup;
    private static ArrayList<AbstractTool> toolList;
    public static ToolEditor EDITOR;

    public Toolbar(MainFrame mainFrame) {
        super("tools", mainFrame, 140, 500, 200, 200);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setTitle("ToolBar");

        EDITOR = new ToolEditor();
        mainFrame.add(EDITOR, BorderLayout.NORTH);

        createTools();
        setVisible(true);
    }

    private void createTools() {
        toolGroup = new ButtonGroup();
        toolList = new ArrayList<>();

        addTool(new PencilTool());
        addTool(new EraserTool());
        addTool(new BrushTool());
        addTool(new BucketTool());
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
