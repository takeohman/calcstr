package com.tkohdk.strcalc.tokenizer;

import com.tkohdk.strcalc.checker.BigDecimalNumericChecker;
import com.tkohdk.strcalc.checker.FunctionChecker;
import com.tkohdk.strcalc.checker.OperatorChecker;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by takeoh on 2017/09/02.
 */
public class TokenValueCheckerTest {

    @Test
    public void isOperator() throws Exception {
        TokenValueChecker ec = new TokenValueChecker(new BigDecimalNumericChecker(), new FunctionChecker(), new OperatorChecker());
        assertTrue(ec.isOperator("+"));
        assertTrue(ec.isOperator("-"));
        assertTrue(ec.isOperator("*"));
        assertTrue(ec.isOperator("/"));
        assertFalse(ec.isOperator("7"));
    }

    @Test
    public void isNumeric() throws Exception {
        TokenValueChecker ec = new TokenValueChecker(new BigDecimalNumericChecker(), new FunctionChecker(), new OperatorChecker());
        assertFalse(ec.isNumeric("+"));
        assertFalse(ec.isNumeric("-"));
        assertFalse(ec.isNumeric("*"));
        assertFalse(ec.isNumeric("/"));
        assertTrue(ec.isNumeric("77"));
        assertTrue(ec.isNumeric("0.1"));
    }

}