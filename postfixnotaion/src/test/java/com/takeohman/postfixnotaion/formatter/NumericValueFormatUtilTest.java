package com.takeohman.postfixnotaion.formatter;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NumericValueFormatUtilTest {

    @Test
    public void getNumericEdgeString() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        String value = "100 + 10000";
        {
            NumericValueFormatUtil.MatchedString matchedString =
                    nvf.getNumericEdgeString(value, NumericValueFormatUtil.regPatternHead);

            String actual = matchedString.getGroup();
            assertEquals("100", actual);
            // 0123456789....
            // |  |
            // 100 + 10000
            assertEquals(0, matchedString.getStart());
            assertEquals(3, matchedString.getEnd());
        }
        {
            NumericValueFormatUtil.MatchedString matchedString =
                    nvf.getNumericEdgeString(value, NumericValueFormatUtil.regPatternTail);

            String actual = matchedString.getGroup();
            assertEquals("10000", actual);
            // 0123456789....
            //       |    |
            // 100 + 10000
            assertEquals(6, matchedString.getStart());
            assertEquals(11, matchedString.getEnd());
        }
    }
    @Test
    public void getNumericEdgeString1() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        String value = "- 100 + 10000 +";
        {
            NumericValueFormatUtil.MatchedString matchedString =
                    nvf.getNumericEdgeString(value, NumericValueFormatUtil.regPatternHead);
            assertEquals(null, matchedString);
        }
        {
            NumericValueFormatUtil.MatchedString matchedString =
                    nvf.getNumericEdgeString(value, NumericValueFormatUtil.regPatternTail);

            assertEquals(null, matchedString);
        }
    }

    @Test
    public void convertNumericValueWithCursor() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            String value = "12 + 3456 + 1";
            String actual = nvf.convertNumericValueWithCursor(value, 6);
            assertEquals("12 + 3,456 + 1", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 0);
            assertEquals("1,234 + 5678 + 90", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1,234 + 5678 + 90", 0);
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 1);
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 2);
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 3);
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 4);
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 5);
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 6);
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 7);
            //                    01234567|
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 8);
            //                    012345678
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 9);
            //                    012345678
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 10);
            //                    012345678
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 11);
            //                    012345678
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 12);
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 13);
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 14);
            // the cursor is left before 9.
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 15);
            // the cursor is left before 0.
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 16);
            // the cursor is right after 0.
            assertEquals("1234 + 5678 + 90", actual);
        }
            // test case for "out of index"
//            {
//                String actual = nvf.convertNumericValueWithCursor(value, 17);
//                // the cursor is right after 0.
//                assertEquals("1234 + 5678 + 90", actual);
//            }
    }
}