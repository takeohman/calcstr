package com.takeohman.postfixnotaion.formatter;

import com.takeohman.postfixnotaion.checker.PeriodPositionChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericValueFormatUtil {
    static String regPatternHead = "^[0-9,.]+";
    static String regPatternTail = "[0-9,.]+$";
    NumericValueFormatter formatter;
    PeriodPositionChecker checker;
    class MatchedString {
        Matcher mat;
        MatchedString(Matcher mat){
            this.mat = mat;
        }
        String getGroup(){
            return this.mat.group();
        }
        int getStart(){
            return this.mat.start();
        }
        int getEnd(){
            return this.mat.end();
        }
    }
    public NumericValueFormatUtil(){
        this.formatter = new NumericValueFormatter(new CommaEraser());
        this.checker = new PeriodPositionChecker();

    }
    MatchedString getNumericEdgeString(String val, String regPattern){
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(val);
        if(matcher.find()){
            return new MatchedString(matcher);
        }
        return null;
    }

    /**
     * Format the numeric value with cursor.
     * @param problem_str String
     * @param cursorPosition int
     * @return String
     */
    public String convertNumericValueWithCursor(String problem_str, int cursorPosition){
        return this.convertNumericValueWithCursor(problem_str, cursorPosition, "");
    }
    /**
     * Format the numeric value with cursor.
     * @param problem_str String
     * @param cursorPosition int
     * @param insertStr String
     * @return
     */
    public String convertNumericValueWithCursor(String problem_str, int cursorPosition, String insertStr){


        if ((!(insertStr == null || insertStr.length() > 0) && problem_str.length() <= 0) || cursorPosition < 0){
            return problem_str;
        }
        String str_cursor_right = cursorPosition < problem_str.length() ? problem_str.substring(cursorPosition,
                problem_str.length()) : "";

        String str_to_add = insertStr == null ? "" : insertStr;
        int split_index = (insertStr == null && cursorPosition > 0) ? 1 : 0;
        String str_cursor_left = problem_str.substring(0, cursorPosition - split_index) + str_to_add;

        // The prefix is the tail of the value.
        MatchedString ms_prefix = this.getNumericEdgeString(str_cursor_left, regPatternTail);
        String head_of_numeric_value = "";
        String predecessor_str = str_cursor_left;
        if (ms_prefix != null){
            head_of_numeric_value = ms_prefix.getGroup();
            predecessor_str = str_cursor_left.substring(0, ms_prefix.getStart());

//            System.out.println("==============================");
//            System.out.println("head     : '" + head_of_numeric_value + "'");
//            System.out.println("head len : " + head_of_numeric_value.length());
//            System.out.println("getStart : " + ms_prefix.getStart());
//            System.out.println("getEnd   : " + ms_prefix.getEnd());
//            System.out.println("head head: '" + predecessor_str + "'");
//            Log.d("numeric_value", "head : " + head_of_numeric_value);
        }

        // The suffix is the head of the value.
        MatchedString ms_suffix = this.getNumericEdgeString(str_cursor_right, regPatternHead);
        String tail_of_numeric_value = "";
        String successor_str = str_cursor_right;
        if (ms_suffix != null){
            tail_of_numeric_value = ms_suffix.getGroup();
            successor_str = str_cursor_right.substring(ms_suffix.getEnd());
//            System.out.println("==============================");
//            System.out.println("tail     : '" + tail_of_numeric_value + "'");
//            System.out.println("tail len : " + tail_of_numeric_value.length());
//            System.out.println("getStart : " + ms_suffix.getStart());
//            System.out.println("getEnd   : " + ms_suffix.getEnd());
//            System.out.println("tail end : '" + successor_str + "'");
//
//            Log.d("numeric_value", "tail : " + tail_of_numeric_value);
        }

        String numeric_value = "";
        if (!head_of_numeric_value.equals("") ||  !tail_of_numeric_value.equals("")) {
            String _v = head_of_numeric_value + tail_of_numeric_value;

            PeriodPositionChecker.PeriodPositionCheckResult checkResult = this.checker.getPeriodPos(_v);
            Pattern pattern_zero = Pattern.compile("^[0.,]+$");
            Matcher matcher_zero = pattern_zero.matcher(_v);

            if (_v.equals(".") || checkResult.getPeriodCnt() > 1){
                // フォーマット不能なのでそのまま返す
                numeric_value = _v;
            }
            else if (matcher_zero.find()){
                numeric_value = _v;
            }
            else if (checkResult.getPeriodCnt()==0){
                // periodなしは普通にフォーマット
                numeric_value = this.formatter.format(_v);
            }
            else if (checkResult.isHeadPeriod()){
                numeric_value = _v;
            }
            else if (checkResult.isTailPeriod()){
                numeric_value = _v.substring(0, _v.length()-1) + ".";
            }
            else {

                String[] a = _v.split("(?<=[0-9])\\.(?=[0-9])", 2);

                numeric_value = this.formatter.format(a[0]) + "." + a[1];
            }

        }
        return predecessor_str + numeric_value + successor_str;
    }
}
