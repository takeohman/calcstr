package com.takeohman.postfixnotaion;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/09/27.
 */
public class PostfixNotationUtilTest {
    @Test
    public void convertInfixToPostfix() throws Exception {
        PostfixNotationUtil pnu = new PostfixNotationUtil(
                new ExpressionElementChecker(),
                new StringCalculator()
        );
        // 1 + 2
        ArrayList<String> pbm1 = new ArrayList<>();
        pbm1.add("1");
        pbm1.add("+");
        pbm1.add("2");
        ArrayList<String> expected1 = new ArrayList<>();
        expected1.add("1");
        expected1.add("2");
        expected1.add("+");
        ArrayList<String> result1 = pnu.convertInfixToPostfix(pbm1);
        assertEquals(result1, expected1);


        // 1 + 2 * 3
        ArrayList<String> pbm2 = new ArrayList<>();
        pbm2.add("1");
        pbm2.add("+");
        pbm2.add("2");
        pbm2.add("*");
        pbm2.add("3");
        ArrayList<String> expected2 = new ArrayList<>();
        expected2.add("1");
        expected2.add("2");
        expected2.add("3");
        expected2.add("*");
        expected2.add("+");
        ArrayList<String> result2 = pnu.convertInfixToPostfix(pbm2);
        assertEquals(result2, expected2);


        // 計算ができないパターン
        // 1 2 * 3
        ArrayList<String> pbm3 = new ArrayList<>();
        pbm3.add("1");
        pbm3.add("2");
        pbm3.add("*");
        pbm3.add("3");
        ArrayList<String> expected3 = new ArrayList<>();
        expected3.add("1");
        expected3.add("2");
        expected3.add("3");
        expected3.add("*");
        ArrayList<String> result3 = pnu.convertInfixToPostfix(pbm3);
        assertEquals(result3, expected3);
    }

}