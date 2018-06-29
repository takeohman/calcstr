package com.takeohman.postfixnotaion.formatter;

import org.junit.Test;

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
            assertEquals("1,234", actual);
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
            String actual = cf.format("123456789.123456789");
            assertEquals("123,456,789.123456789", actual);
        }
        {
            String actual = cf.format("0123400.0");
            assertEquals("先頭の0が消えることを確認","123,400", actual);
        }
        {
            String actual = cf.format("000000.00000");
            assertEquals("0が消えることを確認","0", actual);
        }
        {
            String actual = cf.myFormat("0.00006609822196");
            assertEquals("0.00006609822196", actual );
        }
        {
            String actual = cf.myFormat(".00006609822196");
            assertEquals(".00006609822196", actual );
        }
        {
            String actual = cf.myFormat(".000066098221960");
            assertEquals(".00006609822196", actual );
        }
        {
            String actual = cf.myFormat("3125.000000000000");
            assertEquals("3,125", actual );
        }
    }
}