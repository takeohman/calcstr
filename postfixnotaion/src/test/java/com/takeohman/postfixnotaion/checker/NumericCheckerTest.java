package com.takeohman.postfixnotaion.checker;

/**
 * Created by takeoh on 2017/12/25.
 */
public class NumericCheckerTest {
    static String str_001 = "1";
    static String ans_001 = "1";
    static String str_002 = "-1";
    static String ans_002 = "-1";
    static String str_003 = "0.3";
    static String ans_003 = "0.3";

    static String str_004 = ".3";
    static String ans_004 = "0.3";

    static String str_005 = "-.3";
    static String ans_005 = "-0.3";

    // 数値変換が不可能な場合
    static String str_101 = "1.1.1";
    static String ans_101 = null;

    static String str_102 = ".1.1";
    static String ans_102 = null;

    static String str_103 = "-.1.1";
    static String ans_103 = null;

    static String str_104 = "A";
    static String ans_104 = null;

    static String str_105 = "0xA";
    static String ans_105 = null;
}