package com.takeohman.postfixnotaion.checker;

import java.util.Arrays;

/**
 * Created by takeoh on 2018/04/19.
 */

public class OperatorChecker implements OperatorCheckerInterface {
    /**
     * 与えられた文字(列)が演算子かどうかを判定する
     * @param val 演算子かどうかを判定する文字列
     * @return boolean
     */
    public boolean isOperator(String val){
        String[] op = {"*", "/","+", "-", "×", "÷", "^", "!"};
        return Arrays.asList(op).contains(val);
    }
}
