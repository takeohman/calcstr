package com.takeohman.postfixnotaion;

/**
 * Created by takeoh on 2017/11/09.
 */

/**
 * 四則計算の、式の文字を表すクラス
 */
class ProblemStrElement {

    int index;  // 式中のインデックス
    String str; // 文字

    private ExpressionElementChecker ec;

    /**
     * コンストラクタ
     * @param index 式中のインデックス
     * @param str 数値や演算子の文字
     */
    ProblemStrElement(int index, String str){
        this.index = index;
        this.str = str;
        this.ec = new ExpressionElementChecker();
    }

    /**
     *
     * @param str
     */
    ProblemStrElement(String str){
        this.index = -1;
        this.str = str;
        this.ec = new ExpressionElementChecker();
    }

    /**
     *
     * @param index
     * @return
     */
    int setIndex(int index){
        this.index = index;
        return this.index;
    }
    /**
     * 数値の場合にtrueを返す
     * @return boolean
     */
    boolean isNumber(){
        return this.ec.isNumber(this.str);
    }

    /**
     * 文字の優先順位を返す
     * @return int
     */
    int getPriority(){
        return this.ec.getValuePriority(this.str);
    }

    /**
     * "("の場合にtrueを返す
     * @return boolean
     */
    boolean isLeftBracket(){
        return this.str.equals("(");
    }

    /**
     * "!"の場合にtrueを返す
     * @return boolean
     */
    boolean isExclamation(){
        return this.str.equals("!");
    }

    /**
     * "+"の場合にtrueを返す
     * @return boolean
     */
    boolean isPlusOperator(){
        return this.str.equals("+");
    }

    /**
     * "-"の場合にtrueを返す
     * @return boolean
     */
    boolean isMinusOperator(){
        return this.str.equals("-");
    }

    /**
     * "*"の場合にtrueを返す
     * @return boolean
     */
    boolean isMultiplicationOperator(){
        return this.str.equals("*");
    }

    /**
     * "/"の場合にtrueを返す
     * @return boolean
     */
    boolean isDivisionOperator(){
        return this.str.equals("/");
    }

    /**
     * ")"の場合にtrueを返す
     * @return boolean
     */
    boolean isRightBracket(){
        return this.str.equals(")");
    }


    /**
     * "sin"の場合にtrueを返す
     * @return
     */
    boolean isSineFunc() {return this.str.equals("sin");}

    /**
     * "cos"の場合にtrueを返す
     * @return
     */
    boolean isCosineFunc() {return this.str.equals("cos");}

    /**
     * "tan"の場合にtrueを返す
     * @return
     */
    boolean isTangentFunc() {return this.str.equals("tan");}

    /**
     * "log"の場合にtrueを返す
     * @return
     */
    boolean isLogarithmFunc() {return this.str.equals("log");}


    boolean isFunction() { return this.ec.isFunction(this.str);}
}
