package tkohdk.lib.calcstr.checker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperatorCheckerTest {

    @Test
    public void isOperator() {
        OperatorChecker oc = new OperatorChecker();

        assertEquals(true, oc.isOperator("+"));
        assertEquals(true, oc.isOperator("-"));
        assertEquals(true, oc.isOperator("*"));
        assertEquals(true, oc.isOperator("×"));
        assertEquals(true, oc.isOperator("/"));
        assertEquals(true, oc.isOperator("÷"));
        assertEquals(true, oc.isOperator("^"));
        assertEquals(true, oc.isOperator("!"));
        assertEquals(false, oc.isOperator("1"));
    }

    @Test
    public void isBinaryOperator() {
        OperatorChecker oc = new OperatorChecker();
        assertEquals(true, oc.isBinaryOperator("+"));
        assertEquals(true, oc.isBinaryOperator("-"));
        assertEquals(true, oc.isBinaryOperator("*"));
        assertEquals(true, oc.isBinaryOperator("×"));
        assertEquals(true, oc.isBinaryOperator("/"));
        assertEquals(true, oc.isBinaryOperator("÷"));
        assertEquals(true, oc.isBinaryOperator("^"));
        assertEquals(false, oc.isBinaryOperator("!"));
        assertEquals(false, oc.isBinaryOperator("1"));
    }

    @Test
    public void isUnaryOperator() {
        OperatorChecker oc = new OperatorChecker();
        assertEquals(false, oc.isUnaryOperator("+"));
        assertEquals(false, oc.isUnaryOperator("-"));
        assertEquals(false, oc.isUnaryOperator("*"));
        assertEquals(false, oc.isUnaryOperator("×"));
        assertEquals(false, oc.isUnaryOperator("/"));
        assertEquals(false, oc.isUnaryOperator("÷"));
        assertEquals(false, oc.isUnaryOperator("^"));
        assertEquals(true, oc.isUnaryOperator("!"));
        assertEquals(false, oc.isUnaryOperator("1"));
    }
}