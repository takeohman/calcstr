package com.takeohman.postfixnotaion.tree.operator;

import com.takeohman.postfixnotaion.tree.node.TreeNode;
import com.takeohman.postfixnotaion.tree.node.TreeBinNode;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BinaryOperatorNode implements TreeBinNode {
    private BinaryOperator operator = null;
    private TreeNode leftNode = null;
    private TreeNode rightNode = null;


    public BinaryOperatorNode(BinaryOperator ope){
        this.operator = ope;
    }

    @Override
    public TreeNode getLeftNode(){return this.leftNode;}

    @Override
    public TreeNode setLeftNode(TreeNode left){
        TreeNode tmp = this.leftNode;
        this.leftNode =left;
        return tmp;
    }

    @Override
    public TreeNode getRightNode(){return this.rightNode;}

    @Override
    public TreeNode setRightNode(TreeNode right){
        TreeNode tmp = this.rightNode;
        this.rightNode = right;
        return tmp;
    }

    @Override
    public Boolean isLeafNode(){
        return false;
    }

    @Override
    public String getValue() {
        if (this.leftNode != null){
            // 左辺は必須
            String _left_value = this.leftNode.getValue();

            // 右辺が空の場合は左辺だけ返す。
            if (this.rightNode == null){
                return _left_value;
            }

            String _right_value = this.rightNode.getValue();

            BigDecimal bc = new BigDecimal(_left_value);
            BigDecimal ans = null;
            switch (this.operator){
                case Plus:
                    ans = bc.add(new BigDecimal(_right_value));
                    break;
                case Minus:
                    ans = bc.subtract(new BigDecimal(_right_value));
                    break;
                case Multiplication:
                    ans = bc.multiply(new BigDecimal(_right_value));
                    break;
                case Division:
                    ans = bc.divide(new BigDecimal(_right_value), RoundingMode.HALF_EVEN);
                    break;
                case Pow:
                    ans = bc.pow(new BigDecimal(_right_value).intValue());
                    break;
            }
            if (ans != null) {
                return ans.toString();
            }
        }
        return null;
    }
}
