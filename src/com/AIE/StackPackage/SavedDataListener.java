package com.AIE.StackPackage;

import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

public interface SavedDataListener {

    boolean undo();
    boolean redo();
    String getAction();
    SmoothIcon getIcon();

}
