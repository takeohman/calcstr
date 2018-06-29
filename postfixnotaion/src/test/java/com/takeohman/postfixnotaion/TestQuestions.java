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
        UI("ui"),
        Impossible("impossible"),
        SinCosTan("sin_cos_tan"),
        Log("log"),
        Comma("comma");

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
                _q = new Question("/3","0.333333333333", tmp, "割り算（割られる数が空の場合に1を対象にすることの確認2）");
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
            case 59: {
                String[] tmp = {"/", "3", "+", "100000"};
                _q = new Question("/3 + 100000","100000.333333333333", tmp, "割り算（割られる数が空の場合に1を対象にすることの確認3）");
                break;
            }
        }

        return _q;
    }

    public Question get_many_operator(int index){
        Question _q = null;
        switch (index) {
            case 0: {
                String[] tmp = {"-", "-1"};
//                String[] tmp = {"-", "-", "1"};
                _q = new Question("--1", "1", tmp, "-が２つ連続する場合。（数字一つ）");
                _q.setIsAmbiguous(true);
                break;
            }
            case 1: {
                String[] tmp = {"1", "-", "-1"};
//                String[] tmp = {"1", "-", "-", "1"};
                _q = new Question("1--1", "2", tmp, "-が２つ連続する場合。（数字２つ）N+Nと判定される。");
                // 逆ポーランドにすると : [1, -, 1, -] = -2 となり期待している2にならない
                _q.setIsAmbiguous(true);
                break;
            }
            case 2: {
                String[] tmp = {"1", "+", "-1"};
//                String[] tmp = {"1", "+", "-", "1"};
                _q = new Question("1+-1", "0", tmp, "+-の順番で演算子が並ぶ場合。計算可能");
                break;
            }
            case 3:{
                String[] tmp = {"1", "-", "--1"};
//                String[] tmp = {"1", "-", "-", "-", "1"};
                _q = new Question("1---1", "0", tmp, "-が3つ連続する場合。N+-Nと判定される。");
                _q.setIsAmbiguous(true);
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
            case 9: {
                String[] tmp = {"2", "*", "*", "-1"};
                _q = new Question("2 * * -1", "0.5", tmp, "階乗にマイナス値が渡った場合のテスト");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
            case 10: {
                String[] tmp = {"25", "*", "-5"};
                _q = new Question("25*-5", "-125", tmp, "マイナス値での掛け算");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
            case 11: {
                String[] tmp = {"25", "*", "--5"};
                _q = new Question("25*--5", "125", tmp, "マイナスが２つ付く数値での掛け算");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
            case 12: {
                String[] tmp = {"25", "*", "---5"};
                _q = new Question("25*---5", "-125", tmp, "マイナスが３つ付く数値での掛け算");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
            case 13: {
                String[] tmp = {"15", "*", "6","(","--3",")"};
                _q = new Question("15*6(--3)", "270", tmp, "マイナスが３つ付く数値での掛け算");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
//            TODO : fix to pass the following test case
            case 14: {
                String[] tmp = {"123", "*", "*", "-2"};
                // The more accurate answer is 0.00006609822196.
                _q = new Question("123**-2", "0.000066098222", tmp, "involution of minus value");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
            case 15: {
                String[] tmp = {"5", "*", "*", "5"};
                // The more accurate answer is 0.00006609822196.
                _q = new Question("5**5", "3125", tmp, "involution");
                // 演算子が2つ続く計算は逆ポーランドにそのまま渡すと無理
                _q.setIsAmbiguous(true);
                break;
            }
        }
        return _q;
    }


    public Question get_log(int index){
        Question _q = null;

        switch (index) {
            case 0: {
                String[] tmp = {"log", "(","1",")"};
                _q = new Question("log(1)",
                        "0",
                        tmp, ""
                );
                break;
            }
            case 1: {
                String[] tmp = {"log", "(","10",")"};
                _q = new Question("log(10)",
                        "1",
                        tmp, ""
                );
                break;
            }
            case 2: {
                String[] tmp = {"log", "(","100",")"};
                _q = new Question("log(100)",
                        "2",
                        tmp, ""
                );
                break;
            }
            case 3: {
                // 2.301029996
                String [] tmp = {"log","(","200",")"};
                _q = new Question("log(200)",
                        "2.301029995663981253528618253767490386962890625",
                        tmp, ""
                );
                break;
            }
        }
        return _q;
    }
    /**
     * sin(N), cos(N), tan(N), log(N)等のテスト
     * @param index
     * @return
     */
    public Question get_sin_cos_tan(int index){
        Question _q = null;

        switch (index){
            case 0:{
                String[] tmp = {"sin","(",")"};
                _q = new Question("sin(1)",
                        "0.8414709848078965048756572286947630345821380615234375",
                        tmp, ""
                );
                break;
            }
            case 1:{
                // 0.4997701026
                String[] tmp = {"sin","(","30","*","3.14","/","180",")"};
                _q = new Question("sin(30*3.14/180)",
//                        "0.499770102614230438131670553048024885356426239013671875",
                        "0.499770102642813685012157520759501494467258453369140625",
                        tmp, ""
                );
                break;
            }
            case 2:{
                // 1.8414709848
                String[] tmp = {"1","+","sin","(","1",")"};
                _q = new Question("1 + sin(1)",
                        "1.8414709848078965048756572286947630345821380615234375",
                        tmp, ""
                );
                break;
            }
            case 3:{
                // 1.6829419696
                String[] tmp = {"2","sin","(","1",")"};
                _q = new Question("2sin(1)",
                        "1.6829419696157930097513144573895260691642761230468750",
                        tmp, ""
                );
                break;
            }
            case 4:{
                // 4.6829419696
                String[] tmp = {"2","sin","(","1",")","+","3"};
                _q = new Question("2sin(1) + 3",
                        "4.6829419696157930097513144573895260691642761230468750",
                        tmp, ""
                );
                break;
            }
            /*
            cos()
            */
            case 5:{
                // 0.5403023059
                String[] tmp = {"cos","(","1",")"};
                _q = new Question("cos(1)",
                        "0.540302305868139765010482733487151563167572021484375",
                        tmp, ""
                );
                break;
            }
            case 6:{
                // 0.8661580944
                String[] tmp = {"cos","(","30","*","3.14","/","180",")"};
                _q = new Question("cos(30*3.14/180)",
//                        "0.86615809442212199353861024064826779067516326904296875",
                        "0.86615809440562951948550107772462069988250732421875",
                        tmp, ""
                );
                break;
            }
            case 7:{
                // 1.5403023059
                String[] tmp = {"1","+","cos","(","1",")"};
                _q = new Question("1 + cos(1)",
                        "1.540302305868139765010482733487151563167572021484375",
                        tmp, ""
                );
                break;
            }
            case 8:{
                // 1.0806046118
                String[] tmp = {"2","cos","(","1",")"};
                _q = new Question("2cos(1)",
                        "1.080604611736279530020965466974303126335144042968750",
                        tmp, ""
                );
                break;
            }
            case 9:{
                // 4.0806046118
                String[] tmp = {"2","cos","(","1",")","+","3"};
                _q = new Question("2cos(1) + 3",
                        "4.080604611736279530020965466974303126335144042968750",
                        tmp, ""
                );
                break;
            }
            /*
            tan()
            */
            case 10:{
                String[] tmp = {"tan","(","1",")"};
                _q = new Question("tan(1)",
                        "1.557407724654902292371616567834280431270599365234375",
                        tmp, ""
                );
                break;
            }
            case 11:{
                String[] tmp = {"tan","(","1",")"};
                _q = new Question("tan(1)",
                        "1.557407724654902292371616567834280431270599365234375",
                        tmp, ""
                );
                break;
            }
            case 12:{
                String[] tmp = {"tan","(","30","*","3.14","/","180",")"};
                _q = new Question("tan(30*3.14/180)",
//                        "0.57699640034844212888032188857323490083217620849609375",
                        "0.57699640039242872102676074064220301806926727294921875",
                        tmp, ""
                );
                break;
            }
            case 13:{
                String[] tmp = {"1","+","tan","(","1",")"};
                _q = new Question("1 + tan(1)",
                        "2.557407724654902292371616567834280431270599365234375",
                        tmp, ""
                );
                break;
            }
            case 14:{
                String[] tmp = {"2","tan","(","1",")"};
                _q = new Question("2tan(1)",
                        "3.114815449309804584743233135668560862541198730468750",
                        tmp, ""
                );
                break;
            }
            case 15:{
                String[] tmp = {"2","tan","(","1",")","+","3"};
                _q = new Question("2tan(1) + 3",
                        "6.114815449309804584743233135668560862541198730468750",
                        tmp, ""
                );
                break;
            }
        }
        return _q;
    }
    /**
     * 計算不能なパターン
     * @param index
     * @return Question
     */
    public Question get_impossible(int index){
        Question _q = null;
        switch (index) {
            case 0: {
                //doCalcの最初、stackへのアクセス箇所で例外
                String[] tmp = {"log","(",")"};
                _q = new Question("log()", null, tmp, "");
                break;
            }
            case 1: {
                String[] tmp = {"(",")"};
                _q = new Question("()", null, tmp, "");
                break;
            }
            case 2: {
                String[] tmp = {"(","("};
                _q = new Question("((", "", tmp, "");
                break;
            }
            case 3: {
                String[] tmp = {")"};
                _q = new Question(")", null, tmp, "");
                break;
            }
            case 4: {
                String[] tmp = {")","("};
                _q = new Question(")(", null, tmp, "");
                break;
            }
            case 5: {
                // 50-*5
                String[] tmp = {"50","-","*","5"};
                _q = new Question("50-*5", null, tmp, "演算子の順序例外(-*)");
                break;
            }
            case 6: {
                // 50-/5
                String[] tmp = {"50","-","/","5"};
                _q = new Question("50-/5", null, tmp, "演算子の順序例外(-/)");
                break;
            }
            case 7: {
                // 50+*5
                String[] tmp = {"50","+","*","5"};
                _q = new Question("50+*5", null, tmp, "演算子の順序例外(+*)");
                break;
            }
            case 8: {
                // 50+/5
                String[] tmp = {"50","+","/","5"};
                _q = new Question("50+/5", null, tmp, "演算子の順序例外(+/)");
                break;
            }
            case 9: {
                // 50*+5
                String[] tmp = {"50","*","+","5"};
                _q = new Question("50*+5", null, tmp, "演算子の順序例外(*+)");
                break;
            }
            case 10: {
                // 50/+5
                String[] tmp = {"50","/","+","5"};
                _q = new Question("50/+5", null, tmp, "演算子の順序例外(/+)");
                break;
            }
            case 11: {
                // 12**1234
                String[] tmp = {"12","*","*","1234"};
                _q = new Question("12**1234", null, tmp, "NumberFormatException::Infinite or NaN");
                break;
            }
            case 12: {
                // 333333333**333
                String[] tmp = {"333333333","*","*","333"};
                _q = new Question("333333333**333", null, tmp, "NumberFormatException::Infinite or NaN");
                break;
            }
        }
        return _q;
    }

    /**
     * UIからの入力を想定したテスト
     * @param index
     * @return
     *
     * UIから入力された文字を解析していく際に、数字以外の入力可能な文字のみが入力されている状態と
     * 式として成り立たない入力不可能な文字が入力されている状態を分けて扱わないと何も入力できなくなる。
     * ここではそういったケースをテストしたい。
     */
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
            case 6: {
                String[] tmp = {".01"};
                _q = new Question(".01", "0.01", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 7: {
                String[] tmp = {"-10.", "+", "4"};
                _q = new Question("-10.+4", "-6", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 8: {
                String[] tmp = {"10.", "+", "4"};
                _q = new Question("10.+4", "14", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
        }
        return _q;
    }
    public Question get_comma(int index){
        Question _q = null;
        switch (index){
            case 0: {
                String[] tmp = {"100,000"};
                _q = new Question("100,000", "100000", tmp, "");
                break;
            }
            case 1: {
                String[] tmp = {"100,000", "+", "2,000"};
                _q = new Question("100,000 + 2,000", "102000", tmp, "");
                break;
            }
            case 3: {
                String[] tmp = {"100,000.123", "+", "2,000"};
                _q = new Question("100,000.123 + 2,000", "102000.123", tmp, "");
                break;
            }
        }
        return _q;
    }
}
