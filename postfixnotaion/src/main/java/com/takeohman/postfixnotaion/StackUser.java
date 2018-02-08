package com.takeohman.postfixnotaion;

import com.takeohman.postfixnotaion.calculator.Calculator;
import com.takeohman.postfixnotaion.tokenizer.TokenElement;
import com.takeohman.postfixnotaion.tokenizer.TokenValueChecker;

import java.util.Stack;

/**
 * Created by takeoh on 2017/11/09.
 */

class StackUser{
    private Calculator sc;
    private TokenValueChecker ec;
    private Stack<TokenElement> numericStack;
    private Stack<TokenElement> operatorStack;
    StackUser(Calculator sc, Stack<TokenElement> numericStack, Stack<TokenElement> operatorStack){
        this.numericStack = numericStack;
        this.operatorStack = operatorStack;
        this.sc = sc;
        this.ec = new TokenValueChecker();
    }
    class NoElementException extends RuntimeException{}
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
    void doCalc(){

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
            TokenElement pbm = operatorStack.pop();

            if (pbm.isExclamation()){
                Number fcAns = this.sc.factorial(numericStack.pop().getStr());
                numericStack.push(new TokenElement(this.ec, pbm.getIndex(), fcAns.toString()));
                return;
            }
            if (operatorStack.size() <= 0){
                return;
            }
        }
        TokenElement _ope = operatorStack.pop();
        if (_ope.isLeftBracket()){
            return;
        }

        /*
        sin, cos, tan, log
         */
        if (_ope.isFunction()){
            TokenElement _num = numericStack.pop();
            Number _a = null;
            if (_ope.isSineFunc()){
                _a = this.sc.sin(_num.getStr());
            } else if (_ope.isCosineFunc()){
                _a = this.sc.cos(_num.getStr());
            } else if (_ope.isTangentFunc()){
                _a = this.sc.tan(_num.getStr());
            } else if (_ope.isLogarithmFunc()){
                _a = this.sc.log10(_num.getStr());
            }
            if (_a != null){
                numericStack.push(new TokenElement(this.ec, _num.getIndex(), _a.toString()));
            }
            return;
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
                return;
            }else if (_ope.isMinusOperator()){
                /*
                演算子が"-"の場合は符号が変わるので-1を掛ける
                 */
                TokenElement _num = numericStack.pop();
                Number _a = this.sc.multiply(_num.getStr(), "-1");
                numericStack.push(new TokenElement(this.ec, 0, _a.toString()));
                return;
            } else if (_ope.isDivisionOperator()){
                /*
                演算子が/の場合は、1をスタックの数字で割る。
                 */
                TokenElement _num = numericStack.pop();
                Number _a = this.sc.divide("1", _num.getStr());
                numericStack.push(new TokenElement(this.ec, 0, _a.toString()));
                return;
            }
        }
        /*
        二項演算子の計算
         */

        TokenElement b = numericStack.pop();
        TokenElement a = numericStack.pop();

        Number ans = this.sc.calculate(a.getStr(), b.getStr(), _ope.getStr());
        numericStack.push(new TokenElement(this.ec, b.getIndex(), ans.toString()));
    }
}
