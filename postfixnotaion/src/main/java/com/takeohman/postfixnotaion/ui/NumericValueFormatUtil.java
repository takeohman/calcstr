package com.takeohman.postfixnotaion.ui;

import com.takeohman.postfixnotaion.checker.BigDecimalNumericChecker;
import com.takeohman.postfixnotaion.checker.PeriodPositionChecker;
import com.takeohman.postfixnotaion.formatter.CommaEraser;
import com.takeohman.postfixnotaion.formatter.NumericValueFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericValueFormatUtil {
    NumericValueFormatter formatter;
    PeriodPositionChecker checker;
    private Pattern patHead;
    private Pattern patTail;
    private Pattern patHeadE;
    private Pattern patHeadNum;
    private Pattern patTailE;

    /**
     *
     */
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

    /**
     *
     */
    public NumericValueFormatUtil(){
        this.formatter = new NumericValueFormatter(new CommaEraser());
        this.checker = new PeriodPositionChecker();
        this.patHead = Pattern.compile("^[0-9,.]+");
        this.patTail = Pattern.compile("[0-9,.]+$");

        this.patHeadE = Pattern.compile("^(E[+-][0-9]+)(.*)");
        this.patHeadNum = Pattern.compile("^([+-][0-9]+)(.*)");
        this.patTailE = Pattern.compile("(^|.*[^0-9.]+)([0-9.]+E[+-]?)$");
    }

    /**
     *
     * @param val
     * @param pattern
     * @return
     */
    MatchedString getNumericEdgeString(String val, Pattern pattern){
        Matcher matcher = pattern.matcher(val);
        if(matcher.find()){
            return new MatchedString(matcher);
        }
        return null;
    }

    /**
     *
     * @param s
     * @return
     */
    String[] getEStringAndOthersFromHead(String s){
        return this.getStringsFromMatcher(s, this.patHeadE);
    }

    /**
     *
     * @param s
     * @return
     */
    String[] getNumStringAndOthersFromHead(String s){
        return this.getStringsFromMatcher(s, this.patHeadNum);
    }

    /**
     *
     * @param s
     * @return
     */
    String[] getEStringAndOthersFromTail(String s) {
        return this.getStringsFromMatcher(s, this.patTailE);
    }

    /**
     *
     * @param s
     * @param _p
     * @return
     */
    private String[] getStringsFromMatcher(String s, Pattern _p){
        Matcher _m = _p.matcher(s);
        String[] ret = null;
        if (_m.find()) {
            if (_m.groupCount() > 0) {
                ret = new String[_m.groupCount()];
                for (int i = 0; i < ret.length; i++) {
                    ret[i] = _m.group(i + 1);
                }
            }
        }
        return ret;
    }
    /**
     * Parse the strings of cursor's left and right, and return result in a string array with 3 elements.
     * @param str_cursor_left : string of cursor's left
     * @param str_cursor_right: string of cursor's right
     * @return String[]
     *
     * [0]  :a predecessor string
     * [1]  :a number string of cursor's left
     * [2]  :a number string of cursor's right
     * [3]  :a successor string
     */
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
        String combined_numeric_value = head_of_numeric_value + tail_of_numeric_value;
        String[] _tmp = new String[4];
        _tmp[0] = predecessor_str;
        _tmp[1] = head_of_numeric_value;
        _tmp[2] = tail_of_numeric_value;
        _tmp[3] = successor_str;

        // check : ...1.23E ???
        String[] parts_from_predecessor = this.getEStringAndOthersFromTail(predecessor_str);

        // if combined_numeric_value is empty, the value can be a number in scientific notation.
        if (combined_numeric_value.equals("")){
            // case : ..1.23E && -45...
            String[] num_parts_from_successor = this.getNumStringAndOthersFromHead(successor_str);
            if (parts_from_predecessor != null && num_parts_from_successor != null){
                _tmp[0] = parts_from_predecessor[0];
                _tmp[1] = parts_from_predecessor[1];
                _tmp[2] = num_parts_from_successor[0];
                _tmp[3] = num_parts_from_successor[1];
            }
        } else {
            String[] parts_from_successor = this.getEStringAndOthersFromHead(successor_str);
            if (parts_from_successor != null){
                _tmp[0] = predecessor_str;
                _tmp[1] = combined_numeric_value;
                _tmp[2] = parts_from_successor[0];
                _tmp[3] = parts_from_successor[1];
            } else if (parts_from_predecessor != null){
                _tmp[0] = parts_from_predecessor[0];
                _tmp[1] = parts_from_predecessor[1];
                _tmp[2] = combined_numeric_value;
                _tmp[3] = successor_str;
            }
        }
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
     *
     * @param numericString
     * @return
     */
    public String formatNumericString(String numericString){
        String numeric_value = "";
        if (!numericString.equals("")) {
            String _v = numericString;

            String[] split_str = _v.split("\\.", 2);
            boolean is_head_zero = split_str[0].length() > 0 && String.valueOf(split_str[0].charAt(0)).equals("0");
            if (split_str.length < 2){
                if (is_head_zero){
                    String reg = "(?<=\\d)(?=(\\d{3})+($|\\D))";
                    Pattern pat = Pattern.compile(reg);
                    Matcher mat = pat.matcher(split_str[0].replace(",",""));
                    numeric_value = mat.replaceAll(",");
                } else {
                    numeric_value = this.formatter.format(split_str[0]);
                }
            } else {
                boolean is_head_period = split_str[0].equals("");
                boolean is_tail_period = split_str[1].equals("");

                if (is_head_period && is_tail_period){  // "."
                    numeric_value = _v;
                } else if (is_head_period) {            // ".1234"
                    numeric_value = _v;
                } else {
                    // "123,456." , 123,456.789
                    if (is_head_zero){
                        String reg = "(?<=\\d)(?=(\\d{3})+($|\\D))";
                        Pattern pat = Pattern.compile(reg);
                        Matcher mat = pat.matcher(split_str[0].replace(",",""));
                        numeric_value = mat.replaceAll(",") + "." + split_str[1];
                    } else {
                        numeric_value = this.formatter.format(split_str[0]) + "." + split_str[1];
                    }
                }
            }
        }
        return numeric_value;
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

        BigDecimalNumericChecker bdChecker = new BigDecimalNumericChecker();
        String temp_str_cursor_left = problem_str.substring(0, cursorPosition - split_index);

        String left_char_of_cursor = "";

        if (temp_str_cursor_left.length() > 0){
            left_char_of_cursor = String.valueOf(temp_str_cursor_left.charAt(temp_str_cursor_left.length()-1));
        }
        String str_cursor_left = null;
//        StringBuilder sb_cursor_left = new StringBuilder("");
        if (bdChecker.isNumeric(str_to_add) || str_to_add.equals("")) {
            // str_to_addが数字の場合は、追加先が数字だった場合、数字を分割しないのでそのままで大丈夫
            str_cursor_left = temp_str_cursor_left + str_to_add;
//            sb_cursor_left.append(temp_str_cursor_left).append(str_to_add);
        } else if (str_cursor_right.equals("") || !bdChecker.isNumeric(String.valueOf(str_cursor_right.charAt(0)))){
            // str_to_addの追加先の右側が数字ではない場合は、追加先の数字を分割しないのでそのままで大丈夫
            str_cursor_left = temp_str_cursor_left + str_to_add;
//            sb_cursor_left.append(temp_str_cursor_left).append(str_to_add);
        } else if (!bdChecker.isNumeric(left_char_of_cursor) && !left_char_of_cursor.equals(",")){
            // str_to_addの追加先の左側が数字ではない場合は、追加先の数字を分割しないのでそのままで大丈夫
            str_cursor_left = temp_str_cursor_left + str_to_add;
//            sb_cursor_left.append(temp_str_cursor_left).append(str_to_add);
        } else {
            // when the str_to_add is not a numeric value, the numeric string left of it should be reformatted.
            str_cursor_left = this.convertNumericValueWithCursor(temp_str_cursor_left,temp_str_cursor_left.length(),"") + str_to_add;
//            sb_cursor_left.append(this.convertNumericValueWithCursor(temp_str_cursor_left,temp_str_cursor_left.length(),"") ).append(str_to_add);
        }

        String[] strings = this.getStringsAroundTheCursor(str_cursor_left, str_cursor_right);
        String num_head = strings[1];
        String num_tail = strings[2];
        if (num_head.endsWith(".")){
            // TODO: check the length of before and after to move the cursor position.
            num_tail = num_tail.replace(",","");
        }
        return strings[0] + this.formatNumericString(num_head + num_tail) + strings[3];

    }
}
