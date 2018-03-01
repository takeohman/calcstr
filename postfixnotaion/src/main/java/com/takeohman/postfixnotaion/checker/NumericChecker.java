package com.takeohman.postfixnotaion.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class NumericChecker implements NumericCheckerInterface{
    /**
     * 先頭が"."の数字変換可能な文字列かどうかをチェックする。
     * @param num string ".123", "-.456"
     * @return boolean
     */
    @Override
    public boolean isIncompleteDecimal(String num){
        if (this.isNumeric(num)){
            // num文字列の先頭が"."かどうかをチェックする
            Pattern pat = Pattern.compile("(^[-]?\\..*)");
            Matcher mat = pat.matcher(num);
            if (mat.find()){
                return true;
            }
        }
        return false;
    }
}