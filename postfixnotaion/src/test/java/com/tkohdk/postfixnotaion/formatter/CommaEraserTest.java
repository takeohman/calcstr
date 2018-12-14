package com.tkohdk.postfixnotaion.formatter;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CommaEraserTest {

    @Test
    public void myFormat() {
        CommaEraser ce = new CommaEraser();

        {
            String actual = ce.myFormat("123,456");
            assertEquals("123456", actual);
        }
    }
}