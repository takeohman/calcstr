package com.takeohman.postfixnotaion.formatter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringRZeroTrimmerTest {

    @Test
    public void format() {
        StringRZeroTrimmer trimmer = new StringRZeroTrimmer();

        {
            String actual = trimmer.format("700000");
            assertEquals("700000", actual );
        }
        {
            String actual = trimmer.format("7000");
            assertEquals("7000", actual );
        }
        {
            String actual = trimmer.format("70");
            assertEquals("70", actual );
        }
        {
            String actual = trimmer.format("7.000");
            assertEquals("7", actual);
        }
        {
            String actual = trimmer.format("7.100");
            assertEquals("7.1", actual);
        }
        {
            String actual = trimmer.format("7.101");
            assertEquals("7.101", actual);
        }
        {
            String actual = trimmer.format("7.");
            assertEquals("7.", actual);
        }
    }

    @Test
    public void format1() {
        StringRZeroTrimmer trimmer = new StringRZeroTrimmer(new NumericValueFormatter());
        {
            String actual = trimmer.format("700000");
            assertEquals("700,000", actual );
        }
        {
            String actual = trimmer.format("7000");
            assertEquals("7,000", actual );
        }
        {
            String actual = trimmer.format("70");
            assertEquals("70", actual );
        }
        {
            String actual = trimmer.format("7.000");
            assertEquals("7", actual);
        }
        {
            String actual = trimmer.format("7.100");
            assertEquals("7.1", actual);
        }
        {
            String actual = trimmer.format("7.101");
            assertEquals("7.101", actual);
        }
        {
            String actual = trimmer.format("7.");
            assertEquals("7", actual);
        }
    }
}