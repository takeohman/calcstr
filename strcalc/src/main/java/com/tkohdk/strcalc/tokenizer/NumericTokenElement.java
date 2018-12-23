package com.tkohdk.strcalc.tokenizer;

import java.math.BigDecimal;

public class NumericTokenElement extends TokenElementObject {
    protected Number bc;
    public NumericTokenElement(int index, Number bc){
        super(index);
        this.bc = bc;
    }

    @Override
    public String getStr() {
        return this.bc.toString();
    }

    @Override
    public Number getNumberObject() {
        return this.bc;
    }

    @Override
    public String setStr(String str) {
        this.bc = null;
        this.bc = new BigDecimal(str);
        return str;
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public boolean isIncompleteDecimal() {
        return false;
    }

    @Override
    public boolean isLeftBracket() {
        return false;
    }

    @Override
    public boolean isExclamation() {
        return false;
    }

    @Override
    public boolean isPlusOperator() {
        return false;
    }

    @Override
    public boolean isMinusOperator() {
        return false;
    }

    @Override
    public boolean isMultiplicationOperator() {
        return false;
    }

    @Override
    public boolean isDivisionOperator() {
        return false;
    }

    @Override
    public boolean isRightBracket() {
        return false;
    }

    @Override
    public boolean isSineFunc() {
        return false;
    }

    @Override
    public boolean isCosineFunc() {
        return false;
    }

    @Override
    public boolean isTangentFunc() {
        return false;
    }

    @Override
    public boolean isLogarithmFunc() {
        return false;
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean isPeriodStr() {
        return false;
    }

    @Override
    public boolean isInvolutionOperator() {
        return false;
    }

    @Override
    public boolean isBinaryOperator() {
        return false;
    }

    @Override
    public boolean isUnaryOperator() {
        return false;
    }
}
