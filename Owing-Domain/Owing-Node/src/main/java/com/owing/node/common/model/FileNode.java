package com.owing.node.common.model;

public interface FileNode<T extends FolderNode> {
    void connectFolder(T folderNode);
}
