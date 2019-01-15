package tkohdk.lib.calcstr;

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
        Comma("comma"),
        ScientificNotation("scientific_notation");

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
                _q = new Question("(+3)", null, tmp, "符号付きの１つの数字：+ ");
                _q.setIsAmbiguous(true); // for PostfixNotationUtilTest
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
            case 60: {
                String[] tmp = {"4", "^","3","^","2"};
                _q = new Question("4^3^2","262144", tmp, "a series of two involution operations.");
                _q.setIsAmbiguous(true);// TODO: need to pass the test case in PostfixNotationUtilTest
                break;
            }
            case 61: {
                String[] tmp = {"2", "^","2","^","3","^","2"};
                // 1.340780793E154
                _q = new Question("2^2^3^2","13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084096", tmp, "a series of two involution operations.");
                _q.setIsAmbiguous(true);// TODO: need to pass the test case in PostfixNotationUtilTest
                break;
            }
            case 62:{
                String[] tmp = {"0", "(","2","(","3",")","-","2",")"};
                _q = new Question("0(2(3)-2)","0", tmp, "multiplication case with subtraction after right bracket");

                break;
            }
            case 63:{
                String[] tmp = {"1","(","*","2","/","3",")"};
                _q = new Question("1(*2/3)",null, tmp, "");
                _q.setIsAmbiguous(true); // for PostfixNotationUtilTest

                break;
            }
            case 64:{
                String[] tmp = {"1","(","*","*","2","/","3",")"};
                _q = new Question("1(**2/3)",null, tmp, "");
                break;
            }
            case 65:{
                String[] tmp = {"5", "*" , "*" , "(" , "6", "-", "1", ")"};
                _q = new Question("5**(6-1)","3125", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }

            case 66:{
                String[] tmp = {"2", "*", "*", "-55.55"};
                _q = new Question("2**-55.55", "1.89576168201698791917337432830149664707679595283817963746741952490992844104766845703125E-17", tmp, "");
                _q.setIsAmbiguous(true);
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
            case 14: {
                String[] tmp = {"123", "*", "*", "-2"};
                // The more accurate answer is 0.00006609822196.
                _q = new Question("123**-2", "0.000066098221957829330036431059181012415137956850230693817138671875", tmp, "involution of minus value");
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
            case 16: {
                // 65*/*6
                String[] tmp = {"65","*","/","*","6"};
                _q = new Question("65*/*6", null, tmp, "InvalidElementOrderException");
                _q.setIsAmbiguous(true);
                break;
            }
            case 17: {
                // 65/*/6
                String[] tmp = {"65","/","*","/","6"};
                _q = new Question("65/*/6", null, tmp, "InvalidElementOrderException");
                _q.setIsAmbiguous(true);
                break;
            }
            case 18: {
                // 65**/6
                String[] tmp = {"65","*","*","/","6"};
                _q = new Question("65**/6", null, tmp, "InvalidElementOrderException");
                _q.setIsAmbiguous(true);
                break;
            }
            case 19: {
                // 65***6
                String[] tmp = {"65","*","*","*","6"};
                _q = new Question("65***6", null, tmp, "InvalidElementOrderException");
                _q.setIsAmbiguous(true);
                break;
            }
            case 20: {
                // 65**+6
                String[] tmp = {"65","*","*","+","6"};
                _q = new Question("65**+6", null, tmp, "InvalidElementOrderException");
                _q.setIsAmbiguous(true);
                break;
            }
            case 21: {
                // 65**-3
                String[] tmp = {"65","*","*","-3"};
                _q = new Question("65**-3", "0.00000364132908511606736193092805076165774380569928325712680816650390625", tmp, "Involution by a negative value");
                _q.setIsAmbiguous(true);
                break;
            }
            case 22: {
                // 65**-3
                String[] tmp = {"12","*","*","555.55"};
                _q = new Question("12**555.55", "346051902930869259255862562121400060443632474677107944390787599659169710993599194353306466666722458350079134821872426266811608679769560414246981977707760751780581870316171958488829615858436777752395276341568837165524062056930140536496003435967836767451283789795621282156757587618780526842583701778399033146510595791475733029320021681487608433494340234454068976271919308055839705128957675694454021998048849551839522063075982757582542213150615036605193564855643228047223182115527276303330544109056053165424859525998199008303546943056407981831500490899611049530750081115506442655469138533075049598943232", tmp, "Involution by a negative value");
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
                String ans = "512457227660167652919721216816298450174082579578141321414949405057376701667864944761946512604878280970954895517166660784669535067223776456995955367539450864513317608107459099530181582907549083015757513460695798749619562450736053146868171870251599313177981845657548585765554078334956377334446910990143952144627431424418746280088728554377476598885356187784547299171800766083569138013272203518042882085661164825284152702064013923237334020997919430381708552278707763648774321918404687260445188855608577257870167224513353440101231509405764502749723340699501026999490254558171129110247489696851219515119346549938559274426812749750560286664023034961804812216630491334788741110907635367662320278106636213136164039583026517516941050248797023068140570535141543124368007868639068641651344166307446804891693896722089788471612463756501862347598685490276574906110846963337013328297278639921470526279912175409596013438428162150685223262482022818573344642493674086259433546546657265530526028524650085879056539966802039733915080922533271458566188380967816906549893479078430521414735637870314289422433695525660309191669609698010791353542267282196962430513568872515694514447210163857531873274898322299277805373915319921462559628044902807941560828510135034938404952418245928010254583321021897876964693254202875162790732917865457123208507570741590360064";
                String[] tmp = {"12","*","*","1234"};
                _q = new Question("12**1234", ans, tmp, "NumberFormatException::Infinite or NaN");
                break;
            }
            case 12: {
                // 333333333**333
                String ans = "1314080690631997019001380075176934353055854544789849183993885309968452531906605976601808352198061788932197266474312375208017269073444126781964087571365440785339172817067773243244978455422382729643321780768573579387642963549919336091951534545089510464956546529860270449625451743897413292119246034500996287926345839027724369236215086369158886734523080269853525471310914787649680264655868263504876764753654175610529398997918244804485893960976958425389640903162765813970959268700377754368879638265773493708337469484836509703996198399441021419519568937847672749019643324502859361805447622290270471454773627654877595983125473382367052090145011411212983219876297336028934913363025057626442369648443080077324657568133436802052487643470454239621442494999680306341433821023526138112344594573328812393513693013454241635841709827373356504754226093947538861041847899897720775064185836144190085028035825072300067031749265587150844530161714094303044378507702057560770797227939881041719741581092796501065724681316490794376739624246097457819681472294005205024834551992686343159093115565414269278557324545333104014826835874441645057655961791895254499408886698641247942359574144588038898602767823475036805334541948673262676039844887674317944620618972092263224711551787216305343050216463639638043317737688238739612185308754575838647837718298798165363860918425103140087838452040788484857814089454589731161935244666507969505068497968368425950263437837060137862713051871333774903680484623179326195515323874354210591819253166597542942081429273091277658568019105596749132425056934721579707986712494252708312025647076718923505676497713659058002310605983890712155516121898891662609942900023149150954229418249543721534800599502485280968909413760565181982908863167035078697751867453278188650994630472783736252489277759915158010798262577078734718466915769537067402256727824691940745307896965507887534791878694119306294249103004302376958129558021413660451347912640685384185541712009875413129969509953305734166719956166326773095125564783464537927744191852243509380718170012615296758507641459705831146272867701171077541279152097503221218763240858548732678988734800506722884963946559728254189870833892589903834716371487029563834805691705247034676776874954686536176889615776209962572926977575874829123065444695933548227414145158719097459733133977187971512951953186385486829599158283992238593163577932943291490124974664150065655566301752420424798118749301275156792188470081556568563576063467126822558830156553566806903746093705180898016085176114822637617610711263757517062236982524309899293025356415134874574684821473127216294426865679663641945191437533227033708608986201922166100443465179598650992104112456078533936063603977552850929800600442719292752344891212346163339456089961174575228653659926741080105390356876065560539705869476639677361678781654145839234939672536245413";
                String[] tmp = {"333333333","*","*","333"};
                _q = new Question("333333333**333", ans, tmp, "NumberFormatException::Infinite or NaN");
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

    public Question get_scientific_notaion(int index) {
        Question _q = null;
        switch (index) {
            case 0: {
                String[] tmp = {"1.2299848E120", "*", "2"};
                _q = new Question("1.2299848E120*2", "2.4599696E+120", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 1: {
                String[] tmp = {"2", "*", "1.2299848E120"};
                _q = new Question("2*1.2299848E120", "2.4599696E+120", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            // TODO: google の 電卓では 答えは5.5511151E-17だった。この誤差が妥当なものかは調査が必要
            case 2:{
                String[] tmp = {"2.7755575E-17", "*", "2"};
                _q = new Question("2.7755575E-17*2", "5.5511150E-17", tmp, "");

                break;
            }
            case 3:{
                String[] tmp = {"-2.7755575E-17", "*", "2"};
                _q = new Question("-2.7755575E-17*2", "-5.5511150E-17", tmp, "");

                break;
            }
            case 4:{
                String[] tmp = {"2", "*", "2.7755575E-17"};
                _q = new Question("2*2.7755575E-17", "5.5511150E-17", tmp, "");
                break;
            }
            case 5:{
                String[] tmp = {"2", "*", "2.7755575E-17"};
                _q = new Question("2*2.7755575E-17", "5.5511150E-17", tmp, "");
                break;
            }
            case 6:{
                String[] tmp = {"2", "*", "-2.7755575E-17"};
                _q = new Question("2*-2.7755575E-17", "-5.5511150E-17", tmp, "");
                break;
            }
            case 7:{
                String[] tmp = {"2", "*", "-2.7755575E-17", "*", "10000"};
//                _q = new Question("2*-2.7755575E-17*10000", "-5.5511150E-17", tmp, "");
                _q = new Question("2*-2.7755575E-17*10000", "-5.55111500000E-13", tmp, "");
                break;
            }
            case 8: {
                String[] tmp = {"-2", "*", "1.2299848E120", "*", "-3"};
                _q = new Question("-2*1.2299848E120*-3", "7.3799088E+120", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
            case 9: {
                String[] tmp = {"-2", "*", "(", "1.2299848E120",")", "*", "-3"};
                _q = new Question("-2*(1.2299848E120)*-3", "7.3799088E+120", tmp, "");
                _q.setIsAmbiguous(true);
                break;
            }
        }
        return _q;
    }

}
