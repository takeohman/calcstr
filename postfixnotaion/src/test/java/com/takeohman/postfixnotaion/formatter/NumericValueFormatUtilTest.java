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
    @Test
    public void convertNumericValueWithCursor2() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            String value = "12 + 3456 + 1";
            String actual = nvf.convertNumericValueWithCursor(value, 6, null);
            assertEquals("12 + 456 + 1", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 0, null);
            assertEquals("1,234 + 5678 + 90", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1,234 + 5678 + 90", 0, null);
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 1, null);
            assertEquals("234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 2, null);
            assertEquals("134 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 3, null);
            assertEquals("124 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 4, null);
            assertEquals("123 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 5, null);
            // "1234+ 5678 + 90" can be the expected result, but may be no problem.
            assertEquals("1,234+ 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 6, null);
            assertEquals("1234  5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 7, null);
            assertEquals("1234 +5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 8, null);
            assertEquals("1234 + 678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 9, null);
            assertEquals("1234 + 578 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 10, null);
            assertEquals("1234 + 568 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 11, null);
            assertEquals("1234 + 567 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 12, null);
            assertEquals("1234 + 5,678+ 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 13, null);
            assertEquals("1234 + 5678  90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 14, null);
            assertEquals("1234 + 5678 +90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 15, null);
            assertEquals("1234 + 5678 + 0", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 16, null);
            assertEquals("1234 + 5678 + 9", actual);
        }
    }
    @Test
    public void convertNumericValueWithCursor3() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();

        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 0, "1");
            assertEquals("11,234 + 5678 + 90", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1,234 + 5678 + 90", 0, "1");
            assertEquals("11,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 1, "1");
            assertEquals("11,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 2, "1");
            assertEquals("12,134 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 3, "1");
            assertEquals("12,314 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 4, "1");
            assertEquals("12,341 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 5, "1");
            assertEquals("1234 1+ 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 6, "1");
            assertEquals("1234 +1 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 7, "1");
            assertEquals("1234 + 15,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 8, "1");
            assertEquals("1234 + 51,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 9, "1");
            assertEquals("1234 + 56,178 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 10, "1");
            assertEquals("1234 + 56,718 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 11, "1");
            assertEquals("1234 + 56,781 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 12, "1");
            assertEquals("1234 + 5678 1+ 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 13, "1");
            assertEquals("1234 + 5678 +1 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 14, "12");
            assertEquals("1234 + 5678 + 1,290", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 15, "12");
            assertEquals("1234 + 5678 + 9,120", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 16, "12");
            assertEquals("1234 + 5678 + 9,012", actual);
        }
    }
}