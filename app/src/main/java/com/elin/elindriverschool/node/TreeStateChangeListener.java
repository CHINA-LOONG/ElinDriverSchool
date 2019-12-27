package com.elin.elindriverschool.node;

public interface TreeStateChangeListener {
    void onOpen(TreeItem treeItem, int position);
    void onClose(TreeItem treeItem, int position);
}
