package com.takeohman.postfixnotaion.formatter;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by takeoh on 2018/01/08.
 */
public class CommaFormatterTest {

    @Test
    public void format() throws Exception {
        CommaFormatter cf = new CommaFormatter();
        String actual = cf.format("1234");
        assertEquals(actual, "1,234");
    }
}