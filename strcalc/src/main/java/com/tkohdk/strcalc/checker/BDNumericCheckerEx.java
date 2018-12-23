package com.tkohdk.strcalc.checker;

public class BDNumericCheckerEx extends BigDecimalNumericChecker{
//    private Pattern pattern;
    public BDNumericCheckerEx(){
        super();
//        this.pattern = Pattern.compile("^(--)*[-]?[0-9.,]*$");
    }
    @Override
    public String getNumericValue(String num){

//        Matcher matcher = this.pattern.matcher(num);
//
//        if (matcher.find()) {
            return super.getNumericValue(num.replaceAll("^(--)+|,", ""));
//        } else {
//            return null;
//        }
    }
}
