package com.takeohman.postfixnotaion.tokenizer;

import com.takeohman.postfixnotaion.checker.NumericCheckerInterface;

/**
 * Created by takeoh on 2017/09/02.
 */

public interface TokenCheckerInterface extends NumericCheckerInterface {
    /**
     * 与えられた文字列の優先順位を返す。戻り値が大きい方が優先度高。
     * @param val 文字列
     * @return int
     */
    int getValuePriority(String val);

    /**
     * 与えられた文字列がxxx(value)形式の関数名に一致する場合にtrueを返す。
     * @param val (ex. sin, cos, tan)
     * @return true or false
     */
    boolean isFunction(String val);
}
