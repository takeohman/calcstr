package com.tkohdk.postfixnotaion.checker;

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
    private Pattern patPeriod;
    public PeriodPositionChecker(){
        this.patPeriod = Pattern.compile("\\.");
    }
    /**
     * Return true when the value starts ".".
     * @param value String to be checked.
     * @return boolean
     */
    boolean isTheHeadPeriod(String value){
        return value.length() > 0 && String.valueOf(value.charAt(0)).equals(".");
    }

    /**
     * Return true when the value ends ".".
     * @param value String to be checked.
     * @return boolean
     */
    boolean isTheTailPeriod(String value){
        return value.length() > 0  && String.valueOf(value.charAt(value.length()-1)).equals(".");
    }


    /**
     * Return a PeriodPositionCheckResult object
     * @param value String to be checked.
     * @return boolean
     */
    public PeriodPositionCheckResult getPeriodPos(String value){
        Matcher matcher = this.patPeriod.matcher(value);
        int periodCnt = 0;
        boolean isHeadPeriod = false;
        boolean isTailPeriod = false;

        while (matcher.find()){
            periodCnt++;
            int start = matcher.start();
            int end = matcher.end();
            if (start == 0){
                isHeadPeriod = true;
            }
            if (end == value.length()){
                isTailPeriod = true;
            }
        }
        return new PeriodPositionCheckResult(periodCnt, isHeadPeriod, isTailPeriod);

    }
}
