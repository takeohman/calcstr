package com.takeohman.postfixnotaion;

import com.takeohman.postfixnotaion.calculator.BigDecimalCalculator;
import com.takeohman.postfixnotaion.calculator.Calculator;
import com.takeohman.postfixnotaion.splitter.StringSplitter;
import com.takeohman.postfixnotaion.tokenizer.StringTokenizer;
import com.takeohman.postfixnotaion.tokenizer.TokenElement;
import com.takeohman.postfixnotaion.tokenizer.TokenValueChecker;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by takeoh on 2017/08/14.
 */

public class PostfixNotation {

    private Calculator calculator;
    private StringTokenizer stringTokenizer;

    /**
     * コンストラクタ
     */
    public PostfixNotation(){

        this.init(
            new BigDecimalCalculator(),
            new StringTokenizer(new StringSplitter(), new TokenValueChecker())
        );
    }

    /**
     *
     * @param calculator
     * @param stringTokenizer
     */
    public PostfixNotation(Calculator calculator, StringTokenizer stringTokenizer){
        init(calculator, stringTokenizer);
    }

    /**
     *
     * @param calculator
     * @param stringTokenizer
     */
    void init(Calculator calculator, StringTokenizer stringTokenizer){
        this.calculator = calculator;
        this.stringTokenizer = stringTokenizer;
    }

    /**
     * ProblemStrオブジェクトのリストを計算する
     * @param pbmTokenObjList 問題式を分割したProblemStrオブジェクトのリスト
     * @return 答えのString
     */
    private String calcInfixProblemStrList(ArrayList<TokenElement> pbmTokenObjList){

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

        try {
            ArrayList<TokenElement> pbmTokenObjList = this.stringTokenizer.getProblemTokenObjListFromStr(problemStr);
            return this.calcInfixProblemStrList(pbmTokenObjList);
        } catch (StackUser.NoElementException ex) {
            //想定内
            return null;
        } catch (StringTokenizer.InvalidElementOrderException ex) {
            //想定内
            return null;
        } catch (StringTokenizer.InvalidBracketCountException ex) {
            return null;
        } catch (StringTokenizer.LeftBracketOnlyException ex){
            return "";
        } catch (Exception ex) {
            //想定外
            return "";
        }
    }
}
