package com.takeohman.postfixnotaion.tokenizer;

public abstract class TokenElementObject {
    protected Boolean isValid;
    protected int index;  // 式中のインデックス
    protected int priority;
    protected String str; // 文字


    public TokenElementObject(int index){
        this.index = index;
        this.isValid = true;
    }

    public Boolean getIsValid(){
        return this.isValid;
    }

    public void setIsValid(Boolean isValid){
        this.isValid = isValid;
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
    abstract public String getStr();

    public String getRawStr(){
        return this.str;
    }

    /**
     *
     * @param str
     * @return
     */
    abstract public String setStr(String str);

    /**
     * 数値の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isNumeric();


    abstract public boolean isIncompleteDecimal();
    /**
     * 文字の優先順位を返す
     * @return int
     */
    public int getPriority(){
        return this.priority;
    }

    public void setPriority(int priority) {this.priority = priority;}

    /**
     * "("の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isLeftBracket();

    /**
     * "!"の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isExclamation();

    /**
     * "+"の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isPlusOperator();

    /**
     * "-"の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isMinusOperator();

    /**
     * "*"の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isMultiplicationOperator();

    /**
     * "/"の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isDivisionOperator();

    /**
     * ")"の場合にtrueを返す
     * @return boolean
     */
    abstract public boolean isRightBracket();


    /**
     * "sin"の場合にtrueを返す
     * @return
     */
    abstract public boolean isSineFunc();

    /**
     * "cos"の場合にtrueを返す
     * @return
     */
    abstract public boolean isCosineFunc();

    /**
     * "tan"の場合にtrueを返す
     * @return
     */
    abstract public boolean isTangentFunc();

    /**
     * "log"の場合にtrueを返す
     * @return
     */
    abstract public boolean isLogarithmFunc();


    abstract public boolean isFunction();

    abstract public boolean isPeriodStr();

    abstract public boolean isInvolutionOperator();

    abstract public boolean isBinaryOperator();
    abstract public boolean isUnaryOperator();
}
