package com.tkohdk.strcalc.checker;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PeriodPositionCheckerTest {

    @Test
    public void isTheHeadPeriod() {
        PeriodPositionChecker nvf = new PeriodPositionChecker();
        {
            boolean actual = nvf.isTheHeadPeriod(".");
            assertEquals(true, actual);
        }
        {
            boolean actual = nvf.isTheHeadPeriod(".123");
            assertEquals(true, actual);
        }
        {
            boolean actual = nvf.isTheHeadPeriod("123.");
            assertEquals(false, actual);
        }
        {
            boolean actual = nvf.isTheHeadPeriod("123.456");
            assertEquals(false, actual);
        }
    }

    @Test
    public void isTheTailPeriod() {
        PeriodPositionChecker nvf = new PeriodPositionChecker();
        {
            boolean actual = nvf.isTheTailPeriod(".");
            assertEquals(true, actual);
        }
        {
            boolean actual = nvf.isTheTailPeriod(".123");
            assertEquals(false, actual);
        }
        {
            boolean actual = nvf.isTheTailPeriod("123.");
            assertEquals(true, actual);
        }
        {
            boolean actual = nvf.isTheTailPeriod("123.456");
            assertEquals(false, actual);
        }
    }

    @Test
    public void getPeriodPos() {
        PeriodPositionChecker checker = new PeriodPositionChecker();

        {
            PeriodPositionChecker.PeriodPositionCheckResult result = checker.getPeriodPos(".");
            int actual = result.getPeriodCnt();
            assertEquals(1, actual);
            assertEquals(true, result.isHeadPeriod());
            assertEquals(true, result.isTailPeriod());
        }
        {
            PeriodPositionChecker.PeriodPositionCheckResult result = checker.getPeriodPos("..");
            int actual = result.getPeriodCnt();
            assertEquals(2, actual);
            assertEquals(true, result.isHeadPeriod());
            assertEquals(true, result.isTailPeriod());
        }
        {
            PeriodPositionChecker.PeriodPositionCheckResult result = checker.getPeriodPos(".1.");
            int actual = result.getPeriodCnt();
            assertEquals(2, actual);
            assertEquals(true, result.isHeadPeriod());
            assertEquals(true, result.isTailPeriod());
        }
        {
            PeriodPositionChecker.PeriodPositionCheckResult result = checker.getPeriodPos("1.");
            int actual = result.getPeriodCnt();
            assertEquals(1, actual);
            assertEquals(false, result.isHeadPeriod());
            assertEquals(true, result.isTailPeriod());
        }
        {
            PeriodPositionChecker.PeriodPositionCheckResult result = checker.getPeriodPos(".1");
            int actual = result.getPeriodCnt();
            assertEquals(1, actual);
            assertEquals(true, result.isHeadPeriod());
            assertEquals(false, result.isTailPeriod());
        }
    }
}