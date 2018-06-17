package com.takeohman.postfixnotaion.formatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericValueFormatUtil {
    static String regPatternHead = "^[0-9,.]+";
    static String regPatternTail = "[0-9,.]+$";
    NumericValueFormatter formatter;
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
    String convertNumericValueWithCursor(String problem_str, int cursorPosition){
        return this.convertNumericValueWithCursor(problem_str, cursorPosition, "");
    }
    /**
     * Format the numeric value with cursor.
     * @param problem_str String
     * @param cursorPosition int
     * @param insertStr String
     * @return
     */
    String convertNumericValueWithCursor(String problem_str, int cursorPosition, String insertStr){


        if (problem_str.length() <= 0 || cursorPosition < 0){
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
            numeric_value = this.formatter.format(head_of_numeric_value + tail_of_numeric_value);
        }
        return predecessor_str + numeric_value + successor_str;
    }
}
