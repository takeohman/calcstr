package com.takeohman.postfixnotaion.core;

import java.util.ArrayList;

/**
 * Created by takeoh on 2018/02/10.
 */


public class StringfiableList<T> extends ArrayList<T> {
    public String toString(){
        StringBuilder ret = new StringBuilder();
        for (T _obj : this) {
            ret.append(_obj.toString());
        }
        return ret.toString();
    }
}
