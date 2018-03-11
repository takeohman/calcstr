package com.takeohman.postfixnotaion;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by takeoh on 2018/02/22.
 */

public class TestQuestions {
    private static ArrayList<Question> questionList = null;


    public static class Question{
        String question = null;
        String ans = null;
        String desc = null;
        ArrayList<String> expectedList = null;

        Question(String q, String ans, String[] expectedList, String desc){
            this.question = q;
            this.ans = ans;
            this.desc = desc;
            this.expectedList = new ArrayList<>();
            Collections.addAll(this.expectedList, expectedList);
        }
        public String getQuestion(){
            return this.question;
        }
        public String getAns(){
            return this.ans;
        }
        public ArrayList<String> getExpectedList(){return this.expectedList;}
    }
    public static ArrayList<Question> getQuestionList(){
        if (questionList == null){
            questionList = _getQuestion();
            questionList.addAll(_getQuestion_TwoOrMoreOperator());
        }
        return questionList;
    }
    public static ArrayList<Question> _getQuestion(){
        ArrayList<Question> ql = new ArrayList<>();

        {
            String[] tmp = {"+"};
            ql.add(new Question("+","0", tmp, ""));
        }
        {
            String[] tmp = {"*"};
            ql.add(new Question("*", "1", tmp, ""));
        }
        {
            String[] tmp = {"+","1"};
            ql.add(new Question("+1", "1", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3",")"};
            ql.add(new Question("(1 + 2) * (2 + 3)", "15", tmp, ""));
        }
        {
            String[] tmp = {"1","-","(","-1",")"};
            ql.add(new Question("1-(-1)","2", tmp, "マイナス値の引き算＝足し算"));
        }
        {
            String[] tmp = {"0.3", "+", "0.3"};
            ql.add(new Question("0.3 + 0.3", "0.6", tmp, "小数の足し算"));
        }
        {
            String[] tmp = {"-0.3", "-", "0.3"};
            ql.add(new Question("-0.3 - 0.3", "-0.6", tmp, "小数の引き算"));
        }
        {
            String[] tmp = {"(","+","3",")"};
            ql.add(new Question("(+3)", "3", tmp, "符号付きの１つの数字：+ "));
        }
        {
            String[] tmp = {"-2"};
            ql.add(new Question("- 2", "-2", tmp, "符号付きの１つの数字：- "));
        }
        {
            String[] tmp = {"*", "2"};
            ql.add(new Question("*2","2", tmp, "符号付きの１つの数字：* "));
        }
        {
            String[] tmp = {"2", "-"};
            ql.add(new Question("2-","2", tmp, "不完全な式：二項演算子の右辺がない場合"));
        }
        {
            String[] tmp = {"/", "2"};
            ql.add(new Question("/2", "0.5", tmp, "割り算（割られる数が空の場合に1を対象にすることの確認1）"));
        }
        {
            String[] tmp = {"/", "3"};
            ql.add(new Question("/3","0.3333333333", tmp, "割り算（割られる数が空の場合に1を対象にすることの確認2）"));
        }
        {
            String[] tmp = {"4", "!"};
            ql.add(new Question("4!","24", tmp, "階乗演算子（ 数字一つ ）"));
        }
        {
            String[] tmp = {"(", "4", "!"};
            ql.add(new Question("(4!","24", tmp, "階乗演算子（ 数字一つと左括弧のみ ）"));
        }
        {
            String[] tmp = {"(", "4", ")", "!"};
            ql.add(new Question("(4)!","24", tmp, "階乗演算子（ 括弧内の値１つに対して階乗 ）"));
        }
        {
            String[] tmp = {"(", "-4", ")", "!"};
            ql.add(new Question("(-4)!","24", tmp, "階乗演算子（ 括弧内のマイナス値１つに対して階乗 ）"));
        }
        {
            String[] tmp = {"3", "!", "4"};
            ql.add(new Question("3!4","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=N!N ）"));
        }
        {
            String[] tmp = {"(", "3", ")", "!", "4"};
            ql.add(new Question("(3)!4","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=(N)!N )"));
        }
        {
            String[] tmp = {"3", "!", "(", "4", ")"};
            ql.add(new Question("3!(4)","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=N!(N) )"));
        }
        {
            String[] tmp = {"(", "3", ")", "!", "(", "4", ")"};
            ql.add(new Question("(3)!(4)","24", tmp, "階乗演算子（ 階乗演算子を挟んで数字が並ぶ場合=(N)!(N) )"));
        }
        {
            String[] tmp = {"1", "!", "("};
            ql.add(new Question("1!(","1", tmp, "階乗演算子（左括弧が多い場合）"));
        }
        {
            String[] tmp = {"2", "^", "3"};
            ql.add(new Question("2^3","8", tmp, "累乗演算子"));
        }
        {
            String[] tmp = {"(", "2", ")", "^", "3"};
            ql.add(new Question("(2)^3","8", tmp, "累乗演算子"));
        }
        {
            String[] tmp = {"(", "1", "+", "1", ")", "^", "3"};
            ql.add(new Question("(1+1)^3","8", tmp, "累乗演算子（括弧内の足し算が先に計算されることを確認）"));
        }
        {
            String[] tmp = {"6", "^", "3", "*", "2", "-", "3", "^", "2"};
            ql.add(new Question("6^3*2-3^2","423", tmp, ""));
        }
        {
            String[] tmp = {"2", "^", "3", "!"};
            ql.add(new Question("2^3!","64", tmp, ""));
        }
        {
            String[] tmp = {"(", "(", "-4", ")", ")", "!"};
            ql.add(new Question("((-4))!","-24", tmp, ""));
        }
        {
            String[] tmp = {"4", "!", "+", "1"};
            ql.add(new Question("4! + 1","25", tmp, ""));
        }
        {
            String[] tmp = {"(", "2", "+", "2", ")", "!", "+", "1"};
            ql.add(new Question("(2 + 2)! + 1","25", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "4", ")"};
            ql.add(new Question("(1 + 2) * (2 + 3 * 4) ","42", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "("};
            ql.add(new Question("(1 + 2) * (2 + 3 * ( ","15", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4"};
            ql.add(new Question("(1 + 2) * (2 + 3 * (4 ","42", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", ")"};
            ql.add(new Question("(1 + 2) * (2 + 3 * (4) ","42", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+"};
            ql.add(new Question("(1 + 2) * (2 + 3 * (4 +  ","42", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+", "1"};
            ql.add(new Question("(1 + 2) * (2 + 3 * (4 + 1 ","51", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+", "1", ")"};
            ql.add(new Question("(1 + 2) * (2 + 3 * (4 + 1) ","51", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", "*", "(", "4", "+", "1", ")", ")"};
            ql.add(new Question("(1 + 2) * (2 + 3 * (4 + 1)) ","51", tmp, ""));
        }
        {
            String[] tmp = {"1", "+", "2", "*", "(", "2", "+", "3", "*", "("};
            ql.add(new Question("1 + 2 * (2 + 3 * ( ","11", tmp, ""));
        }
        {
            String[] tmp = {"(", "1", ")", "+", "2", "*", "(", "2", "+", "3", "*", "("};
            ql.add(new Question("(1) + 2 * (2 + 3 * ( ","11", tmp, ""));
        }
        {
            String[] tmp = {"2", "(", "1", "+", "2", ")"};
            ql.add(new Question("2(1 + 2)", "6", tmp, ""));
        }
        {
            String[] tmp = {"3", "(", "2", ")"};
            ql.add(new Question("3(2)", "6", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"3", "(", "(", "2", ")", ")"};
            ql.add(new Question("3((2))", "6", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "3", "(", "(", "2", ")", ")", ")"};
            ql.add(new Question("(3((2)))", "6", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "3", "(", "(", "2", ")", ")"};
            ql.add(new Question("(3((2))", "6", tmp, "数字のみが並ぶ場合（括弧あり：右括弧が１つ足りない）"));
        }
        {
            String[] tmp = {"(", "2", ")", "3"};
            ql.add(new Question("(2)3", "6", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "2", ")", "(", "3", ")"};
            ql.add(new Question("(2)(3)", "6", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "3", ")", "4", "(", "5", ")"};
            ql.add(new Question("(3)4(5)", "60", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "3", ")", "(", "4", ")", "(", "5", ")"};
            ql.add(new Question("(3)(4)(5)", "60", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "3", ")", "(", "(", "4", ")", "(", "5", ")"};
            ql.add(new Question("(3)((4)(5)", "60", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "3", ")", "(", "(", "4", ")", "(", "5", ")", ")"};
            ql.add(new Question("(3)((4)(5))", "60", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "(", "3", ")", "(", "4", ")", "(", "5", ")", ")"};
            ql.add(new Question("((3)(4)(5))", "60", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"(", "(", "3", ")", "(", "4", ")", ")", "(", "5", ")", ")"};
            ql.add(new Question("((3)(4))(5))", null, tmp, "数字のみが並ぶ場合（右括弧過多で計算不可）"));
        }
        {
            String[] tmp = {"4", "(", "3", "(", "2", ")", ")"};
            ql.add(new Question("4(3(2))", "24", tmp, "数字のみが並ぶ場合（括弧あり）"));
        }
        {
            String[] tmp = {"-3", "(", "2", ")"};
            ql.add(new Question("-3(2)", "-6", tmp, "数字のみが並ぶ場合（マイナス、括弧あり）"));
        }
        {
            String[] tmp = {"-3", "(", "-2", ")"};
            ql.add(new Question("-3(-2)", "6", tmp, "数字のみが並ぶ場合（マイナス、括弧あり）"));
        }
        {
            String[] tmp = {"1.23", ".4"};
            ql.add(new Question("1.23.4", "0.492", tmp, ".が複数ある場合"));
        }
        {
            String[] tmp = {".1", ".23", ".4"};
            ql.add(new Question(".1.23.4", "0.0092", tmp, "数字の先頭に.があり、合計で3つの.がある式。(=0.1*0.23*0.4)"));
        }

        return ql;
    }

    public static ArrayList<Question> _getQuestion_TwoOrMoreOperator(){
        ArrayList<Question> ql = new ArrayList<>();
        {
            String[] tmp = {"-", "-", "1"};
            ql.add(new Question("--1", "1", tmp, "-が２つ連続する場合。（数字一つ）"));
        }
        {
            String[] tmp = {"1", "-", "-", "1"};
            ql.add(new Question("1--1", "2", tmp, "-が２つ連続する場合。（数字２つ）N+Nと判定される。"));
        }
        {
            String[] tmp = {"1", "+", "-", "1"};
            ql.add(new Question("1+-1", "0", tmp, "+-の順番で演算子が並ぶ場合。計算可能"));
        }
        {
            String[] tmp = {"1", "-", "-", "-", "1"};
            ql.add(new Question("1---1", "0", tmp, "-が3つ連続する場合。N+-Nと判定される。"));
        }
        {
            String[] tmp = {"1", "-", "+", "1"};
            ql.add(new Question("1-+1", null, tmp, "-+の順番で演算子が並ぶ場合。計算不可。"));
        }
        {
            String[] tmp = {"2", "*", "-2"};
            ql.add(new Question("2*-2", "-4", tmp, "*-の順番で演算子が並ぶ場合。計算可能。"));
        }
        {
            String[] tmp = {"2", "-", "+", "2"};
            ql.add(new Question("2-+2", null, tmp, "-+の順番で演算子が並ぶ場合。計算不可。"));

        }
        {
            String[] tmp = {"6", "*", "*", "3"};
            ql.add(new Question("6**3", "216", tmp, "*が2つ連続する場合。=6*6*6"));
        }
        {
            String[] tmp = {"(", "1", "+", "2", ")", "*", "(", "2", "+", "3", ")"};
            ql.add(new Question("(1 + 2) * (2 + 3)","15", tmp, ""));
        }
        return ql;
    }
}
