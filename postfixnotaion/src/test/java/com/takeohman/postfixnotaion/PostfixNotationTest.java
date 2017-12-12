package com.takeohman.postfixnotaion;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/08/15.
 */
public class PostfixNotationTest {

    @Test
    public void calcInfixStr_2() throws Exception {
        //
        // sin(N), cos(N), tan(N), log(N)等のテスト
        //

        PostfixNotation pn = new PostfixNotation();
        /*
        sin()
         */
        // 0.8414709848
        String expected = "0.8414709848078965048756572286947630345821380615234375";
        String ans = pn.calcInfixStr("sin(1)");
        assertEquals(expected, ans);

        // 0.4997701026
        expected = "0.499770102614230438131670553048024885356426239013671875";
        ans = pn.calcInfixStr("sin(30*3.14/180)");
        assertEquals(expected, ans);
        // 1.8414709848
        expected = "1.8414709848078965048756572286947630345821380615234375";
        ans = pn.calcInfixStr("1 + sin(1)");
        assertEquals(expected, ans);

        // 1.6829419696
        expected = "1.6829419696157930097513144573895260691642761230468750";
        ans = pn.calcInfixStr("2sin(1)");
        assertEquals(expected, ans);

        // 4.6829419696
        expected = "4.6829419696157930097513144573895260691642761230468750";
        ans = pn.calcInfixStr("2sin(1) + 3");
        assertEquals(expected, ans);


        /*
        cos()
         */
        // 0.5403023059
        expected = "0.540302305868139765010482733487151563167572021484375";
        ans = pn.calcInfixStr("cos(1)");
        assertEquals(expected, ans);

        // 0.8661580944
        expected = "0.86615809442212199353861024064826779067516326904296875";
        ans = pn.calcInfixStr("cos(30*3.14/180)");
        assertEquals(expected, ans);
        // 1.5403023059
        expected = "1.540302305868139765010482733487151563167572021484375";
        ans = pn.calcInfixStr("1 + cos(1)");
        assertEquals(expected, ans);

        // 1.0806046118
        expected = "1.080604611736279530020965466974303126335144042968750";
        ans = pn.calcInfixStr("2cos(1)");
        assertEquals(expected, ans);

        // 4.0806046118
        expected = "4.080604611736279530020965466974303126335144042968750";
        ans = pn.calcInfixStr("2cos(1) + 3");
        assertEquals(expected, ans);


        /*
        tan()
         */
        expected = "1.557407724654902292371616567834280431270599365234375";
        ans = pn.calcInfixStr("tan(1)");
        assertEquals(expected, ans);

        expected = "0.57699640034844212888032188857323490083217620849609375";
        ans = pn.calcInfixStr("tan(30*3.14/180)");
        assertEquals(expected, ans);

        expected = "2.557407724654902292371616567834280431270599365234375";
        ans = pn.calcInfixStr("1 + tan(1)");
        assertEquals(expected, ans);

        expected = "3.114815449309804584743233135668560862541198730468750";
        ans = pn.calcInfixStr("2tan(1)");
        assertEquals(expected, ans);

        expected = "6.114815449309804584743233135668560862541198730468750";
        ans = pn.calcInfixStr("2tan(1) + 3");
        assertEquals(expected, ans);


        // ========== ========== ========== ========== ==========
        //
        //  log()
        //
        //

        expected = "0";
        ans = pn.calcInfixStr("log(1)");
        assertEquals(expected, ans);

        expected = "1";
        ans = pn.calcInfixStr("log(10)");
        assertEquals(expected, ans);

        expected = "2";
        ans = pn.calcInfixStr("log(100)");
        assertEquals(expected, ans);

        // 2.301029996
        expected = "2.301029995663981253528618253767490386962890625";
        ans = pn.calcInfixStr("log(200)");
        assertEquals(expected, ans);
    }

