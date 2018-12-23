package com.tkohdk.strcalc.tokenizer;

import com.tkohdk.strcalc.checker.FunctionCheckerInterface;
import com.tkohdk.strcalc.checker.NumericCheckerInterface;
import com.tkohdk.strcalc.checker.OperatorCheckerInterface;

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
