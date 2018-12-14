package com.tkohdk.postfixnotaion;

import com.tkohdk.postfixnotaion.calculator.BigDecimalCalculator;
import com.tkohdk.postfixnotaion.calculator.Calculator;
import com.tkohdk.postfixnotaion.checker.BDNumericCheckerEx;
import com.tkohdk.postfixnotaion.checker.FunctionChecker;
import com.tkohdk.postfixnotaion.checker.OperatorChecker;
import com.tkohdk.postfixnotaion.splitter.StringSplitter;
import com.tkohdk.postfixnotaion.tokenizer.StringListTokenizer;
import com.tkohdk.postfixnotaion.tokenizer.StringTokenizer;
import com.tkohdk.postfixnotaion.tokenizer.TokenElement;
import com.tkohdk.postfixnotaion.tokenizer.TokenElementList;
import com.tkohdk.postfixnotaion.tokenizer.TokenElementObject;
import com.tkohdk.postfixnotaion.tokenizer.TokenValueChecker;

import java.util.Stack;

/**
 * Created by takeoh on 2017/08/14.
 */

public class PostfixNotation {

    private Calculator calculator;
    private StringListTokenizer stringListTokenizer;

    /**
     * コンストラクタ
     */
    public PostfixNotation(){

        this.init(
            new BigDecimalCalculator(),
            new StringListTokenizer(
                    new StringTokenizer(new StringSplitter()),
                    new TokenValueChecker(new BDNumericCheckerEx(), new FunctionChecker(), new OperatorChecker())
            )
        );
    }

    /**
     *
     * @param calculator
     * @param stringListTokenizer
     */
    public PostfixNotation(Calculator calculator, StringListTokenizer stringListTokenizer){
        init(calculator, stringListTokenizer);
    }

    /**
     *
     * @param calculator
     * @param stringListTokenizer
     */
    void init(Calculator calculator, StringListTokenizer stringListTokenizer){
        this.calculator = calculator;
        this.stringListTokenizer = stringListTokenizer;
    }


    /**
     * ProblemStrオブジェクトのリストを計算する
     * @param pbmTokenObjList 問題式を分割したProblemStrオブジェクトのリスト
     * @return 答えのString
     */
    public TokenElementObject calcInfixProblemStrList(TokenElementList pbmTokenObjList){

        Stack<TokenElementObject> numericStack = new Stack<>();
        Stack<TokenElementObject> operatorStack = new Stack<>();

        StackUser su = new StackUser(this.calculator, numericStack, operatorStack);
        int idx = 0;
        while (idx < pbmTokenObjList.size()) {

            TokenElement listItem = pbmTokenObjList.get(idx++);

            // 無効な要素の場合は次の要素を取り直し。
            if (!listItem.getIsValid()){
                continue;
            }
            /*
            処理対象が数字以外の場合
             */
            int listItemPriority = listItem.getPriority();
            boolean hasLeftAlreadyPop = false;
            while( operatorStack.size() > 0
                    && !(operatorStack.lastElement().isInvolutionOperator() && listItem.isInvolutionOperator())
                    && listItemPriority <= operatorStack.lastElement().getPriority()
                    && (!operatorStack.lastElement().isLeftBracket()
                    || (!hasLeftAlreadyPop && operatorStack.lastElement().isLeftBracket() && listItem.isRightBracket()))) {
                /*
                スタックの内容を計算する
                 */
                if (su.doCalc()){
                    hasLeftAlreadyPop = true;
                }
            }
            if (listItem.isNumeric()) {
                numericStack.push(listItem);
            }else if (!listItem.isRightBracket()) {
                operatorStack.push(listItem);
            }
        }

        while (operatorStack.size() > 0){
            su.doCalc();
        }

        return numericStack.pop();
    }

    /**
     * 中置き記法の計算問題を計算する
     * @param problemStr 式 (ex. "1 + 2")
     * @return 答え (ex. "3", "": 入力可能だが計算不可能な場合, null:入力不可能文字が入力された場合)
     */
    public String calcInfixStr(String problemStr){
        PostfixNotationResult result = this.calcInfixProblemString(problemStr);
        return result.getAns();
    }

    public PostfixNotationResult calcInfixProblemString(String problemStr){
        // TODO: ansをTokenElement or NumericTokenElementにする
        TokenElementObject ansElm = null;
        TokenElementList pbmTokenObjList = null;
        Exception _ex = null;
        try {
            pbmTokenObjList = this.stringListTokenizer.getList(problemStr);
            ansElm = this.calcInfixProblemStrList(pbmTokenObjList);
        } catch (Exception ex) {
            _ex = ex;
        }
        return new PostfixNotationResult(problemStr, ansElm, pbmTokenObjList, _ex);
    }
}
