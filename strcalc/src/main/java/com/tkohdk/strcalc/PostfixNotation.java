package com.tkohdk.strcalc;

import com.tkohdk.strcalc.calculator.BigDecimalCalculator;
import com.tkohdk.strcalc.calculator.Calculator;
import com.tkohdk.strcalc.checker.BDNumericCheckerEx;
import com.tkohdk.strcalc.checker.FunctionChecker;
import com.tkohdk.strcalc.checker.OperatorChecker;
import com.tkohdk.strcalc.splitter.StringSplitter;
import com.tkohdk.strcalc.tokenizer.StringListTokenizer;
import com.tkohdk.strcalc.tokenizer.StringTokenizer;
import com.tkohdk.strcalc.tokenizer.TokenElement;
import com.tkohdk.strcalc.tokenizer.TokenElementList;
import com.tkohdk.strcalc.tokenizer.TokenElementObject;
import com.tkohdk.strcalc.tokenizer.TokenValueChecker;

import java.math.MathContext;
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
    public PostfixNotation(MathContext mc){
//        MathContext mc = new MathContext(12, RoundingMode.HALF_EVEN);
        this.init(
            new BigDecimalCalculator(mc),
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
