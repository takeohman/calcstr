package com.takeohman.postfixnotaion;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
/**
 * Created by takeoh on 2018/02/22.
 */

public class TestQuestions implements Iterator<TestQuestions.Question> {
    public enum TestQuestionType{
        Basic("basic"),
        ManyOperator("many_operator"),
        UI("ui");

        String type;
        TestQuestionType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }

    }
    TestQuestionType type = null;
    Method get_test_method = null;
    public TestQuestions(TestQuestionType type){
        this.type = type;
        try {
            this.get_test_method = this.getClass().getMethod("get_" + type.getType(), int.class);
        }catch (Exception ex){

        }
        this.cur_index = 0;
    }

    private int cur_index = 0;
    @Override
    public boolean hasNext() {
        return this.get_question(this.cur_index) != null;
    }

    @Override
    public Question next() {
        return this.get_question(this.cur_index++);
    }

    public Question get_question(int index){
        Question _q = null;
        try {
            _q = (Question)this.get_test_method.invoke(this, index);
        } catch (Exception ex){

        }
        return _q;
    }

    public static class Question{
        String question = null;
        String ans = null;
        String desc = null;
        Boolean isAmbiguous = false;
        ArrayList<String> expectedList = null;

        Question(String q, String ans, String[] expectedList, String desc){
            this.question = q;
            this.ans = ans;
            this.desc = desc;
            this.expectedList = new ArrayList<>();
            this.isAmbiguous = false;
            Collections.addAll(this.expectedList, expectedList);
        }
        public String getQuestion(){
            return this.question;
        }
        public String getAns(){
            return this.ans;
        }
        public void setIsAmbiguous(Boolean isAmbiguous){this.isAmbiguous = isAmbiguous;}
        public boolean isAmbiguous(){return this.isAmbiguous;}
        public ArrayList<String> getExpectedList(){return this.expectedList;}
    }
