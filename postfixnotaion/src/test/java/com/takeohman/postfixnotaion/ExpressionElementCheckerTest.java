package com.takeohman.postfixnotaion;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by takeoh on 2017/09/02.
 */
public class ExpressionElementCheckerTest {

    @Test
    public void isOperator() throws Exception {
        ExpressionElementChecker ec = new ExpressionElementChecker();
        assertTrue(ec.isOperator("+"));
        assertTrue(ec.isOperator("-"));
        assertTrue(ec.isOperator("*"));
        assertTrue(ec.isOperator("/"));
        assertFalse(ec.isOperator("7"));
    }

    @Test
    public void isNumber() throws Exception {
        ExpressionElementChecker ec = new ExpressionElementChecker();
        assertFalse(ec.isNumber("+"));
        assertFalse(ec.isNumber("-"));
        assertFalse(ec.isNumber("*"));
        assertFalse(ec.isNumber("/"));
        assertTrue(ec.isNumber("77"));
        assertTrue(ec.isNumber("0.1"));
    }

}