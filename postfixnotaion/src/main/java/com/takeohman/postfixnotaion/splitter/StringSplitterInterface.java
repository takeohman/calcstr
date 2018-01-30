package com.takeohman.postfixnotaion.splitter;

import java.util.regex.Matcher;

/**
 * Created by takeoh on 2018/01/30.
 */

public interface StringSplitterInterface {
    Matcher getMatcher(String problemStr);
}
