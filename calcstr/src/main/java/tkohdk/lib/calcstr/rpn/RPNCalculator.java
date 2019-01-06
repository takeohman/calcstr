package tkohdk.lib.calcstr.rpn;

import java.math.MathContext;
import java.util.Stack;

import tkohdk.lib.calcstr.calculator.BigDecimalCalculator;
import tkohdk.lib.calcstr.calculator.Calculator;
import tkohdk.lib.calcstr.tokenizer.TokenElement;
import tkohdk.lib.calcstr.tokenizer.TokenElementList;
import tkohdk.lib.calcstr.tokenizer.TokenElementObject;
import tkohdk.lib.calcstr.TokenCalculator;

public class RPNCalculator implements TokenCalculator{
    private Calculator calculator;

    public RPNCalculator(MathContext mc){
        this.calculator = new BigDecimalCalculator(mc);
    }
    /**
     * ProblemStrオブジェクトのリストを計算する
     * @param pbmTokenObjList 問題式を分割したProblemStrオブジェクトのリスト
     * @return 答えのString
     */
    public TokenElementObject calculateTokenElementList(TokenElementList pbmTokenObjList){

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
}
