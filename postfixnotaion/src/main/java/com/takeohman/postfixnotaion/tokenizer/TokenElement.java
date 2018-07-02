package com.takeohman.postfixnotaion.tokenizer;

/**
 * Created by takeoh on 2017/11/09.
 */

/**
 * 四則計算の、式の文字を表すクラス
 */
public class TokenElement {

    int index;  // 式中のインデックス
    String str; // 文字
    String numeric_str;
    Boolean isValid;

    private TokenCheckerInterface ec;

    /**
     * コンストラクタ
     * @param index 式中のインデックス
     * @param str 数値や演算子の文字
     */
    public TokenElement(TokenCheckerInterface ec, int index, String str){
        this.index = index;
        this.ec = ec;
        this.setStr(str);
        this.isValid = true;
    }

    /**
     *
     * @param str
     */
    public TokenElement(TokenCheckerInterface ec, String str){
        this.index = -1;
        this.ec = ec;
        this.setStr(str);
        this.isValid = true;
    }

    public Boolean getIsValid(){
        return this.isValid;
    }

    public void setIsValid(Boolean isValid){
        this.isValid = isValid;
    }
    /**
     * toStringのオーバーライド
     * @return String
     */
    public String toString(){
        return this.str;
    }

    /**
     * インデックスの取得
     * @return int
     */
    public int getIndex(){
        return this.index;
    }

    /**
     *
     * @param index
     * @return
     */
    public int setIndex(int index){
        this.index = index;
        return this.index;
    }

    /**
     *
     * @return String
     */
    public String getStr(){
        if (this.numeric_str != null){
            return this.numeric_str;
        }
        return this.str;
    }

    /**
     *
     * @param str
     * @return
     */
    public String setStr(String str){
        this.str = str;
        this.numeric_str = this.ec.getNumericValue(str);
        return this.str;
    }

    /**
     * 数値の場合にtrueを返す
     * @return boolean
     */
    public boolean isNumeric(){
        return this.ec.isNumeric(this.str);
    }



    public boolean isIncompleteDecimal(){
        return this.ec.isIncompleteDecimal(this.str);
    }
    /**
     * 文字の優先順位を返す
     * @return int
     */
    public int getPriority(){
        return this.ec.getValuePriority(this.str);
    }

    /**
     * "("の場合にtrueを返す
     * @return boolean
     */
    public boolean isLeftBracket(){
        return this.str.equals("(");
    }

    /**
     * "!"の場合にtrueを返す
     * @return boolean
     */
    public boolean isExclamation(){
        return this.str.equals("!");
    }

    /**
     * "+"の場合にtrueを返す
     * @return boolean
     */
    public boolean isPlusOperator(){
        return this.str.equals("+");
    }

    /**
     * "-"の場合にtrueを返す
     * @return boolean
     */
    public boolean isMinusOperator(){
        return this.str.equals("-");
    }

    /**
     * "*"の場合にtrueを返す
     * @return boolean
     */
    public boolean isMultiplicationOperator(){
        return this.str.equals("*");
    }

    /**
     * "/"の場合にtrueを返す
     * @return boolean
     */
    public boolean isDivisionOperator(){
        return this.str.equals("/");
    }

    /**
     * ")"の場合にtrueを返す
     * @return boolean
     */
    public boolean isRightBracket(){
        return this.str.equals(")");
    }


    /**
     * "sin"の場合にtrueを返す
     * @return
     */
    public boolean isSineFunc() {return this.str.equals("sin");}

    /**
     * "cos"の場合にtrueを返す
     * @return
     */
    public boolean isCosineFunc() {return this.str.equals("cos");}

    /**
     * "tan"の場合にtrueを返す
     * @return
     */
    public boolean isTangentFunc() {return this.str.equals("tan");}

    /**
     * "log"の場合にtrueを返す
     * @return
     */
    public boolean isLogarithmFunc() {return this.str.equals("log");}


    public boolean isFunction() { return this.ec.isFunction(this.str);}

    public boolean isPeriodStr(){ return this.str.equals(".");}

    public boolean isInvolusionOperator(){return this.str.equals("^");}

    public boolean isBinaryOperator() {return this.ec.isBinaryOperator(this.str);}
    public boolean isUnaryOperator() {return this.ec.isUnaryOperator(this.str);}
}
