package com.takeohman.postfixnotaion;

import com.takeohman.postfixnotaion.calculator.BigDecimalCalculator;
import com.takeohman.postfixnotaion.calculator.Calculator;
import com.takeohman.postfixnotaion.checker.BigDecimalNumericChecker;
import com.takeohman.postfixnotaion.checker.FunctionChecker;
import com.takeohman.postfixnotaion.checker.OperatorChecker;
import com.takeohman.postfixnotaion.splitter.StringSplitter;
import com.takeohman.postfixnotaion.tokenizer.StringListTokenizer;
import com.takeohman.postfixnotaion.tokenizer.StringTokenizer;
import com.takeohman.postfixnotaion.tokenizer.TokenElement;
import com.takeohman.postfixnotaion.tokenizer.TokenElementList;
import com.takeohman.postfixnotaion.tokenizer.TokenValueChecker;

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
                    new TokenValueChecker(new BigDecimalNumericChecker(), new FunctionChecker(), new OperatorChecker())
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
    private String calcInfixProblemStrList(TokenElementList pbmTokenObjList){

        Stack<TokenElement> numericStack = new Stack<>();
        Stack<TokenElement> operatorStack = new Stack<>();

        StackUser su = new StackUser(this.calculator, numericStack, operatorStack);
        int idx = 0;
        while (idx < pbmTokenObjList.size()) {

            TokenElement listItem = pbmTokenObjList.get(idx++);

            /*
            処理対象が数字以外の場合
             */
            int listItemPriority = listItem.getPriority();
            while( operatorStack.size() > 0
                    && listItemPriority <= operatorStack.lastElement().getPriority()
                    && (!operatorStack.lastElement().isLeftBracket()
                    || (operatorStack.lastElement().isLeftBracket() && listItem.isRightBracket()))) {
                /*
                スタックの内容を計算する
                 */
                su.doCalc();
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

        return numericStack.pop().getStr();
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

        String ans = null;
        TokenElementList pbmTokenObjList = null;
        try {
            pbmTokenObjList = this.stringListTokenizer.getList(problemStr);
            ans = this.calcInfixProblemStrList(pbmTokenObjList);

        } catch (StackUser.NoElementException ex) {
            //想定内
            ans = null;
        } catch (StringListTokenizer.InvalidElementOrderException ex) {
            //想定内
            ans = null;
        } catch (StringListTokenizer.InvalidBracketCountException ex) {
            ans = null;
        } catch (StringListTokenizer.LeftBracketOnlyException ex){
            ans =  "";
        } catch (Exception ex) {
            //想定外
            ans =  "";
        }
        return new PostfixNotationResult(problemStr, ans, pbmTokenObjList);
    }
}
