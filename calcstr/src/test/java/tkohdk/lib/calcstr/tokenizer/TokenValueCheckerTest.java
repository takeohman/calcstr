package tkohdk.lib.calcstr.tokenizer;

import tkohdk.lib.calcstr.checker.BigDecimalNumericChecker;
import tkohdk.lib.calcstr.checker.FunctionChecker;
import tkohdk.lib.calcstr.checker.OperatorChecker;

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