    @Test
    public void calcInfixStr_1() throws Exception {
        // UIからの入力を想定したテスト
        //
        // UIから入力された文字を解析していく際に、数字以外の入力可能な文字のみが入力されている状態と
        // 式として成り立たない入力不可能な文字が入力されている状態を分けて扱わないと何も入力できなくなる。
        // ここではそういったケースをテストしたい。

        PostfixNotation pn = new PostfixNotation();
        String expected = "";
        String ans = pn.calcInfixStr("(");
        assertEquals(expected, ans);

        expected = "1";
        ans = pn.calcInfixStr("(1");
        assertEquals(expected, ans);

        expected = "1";
        ans = pn.calcInfixStr("(1-");
        assertEquals(expected, ans);

        expected = "-1";
        ans = pn.calcInfixStr("(1-2");
        assertEquals(expected, ans);

        expected = "-1";
        ans = pn.calcInfixStr("(1-2(");
        assertEquals(expected, ans);

        expected = "-5";
        ans = pn.calcInfixStr("(1-2(3");
        assertEquals(expected, ans);
    }

    @Test
    public void calcInfixStr() throws Exception {
        PostfixNotation pn = new PostfixNotation();
        String expected = "15";
        String ans = pn.calcInfixStr("(1 + 2) * (2 + 3) ");
        assertEquals(expected, ans);


        expected = "2";
        ans = pn.calcInfixStr("1-(-1)");
        assertEquals(expected, ans);



        // 演算子が2つ連続する場合
        expected = "1";
        ans = pn.calcInfixStr("--1");
        assertEquals(expected, ans);

        expected = "2";
        ans = pn.calcInfixStr("1--1");
        assertEquals(expected, ans);

        expected = "0";
        ans = pn.calcInfixStr("1+-1");
        assertEquals(expected, ans);

        expected = "0";
        ans = pn.calcInfixStr("1---1");
        assertEquals(expected, ans);

        expected = null;
        ans = pn.calcInfixStr("1-+1");
        assertEquals(expected, ans);

        expected = "0.6";
        ans = pn.calcInfixStr("0.3 + 0.3");
        assertEquals(expected, ans);

        // ^([-][0-9]+[.]?[0-9]+)
        expected = "-0.6";
        ans = pn.calcInfixStr("-0.3 - 0.3");
        assertEquals(expected, ans);

        expected = "0";
        ans = pn.calcInfixStr("+");
        assertEquals(expected, ans);

        expected = "1";
        ans = pn.calcInfixStr("*");
        assertEquals(expected, ans);

        expected = "1";
        ans = pn.calcInfixStr("+1");
        assertEquals(expected, ans);

        expected = "3";
        ans = pn.calcInfixStr("(+3)");
        assertEquals(expected, ans);

        expected = "-2";
        ans = pn.calcInfixStr("- 2");
        assertEquals(expected, ans);

        expected = "2";
        ans = pn.calcInfixStr("*2");
        assertEquals(expected, ans);

        //不完全な式だが2を期待
        expected = "2";
        ans = pn.calcInfixStr("2-");
        assertEquals(expected, ans);

        expected = "0.5";
        ans = pn.calcInfixStr("/2");
        assertEquals(expected, ans);

        expected = "0.3333333333";
        ans = pn.calcInfixStr("/3");
        assertEquals(expected, ans);
        // 階乗
        expected = "24";
        ans = pn.calcInfixStr("4!");
        assertEquals(expected, ans);

        expected = "24";
        ans = pn.calcInfixStr("(4!");
        assertEquals(expected, ans);

        expected = "24";
        ans = pn.calcInfixStr("(4)!");
        assertEquals(expected, ans);

        expected = "-24";
        ans = pn.calcInfixStr("(-4)!");
        assertEquals(expected, ans);

        expected = "24";
        ans = pn.calcInfixStr("3!4");
        assertEquals(expected, ans);

        expected = "24";
        ans = pn.calcInfixStr("(3)!4");
        assertEquals(expected, ans);

        //不完全な式だが1を期待
        expected = "1";
        ans = pn.calcInfixStr("1!(");
        assertEquals(expected, ans);

        expected = "24";
        ans = pn.calcInfixStr("3!(4)");
        assertEquals(expected, ans);

        expected = "24";
        ans = pn.calcInfixStr("(3)!(4)");
        assertEquals(expected, ans);

        expected = "8";
        ans = pn.calcInfixStr("2^3");
        assertEquals(expected, ans);

        expected = "8";
        ans = pn.calcInfixStr("(2)^3");
        assertEquals(expected, ans);

        expected = "8";
        ans = pn.calcInfixStr("(1+1)^3");
        assertEquals(expected, ans);

        expected = "64";
        ans = pn.calcInfixStr("2^3!");
        assertEquals(expected, ans);

        expected = "-24";
        ans = pn.calcInfixStr("((-4))!");
        assertEquals(expected, ans);

        expected = "25";
        ans = pn.calcInfixStr("4! + 1");
        assertEquals(expected, ans);

        expected = "25";
        ans = pn.calcInfixStr("(2 + 2)! + 1");
        assertEquals(expected, ans);

        expected = "42";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * 4) ");
        assertEquals(expected, ans);

