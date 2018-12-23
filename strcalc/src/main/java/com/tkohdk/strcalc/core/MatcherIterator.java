package com.tkohdk.strcalc.core;

import java.util.Iterator;
import java.util.regex.Matcher;

/**
 * Created by takeoh on 2018/03/07.
 */

public class MatcherIterator implements Iterator<String> {
    Matcher matcher = null;
    int group_index = 1;

    /**
     * 正規表現パターンの照合結果（Matcherオブジェクト）用イテレータ
     * @param matcher
     */
    public MatcherIterator(Matcher matcher){
        this.init(matcher, this.group_index);
    }

    private void init(Matcher matcher, int group_index){
        this.matcher = matcher;
        this.group_index = group_index;
    }
    public String next(){
        return matcher.group(this.group_index);
    }
    public boolean hasNext(){
        return matcher.find();
    }

}
