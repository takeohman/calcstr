package tkohdk.lib.calcstr.tree.operator;

import tkohdk.lib.calcstr.tree.node.TreeNode;
import tkohdk.lib.calcstr.tree.node.TreeUniNode;

import java.math.BigDecimal;

/**
 * 単項演算子のノードクラス
 */
public class UnaryOperatorNode implements TreeUniNode {

    private UnaryOperator operator = null;

    private TreeNode node = null;


    public UnaryOperatorNode(UnaryOperator ope){
        this.operator = ope;
    }

    @Override
    public TreeNode setNode(TreeNode node) {
        TreeNode _tmp = this.node;
        this.node = node;
        return _tmp;
    }

    @Override
    public TreeNode getNode() {
        return this.node;
    }

    @Override
    public Boolean isLeafNode() {
        return false;
    }

    @Override
    public String getValue() {
        if (this.node != null){
            String _tmp = this.node.getValue();
            BigDecimal ans = null;
            BigDecimal bc = new BigDecimal(_tmp);
            switch(this.operator){
                case Sin:
                    ans = new BigDecimal(Math.sin(bc.doubleValue()));
                    break;
                case Cos:
                    ans = new BigDecimal(Math.cos(bc.doubleValue()));
                    break;
                case Tan:
                    ans = new BigDecimal(Math.tan(bc.doubleValue()));
                    break;
                case Log:
                    ans = new BigDecimal(Math.log(bc.doubleValue()));
                    break;
                case Log10:
                    ans = new BigDecimal(Math.log10(bc.doubleValue()));
                    break;
                case Log1p:
                    ans = new BigDecimal(Math.log1p(bc.doubleValue()));
                    break;
                case Factorial:
                    ans = this.factorial(bc.toString());
                    break;
            }

            if (ans != null){
                return ans.toString();
            }
        }
        return null;
    }

    @Override
    public String getExpression() {
        return "";
    }

    protected BigDecimal factorial(String intValStr){

        BigDecimal decOrg = new BigDecimal(intValStr);

        BigDecimal decToCalc = decOrg.abs();
        BigDecimal srcNum = new BigDecimal(1);

        long idx = 1;

        while(decToCalc.compareTo(new BigDecimal(idx))  > -1){
            srcNum = srcNum.multiply(new BigDecimal(idx));
            idx++;
        }


        return srcNum.multiply(new BigDecimal(decOrg.signum() == -1? -1:1));
    }
}
