package com.takeohman.postfixnotaion.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BDNumericCheckerEx extends BigDecimalNumericChecker{
    @Override
    public String getNumericValue(String num){
        Pattern pattern = Pattern.compile("^(--)*[-]?[0-9.,]*$");
        Matcher matcher = pattern.matcher(num);

        if (matcher.find()) {
            return super.getNumericValue(num.replaceAll("^(--)+|,", ""));
        } else {
            return null;
        }
    }
}
