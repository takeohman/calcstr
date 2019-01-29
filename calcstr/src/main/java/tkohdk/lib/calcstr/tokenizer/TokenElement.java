package tkohdk.lib.calcstr.tokenizer;

/**
 * Created by takeoh on 2017/11/09.
 */

import java.math.BigDecimal;

/**
 * 四則計算の、式の文字を表すクラス
 */
public class TokenElement extends TokenElementObject{

    protected String numeric_str;
    protected boolean isIncompleteDec;

    private TokenCheckerInterface ec;

    /**
     * コンストラクタ
     * @param index 式中のインデックス
     * @param str 数値や演算子の文字
     */
    public TokenElement(TokenCheckerInterface ec, int index, String str){
        super(index);
        this.ec = ec;
        this.setStr(str);
    }

    /**
     *
     * @param str
     */
    public TokenElement(TokenCheckerInterface ec, String str){
        super(-1);
        this.ec = ec;
        this.setStr(str);
    }

    /**
     * toStringのオーバーライド
     * @return String
     */
    public String toString(){
        return this.str;
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
        this.setPriority(this.ec.getValuePriority(str));
        this.isIncompleteDec = this.ec.isIncompleteDecimal(str);
        return this.str;
    }


    /**
     * Return Number Object if it's possible.
     * @return Number or null
     */
    public BigDecimal getNumberObject(){
        if (this.numeric_str != null){
            return new BigDecimal(this.numeric_str);
        }
        return null;
    }
    /**
     * 数値の場合にtrueを返す
     * @return boolean
     */
    public boolean isNumeric(){
        return this.numeric_str != null;
//        return this.ec.isNumeric(this.str);
    }



    public boolean isIncompleteDecimal(){
        return this.isIncompleteDec;
//        return this.ec.isIncompleteDecimal(this.str);
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

    public boolean isInvolutionOperator(){return this.str.equals("^");}

    public boolean isEMark(){return this.str.equals("E") || this.str.equals("e");}

    public boolean isBinaryOperator() {return this.ec.isBinaryOperator(this.str);}
    public boolean isUnaryOperator() {return this.ec.isUnaryOperator(this.str);}
}
