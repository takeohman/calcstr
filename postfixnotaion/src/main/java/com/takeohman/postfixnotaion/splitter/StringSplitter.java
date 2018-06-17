package com.takeohman.postfixnotaion.splitter;

import com.takeohman.postfixnotaion.core.MatcherIterator;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by takeoh on 2018/01/30.
 */

public class StringSplitter implements Splitter {
//    private final String splitPattern =
////            "(^[-][0-9]+[.]?[0-9]+|^[-][0-9]+|(?<=\\()[-][0-9]+|[0-9]+[.]?[0-9]+|[0-9]+|!|sin|cos|tan|log|[^()0-9 ]+?|\\(|\\))";
////        "(^[-][0-9]+[.]?[0-9]+|^[-][0-9]+|(?<=([*/]))[-+](?:<P>[0-9]+|[0-9]+[.]?[0-9]+|[0-9]+)|(?<=\\()[-](?:<P>[0-9]+|[0-9]+[.]?[0-9]+|[0-9]+)|[0-9]+[.]?[0-9]+|[0-9]+|!|sin|cos|tan|log|[^()0-9 ]+?|\\(|\\))";
//            "(^[-][0-9]*[.]?[0-9]+|^[-][0-9]+|(?<=([*/]))[-+](?:<P>[0-9]+|[0-9]*[.]?[0-9]+|[0-9]+)|(?<=\\()[-](?:<P>[0-9]+|[0-9]*[.]?[0-9]+|[0-9]+)|[0-9]*[.]?[0-9]+|[0-9]+|!|sin|cos|tan|log|[^()0-9 ]+?|\\(|\\))";
    /**
     * 正規表現で分割した結果をMatcherとして返す
     * @param problemStr 計算問題の文字列
     * @return Matcher
     */
    public Matcher getMatcher(String problemStr){
        String splitPattern = "(^[-][0-9,]*[.]?[0-9]+|^[-][0-9,]+|(?<=([*/]))[-+](?:<P>[0-9,]+|[0-9,]*[.]?[0-9]+|[0-9,]+)|(?<=\\()[-](?:<P>[0-9,]+|[0-9,]*[.]?[0-9]+|[0-9,]+)|[0-9,]*[.]?[0-9]+|[0-9,]+|!|sin|cos|tan|log|[^()0-9, ]+?|\\(|\\))";

        String trimmedStr = problemStr.replace(" ", "");

        Pattern pat = Pattern.compile(splitPattern);
        return pat.matcher(trimmedStr);
    }
    @Override
    public Iterator<String> getIterator(String problemStr){
        return new MatcherIterator(this.getMatcher(problemStr));
    }
}
