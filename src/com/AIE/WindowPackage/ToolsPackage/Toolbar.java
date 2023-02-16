package com.AIE.WindowPackage.ToolsPackage;

import com.AIE.Canvas;
import com.AIE.WindowPackage.AbstractWindow;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Toolbar extends AbstractWindow {

    public static final String NAME = "ToolBar";
    private static ButtonGroup toolGroup;
    private static ArrayList<AbstractTool> toolList;

    public Toolbar(MainFrame mainFrame) {
        super(mainFrame, 50, 500, 100, 100);
        setTitle("ToolBar");
        createTools();
        setVisible(true);
    }

    private void createTools() {
        toolGroup = new ButtonGroup();
        toolList = new ArrayList<>();

        addTool(new Pencil());

//        toolGroup.setSelected(toolList.get(0).getModel(), true);
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

    public static class CanvasToolInteraction extends MouseAdapter {

        private final Canvas canvas;

        public CanvasToolInteraction(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            AbstractTool tool = getCurrentTool();
            if(tool == null) return;

            if(invalidInput(e)) {
                tool.inputConflictClear(canvas, e);
                return;
            }
                tool.pressed(canvas, e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            AbstractTool tool = getCurrentTool();
            if(tool == null) return;

            if(invalidInput(e)) {
                tool.inputConflictClear(canvas, e);
                return;
            }
            tool.released(canvas, e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            AbstractTool tool = getCurrentTool();
            if(tool == null) return;

            if(invalidInput(e)) {
                tool.inputConflictClear(canvas, e);
                return;
            }
            tool.dragged(canvas, e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            AbstractTool tool = getCurrentTool();
            if(tool != null)
                tool.moved(canvas, e);
        }

        private boolean invalidInput(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e))
                return SwingUtilities.isMiddleMouseButton(e);
            return true;
        }
    }

}
