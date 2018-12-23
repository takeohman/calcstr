package com.tkohdk.strcalc.checker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by takeoh on 2017/12/22.
 */
public class FloatNumericCheckerTest {
    @Test
    public void isNumeric() throws Exception {
        FloatNumericChecker fc = new FloatNumericChecker();
        assertEquals(true, fc.isNumeric(NumericCheckerTest.str_001));
        assertEquals(true, fc.isNumeric(NumericCheckerTest.str_002));
        assertEquals(true, fc.isNumeric(NumericCheckerTest.str_003));
        assertEquals(true, fc.isNumeric(NumericCheckerTest.str_004));
        assertEquals(true, fc.isNumeric(NumericCheckerTest.str_005));

        assertEquals(false, fc.isNumeric(NumericCheckerTest.str_101));
        assertEquals(false, fc.isNumeric(NumericCheckerTest.str_102));
        assertEquals(false, fc.isNumeric(NumericCheckerTest.str_103));
        assertEquals(false, fc.isNumeric(NumericCheckerTest.str_104));
        assertEquals(false, fc.isNumeric(NumericCheckerTest.str_105));
        assertEquals(false, fc.isNumeric(NumericCheckerTest.str_106));
    }
    @Test
    public void getNumericValue() throws Exception {
        FloatNumericChecker fc = new FloatNumericChecker();
        assertEquals(NumericCheckerTest.ans_001 + ".0", fc.getNumericValue(NumericCheckerTest.str_001));
        assertEquals(NumericCheckerTest.ans_002 + ".0", fc.getNumericValue(NumericCheckerTest.str_002));
        assertEquals(NumericCheckerTest.ans_003, fc.getNumericValue(NumericCheckerTest.str_003));
        assertEquals(NumericCheckerTest.ans_004, fc.getNumericValue(NumericCheckerTest.str_004));
        assertEquals(NumericCheckerTest.ans_005, fc.getNumericValue(NumericCheckerTest.str_005));

        assertEquals(NumericCheckerTest.ans_101, fc.getNumericValue(NumericCheckerTest.str_101));
        assertEquals(NumericCheckerTest.ans_102, fc.getNumericValue(NumericCheckerTest.str_102));
        assertEquals(NumericCheckerTest.ans_103, fc.getNumericValue(NumericCheckerTest.str_103));
        assertEquals(NumericCheckerTest.ans_104, fc.getNumericValue(NumericCheckerTest.str_104));
        assertEquals(NumericCheckerTest.ans_105, fc.getNumericValue(NumericCheckerTest.str_105));
        assertEquals(NumericCheckerTest.ans_106, fc.getNumericValue(NumericCheckerTest.str_106));
    }
}