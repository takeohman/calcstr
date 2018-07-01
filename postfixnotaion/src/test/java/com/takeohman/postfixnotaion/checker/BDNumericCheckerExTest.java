package com.takeohman.postfixnotaion.checker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BDNumericCheckerExTest {

    @Test
    public void getNumericValue() {
        BDNumericCheckerEx bd = new BDNumericCheckerEx();
        assertEquals("-1", bd.getNumericValue("-1"));
        assertEquals("1", bd.getNumericValue("--1"));
        assertEquals("-1", bd.getNumericValue("---1"));
        assertEquals("1", bd.getNumericValue("----1"));
    }
}