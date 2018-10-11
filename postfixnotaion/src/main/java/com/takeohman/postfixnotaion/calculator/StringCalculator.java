package com.takeohman.postfixnotaion.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by takeoh on 2017/08/30.
 */


public class StringCalculator implements Calculator<BigDecimal, String>{
    private MathContext mc;
    int scale;

    public StringCalculator(){
        this.mc = new MathContext(12, RoundingMode.HALF_EVEN);
        this.scale = 12;
    }

    /**
     * addition
     * @param strA
     * @param strB
     * @return
     */
    public BigDecimal add(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.add(b);
    }

    /**
     * subtraction
     * @param strA
     * @param strB
     * @return
     */
    public BigDecimal subtract(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.subtract(b);
    }

    /**
     * multiplication
     * @param strA
     * @param strB
     * @return
     */
    public BigDecimal multiply(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.multiply(b);
    }

    /**
     * division
     * @param strA
     * @param strB
     * @return
     */
    public BigDecimal divide(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        return a.divide(b,this.mc);
    }

    /**
     * involution (repeated multiplication)
     * @param strA
     * @param strB
     * @return
     */
    public BigDecimal involution(String strA, String strB){
        BigDecimal a = new BigDecimal(strA);
        BigDecimal b = new BigDecimal(strB);
        BigDecimal bdAns = null;
        String bStr = b.toString();

        // Since BigDecimal only allow int value for involution, check bStr.
        if ( bStr.contains(".") || b.compareTo(BigDecimal.ZERO) < 0 ) {

            boolean is_minus_value = bStr.startsWith("-");
            // 整数
            String[] split_float_value = bStr.split("[.]", 2);
            BigDecimal _numeric = new BigDecimal(split_float_value[0]);
            double _numeric_ans = Math.pow(a.doubleValue(), _numeric.intValueExact());
            BigDecimal _n = null;
            if (Double.isInfinite(_numeric_ans)){
                _n = a.pow(_numeric.intValueExact());
            } else {
                _n = new BigDecimal(_numeric_ans);
            }

            // 小数点以下に値がある場合はその計算
            if (split_float_value.length > 1){
                BigDecimal _float = new BigDecimal((is_minus_value ? "-0." : "0.") + split_float_value[1]);
                double _float_ans = Math.pow(a.doubleValue(), _float.doubleValue());
                BigDecimal _f = new BigDecimal(_float_ans);

                // multiply the results of Math.pow of NNN.xxx and xxx.NNN.
                bdAns = _n.multiply(_f);
            } else {
                bdAns = _n;
            }

        } else {
            bdAns = a.pow(b.intValueExact());
        }
        return bdAns.stripTrailingZeros();
    }

    /**
     * calculate by binary operator
     * @param strA
     * @param strB
     * @param operator　演算子( +, -, *, / )
     * @return
     *
     * 【補足】
     * operatorに未定義の演算子が渡された場合は加算する。
     */
    public BigDecimal calculate(String operator, String strA, String strB) {
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

    public BigDecimal calculate(String operator, String str){
        switch (operator) {
            case "sin":
                return this.sin(str);
            case "cos":
                return this.cos(str);
            case "tan":
                return this.tan(str);
            case "log":
                return this.log10(str);
            case "log10":
                return this.log10(str);
            default:
                return null;
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
    public BigDecimal factorial(String intValStr){

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
    public BigDecimal sin(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.sin(bc.doubleValue()));
    }

    public BigDecimal cos(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.cos(bc.doubleValue()));
    }

    public BigDecimal tan(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.tan(bc.doubleValue()));
    }

    public BigDecimal log(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.log(bc.doubleValue()));
    }

    public BigDecimal log10(String inValStr){
        BigDecimal bc = new BigDecimal(inValStr);
        return new BigDecimal(Math.log10(bc.doubleValue()));
    }
}
