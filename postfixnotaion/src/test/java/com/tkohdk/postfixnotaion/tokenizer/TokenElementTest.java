package com.tkohdk.postfixnotaion.tokenizer;

import com.tkohdk.postfixnotaion.checker.BigDecimalNumericChecker;
import com.tkohdk.postfixnotaion.checker.FunctionChecker;
import com.tkohdk.postfixnotaion.checker.OperatorChecker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenElementTest {



    @Test
    public void getIndex() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.getIndex(), 3);
    }

    @Test
    public void setIndex() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.getIndex(), 3);

        element.setIndex(7);
        assertEquals(element.getIndex(), 7);
    }

    @Test
    public void getStr() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.getStr(), "+");
    }

    @Test
    public void setStr() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.getStr(), "+");

        element.setStr("*");
        assertEquals(element.getStr(), "*");
    }

    @Test
    public void isNumeric() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.isNumeric(), false);

        element.setStr("3");
        assertEquals(element.isNumeric(), true);
    }

    @Test
    public void isIncompleteDecimal() {
    }

    @Test
    public void getPriority() {
    }

    @Test
    public void isLeftBracket() {
    }

    @Test
    public void isExclamation() {
    }

    @Test
    public void isPlusOperator() {
    }

    @Test
    public void isMinusOperator() {
    }

    @Test
    public void isMultiplicationOperator() {
    }

    @Test
    public void isDivisionOperator() {
    }

    @Test
    public void isRightBracket() {
    }

    @Test
    public void isSineFunc() {
    }

    @Test
    public void isCosineFunc() {
    }

    @Test
    public void isTangentFunc() {
    }

    @Test
    public void isLogarithmFunc() {
    }

    @Test
    public void isFunction() {
    }

    @Test
    public void isPeriodStr() {
    }

    @Test
    public void isBinaryOperator() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.isBinaryOperator(), true);

        element.setStr("!");
        assertEquals(element.isBinaryOperator(), false);
    }

    @Test
    public void isUnaryOperator() {
        TokenValueChecker tc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker()
        );
        TokenElement element = new TokenElement(tc, 3, "+");
        assertEquals(element.isUnaryOperator(), false);

        element.setStr("!");
        assertEquals(element.isUnaryOperator(), true);
    }
}