package com.takeohman.postfixnotaion;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by takeoh on 2017/08/30.
 */


class StringCalculator {
    private MathContext mc;

    StringCalculator(){
        this.mc = new MathContext(10, RoundingMode.HALF_EVEN);
    }

    /**
     * 足し算
     * @param strA
     * @param strB
     * @return
     */
    BigDecimal add(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.add(b);
    }

    /**
     * 引き算
     * @param strA
     * @param strB
     * @return
     */
    BigDecimal subtract(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.subtract(b);
    }

    /**
     * 掛け算
     * @param strA
     * @param strB
     * @return
     */
    BigDecimal multiply(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.multiply(b);
    }

    /**
     * 割り算
     * @param strA
     * @param strB
     * @return
     */
    BigDecimal divide(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.divide(b,this.mc);
    }

    /**
     * 累乗
     * @param strA
     * @param strB
     * @return
     */
    BigDecimal involution(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.pow(b.intValue());
    }

    /**
     * 二項演算子を使った計算を行う
     * @param strA
     * @param strB
     * @param operator　演算子( +, -, *, / )
     * @return
     *
     * 【補足】
     * operatorに未定義の演算子が渡された場合は加算する。
     */
    BigDecimal calculate(String strA, String strB, String operator) {
        /*
            逆ポーランドの計算方法は、"operatorの場合にスタックを取り出して計算"なので
            operatorの値は必ず演算子になるはず。それゆえ、switch文のデフォルトは"+"とまとめている。
         */
        switch (operator) {
            case "^":
                return this.involution(strA, strB);
            case "-":
                return this.subtract(strA,strB);
            case "*":
            case "×":
            case ".":
                return this.multiply(strA,strB);
            case "/":
            case "÷":
                return this.divide(strA,strB);
            case "+":
            default:
                return this.add(strA,strB);
        }
    }

    /**
     * 渡された数値文字列を階乗する。
     *
     * @param intValStr 階乗する数値の文字列
     * @return BigDecimal
     *
     * TODO: 計算処理の負荷については入力数値の上限を決める等を検討すること
     */
    BigDecimal factorial(String intValStr){

        BigDecimal decOrg = new BigDecimal(intValStr);

        BigDecimal decToCalc = decOrg.abs();
        BigDecimal srcNum = new BigDecimal(1);

        long idx = 1;

        while(decToCalc.compareTo(new BigDecimal(idx))  > -1){
            srcNum = srcNum.multiply(new BigDecimal(idx));
            idx++;
        }


        return srcNum.multiply(new BigDecimal(decOrg.signum() == -1? -1:1));
    }

    /**
     *
     * @param inValStr
     * @return
     */
    BigDecimal sin(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.sin(bc.doubleValue()));
    }

    BigDecimal cos(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.cos(bc.doubleValue()));
    }

    BigDecimal tan(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.tan(bc.doubleValue()));
    }

    BigDecimal log(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.log(bc.doubleValue()));
    }

    BigDecimal log10(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.log10(bc.doubleValue()));
    }
}
