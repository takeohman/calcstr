package com.tkohdk.postfixnotaion.tree.node;

public interface TreeUniNode extends TreeNode {
    TreeNode setNode(TreeNode right);
    TreeNode getNode();
}
