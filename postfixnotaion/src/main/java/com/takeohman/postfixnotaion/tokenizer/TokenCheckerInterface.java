package com.takeohman.postfixnotaion.tokenizer;

import com.takeohman.postfixnotaion.checker.NumericCheckerInterface;

/**
 * Created by takeoh on 2017/09/02.
 */

public interface TokenCheckerInterface extends NumericCheckerInterface {
    int getValuePriority(String val);
    boolean isFunction(String val);
}