//    protected int id = 0;

    public Question get_basic(int id){
        ArrayList<Question> ql = new ArrayList<>();
        Question _q = null;
        switch(id){
            case 0: {
                String[] tmp = {"+"};
                _q = new Question("+", "0", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 1: {
                String[] tmp = {"*"};
                _q = new Question("*", "1", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 2: {
                String[] tmp = {"+","1"};
                _q = new Question("+1", "1", tmp, "");
                break;
            }
            case 3: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3",")"};
                _q = new Question("(1 + 2) * (2 + 3)", "15", tmp, "");
                break;
            }
            case 4: {
                String[] tmp = {"1","-","(","-1",")"};
                _q = new Question("1-(-1)","2", tmp, "マイナス値の引き算＝足し算");
                break;
            }
            case 5: {
                String[] tmp = {"0.3", "+", "0.3"};
                _q = new Question("0.3 + 0.3", "0.6", tmp, "小数の足し算");
                break;
            }
            case 6: {
                String[] tmp = {"-0.3", "-", "0.3"};
                _q = new Question("-0.3 - 0.3", "-0.6", tmp, "小数の引き算");
                break;
            }
            case 7: {
                String[] tmp = {"(","+","3",")"};
                _q = new Question("(+3)", "3", tmp, "符号付きの１つの数字：+ ");
                break;
            }
            case 8: {
                String[] tmp = {"-2"};
                _q = new Question("- 2", "-2", tmp, "符号付きの１つの数字：- ");
                break;
            }
            case 9: {
                String[] tmp = {"*", "2"};
                _q = new Question("*2","2", tmp, "符号付きの１つの数字：* ");
                break;
            }
            case 10: {
                String[] tmp = {"2", "-"};
                _q = new Question("2-","2", tmp, "不完全な式：二項演算子の右辺がない場合");
                // TODO: 2-と-2の区別がつかない
                _q.setIsAmbiguous(true);
                break;
            }
            case 11: {
                String[] tmp = {"/", "2"};
                _q = new Question("/2", "0.5", tmp, "割り算（割られる数が空の場合に1を対象にすることの確認1）");
                break;
            }
            case 12: {
                String[] tmp = {"/", "3"};
                _q = new Question("/3","0.3333333333", tmp, "割り算（割られる数が空の場合に1を対象にすることの確認2）");
                break;
            }
            case 13: {
                String[] tmp = {"4", "!"};
                _q = new Question("4!","24", tmp, "階乗演算子（ 数字一つ ）");
                break;
            }
            case 14: {
                String[] tmp = {"(", "4", "!"};
                _q = new Question("(4!","24", tmp, "階乗演算子（ 数字一つと左括弧のみ ）");
                break;
            }
            case 15: {
                String[] tmp = {"(", "4", ")", "!"};
                _q = new Question("(4)!","24", tmp, "階乗演算子（ 括弧内の値１つに対して階乗 ）");
                break;
            }
            case 16: {
                // GoogleのWeb版計算機では以下の結果になる。
                // "-" は
                // -4! = -24
                // (-4)! = ERROR
                //
                String[] tmp = {"(", "-4", ")", "!"};
                _q = new Question("(-4)!","-24", tmp, "階乗演算子（ 括弧内のマイナス値１つに対して階乗 ）");
                break;
            }
            case 17: {
                String[] tmp = {"(", "-5", ")", "!"};
                _q = new Question("(-5)!","-120", tmp, "階乗演算子（ 括弧内の値１つに対して階乗 ）");
                break;
            }
            case 18: {
                String[] tmp = {"3", "!", "4"};
                _q = new Question("3!4","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=N!N ）");
                break;
            }
            case 19: {
                String[] tmp = {"(", "3", ")", "!", "4"};
                _q = new Question("(3)!4","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=(N)!N )");
                break;
            }
            case 20: {
                String[] tmp = {"3", "!", "(", "4", ")"};
                _q = new Question("3!(4)","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=N!(N) )");
                break;
            }
            case 21: {
                String[] tmp = {"(", "3", ")", "!", "(", "4", ")"};
                _q = new Question("(3)!(4)","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=(N)!(N) )");
                break;
            }
            case 22: {
                String[] tmp = {"1", "!", "("};
                _q = new Question("1!(","1", tmp, "階乗演算子（左括弧が多い場合）");
                break;
            }
            case 23: {
                String[] tmp = {"2", "^", "3"};
                _q = new Question("2^3","8", tmp, "累乗演算子");
                break;
            }
            case 24: {
                String[] tmp = {"(", "2", ")", "^", "3"};
                _q = new Question("(2)^3","8", tmp, "累乗演算子");
                break;
            }
            case 25: {
                String[] tmp = {"(", "1", "+", "1", ")", "^", "3"};
                _q = new Question("(1+1)^3","8", tmp, "累乗演算子（括弧内の足し算が先に計算されることを確認）");
                break;
            }
            case 26: {
                String[] tmp = {"6", "^", "3", "*", "2", "-", "3", "^", "2"};
                _q = new Question("6^3*2-3^2","423", tmp, "");
                break;
            }
            case 27: {
                String[] tmp = {"2", "^", "3", "!"};
                _q = new Question("2^3!","64", tmp, "");
                break;
            }
            case 28: {
                String[] tmp = {"(", "(", "-4", ")", ")", "!"};
                _q = new Question("((-4))!","-24", tmp, "");
                break;
            }
            case 29: {
                String[] tmp = {"4", "!", "+", "1"};
                _q = new Question("4! + 1","25", tmp, "");
                break;
            }
            case 30: {
                String[] tmp = {"(", "2", "+", "2", ")", "!", "+", "1"};
                _q = new Question("(2 + 2)! + 1","25", tmp, "");
                break;
            }
            case 31: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "4", ")"};
                _q = new Question("(1 + 2) * (2 + 3 * 4) ","42", tmp, "");
                break;
            }
            case 32: {
                // TODO:逆ポーランド記法だと答えは9になる。答えを別に用意する等が必要か。
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "("};
                _q = new Question("(1 + 2) * (2 + 3 * ( ","15", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 33: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4"};
                _q = new Question("(1 + 2) * (2 + 3 * (4 ","42", tmp, "");
                break;
            }
            case 34: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", ")"};
                _q = new Question("(1 + 2) * (2 + 3 * (4) ","42", tmp, "");
                break;
            }
            case 35: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+"};
                _q = new Question("(1 + 2) * (2 + 3 * (4 +  ","42", tmp, "");
                // 逆ポーランド記法にして普通に計算すると17になるのでambiguous=trueにする。
                _q.setIsAmbiguous(true);
                break;
            }
            case 36: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+", "1"};
                _q = new Question("(1 + 2) * (2 + 3 * (4 + 1 ","51", tmp, "");
                break;
            }
            case 37: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+", "1", ")"};
                _q = new Question("(1 + 2) * (2 + 3 * (4 + 1) ","51", tmp, "");
                break;
            }
            case 38: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+", "1", ")", ")"};
                _q = new Question("(1 + 2) * (2 + 3 * (4 + 1)) ","51", tmp, "");
                break;
            }
            case 39: {
                String[] tmp = {"1", "+", "2", "*", "(", "2", "+", "3", "*", "("};
                _q = new Question("1 + 2 * (2 + 3 * ( ","11", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 40: {
                String[] tmp = {"(", "1", ")", "+", "2", "*", "(", "2", "+", "3", "*", "("};
                _q = new Question("(1) + 2 * (2 + 3 * ( ","11", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 41: {
                String[] tmp = {"2", "(", "1", "+", "2", ")"};
                _q = new Question("2(1 + 2)", "6", tmp, "");
                break;
            }
            case 42: {
                String[] tmp = {"3", "(", "2", ")"};
                _q = new Question("3(2)", "6", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 43: {
                String[] tmp = {"3", "(", "(", "2", ")", ")"};
                _q = new Question("3((2))", "6", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 44: {
                String[] tmp = {"(", "3", "(", "(", "2", ")", ")", ")"};
                _q = new Question("(3((2)))", "6", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 45: {
                String[] tmp = {"(", "3", "(", "(", "2", ")", ")"};
                _q = new Question("(3((2))", "6", tmp, "数字のみが並ぶ場合（括弧あり：右括弧が１つ足りない）");
                break;
            }
            case 46: {
                String[] tmp = {"(", "2", ")", "3"};
                _q = new Question("(2)3", "6", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 47: {
                String[] tmp = {"(", "2", ")", "(", "3", ")"};
                _q = new Question("(2)(3)", "6", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 48: {
                String[] tmp = {"(", "3", ")", "4", "(", "5", ")"};
                _q = new Question("(3)4(5)", "60", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 49: {
                String[] tmp = {"(", "3", ")", "(", "4", ")", "(", "5", ")"};
                _q = new Question("(3)(4)(5)", "60", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 50: {
                String[] tmp = {"(", "3", ")", "(", "(", "4", ")", "(", "5", ")"};
                _q = new Question("(3)((4)(5)", "60", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 51: {
                String[] tmp = {"(", "3", ")", "(", "(", "4", ")", "(", "5", ")", ")"};
                _q = new Question("(3)((4)(5))", "60", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 52: {
                String[] tmp = {"(", "(", "3", ")", "(", "4", ")", "(", "5", ")", ")"};
                _q = new Question("((3)(4)(5))", "60", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 53: {
                String[] tmp = {"(", "(", "3", ")", "(", "4", ")", ")", "(", "5", ")", ")"};
                _q = new Question("((3)(4))(5))", null, tmp, "数字のみが並ぶ場合（右括弧過多で計算不可）");
                break;
            }
            case 54: {
                String[] tmp = {"4", "(", "3", "(", "2", ")", ")"};
                _q = new Question("4(3(2))", "24", tmp, "数字のみが並ぶ場合（括弧あり）");
                break;
            }
            case 55: {
                String[] tmp = {"-3", "(", "2", ")"};
                _q = new Question("-3(2)", "-6", tmp, "数字のみが並ぶ場合（マイナス、括弧あり）");
                break;
            }
            case 56: {
                String[] tmp = {"-3", "(", "-2", ")"};
                _q = new Question("-3(-2)", "6", tmp, "数字のみが並ぶ場合（マイナス、括弧あり）");
                break;
            }
            case 57: {
                String[] tmp = {"1.23", ".4"};
                _q = new Question("1.23.4", "0.492", tmp, ".が複数ある場合");
                break;
            }
            case 58: {
                String[] tmp = {".1", ".23", ".4"};
                _q = new Question(".1.23.4", "0.0092", tmp, "数字の先頭に.があり、合計で3つの.がある式。(=0.1*0.23*0.4)");
                break;
            }
        }

        return _q;
    }

    public Question get_many_operator(int index){
        Question _q = null;
        switch (index) {
            case 0: {
                String[] tmp = {"-", "-", "1"};
                _q = new Question("--1", "1", tmp, "-が２つ連続する場合。（数字一つ）");
                _q.setIsAmbiguous(true);
                break;
            }
            case 1: {
                String[] tmp = {"1", "-", "-", "1"};
                _q = new Question("1--1", "2", tmp, "-が２つ連続する場合。（数字２つ）N+Nと判定される。");
                // 逆ポーランドにすると : [1, -, 1, -] = -2 となり期待している2にならない
                _q.setIsAmbiguous(true);
                break;
            }
            case 2: {
                String[] tmp = {"1", "+", "-", "1"};
                _q = new Question("1+-1", "0", tmp, "+-の順番で演算子が並ぶ場合。計算可能");
                break;
            }
            case 3:{
                String[] tmp = {"1", "-", "-", "-", "1"};
                _q = new Question("1---1", "0", tmp, "-が3つ連続する場合。N+-Nと判定される。");
                break;
            }
            case 4: {
                String[] tmp = {"1", "-", "+", "1"};
                _q = new Question("1-+1", null, tmp, "-+の順番で演算子が並ぶ場合。計算不可。");
                _q.setIsAmbiguous(true);
                break;
            }
            case 5: {
                String[] tmp = {"2", "*", "-2"};
                _q = new Question("2*-2", "-4", tmp, "*-の順番で演算子が並ぶ場合。計算可能。");
                break;
            }
            case 6: {
                String[] tmp = {"2", "-", "+", "2"};
                _q = new Question("2-+2", null, tmp, "-+の順番で演算子が並ぶ場合。計算不可。");
                _q.setIsAmbiguous(true);
                break;
            }
            case 7: {
                String[] tmp = {"6", "*", "*", "3"};
                _q = new Question("6**3", "216", tmp, "*が2つ連続する場合。=6*6*6");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
            case 8: {
                String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", ")"};
                _q = new Question("(1 + 2) * (2 + 3)", "15", tmp, "");
                break;
            }
        }
        return _q;
    }

    public Question get_ui(int index){
        Question _q = null;
        switch (index){
            case 0: {
                String[] tmp = {"("};
                _q = new Question("(", "", tmp, "");
                break;
            }
            case 1: {
                String[] tmp = {"(", "1"};
                _q = new Question("(1", "1", tmp, "");
                break;
            }
            case 2: {
                String[] tmp = {"(", "1", "-"};
                _q = new Question("(1-", "1", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 3: {
                String[] tmp = {"(", "1", "-", "2"};
                _q = new Question("(1-2", "-1", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 4: {
                String[] tmp = {"(", "1", "-", "2", "("};
                _q = new Question("(1-2(", "-1", tmp, "");
                break;
            }
            case 5: {
                String[] tmp = {"(", "1", "-", "2", "(", "3"};
                _q = new Question("(1-2(3", "-5", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
        }
        return _q;
    }
}
