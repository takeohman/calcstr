package com.takeohman.postfixnotaion;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by takeoh on 2017/08/14.
 */

public class PostfixNotation {

    private StringCalculator sc;
    private StringSplitter sp;

    /**
     * コンストラクタ
     */
    public PostfixNotation(){
        this.sc = new StringCalculator();
        this.sp = new StringSplitter();
    }


    /**
     * ProblemStrオブジェクトのリストを計算する
     * @param pbmStrObjList 問題式を分割したProblemStrオブジェクトのリスト
     * @return 答えのString
     */
    private String calcInfixProblemStrList(ArrayList<ProblemStrElement> pbmStrObjList){

        Stack<ProblemStrElement> numericStack = new Stack<>();
        Stack<ProblemStrElement> operatorStack = new Stack<>();

        StackUser su = new StackUser(this.sc, numericStack, operatorStack);
        int idx = 0;
        while (idx < pbmStrObjList.size()) {

            ProblemStrElement listItem = pbmStrObjList.get(idx++);

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
     * @return 答え (ex. "3")
     */
    public String calcInfixStr(String problemStr){

        try {
            ArrayList<ProblemStrElement> pbmStrObjList = this.sp.getProblemStrObjListFromStr(problemStr);
            return this.calcInfixProblemStrList(pbmStrObjList);
        } catch (StackUser.NoElementException ex) {
            //想定内
            return "";
        } catch (StringSplitter.InvalidElementOrderException ex){
            //想定内
            return "";
        } catch (Exception ex) {
            //想定外
            return "";
        }
    }
}
