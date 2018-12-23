package com.tkohdk.strcalc.tokenizer;

import com.tkohdk.strcalc.checker.FunctionCheckerInterface;
import com.tkohdk.strcalc.checker.NumericChecker;
import com.tkohdk.strcalc.checker.OperatorCheckerInterface;
import com.tkohdk.strcalc.checker.PriorityChecker;

public class TokenValueChecker implements TokenCheckerInterface {

    protected NumericChecker numericChecker;
    private FunctionCheckerInterface functionChecker;
    private OperatorCheckerInterface operatorChecker;
    private PriorityChecker priorityChecker;

    public TokenValueChecker(NumericChecker numericChecker, FunctionCheckerInterface functionChecker, OperatorCheckerInterface operatorChecker){
        this.numericChecker = numericChecker;
        this.functionChecker = functionChecker;
        this.operatorChecker = operatorChecker;
        this.priorityChecker = new PriorityChecker(numericChecker);
    }

    /**
     * 与えられた文字列の優先順位を返す。戻り値が大きい方が優先度高。
     * @param val 文字列
     * @return int
     */
    @Override
    public int getValuePriority(String val){
        return this.priorityChecker.getValuePriority(val);
    }

    /**
     * 与えられた文字(列)が演算子かどうかを判定する
     * @param val 演算子かどうかを判定する文字列
     * @return boolean
     */
    public boolean isOperator(String val){
        return this.operatorChecker.isOperator(val);
    }
    public boolean isBinaryOperator(String val){
        return this.operatorChecker.isBinaryOperator(val);
    }
    public boolean isUnaryOperator(String val){
        return this.operatorChecker.isUnaryOperator(val);
    }

    @Override
    public boolean isFunction(String val){
        return this.functionChecker.isFunction(val);
    }


    @Override
    public boolean isIncompleteDecimal(String num){
        return this.numericChecker.isIncompleteDecimal(num);
    }

    /**
     * 与えられた文字（列）が数値かどうかを判定する
     * @param num 数値かどうかを判定する文字列
     * @return boolean
     */
    @Override
    public boolean isNumeric(String num){
        return this.numericChecker.isNumeric(num);
    }

    @Override
    public String getNumericValue(String num){
        return this.numericChecker.getNumericValue(num);
    }
}