        expected = "51";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * (4 + 1)) ");
        assertEquals(expected, ans);


        expected = "51";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * (4 + 1) ");
        assertEquals(expected, ans);

        expected = "51";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * (4 + 1 ");
        assertEquals(expected, ans);

        expected = "42";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * (4 +  ");
        assertEquals(expected, ans);

        expected = "42";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * (4) ");
        assertEquals(expected, ans);

        expected = "42";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * (4 ");
        assertEquals(expected, ans);

        expected = "15";
        ans = pn.calcInfixStr("(1 + 2) * (2 + 3 * ( ");
        assertEquals(expected, ans);

        expected = "11";
        ans = pn.calcInfixStr("1 + 2 * (2 + 3 * ( ");
        assertEquals(expected, ans);

        expected = "11";
        ans = pn.calcInfixStr("(1) + 2 * (2 + 3 * ( ");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("3(2)");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("(2)3");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("(2)(3)");
        assertEquals(expected, ans);

        expected = "60";
        ans = pn.calcInfixStr("(3)4(5)");
        assertEquals(expected, ans);

        expected = "60";
        ans = pn.calcInfixStr("(3)(4)(5)");
        assertEquals(expected, ans);

        expected = "60";
        ans = pn.calcInfixStr("(3)((4)(5)");
        assertEquals(expected, ans);

        expected = "60";
        ans = pn.calcInfixStr("(3)((4)(5))");
        assertEquals(expected, ans);

        expected = "60";
        ans = pn.calcInfixStr("((3)(4)(5))");
        assertEquals(expected, ans);

        expected = "60";
        ans = pn.calcInfixStr("((3)(4))(5))");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("3((2))");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("(3((2)))");
        assertEquals(expected, ans);

        // case: )が1つ足りない
        expected = "6";
        ans = pn.calcInfixStr("(3((2))");
        assertEquals(expected, ans);

        // case: (n)形式の掛算
        expected = "24";
        ans = pn.calcInfixStr("4(3(2))");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("2(1 + 2)");
        assertEquals(expected, ans);

        // マイナス値の掛算
        expected = "-6";
        ans = pn.calcInfixStr("-3(2)");
        assertEquals(expected, ans);

        expected = "6";
        ans = pn.calcInfixStr("-3(-2)");
        assertEquals(expected, ans);


        // ========== ========== ========== ========== ==========
        //
        // 計算不能なパターン
        //
        //

        // doCalcの最初、stackへのアクセス箇所で例外
        //
        ans = pn.calcInfixStr("log()");
        Assert.assertNull(ans);

        ans = pn.calcInfixStr("()");
        Assert.assertNull(ans);
    }
}