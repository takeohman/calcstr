package com.takeohman.postfixnotaion.formatter;

import org.junit.Test;

import java.text.DecimalFormat;

import static junit.framework.Assert.assertEquals;

/**
 * Created by takeoh on 2018/01/08.
 */
public class NumericValueFormatterTest {

    @Test
    public void format() throws Exception {
        NumericValueFormatter cf = new NumericValueFormatter();
        {
            String actual = cf.format("1234");
            assertEquals(actual, "1,234");
        }
        {
            String actual = cf.format("1234000");
            assertEquals("1,234,000", actual);
        }
        {
            String actual = cf.format("123400.0");
            assertEquals("'.0' should be deleted.", "123,400", actual);
        }
        {
            String actual = cf.format("0123400.0");
            assertEquals("先頭の0が消えることを確認","123,400", actual);
        }
    }
    @Test
    public void format1() throws Exception {
        NumericValueFormatter cf = new NumericValueFormatter(new StringRZeroTrimmer());
        {
            String actual = cf.format("1234");
            assertEquals("1,234", actual);
        }
        {
            String actual = cf.format("1234000");
            assertEquals("1,234,000", actual);
        }
        {
            String actual = cf.format("123400.0");
            assertEquals("123,400", actual);
        }
    }
    @Test
    public void format2() throws Exception {
        NumericValueFormatter cf = new NumericValueFormatter(new DecimalFormat("#,###.##########"));
        {
            String actual = cf.format("1234");
            assertEquals("1,234", actual);
        }
        {
            String actual = cf.format("1234000");
            assertEquals("1,234,000", actual);
        }
        {
            String actual = cf.format("123456789.123456789");
            assertEquals("123,456,789.123456789", actual);
        }
    }
    @Test
    public void format3() throws Exception {
        NumericValueFormatter cf = new NumericValueFormatter(new DecimalFormat("#,###.##########"));
        {
            String actual = cf.format("1234");
            assertEquals(actual, "1,234");
        }
        {
            String actual = cf.format("1234000");
            assertEquals("1,234,000", actual);
        }
        {
            String actual = cf.format("123400.0");
            assertEquals("'.0' should be deleted.", "123,400", actual);
        }
        {
            String actual = cf.format("0123400.0");
            assertEquals("先頭の0が消えることを確認","123,400", actual);
        }
        {
            String actual = cf.format("000000.00000");
            assertEquals("0が消えることを確認","0", actual);
        }
    }
}