package tkohdk.lib.calcstr.rpn;

import java.math.BigDecimal;
import java.util.Stack;

import tkohdk.lib.calcstr.calculator.Calculator;
import tkohdk.lib.calcstr.exception.InvalidOperatorException;
import tkohdk.lib.calcstr.exception.NoElementException;
import tkohdk.lib.calcstr.tokenizer.NumericTokenElement;
import tkohdk.lib.calcstr.tokenizer.TokenElementObject;

/**
 * Created by takeoh on 2017/11/09.
 */

class StackUser {
    private Calculator<BigDecimal, BigDecimal> sc;
//    private TokenValueChecker ec;
    private Stack<TokenElementObject> numericStack;
    private Stack<TokenElementObject> operatorStack;
    StackUser(Calculator sc, Stack<TokenElementObject> numericStack, Stack<TokenElementObject> operatorStack){
        this.numericStack = numericStack;
        this.operatorStack = operatorStack;
        this.sc = sc;
    }
    /**
     *
     * 演算子がない場合：ここにはこない
     * 　【理由】
     * 　　・数字スタックは答えのはず。
     * 　　・数字が複数あっても計算方法がわからないので何もできない。
     * 演算子がある場合：とりあえず計算
     * 　　・階乗演算子の場合 => 演算子のすぐ前の数字に対して計算する
     * 　　・その他は二項演算子の前提で処理をすすめる。
     */
    boolean doCalc(){

        // スタックが空だといずれにせよ例外になるので、ここで独自の例外を投げておく。
        if (numericStack.size() <= 0 || operatorStack.size() <= 0){
            throw new NoElementException();
        }

        int idx_b = numericStack.lastElement().getIndex();

        while (idx_b < operatorStack.lastElement().getIndex()){
            /*
            二項演算子で計算する場合、二項目の数字よりも後には基本的には何もないはずなのでここで掃除をする。

            追加：N!（階乗）の対応はここで行う。
             */
            TokenElementObject pbm = operatorStack.pop();

            if (pbm.isExclamation()){
                BigDecimal fcAns = this.sc.factorial(numericStack.pop().getNumberObject());
                numericStack.push(new NumericTokenElement(pbm.getIndex(), fcAns));
                return false;
            }
            if (operatorStack.size() <= 0){
                return false;
            }
        }
        TokenElementObject _ope = operatorStack.pop();
        if (_ope.isLeftBracket()){
            // 左括弧削除済みの場合にtrue
            return true;
        }

        /*
        sin, cos, tan, log
         */
        if (_ope.isFunction()){
            TokenElementObject _num = numericStack.pop();
            BigDecimal _a = null;
            if (_ope.isSineFunc()){
                _a = this.sc.sin(_num.getNumberObject());
            } else if (_ope.isCosineFunc()){
                _a = this.sc.cos(_num.getNumberObject());
            } else if (_ope.isTangentFunc()){
                _a = this.sc.tan(_num.getNumberObject());
            } else if (_ope.isLogarithmFunc()){
                _a = this.sc.log10(_num.getNumberObject());
            }
            if (_a != null){
                numericStack.push(new NumericTokenElement(_num.getIndex(), _a));
            }
            return false;
        }

        /*
        以降は二項演算子の計算なので、ここで、例えば"+1"のような要素のみが残っているケースをチェックしておく
         */
        if (numericStack.size() == 1) {
            /*
            ここの段階で、数値より後ろの演算子は消えている。

            演算子が無い場合：ここには来ない
            演算子がある場合：最後の数字に対する直前の演算子なので数値に適応可能な場合は適応して終了
             */
            if (_ope.isPlusOperator() || _ope.isMultiplicationOperator()) {
                /*
                演算子が"+", "*"の場合はなにもしない
                 */
                return false;
            }else if (_ope.isMinusOperator()){
                /*
                演算子が"-"の場合は符号が変わるので-1を掛ける
                 */
                TokenElementObject _num = numericStack.pop();
                BigDecimal _a = this.sc.multiply(_num.getNumberObject(), new BigDecimal("-1"));
                numericStack.push(new NumericTokenElement(0, _a));
                return false;
            } else if (_ope.isDivisionOperator()){
                /*
                演算子が/の場合は、1をスタックの数字で割る。
                 */
                TokenElementObject _num = numericStack.pop();
                BigDecimal _a = this.sc.divide(new BigDecimal("1"), _num.getNumberObject());
                numericStack.push(new NumericTokenElement( 0, _a));
                return false;
            }
        }
        /*
        二項演算子の計算
         */

        TokenElementObject b = numericStack.pop();
        TokenElementObject a = numericStack.pop();

        BigDecimal ans = this.sc.calculate(_ope.getStr(), a.getNumberObject(), b.getNumberObject());
        if (ans == null){
            // if ans is null, it must be caused by inputting invalid operator.
            throw new InvalidOperatorException();
        }
        numericStack.push(new NumericTokenElement(b.getIndex(), ans));
        return false;
    }
}
