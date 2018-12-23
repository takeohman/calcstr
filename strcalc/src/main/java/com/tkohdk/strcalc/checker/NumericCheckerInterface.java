package com.tkohdk.strcalc.checker;

/**
 * Created by takeoh on 2017/12/22.
 */

public interface NumericCheckerInterface {
    /**
     * 数字の場合にTrueを返す
     * @param num 対象の文字列
     * @return True or False
     */
    boolean isNumeric(String num);

    /**
     * 数値変換が可能な場合にその数字の文字列を返す。
     * @param num 対象の文字列
     * @return String or null
     */
    String getNumericValue(String num);

    /**
     * 先頭が"."の数字変換可能な文字列かどうかをチェックする。
     * @param num string ".123", "-.456"
     * @return boolean
     */
    boolean isIncompleteDecimal(String num);
}
