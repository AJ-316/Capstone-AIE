package com.AIE.WindowPackage.ToolsPackage;

import com.AIE.WindowPackage.AbstractWindow;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Toolbar extends AbstractWindow {

    public static final String NAME = "ToolBar";
    private static ButtonGroup toolGroup;
    private static ArrayList<AbstractTool> toolList;

    public Toolbar(MainFrame mainFrame) {
        super(mainFrame, 50, 500, 200, 200);
        setTitle("ToolBar");
        createTools();
        setVisible(true);
    }

    private void createTools() {
        toolGroup = new ButtonGroup();
        toolList = new ArrayList<>();

        addTool(new Pencil());
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
