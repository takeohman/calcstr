package com.takeohman.postfixnotaion.formatter;

import com.takeohman.postfixnotaion.checker.PeriodPositionChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericValueFormatUtil {
    NumericValueFormatter formatter;
    PeriodPositionChecker checker;
    private Pattern patHead;
    private Pattern patTail;
    private Pattern patIsZeroOnly;
    private Pattern patIsZeroHead;
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
        this.patHead = Pattern.compile("^[0-9,.]+");
        this.patTail = Pattern.compile("[0-9,.]+$");
        this.patIsZeroOnly = Pattern.compile("^[0.,]+$");
        this.patIsZeroHead = Pattern.compile("^[0]+");

    }
    MatchedString getNumericEdgeString(String val, Pattern pattern){
        Matcher matcher = pattern.matcher(val);
        if(matcher.find()){
            return new MatchedString(matcher);
        }
        return null;
    }

    String[] getStringsAroundTheCursor(String str_cursor_left, String str_cursor_right){

        MatchedString ms_prefix = this.getNumericEdgeString(str_cursor_left, this.patTail);
        String head_of_numeric_value = "";
        String predecessor_str = str_cursor_left;
        if (ms_prefix != null){
            head_of_numeric_value = ms_prefix.getGroup();
            predecessor_str = str_cursor_left.substring(0, ms_prefix.getStart());
        }

        // The suffix is the head of the value.
        MatchedString ms_suffix = this.getNumericEdgeString(str_cursor_right, this.patHead);
        String tail_of_numeric_value = "";
        String successor_str = str_cursor_right;
        if (ms_suffix != null){
            tail_of_numeric_value = ms_suffix.getGroup();
            successor_str = str_cursor_right.substring(ms_suffix.getEnd());
        }
        String[] _tmp = new String[3];
        _tmp[0] = predecessor_str;
        _tmp[1] = head_of_numeric_value + tail_of_numeric_value;
        _tmp[2] = successor_str;
        return _tmp;
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
        if (split_index > 0){
            if (problem_str.substring(cursorPosition - split_index, cursorPosition).equals(",")){
                split_index+=1;
            }
        }

        String str_cursor_left = problem_str.substring(0, cursorPosition - split_index) + str_to_add;

        String[] strings = this.getStringsAroundTheCursor(str_cursor_left, str_cursor_right);
        String predecessor_str = strings[0];
        String temp_numeric_value = strings[1];
        String successor_str = strings[2];
        String numeric_value = "";
        if (!temp_numeric_value.equals("")) {
            String _v = temp_numeric_value;

            PeriodPositionChecker.PeriodPositionCheckResult checkResult = this.checker.getPeriodPos(_v);
//            Matcher matcher_zero = this.patIsZeroOnly.matcher(_v);

            if (_v.equals(".") || checkResult.getPeriodCnt() > 1){
                // フォーマット不能なのでそのまま返す
                numeric_value = _v;
            }
            else if (this.patIsZeroOnly.matcher(_v).find()){
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
                if (this.patIsZeroHead.matcher(a[0]).find()){
                    numeric_value = _v;
                } else {
                    numeric_value = this.formatter.format(a[0]) + "." + a[1];
                }
            }

        }
        return predecessor_str + numeric_value + successor_str;
    }
}
