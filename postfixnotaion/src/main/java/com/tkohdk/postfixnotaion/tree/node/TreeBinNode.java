package com.tkohdk.postfixnotaion.tree.node;

/**
 *
 */
public interface TreeBinNode extends TreeNode {
    TreeNode setLeftNode(TreeNode left);
    TreeNode setRightNode(TreeNode right);
    TreeNode getLeftNode();
    TreeNode getRightNode();
}
