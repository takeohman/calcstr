package com.takeohman.postfixnotaion;

import com.takeohman.postfixnotaion.splitter.StringSplitter;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by takeoh on 2017/08/14.
 */

public class PostfixNotation {

    private StringCalculator sc;
    private StringTokenizer sp;

    /**
     * コンストラクタ
     */
    public PostfixNotation(){
        this.sc = new StringCalculator();
        this.sp = new StringTokenizer(new StringSplitter());
    }


    /**
     * ProblemStrオブジェクトのリストを計算する
     * @param pbmTokenObjList 問題式を分割したProblemStrオブジェクトのリスト
     * @return 答えのString
     */
    private String calcInfixProblemStrList(ArrayList<TokenElement> pbmTokenObjList){

        Stack<TokenElement> numericStack = new Stack<>();
        Stack<TokenElement> operatorStack = new Stack<>();

        StackUser su = new StackUser(this.sc, numericStack, operatorStack);
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
            if (listItem.isNumber()) {
                numericStack.push(listItem);
            }else if (!listItem.isRightBracket()) {
                operatorStack.push(listItem);
            }
        }

        while (operatorStack.size() > 0){
            su.doCalc();
        }

        return numericStack.pop().str;
    }

    /**
     * 中置き記法の計算問題を計算する
     * @param problemStr 式 (ex. "1 + 2")
     * @return 答え (ex. "3", "": 入力可能だが計算不可能な場合, null:入力不可能文字が入力された場合)
     */
    public String calcInfixStr(String problemStr){

        try {
            ArrayList<TokenElement> pbmTokenObjList = this.sp.getProblemTokenObjListFromStr(problemStr);
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
