package tkohdk.lib.calcstr.calculator;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by takeoh on 2017/08/30.
 */


public class BigDecimalCalculator implements Calculator<BigDecimal, BigDecimal>{
    private MathContext mc;

    public BigDecimalCalculator(MathContext mc){
        this.mc = mc;
    }

    /**
     * addition
     * @param valA
     * @param valB
     * @return
     */
    public BigDecimal add(BigDecimal valA, BigDecimal valB){
        return valA.add(valB);
    }

    /**
     * subtraction
     * @param valA
     * @param valB
     * @return
     */
    public BigDecimal subtract(BigDecimal valA, BigDecimal valB){
        return valA.subtract(valB);
    }

    /**
     * multiplication
     * @param valA
     * @param valB
     * @return
     */
    public BigDecimal multiply(BigDecimal valA, BigDecimal valB){
        return valA.multiply(valB);
    }

    /**
     * division
     * @param valA
     * @param valB
     * @return
     */
    public BigDecimal divide(BigDecimal valA, BigDecimal valB){
        return valA.divide(valB, this.mc);
    }

    /**
     * involution (repeated multiplication)
     * @param valA
     * @param valB
     * @return
     */
    public BigDecimal involution(BigDecimal valA, BigDecimal valB){
        BigDecimal a = valA;
        BigDecimal b = valB;
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
     * @param valA
     * @param valB
     * @param operator　演算子( +, -, *, / )
     * @return
     *
     * 【補足】
     * operatorに未定義の演算子が渡された場合は加算する。
     */
    public BigDecimal calculate(String operator, BigDecimal valA, BigDecimal valB) {
        /*
            逆ポーランドの計算方法は、"operatorの場合にスタックを取り出して計算"なので
            operatorの値は必ず演算子になるはず。
            case にない演算子の場合はnullを返す。
         */
        switch (operator) {
            case "^":
                return this.involution(valA, valB);
            case "-":
                return this.subtract(valA,valB);
            case "*":
            case "×":
            case ".":
                return this.multiply(valA,valB);
            case "/":
            case "÷":
                return this.divide(valA,valB);
            case "+":
                return this.add(valA,valB);
            default:
                return null;
        }
    }

    public BigDecimal calculate(String operator, BigDecimal val){
        switch (operator) {
            case "sin":
                return this.sin(val);
            case "cos":
                return this.cos(val);
            case "tan":
                return this.tan(val);
            case "log":
                return this.log10(val);
            case "log10":
                return this.log10(val);
            default:
                return null;
        }
    }

    /**
     * 渡された数値文字列を階乗する。
     *
     * @param intVal 階乗する数値の文字列
     * @return BigDecimal
     *
     * TODO: 計算処理の負荷については入力数値の上限を決める等を検討すること
     */
    public BigDecimal factorial(BigDecimal intVal){

        BigDecimal decOrg = intVal;

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
     * @param inVal
     * @return
     */
    public BigDecimal sin(BigDecimal inVal){
        return new BigDecimal(Math.sin(inVal.doubleValue()));
    }

    public BigDecimal cos(BigDecimal inVal){
        return new BigDecimal(Math.cos(inVal.doubleValue()));
    }

    public BigDecimal tan(BigDecimal inVal){
        return new BigDecimal(Math.tan(inVal.doubleValue()));
    }

    public BigDecimal log(BigDecimal inVal){
        return new BigDecimal(Math.log(inVal.doubleValue()));
    }

    public BigDecimal log10(BigDecimal inVal){
        return new BigDecimal(Math.log10(inVal.doubleValue()));
    }
}
