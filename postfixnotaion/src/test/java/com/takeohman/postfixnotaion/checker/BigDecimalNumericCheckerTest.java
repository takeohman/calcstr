package com.takeohman.postfixnotaion.checker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/12/22.
 */
public class BigDecimalNumericCheckerTest {
    @Test
    public void isNumeric() throws Exception {
        BigDecimalNumericChecker bd = new BigDecimalNumericChecker();
        assertEquals(true, bd.isNumeric(NumericCheckerTest.str_001));
        assertEquals(true, bd.isNumeric(NumericCheckerTest.str_002));
        assertEquals(true, bd.isNumeric(NumericCheckerTest.str_003));
        assertEquals(true, bd.isNumeric(NumericCheckerTest.str_004));
        assertEquals(true, bd.isNumeric(NumericCheckerTest.str_005));

        assertEquals(false, bd.isNumeric(NumericCheckerTest.str_101));
        assertEquals(false, bd.isNumeric(NumericCheckerTest.str_102));
        assertEquals(false, bd.isNumeric(NumericCheckerTest.str_103));
        assertEquals(false, bd.isNumeric(NumericCheckerTest.str_104));
        assertEquals(false, bd.isNumeric(NumericCheckerTest.str_105));
    }

    @Test
    public void getNumericValue() throws Exception {
        BigDecimalNumericChecker bd = new BigDecimalNumericChecker();
        assertEquals(NumericCheckerTest.ans_001, bd.getNumericValue(NumericCheckerTest.str_001));
        assertEquals(NumericCheckerTest.ans_002, bd.getNumericValue(NumericCheckerTest.str_002));
        assertEquals(NumericCheckerTest.ans_003, bd.getNumericValue(NumericCheckerTest.str_003));
        assertEquals(NumericCheckerTest.ans_004, bd.getNumericValue(NumericCheckerTest.str_004));
        assertEquals(NumericCheckerTest.ans_005, bd.getNumericValue(NumericCheckerTest.str_005));

        assertEquals(NumericCheckerTest.ans_101, bd.getNumericValue(NumericCheckerTest.str_101));
        assertEquals(NumericCheckerTest.ans_102, bd.getNumericValue(NumericCheckerTest.str_102));
        assertEquals(NumericCheckerTest.ans_103, bd.getNumericValue(NumericCheckerTest.str_103));
        assertEquals(NumericCheckerTest.ans_104, bd.getNumericValue(NumericCheckerTest.str_104));
        assertEquals(NumericCheckerTest.ans_105, bd.getNumericValue(NumericCheckerTest.str_105));
    }

    @Test
    public void isIncompleteDecimal() throws Exception {
        BigDecimalNumericChecker bd = new BigDecimalNumericChecker();
        assertEquals(false, bd.isIncompleteDecimal(NumericCheckerTest.str_001));
        assertEquals(false, bd.isIncompleteDecimal(NumericCheckerTest.str_002));
        assertEquals(false, bd.isIncompleteDecimal(NumericCheckerTest.str_003));
        assertEquals(true, bd.isIncompleteDecimal(NumericCheckerTest.str_004));
        assertEquals(true, bd.isIncompleteDecimal(NumericCheckerTest.str_005));
    }

}