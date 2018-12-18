package com.tkohdk.postfixnotaion.ui;

import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;

public class NumericValueFormatUtilTest {

    @Test
    public void getNumericEdgeString() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        String value = "100 + 10000";
        {
            NumericValueFormatUtil.MatchedString matchedString =
                    nvf.getNumericEdgeString(value, Pattern.compile("^[0-9,.]+"));

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
                    nvf.getNumericEdgeString(value, Pattern.compile("[0-9,.]+$"));

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
                    nvf.getNumericEdgeString(value, Pattern.compile("^[0-9,.]+"));
            assertEquals(null, matchedString);
        }
        {
            NumericValueFormatUtil.MatchedString matchedString =
                    nvf.getNumericEdgeString(value, Pattern.compile("[0-9,.]+$"));

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
            String actual = nvf.convertNumericValueWithCursor(value, 6, null).getNumericValueString();
            assertEquals("12 + 456 + 1", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 0, null).getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1,234 + 5678 + 90", 0, null).getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 1, null).getNumericValueString();
            assertEquals("234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 2, null).getNumericValueString();
            assertEquals("134 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 3, null).getNumericValueString();
            assertEquals("124 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 4, null).getNumericValueString();
            assertEquals("123 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 5, null).getNumericValueString();
            // "1234+ 5678 + 90" can be the expected result, but may be no problem.
            assertEquals("1,234+ 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 6, null).getNumericValueString();
            assertEquals("1234  5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 7, null).getNumericValueString();
            assertEquals("1234 +5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 8, null).getNumericValueString();
            assertEquals("1234 + 678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 9, null).getNumericValueString();
            assertEquals("1234 + 578 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 10, null).getNumericValueString();
            assertEquals("1234 + 568 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 11, null).getNumericValueString();
            assertEquals("1234 + 567 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 12, null).getNumericValueString();
            assertEquals("1234 + 5,678+ 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 13, null).getNumericValueString();
            assertEquals("1234 + 5678  90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 14, null).getNumericValueString();
            assertEquals("1234 + 5678 +90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 15, null).getNumericValueString();
            assertEquals("1234 + 5678 + 0", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 16, null).getNumericValueString();
            assertEquals("1234 + 5678 + 9", actual);
        }
    }
    @Test
    public void convertNumericValueWithCursor2_2() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            String value = "12 + 3456 + 1";
            String actual = nvf.convertNumericValueWithCursor(value, 6, "").getNumericValueString();
            assertEquals("12 + 3,456 + 1", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 0, "").getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1,234 + 5678 + 90", 0, "").getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 1, "").getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 2, "").getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 3, "").getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 4, "").getNumericValueString();
            assertEquals("1,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 5, "").getNumericValueString();
            // "1234+ 5678 + 90" can be the expected result, but may be no problem.
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 6, "").getNumericValueString();
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 7, "").getNumericValueString();
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 8, "").getNumericValueString();
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 9, "").getNumericValueString();
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 10, "").getNumericValueString();
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 11, "").getNumericValueString();
            assertEquals("1234 + 5,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 12, "").getNumericValueString();
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 13, "").getNumericValueString();
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 14, "").getNumericValueString();
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 15, "").getNumericValueString();
            assertEquals("1234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 16, "").getNumericValueString();
            assertEquals("1234 + 5678 + 90", actual);
        }
    }
    @Test
    public void convertNumericValueWithCursor3() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();

        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 0, "1").getNumericValueString();
            assertEquals("11,234 + 5678 + 90", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("1,234 + 5678 + 90", 0, "1").getNumericValueString();
            assertEquals("11,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 1, "1").getNumericValueString();
            assertEquals("11,234 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 2, "1").getNumericValueString();
            assertEquals("12,134 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 3, "1").getNumericValueString();
            assertEquals("12,314 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 4, "1").getNumericValueString();
            assertEquals("12,341 + 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 5, "1").getNumericValueString();
            assertEquals("1234 1+ 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 6, "1").getNumericValueString();
            assertEquals("1234 +1 5678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 7, "1").getNumericValueString();
            assertEquals("1234 + 15,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 8, "1").getNumericValueString();
            assertEquals("1234 + 51,678 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 9, "1").getNumericValueString();
            assertEquals("1234 + 56,178 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 10, "1").getNumericValueString();
            assertEquals("1234 + 56,718 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 11, "1").getNumericValueString();
            assertEquals("1234 + 56,781 + 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 12, "1").getNumericValueString();
            assertEquals("1234 + 5678 1+ 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 13, "1").getNumericValueString();
            assertEquals("1234 + 5678 +1 90", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 14, "12").getNumericValueString();
            assertEquals("1234 + 5678 + 1,290", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 15, "12").getNumericValueString();
            assertEquals("1234 + 5678 + 9,120", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1234 + 5678 + 90", 16, "12").getNumericValueString();
            assertEquals("1234 + 5678 + 9,012", actual);
        }
    }
    @Test
    public void convertNumericValueWithCursor4() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            String actual = nvf.convertNumericValueWithCursor(".", 0, "").getNumericValueString();
            assertEquals(".", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100.01", 0, "").getNumericValueString();
            assertEquals("100.01", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100.100100", 0, "").getNumericValueString();
            assertEquals("100.100100", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor(".01", 0, "").getNumericValueString();
            assertEquals(".01", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100.", 0, "").getNumericValueString();
            assertEquals("100.", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100.00", 0, "").getNumericValueString();
            assertEquals("100.00", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("10(0).00", 3, "0").getNumericValueString();
            assertEquals("10(00).00", actual);
        }
    }
    @Test
    public void convertNumericValueWithCursor5() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            //右端に数字以外追加ー＞数字を分けないので左側フォーマットなし
            String actual = nvf.convertNumericValueWithCursor("", 0, ")").getNumericValueString();
            assertEquals(")", actual);
        }
        {
            //右端に数字以外追加ー＞数字を分けないので左側フォーマットなし
            String actual = nvf.convertNumericValueWithCursor("", 0, "(").getNumericValueString();
            assertEquals("(", actual);
        }
        {
            //右端に数字以外追加ー＞数字を分けないので左側フォーマットなし
            String actual = nvf.convertNumericValueWithCursor("123(*4", 6, ")").getNumericValueString();
            assertEquals("123(*4)", actual);
        }
        {
            //演算子の次に演算子を追加ー＞
            String actual = nvf.convertNumericValueWithCursor("123*45", 4, "*").getNumericValueString();
            assertEquals("123**45", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("12345", 4, "*").getNumericValueString();
            assertEquals("1,234*5", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("12,345", 5, "*").getNumericValueString();
            assertEquals("1,234*5", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("(2)12,345", 5, "*").getNumericValueString();
            assertEquals("(2)12*345", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("(2)12,345,678", 6, "*").getNumericValueString();
            assertEquals("(2)12*345,678", actual);
        }

        {
            String actual = nvf.convertNumericValueWithCursor("(2)12,345,678", 6, "*").getNumericValueString();
            assertEquals("(2)12*345,678", actual);
        }
    }
    @Test
    public void convertNumericValueWithCursor6() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();

        {
            NumericValueFormatUtil.ConvertNumericValueResult result = nvf.convertNumericValueWithCursor("123.4567", 4, "*");
            String actual = result.getNumericValueString();
            assertEquals("123.*4,567", actual);
            assertEquals(1, result.getCursorMove());
        }
        {
            // erase '+'
            // +が削除され、カーソル左と右の数字が連結されて左側のカンマが削除されることを確認する
            NumericValueFormatUtil.ConvertNumericValueResult result = nvf.convertNumericValueWithCursor("1,234.0+5,678", 8, null);
            String actual = result.getNumericValueString();
            assertEquals("1,234.05678", actual);
            assertEquals(-1, result.getCursorMove());
        }
        {
            // erase '+'
            // +が削除され、カーソル左と右の数字が連結されて左側のカンマが削除されることを確認する
            NumericValueFormatUtil.ConvertNumericValueResult result = nvf.convertNumericValueWithCursor("1,234.05678", 7, "+");
            String actual = result.getNumericValueString();
            assertEquals("1,234.0+5,678", actual);
            assertEquals(1, result.getCursorMove());
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100,000,001", 6, ".").getNumericValueString();
            assertEquals("10,000.0001", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100,000,001", 7, ".").getNumericValueString();
            assertEquals("100,000.001", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("1+100,000.001+2", 10, null).getNumericValueString();
            assertEquals("1+100,000,001+2", actual);
        }
        {
            String actual = nvf.convertNumericValueWithCursor("100,000.001", 8, null).getNumericValueString();
            assertEquals("100,000,001", actual);
        }

        {
            NumericValueFormatUtil.ConvertNumericValueResult result = nvf.convertNumericValueWithCursor("100,000.1234.56", 8, null);
            String actual = result.getNumericValueString();
            assertEquals("1,000,001,234.56", actual);
            assertEquals(1, result.getCursorMove());
        }

        {
            NumericValueFormatUtil.ConvertNumericValueResult result = nvf.convertNumericValueWithCursor("1,000,001,234.56", 8, ".");
            String actual = result.getNumericValueString();
            assertEquals("100,000.1234.56", actual);
            assertEquals(-1, result.getCursorMove());
        }
        {
            NumericValueFormatUtil.ConvertNumericValueResult result = nvf.convertNumericValueWithCursor("1,000,001,234.56", 9, "*");
            String actual = result.getNumericValueString();
            assertEquals("1,000,001*234.56", actual);
            assertEquals(-1, result.getCursorMove());
        }

    }


    public void showParamOfGetStringsAroundTheCursor(String exp,int index, String[] actual){
        if (false){
            return;
        }
        System.out.println("============================");
        System.out.println("expressin     : " + exp);
        System.out.println("  index       : " + String.valueOf(index));
        System.out.println("  pre | suffix: " + exp.substring(0,index) + " | " + exp.substring(index,exp.length()));
        System.out.println("    actual[0] : " + actual[0]);
        System.out.println("    actual[1] : " + actual[1]);
        System.out.println("    actual[2] : " + actual[2]);
        System.out.println("    actual[3] : " + actual[3]);
    }
    @Test
    public void getStringsAroundTheCursor() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        String exp = "1*-2.3E-45*67";
//        String[] expected = {"1*-", "2.3", "E-45*67"};
        String[] expected = {"1*-", "2.3E-45", "*67"};
        {
            int index = 2;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
        }
        // ここから →
        {
            int index = 3;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }

        {
            int index = 4;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        {
            int index = 5;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        {
            int index = 6;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        {
            int index = 7;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        {
            int index = 8;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        {
            int index = 9;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        {
            int index = 10;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
            assertEquals(expected[1], actual[1] + actual[2]);
        }
        // ← ここまで
        {
            int index = 11;
            String[] actual = nvf.getStringsAroundTheCursor(exp.substring(0,index), exp.substring(index,exp.length()));
            this.showParamOfGetStringsAroundTheCursor(exp, index, actual);
        }
    }
    @Test
    public void formatNumericString() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            String actual = nvf.formatNumericString("");
            assertEquals("", actual);
        }
        {
            String actual = nvf.formatNumericString(".");
            assertEquals(".", actual);
        }
        {
            String actual = nvf.formatNumericString("00");
            assertEquals("00", actual);
        }
        {
            String actual = nvf.formatNumericString("01");
            assertEquals("01", actual);
        }
        {
            String actual = nvf.formatNumericString("0100");
            assertEquals("0,100", actual);
        }
        {
            String actual = nvf.formatNumericString(".0100");
            assertEquals(".0100", actual);
        }
        {
            String actual = nvf.formatNumericString(".0100.");
            assertEquals(".0100.", actual);
        }
        {
            String actual = nvf.formatNumericString("0100.");
            assertEquals("0,100.", actual);
        }
        {
            String actual = nvf.formatNumericString("0123.456");
            assertEquals("0,123.456", actual);
        }

        {
            String actual = nvf.formatNumericString("0123,");
            assertEquals("0,123", actual);
        }
    }

    @Test
    public void getEStringAndOthersFromHead() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();

        {
            String[] actual = nvf.getEStringAndOthersFromHead("E+45*67");
            assertEquals(2, actual.length);
            assertEquals("E+45", actual[0]);
            assertEquals("*67", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E-45*67");
            assertEquals(2, actual.length);
            assertEquals("E-45", actual[0]);
            assertEquals("*67", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E-45*6");
            assertEquals(2, actual.length);
            assertEquals("E-45", actual[0]);
            assertEquals("*6", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E-45*");
            assertEquals(2, actual.length);
            assertEquals("E-45", actual[0]);
            assertEquals("*", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E-45");
            assertEquals(2, actual.length);
            assertEquals("E-45", actual[0]);
            assertEquals("", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E-4");
            assertEquals(2, actual.length);
            assertEquals("E-4", actual[0]);
            assertEquals("", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E-");
            assertNull(actual);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("E");
            assertNull(actual);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("");
            assertNull(actual);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("1E-45*67");
            assertNull(actual);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromHead("-45*67");
            assertNull(actual);
        }
    }

    @Test
    public void getEStringAndOthersFromTail() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();

        {
            String[] actual = nvf.getEStringAndOthersFromTail("1+987*1.23E");
            assertEquals(2, actual.length);
            assertEquals("1+987*", actual[0]);
            assertEquals("1.23E", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromTail("+987*1.23E");
            assertEquals(2, actual.length);
            assertEquals("+987*", actual[0]);
            assertEquals("1.23E", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromTail("7*1.23E");
            assertEquals(2, actual.length);
            assertEquals("7*", actual[0]);
            assertEquals("1.23E", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromTail("*1.23E");
            assertEquals(2, actual.length);
            assertEquals("*", actual[0]);
            assertEquals("1.23E", actual[1]);
        }
        {
            String[] actual = nvf.getEStringAndOthersFromTail("1.23E");
            assertEquals(2, actual.length);
            assertEquals("", actual[0]);
            assertEquals("1.23E", actual[1]);
        }
    }

    @Test
    public void getNumStringAndOthersFromHead() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();

        {
            String[] actual = nvf.getNumStringAndOthersFromHead("E+45*67");
            assertNull(actual);
        }
        {
            String[] actual = nvf.getNumStringAndOthersFromHead("-45*67");
            assertEquals(2, actual.length);
            assertEquals("-45", actual[0]);
            assertEquals("*67", actual[1]);
        }
        {
            String[] actual = nvf.getNumStringAndOthersFromHead("45*6");
            assertNull(actual);
        }

        {
            String[] actual = nvf.getNumStringAndOthersFromHead("");
            assertNull(actual);
        }
        {
            String[] actual = nvf.getNumStringAndOthersFromHead("1E-45*67");
            assertNull(actual);
        }
    }

    @Test
    public void isTailOfStringNumberWithPeriod() {
        NumericValueFormatUtil nvf = new NumericValueFormatUtil();
        {
            boolean result = nvf.isTailOfStringNumberWithPeriod("1,234");
            assertFalse(result);
        }
        {
            boolean result = nvf.isTailOfStringNumberWithPeriod("1,234.");
            assertTrue(result);
        }
        {
            boolean result = nvf.isTailOfStringNumberWithPeriod("1,234.5");
            assertTrue(result);
        }
    }
}