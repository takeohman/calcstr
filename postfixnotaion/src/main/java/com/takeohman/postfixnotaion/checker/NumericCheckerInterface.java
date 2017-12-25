package com.takeohman.postfixnotaion.checker;

/**
 * Created by takeoh on 2017/12/22.
 */

public interface NumericCheckerInterface {
    boolean isNumeric(String num);
    String getNumericValue(String num);
    boolean isIncompleteDecimal(String num);
}
