package com.tkohdk.postfixnotaion.tokenizer;

import com.tkohdk.postfixnotaion.splitter.Splitter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by takeoh on 2018/03/01.
 */

public class StringTokenizer implements Tokenizer<ArrayList<String>, String> {
    Splitter splitter = null;

    public StringTokenizer(Splitter splitter){
        this.splitter = splitter;
    }

    @Override
    public ArrayList<String> getList(String pbm) {
        Iterator<String> ite = this.splitter.getIterator(pbm);
        ArrayList<String> ret = new ArrayList<>();
        while(ite.hasNext()){
            ret.add(ite.next());
        }
        return ret;
    }
}


