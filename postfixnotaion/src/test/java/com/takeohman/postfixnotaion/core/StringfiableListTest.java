package com.takeohman.postfixnotaion.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2018/05/02.
 */
public class StringfiableListTest {
    @Test
    public void toString_Test() throws Exception {
        StringfiableList<String> sfl = new StringfiableList<>();
        sfl.add("A");
        sfl.add("B");
        sfl.add("C");
        sfl.add("D");
        assertEquals("toString",sfl.toString(),"ABCD");
    }

}