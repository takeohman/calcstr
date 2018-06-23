package com.takeohman.postfixnotaion;

import com.takeohman.postfixnotaion.tokenizer.TokenElementList;

public class PostfixNotationResult{
    private TokenElementList tokenElementList;
    private String ans;
    private String problemStr;
    private Exception ex;

    public PostfixNotationResult(String problemStr, String ans, TokenElementList elementList){
        this.problemStr = problemStr;
        this.ans = ans;
        this.tokenElementList = elementList;
        this.ex = null;
    }

    public PostfixNotationResult(String problemStr, String ans, TokenElementList elementList, Exception ex){
        this.problemStr = problemStr;
        this.ans = ans;
        this.tokenElementList = elementList;
        this.ex = ex;
    }

    public TokenElementList getTokenElementList(){
        return this.tokenElementList;
    }
    public String getAns(){
        return this.ans;
    }

    public String getProblemStr(){
        return this.problemStr;
    }
}
