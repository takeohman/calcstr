package com.takeohman.postfixnotaion;

import java.util.Arrays;

/**
 * Created by takeoh on 2017/09/02.
 */

class ExpressionElementChecker {
    private static final int PRIORITY_1 = 5;
    private static final int PRIORITY_2 = 4;
    private static final int PRIORITY_3 = 3;
    private static final int PRIORITY_4 = 2;
    private static final int PRIORITY_5 = 1;
    private static final int PRIORITY_6 = 0;
    private static final int PRIORITY_7 = -1;
    private static final int PRIORITY_8 = -2;
    /**
     * 与えられた文字列の優先順位を返す。戻り値が大きい方が優先度高。
     * @param val 文字列
     * @return int
     */
    int getValuePriority(String val){
        String[] p1 = {"("};
        String[] p3 = {"!"};
        String[] p4 = {"^", "sin", "cos", "tan", "log"};
        String[] p5 = {"*", "/"};
        String[] p6 = {"+", "-"};
        String[] p7 = {")"};

        if (Arrays.asList(p1).contains(val)) {          // "("
            return PRIORITY_1;
        }else if (this.isNumber(val)){   // 0,1,2....
            return PRIORITY_3;
        } else if (Arrays.asList(p3).contains(val)){    // !
            return PRIORITY_2;
        } else if (Arrays.asList(p4).contains(val)){    // ^
            return PRIORITY_4;
        } else if (Arrays.asList(p5).contains(val)){    // *, /
            return PRIORITY_5;
        } else if (Arrays.asList(p6).contains(val)){    // +, -
            return PRIORITY_6;
        } else if (Arrays.asList(p7).contains(val)){    // ")"
            return PRIORITY_7;
        } else {
            return PRIORITY_8;
        }
    }

    /**
     * 与えられた文字(列)が演算子かどうかを判定する
     * @param val 演算子かどうかを判定する文字列
     * @return boolean
     */
    boolean isOperator(String val){
        String[] op = {"*", "/","+", "-", "×", "÷", "^"};
        return Arrays.asList(op).contains(val);
    }

    boolean isFunction(String val){
        String[] func = {"sin", "cos", "tan", "log"};
        return Arrays.asList(func).contains(val);
    }

    /**
     * 与えられた文字（列）が数値かどうかを判定する
     * @param num 数値かどうかを判定する文字列
     * @return boolean
     */
    boolean isNumber(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 与えられた文字（列）が小数点を含む数値かどうかを判定する
     * @param num
     * @return
     */
    boolean isDecimalNumber(String num){
        if (this.isNumber(num)){
            return num.contains(".");
        }
        return false;
    }
}
