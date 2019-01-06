package tkohdk.lib.calcstr;

import tkohdk.lib.calcstr.tokenizer.StringListTokenizer;
import tkohdk.lib.calcstr.tokenizer.TokenElementList;
import tkohdk.lib.calcstr.tokenizer.TokenElementObject;
import tkohdk.lib.calcstr.exception.NoElementException;

public class CalcStrResult {
    private TokenElementList tokenElementList;
    private TokenElementObject ans;
    private String problemStr;
    private Exception ex;
    public static final int RESULT_OK = 0;
    public static final int RESULT_EXPECTED_EXCEPTION = 1;
    public static final int RESULT_UNEXPECTED_EXCEPTION = -1;

    public CalcStrResult(String problemStr, TokenElementObject ans, TokenElementList elementList){
        this.problemStr = problemStr;
        this.ans = ans;
        this.tokenElementList = elementList;
        this.ex = null;
    }

    public CalcStrResult(String problemStr, TokenElementObject ans, TokenElementList elementList, Exception ex){
        this.problemStr = problemStr;
        this.ans = ans;
        this.tokenElementList = elementList;
        this.ex = ex;
    }

    public TokenElementList getTokenElementList(){
        return this.tokenElementList;
    }

    /**
     * Get status of calculation result.
     * @return int
     *
     * RESULT_OK(0)                     : OK (=valid TokenElementObject is available)
     * RESULT_EXPECTED_EXCEPTION(1)     : NG
     * RESULT_UNEXPECTED_EXCEPTION(-1)  : NG
     */
    public int getStatus(){
        if (ans != null){
            return RESULT_OK;
        } else if (ex != null){
            if (ex instanceof StringListTokenizer.InvalidElementOrderException ||
                    ex instanceof StringListTokenizer.InvalidBracketCountException ||
                    ex instanceof NoElementException) {
                return RESULT_EXPECTED_EXCEPTION;
            } else if (ex instanceof NumberFormatException){
                return RESULT_UNEXPECTED_EXCEPTION;
            } else {
                return RESULT_UNEXPECTED_EXCEPTION;
            }
        } else {
            return RESULT_EXPECTED_EXCEPTION;
        }
    }

    /**
     * Return TokenElementObject
     * @return TokenElementObject
     */
    public TokenElementObject getAnswerToken(){
        return this.ans;
    }


    /**
     * get the answer of calculation in string.
     * @return string
     */
    public String getAns(){
        String ans = null;
        switch (this.getStatus()){
            case RESULT_OK:
                ans = this.ans.getStr();
                break;
            case RESULT_EXPECTED_EXCEPTION:
                ans = null;
                break;
            case RESULT_UNEXPECTED_EXCEPTION:
                ans = "";
                break;
        }
        return ans;
    }

    public String getProblemStr(){
        return this.problemStr;
    }
}
