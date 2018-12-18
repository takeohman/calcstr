package com.tkohdk.postfixnotaion.checker;

/**
 * Created by takeoh on 2017/12/22.
 */

public class FloatNumericChecker extends NumericChecker {
    public boolean isNumeric(String num){
        return this.getNumericValue(num) != null;
    }

    public String getNumericValue(String num){
        try {
            Float ft = Float.parseFloat(num);
            return ft.toString();
        } catch (NumberFormatException e) {
            return null;
        }
    }
}