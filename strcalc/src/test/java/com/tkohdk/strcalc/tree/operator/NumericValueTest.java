package com.tkohdk.strcalc.tree.operator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by takeoh on 2018/03/11.
 */
public class NumericValueTest {
    @Test
    public void isLeafNode() throws Exception {
        NumericValue nv = new NumericValue("7");
        assertTrue(nv.isLeafNode());
    }

    @Test
    public void getValue() throws Exception {
        NumericValue nv = new NumericValue("7");
        assertEquals("7", nv.getValue());
    }

}