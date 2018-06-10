package com.takeohman.postfixnotaion.calculator;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/08/30.
 */
public class BigDecimalCalculatorTest {
    @Test
    public void involution() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();
        BigDecimal actual = sc.involution("2","3");
        assertEquals(8, actual.intValue());

        actual = sc.involution("3","2");
        assertEquals(9, actual.intValue());

        actual = sc.involution("2","-1");
        assertEquals(new BigDecimal("0.5"), actual);

        actual = sc.involution("2","-1.9");
        assertEquals(new BigDecimal("0.267943365634"), actual);
    }

    @Test
    public void factorial() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();
        BigDecimal actual = sc.factorial("4");
        assertEquals(24, actual.intValue());

        actual = sc.factorial("5");
        assertEquals(120, actual.intValue());

        actual = sc.factorial("10");
        assertEquals(3628800, actual.intValue());

        actual = sc.factorial("15");
        assertEquals(new BigDecimal("1307674368000"), actual);

        // 【参考】
        // https://www.nap.st/factorial_calculation/?lang=ja
        //
        actual = sc.factorial("16");
        assertEquals(new BigDecimal("20922789888000"), actual);

        actual = sc.factorial("17");
        assertEquals(new BigDecimal("355687428096000"), actual);

        actual = sc.factorial("18");
        assertEquals(new BigDecimal("6402373705728000"), actual);

        actual = sc.factorial("19");
        assertEquals(new BigDecimal("121645100408832000"), actual);

        actual = sc.factorial("20");
        assertEquals(new BigDecimal("2432902008176640000"), actual);

        actual = sc.factorial("30");
        assertEquals(new BigDecimal("265252859812191058636308480000000"), actual);

        actual = sc.factorial("40");
        assertEquals(new BigDecimal("815915283247897734345611269596115894272000000000"), actual);

        actual = sc.factorial("50");
        assertEquals(new BigDecimal("30414093201713378043612608166064768844377641568960512000000000000"), actual);

        actual = sc.factorial("60");
        assertEquals(new BigDecimal("8320987112741390144276341183223364380754172606361245952449277696409600000000000000"), actual);

    }



    @Test
    public void add() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();

        {
            BigDecimal bd = sc.add("1", "2");
            assertEquals(bd.intValue(), 3);
        }
//        {
//            BigDecimal bd = sc.add("1,000", "2,000");
//            assertEquals(bd.intValue(), 3000);
//        }
    }

    @Test
    public void subtract() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();
        BigDecimal bd = sc.subtract("1","2");
        assertEquals(new BigDecimal("-1"),bd);
    }

    @Test
    public void multiply() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();

        BigDecimal bd = sc.multiply("1","2");
        assertEquals(new BigDecimal("2"),bd);
    }

    @Test
    public void divide() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();

        BigDecimal bd = sc.calculate("/","1","2");
        assertEquals(new BigDecimal("0.5"), bd.stripTrailingZeros());
    }

    @Test
    public void calculate() throws Exception {
        BigDecimalCalculator sc = new BigDecimalCalculator();

        BigDecimal bd = sc.calculate("+","1","2");
        assertEquals(3, bd.intValue());

        bd = sc.calculate("*","1","2");
        assertEquals(new BigDecimal("2"), bd);

        bd = sc.calculate("-","1","2");
        assertEquals(new BigDecimal("-1"), bd);

        bd = sc.calculate("/","1","2");
        assertEquals(new BigDecimal("0.5"), bd.stripTrailingZeros());
    }
}