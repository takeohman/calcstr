package tkohdk.lib.calcstr.tokenizer;

import tkohdk.lib.calcstr.checker.BigDecimalNumericChecker;
import tkohdk.lib.calcstr.checker.FunctionChecker;
import tkohdk.lib.calcstr.checker.OperatorChecker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenElementListTest {

    @Test
    public void add() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );

        TokenElementList tl =  new TokenElementList();

        assertEquals(tl.getBinaryOperatorCount(), 0);
        assertEquals(tl.getUnaryOperatorCount(), 0);
        assertEquals(tl.getFunctionItemCount(), 0);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.add(new TokenElement(tc, 1, "+"));
        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 0);
        assertEquals(tl.getFunctionItemCount(), 0);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.add(new TokenElement(tc, 2, "!"));
        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 1);
        assertEquals(tl.getFunctionItemCount(), 0);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.add(new TokenElement(tc, 3, "sin"));
        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 1);
        assertEquals(tl.getFunctionItemCount(), 1);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.add(new TokenElement(tc, 4, "7"));
        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 1);
        assertEquals(tl.getFunctionItemCount(), 1);
        assertEquals(tl.getNumericItemCount(), 1);
    }

    @Test
    public void add1() {
    }

    @Test
    public void remove() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );

        TokenElementList tl =  new TokenElementList();
        tl.add(new TokenElement(tc, 1, "+"));
        tl.add(new TokenElement(tc, 2, "!"));
        tl.add(new TokenElement(tc, 3, "sin"));
        tl.add(new TokenElement(tc, 4, "7"));

        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 1);
        assertEquals(tl.getFunctionItemCount(), 1);
        assertEquals(tl.getNumericItemCount(), 1);

        tl.remove(tl.size()-1);

        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 1);
        assertEquals(tl.getFunctionItemCount(), 1);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.remove(tl.size()-1);

        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 1);
        assertEquals(tl.getFunctionItemCount(), 0);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.remove(tl.size()-1);

        assertEquals(tl.getBinaryOperatorCount(), 1);
        assertEquals(tl.getUnaryOperatorCount(), 0);
        assertEquals(tl.getFunctionItemCount(), 0);
        assertEquals(tl.getNumericItemCount(), 0);

        tl.remove(tl.size()-1);

        assertEquals(tl.getBinaryOperatorCount(), 0);
        assertEquals(tl.getUnaryOperatorCount(), 0);
        assertEquals(tl.getFunctionItemCount(), 0);
        assertEquals(tl.getNumericItemCount(), 0);

    }

    @Test
    public void getNumericItemCount() {
    }

    @Test
    public void getFunctionItemCount() {
    }

    @Test
    public void getBinaryOperatorCount() {
    }

    @Test
    public void getUnaryOperatorCount() {
    }
}