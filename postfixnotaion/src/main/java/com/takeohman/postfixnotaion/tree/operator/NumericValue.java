package com.takeohman.postfixnotaion.tree.operator;

import com.takeohman.postfixnotaion.tree.node.TreeNode;

public class NumericValue implements TreeNode {
    private String value = null;

    public NumericValue(String val){
        this.value = val;
    }

    @Override
    public Boolean isLeafNode(){
        return true;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
