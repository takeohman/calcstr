package tkohdk.lib.calcstr.tokenizer;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class NumericTokenElementTest {

    @Test
    public void getNumberObject() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertEquals(bd, element.getNumberObject());
    }

    @Test
    public void setStr() {
    }

    @Test
    public void isNumeric() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertTrue(element.isNumeric());
    }

    @Test
    public void isIncompleteDecimal() {
        {
            BigDecimal bd = new BigDecimal("777");
            NumericTokenElement element = new NumericTokenElement(0, bd);
            assertFalse(element.isIncompleteDecimal());
        }
        {
            BigDecimal bd = new BigDecimal("777.");
            NumericTokenElement element = new NumericTokenElement(0, bd);
            assertFalse(element.isIncompleteDecimal());
        }
        {
            BigDecimal bd = new BigDecimal("777.89");
            NumericTokenElement element = new NumericTokenElement(0, bd);
            assertFalse(element.isIncompleteDecimal());
        }
    }

    @Test
    public void isLeftBracket() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isLeftBracket());
    }

    @Test
    public void isExclamation() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isExclamation());
    }

    @Test
    public void isPlusOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isPlusOperator());
    }

    @Test
    public void isMinusOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isMinusOperator());
    }

    @Test
    public void isMultiplicationOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isMultiplicationOperator());
    }

    @Test
    public void isDivisionOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isDivisionOperator());
    }

    @Test
    public void isRightBracket() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isRightBracket());
    }

    @Test
    public void isSineFunc() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isSineFunc());
    }

    @Test
    public void isCosineFunc() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isCosineFunc());
    }

    @Test
    public void isTangentFunc() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isTangentFunc());
    }

    @Test
    public void isLogarithmFunc() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isLogarithmFunc());
    }

    @Test
    public void isFunction() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isFunction());
    }

    @Test
    public void isPeriodStr() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isPeriodStr());
    }

    @Test
    public void isInvolutionOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isInvolutionOperator());
    }

    @Test
    public void isBinaryOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isBinaryOperator());
    }

    @Test
    public void isUnaryOperator() {
        BigDecimal bd = new BigDecimal("777");
        NumericTokenElement element = new NumericTokenElement(0, bd);
        assertFalse(element.isUnaryOperator());
    }
}