package com.takeohman.postfixnotaion.checker;

import java.math.BigDecimal;

/**
 * Created by takeoh on 2017/12/22.
 */


public class BigDecimalNumericChecker extends NumericChecker{
    public boolean isNumeric(String num){
        return this.getNumericValue(num) != null;
    }
    public String getNumericValue(String num){
        try {
            BigDecimal bc = new BigDecimal(num);
            return bc.toString();
        } catch (Exception ex){
            return null;
        }
    }
}