package tkohdk.lib.calcstr.splitter;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tkohdk.lib.calcstr.core.MatcherIterator;

/**
 * Created by takeoh on 2018/01/30.
 */

public class StringSplitter implements Splitter {

    private Pattern pat;
    public StringSplitter(){
        //                     |Scientific Notification 1-1         |Scientific .... 1-2        |                         |2                    |3          |Scientific ... 4-1             |Scientific ... 4-2    |Scientific ... 5-1                             |Scientific ... 5-2                     |                       |                          |                         |                          |                     |5                |6      |7|8  |9  |10 |11 |12          |
        String splitPattern = "(^[-]?[0-9]+[.][0-9,]+[Ee][-+]?[0-9]+|^[-]?[0-9,]+[Ee][-+]?[0-9]+|^[-][0-9,]+[.]+(?=[*/+-])|^[-][0-9,]*[.]?[0-9]+|^[-][0-9,]+|[0-9]+[.][0-9,]+[Ee][-+]?[0-9]+|[0-9,]+[Ee][-+]?[0-9]+|(?<=([*/+-]))[-]+[0-9]+[.][0-9,]+[Ee][-+]?[0-9]+|(?<=([*/+-]))[-]+[0-9,]+[Ee][-+]?[0-9]+|(?<=([*/+-]))[-]+(?:<P>|[0-9,]*[.]?[0-9]+|[0-9,]+)|(?<=\\()[-]+(?:<P>[0-9,]+|[0-9,]*[.]?[0-9]+|[0-9,]+)|[0-9,]+[.]+(?=[*/+-])|[0-9,]*[.]?[0-9]+|[0-9,]+|!|sin|cos|tan|log|[^()0-9, ]+?|\\(|\\))";
        this.pat = Pattern.compile(splitPattern);
    }
    /**
     * 正規表現で分割した結果をMatcherとして返す
     * @param problemStr 計算問題の文字列
     * @return Matcher
     */
    public Matcher getMatcher(String problemStr){
        String trimmedStr = problemStr.replace(" ", "");
        return pat.matcher(trimmedStr);
    }

    /**
     *
     * @param problemStr
     * @return
     */
    @Override
    public Iterator<String> getIterator(String problemStr){
        return new MatcherIterator(this.getMatcher(problemStr));
    }
}
