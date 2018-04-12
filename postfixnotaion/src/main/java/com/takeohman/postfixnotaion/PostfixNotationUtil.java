package com.takeohman.postfixnotaion;

import com.takeohman.postfixnotaion.calculator.BigDecimalCalculator;
import com.takeohman.postfixnotaion.tokenizer.TokenValueChecker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by takeoh on 2017/09/27.
 */

class PostfixNotationUtil {
    private TokenValueChecker ec;
    private BigDecimalCalculator sc;

    PostfixNotationUtil(TokenValueChecker ec, BigDecimalCalculator sc){
        this.ec = ec;
        this.sc = sc;
    }
    /**
     * 中置き記法計算式の文字列リストから逆ポーランド記法文字列の配列を得る
     * @param problem_str_list 中置き記法文字列
     * @return ArrayList<String>
     */
    ArrayList<String > convertInfixToPostfix(ArrayList<String> problem_str_list){

        ArrayList<String> polish_str_list = new ArrayList<>();
        Stack<String> work_stack = new Stack<>();
        int idx = 0;
        try {
            while (true) {
                //入力の文字列リストの終端まできたら作業用スタックの中身を移し替えて終了する
                if (idx >= problem_str_list.size()) {
                    while (work_stack.size() > 0) {
                        String tmp = work_stack.pop();
                        if (!tmp.equals("(")) {
                            polish_str_list.add(tmp);
                        }
                    }
                    return polish_str_list;
                }

                //文字列配列の先頭から順番に処理
                String list_item = problem_str_list.get(idx++);

                int list_item_priority = this.ec.getValuePriority(list_item);

                while (true) {
                    //作業用スタックが空ではない場合は値を取り出して優先順位を比較する。
                    String stack_item = work_stack.size() > 0 ? work_stack.lastElement() : "";
                    int stack_item_priority = this.ec.getValuePriority(stack_item);

                    if (list_item_priority <= stack_item_priority && !stack_item.equals("(")) {
                        polish_str_list.add(work_stack.pop());
                    } else if (list_item.equals("!")) {
                        if (work_stack.size() > 0) {
                            polish_str_list.add(work_stack.pop());
                        }
                        polish_str_list.add("!");
                        break;
                    } else if (!list_item.equals(")")) {
                        work_stack.push(list_item);
                        break;
                    } else {
                        // list_item == ")" -> work_stack.pop() == "("
                        // TODO: work_stack.size()をチェックortry-catchで補足or stack_item!=""のチェックが必要
                        // TODO: try-catchでエラーとして扱うほうが良い気がする・・・
                        work_stack.pop();
                        break;
                    }
                }
            }
        } catch (Exception ex ){
            return null;
        }
    }
    /**
     * 逆ポーランド記法の規則で積まれたスタックの内容を計算する
     * @param
     * @return
     */
    String calcPolishStrList(ArrayList<String> str_list){
        if (str_list == null){
            return null;
        }

        Stack<String> local_stack = new Stack<>();
        int idx = 0;
        boolean isExceptionFired = false;
        while (true) {
            try {
                if (str_list.size() <= idx){
                    break;
                }
                String st = str_list.get(idx++);

                // まず単項演算子のチェック
                if (st.equals("!")){
                    String a = local_stack.pop();

                    BigDecimal ans = this.sc.factorial(a);
                    local_stack.push(ans.toString());
                } else if (this.ec.isOperator(st)) {
                    // 二項演算子の場合
                    if (local_stack.size() > 1) {
                        String b = local_stack.pop();
                        String a = local_stack.pop();

                        BigDecimal ans = this.sc.calculate(a, b, st);
                        local_stack.push(ans.toString());
                    } else {
                        // 単項演算子ではない（=二項演算子）のにlocal_stackに1つしか要素がない場合
                        String b = local_stack.pop();
                        String a = "0";
                        if (st.equals("*") || st.equals("/")){
                            a = "1";
                        }
                        BigDecimal ans = this.sc.calculate(a, b, st);
                        local_stack.push(ans.toString());
                    }
                } else {
                    local_stack.push(st);
                }
            } catch (Exception e) {
//                return null;
                isExceptionFired = true;
                break;
            }

        }
        // TODO: ここでlocal_stackの中の数字は全部かけ合わせたら？
        while (local_stack.size() > 1){
            String b = local_stack.pop();
            String a = local_stack.pop();
            BigDecimal ans = this.sc.calculate(a, b, "*");
            local_stack.push(ans.toString());
        }
        if (isExceptionFired){
            return null;
        }
        if (local_stack.size() <= 0){
            return "";
        }
        return local_stack.pop();
    }
}
