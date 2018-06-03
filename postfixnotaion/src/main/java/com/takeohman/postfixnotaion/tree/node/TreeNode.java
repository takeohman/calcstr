package com.takeohman.postfixnotaion.tree.node;

public interface TreeNode {
    /**
     * オブジェクトがTreeのNodeの場合にtrueを返す。
     *
     * @return Boolean
     */
    Boolean isLeafNode();

    /**
     * Elementの値を評価して返す。
     * @return String
     */
    String getValue();

    /**
     * 式（値）の文字列をそのまま返す。
     * @return String
     */
    String getExpression();
}
