package com.takeohman.postfixnotaion.checker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionCheckerTest {

    @Test
    public void isFunction() {
        FunctionChecker fc = new FunctionChecker();
        assertEquals(true, fc.isFunction("sin"));
        assertEquals(true, fc.isFunction("cos"));
        assertEquals(true, fc.isFunction("tan"));
        assertEquals(true, fc.isFunction("log"));
        assertEquals(false, fc.isFunction("*"));
    }
}