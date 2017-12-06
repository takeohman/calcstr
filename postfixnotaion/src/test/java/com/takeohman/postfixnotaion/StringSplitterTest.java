package com.takeohman.postfixnotaion;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/11/15.
 */
public class StringSplitterTest {

    @Test
    public void getProblemStrObjListFromStr() throws Exception {
        StringSplitter splitter = new StringSplitter();
        ArrayList<ProblemStrElement> temp = splitter.getProblemStrObjListFromStr("1 * (2 + 3) ");
        ArrayList<String> l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        ArrayList<String> e1 = new ArrayList<>();
        e1.add("1");
        e1.add("*");
        e1.add("(");
        e1.add("2");
        e1.add("+");
        e1.add("3");
        e1.add(")");
        assertEquals(e1, l1);

        temp = splitter.getProblemStrObjListFromStr("(1 + 2) * (2 + 3) ");
        l1 = new ArrayList<>();
        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        ArrayList<String> e2 = new ArrayList<>();
        e2.add("(");
        e2.add("1");
        e2.add("+");
        e2.add("2");
        e2.add(")");
        e2.add("*");
        e2.add("(");
        e2.add("2");
        e2.add("+");
        e2.add("3");
        e2.add(")");
        assertEquals(e2, l1);

        temp = splitter.getProblemStrObjListFromStr("(1 + 2) * (2 + 3) + 0.789");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        ArrayList<String> e3 = new ArrayList<>();
        e3.add("(");
        e3.add("1");
        e3.add("+");
        e3.add("2");
        e3.add(")");
        e3.add("*");
        e3.add("(");
        e3.add("2");
        e3.add("+");
        e3.add("3");
        e3.add(")");
        e3.add("+");
        e3.add("0.789");
        assertEquals(e3, l1);

        temp = splitter.getProblemStrObjListFromStr("-2(-2 - 1)-1-(-2)");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        ArrayList<String> e4 = new ArrayList<>();

        e4.add("-2");
        e4.add("*");
        e4.add("(");
        e4.add("-2");
        e4.add("-");
        e4.add("1");
        e4.add(")");
        e4.add("-");
        e4.add("1");
        e4.add("-");
        e4.add("(");
        e4.add("-2");
        e4.add(")");
        assertEquals(e4, l1);


        /*
        N! の表記も対応してみたいので分割できるようにしておく
         */
        temp = splitter.getProblemStrObjListFromStr("3! + 4!");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        ArrayList<String> e5 = new ArrayList<>();

        e5.add("3");
        e5.add("!");
        e5.add("+");
        e5.add("4");
        e5.add("!");
        assertEquals(e5, l1);

        temp = splitter.getProblemStrObjListFromStr("1 - - 1");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("1");
        e5.add("+");
        e5.add("1");
        assertEquals(e5, l1);


        temp = splitter.getProblemStrObjListFromStr("-3");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("-3");
        assertEquals(e5, l1);

        /*
        +3は、正規表現で-3と同じように+3として取り出すこともできるが
        最終的には"+"を取り除くことを考えると別々の要素とした方がよいと判断した。
         */
        temp = splitter.getProblemStrObjListFromStr("+3");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("+");
        e5.add("3");
        assertEquals(e5, l1);

        temp = splitter.getProblemStrObjListFromStr("tan(3)");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("tan");
        e5.add("(");
        e5.add("3");
        e5.add(")");
        assertEquals(e5, l1);

        temp = splitter.getProblemStrObjListFromStr("1 + tan(3)");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("1");
        e5.add("+");
        e5.add("tan");
        e5.add("(");
        e5.add("3");
        e5.add(")");
        assertEquals(e5, l1);

        temp = splitter.getProblemStrObjListFromStr("1 - tan(3)");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("1");
        e5.add("-");
        e5.add("tan");
        e5.add("(");
        e5.add("3");
        e5.add(")");
        assertEquals(e5, l1);

        temp = splitter.getProblemStrObjListFromStr("1 * tan(3)");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("1");
        e5.add("*");
        e5.add("tan");
        e5.add("(");
        e5.add("3");
        e5.add(")");
        assertEquals(e5, l1);

        temp = splitter.getProblemStrObjListFromStr("3 / tan(3)");
        l1 = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++){
            l1.add(temp.get(i).str);
        }
        e5 = new ArrayList<>();
        e5.add("3");
        e5.add("/");
        e5.add("tan");
        e5.add("(");
        e5.add("3");
        e5.add(")");
        assertEquals(e5, l1);
    }

}