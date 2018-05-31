package com.takeohman.postfixnotaion.tokenizer;

import com.takeohman.postfixnotaion.checker.FunctionCheckerInterface;
import com.takeohman.postfixnotaion.checker.NumericCheckerInterface;
import com.takeohman.postfixnotaion.checker.OperatorCheckerInterface;

/**
 * Created by takeoh on 2017/09/02.
 */

public interface TokenCheckerInterface
        extends NumericCheckerInterface ,FunctionCheckerInterface, OperatorCheckerInterface{
    /**
     * 与えられた文字列の優先順位を返す。戻り値が大きい方が優先度高。
     * @param val 文字列
     * @return int
     */
    int getValuePriority(String val);
}
