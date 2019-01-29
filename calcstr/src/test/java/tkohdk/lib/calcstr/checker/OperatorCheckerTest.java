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
        assertEquals(true, oc.isOperator("e"));
        assertEquals(true, oc.isOperator("E"));
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
        assertEquals(false, oc.isBinaryOperator("e"));
        assertEquals(false, oc.isBinaryOperator("E"));
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
        // TODO : "e", "E"
        assertEquals(false, oc.isUnaryOperator("e"));
        assertEquals(false, oc.isUnaryOperator("E"));
        assertEquals(false, oc.isUnaryOperator("1"));
    }
}