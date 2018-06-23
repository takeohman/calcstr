package com.takeohman.postfixnotaion.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeriodPositionChecker {
    public class PeriodPositionCheckResult{

        private int periodCnt;
        private boolean isHeadPeriod;
        private boolean isTailPeriod;
        PeriodPositionCheckResult(int periodCnt, boolean isHeadPeriod, boolean isTailPeriod){
            this.periodCnt = periodCnt;
            this.isHeadPeriod = isHeadPeriod;
            this.isTailPeriod = isTailPeriod;
        }

        public int getPeriodCnt(){
            return this.periodCnt;
        }

        public boolean isHeadPeriod(){
            return isHeadPeriod;
        }

        public boolean isTailPeriod(){
            return isTailPeriod;
        }

    }
    /**
     * Return true when the value starts ".".
     * @param value String to be checked.
     * @return boolean
     */
    boolean isTheHeadPeriod(String value){
        Pattern pattern = Pattern.compile("^\\.");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    /**
     * Return true when the value ends ".".
     * @param value String to be checked.
     * @return boolean
     */
    boolean isTheTailPeriod(String value){
        Pattern pattern = Pattern.compile("\\.$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }


    /**
     * Return true when the value ends ".".
     * @param value String to be checked.
     * @return boolean
     */
    public PeriodPositionCheckResult getPeriodPos(String value){
        Pattern pattern = Pattern.compile("\\.");
        Matcher matcher = pattern.matcher(value);
        int periodCnt = 0;
        boolean isHeadPeriod = false;
        boolean isTailPeriod = false;

        while (matcher.find()){
            periodCnt++;
//            String tmp = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            if (start == 0){
                isHeadPeriod = true;
            }
            if (end == value.length()){
                isTailPeriod = true;
            }

//            System.out.println("group    : " + tmp);
//            System.out.println("start    : " + start);
//            System.out.println("end      : " + end);
//            System.out.println("value    : " + value.length());
        }

//        Pattern pattern_zero = Pattern.compile("^[0.,]+$");
//        Matcher matcher_zero = pattern_zero.matcher(value);
//        if (matcher_zero.find()){
//            System.out.println("value    : " + value.length());
//        }
        return new PeriodPositionCheckResult(periodCnt, isHeadPeriod, isTailPeriod);

    }
}
