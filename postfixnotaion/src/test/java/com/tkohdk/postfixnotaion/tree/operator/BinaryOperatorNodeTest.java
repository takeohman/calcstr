package com.tkohdk.postfixnotaion.tree.operator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by takeoh on 2018/03/11.
 */
public class BinaryOperatorNodeTest {
    @Test
    public void getLeftNode() throws Exception {

        BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Plus);
        assertNull(bn.getLeftNode());
        assertNull(bn.getRightNode());

        bn.setLeftNode(new NumericValue("1"));
        assertEquals("1", bn.getLeftNode().getValue());
    }

    @Test
    public void setLeftNode() throws Exception {
        BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Plus);
        assertNull(bn.getLeftNode());
        assertNull(bn.getRightNode());

        bn.setLeftNode(new NumericValue("1"));
        assertNotNull(bn.getLeftNode());
        assertNull(bn.getRightNode());
    }

    @Test
    public void getRightNode() throws Exception {
        BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Plus);
        assertNull(bn.getLeftNode());
        assertNull(bn.getRightNode());

        bn.setRightNode(new NumericValue("1"));
        assertEquals("1",bn.getRightNode().getValue());
    }

    @Test
    public void setRightNode() throws Exception {
        BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Plus);
        assertNull(bn.getLeftNode());
        assertNull(bn.getRightNode());

        bn.setRightNode(new NumericValue("1"));
        assertNull(bn.getLeftNode());
        assertNotNull(bn.getRightNode());
    }

    @Test
    public void isLeafNode() throws Exception {
        NumericValue nv = new NumericValue("7");
        assertTrue(nv.isLeafNode());
    }

    @Test
    public void getValue() throws Exception {
        {
            BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Plus);
            bn.setLeftNode(new NumericValue("2"));
            bn.setRightNode(new NumericValue("3"));
            assertEquals("5", bn.getValue());
        }
        {
            BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Minus);
            bn.setLeftNode(new NumericValue("2"));
            bn.setRightNode(new NumericValue("3"));
            assertEquals("-1", bn.getValue());
        }
        {
            BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Multiplication);
            bn.setLeftNode(new NumericValue("2"));
            bn.setRightNode(new NumericValue("3"));
            assertEquals("6", bn.getValue());
        }
        {
            BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Division);
            bn.setLeftNode(new NumericValue("6"));
            bn.setRightNode(new NumericValue("3"));
            assertEquals("2", bn.getValue());
        }
        {
            BinaryOperatorNode bn = new BinaryOperatorNode(BinaryOperator.Pow);
            bn.setLeftNode(new NumericValue("2"));
            bn.setRightNode(new NumericValue("3"));
            assertEquals("8", bn.getValue());
        }
    }

}