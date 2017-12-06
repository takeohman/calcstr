package com.takeohman.postfixnotaion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by takeoh on 2017/09/27.
 */

class PostfixNotationUtil {
    private ExpressionElementChecker ec;
    private StringCalculator sc;

    PostfixNotationUtil(ExpressionElementChecker ec, StringCalculator sc){
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
        while (true){
            //入力の文字列リストの終端まできたら作業用スタックの中身を移し替えて終了する
            if (idx >= problem_str_list.size()){
                while(work_stack.size() > 0){
                    String tmp = work_stack.pop();
                    if(!tmp.equals("(")){
                        polish_str_list.add(tmp);
                    }
                }
                return polish_str_list;
            }

            //文字列配列の先頭から順番に処理
            String list_item = problem_str_list.get(idx++);

            int list_item_priority = this.ec.getValuePriority(list_item);

            while (true){
                //作業用スタックが空ではない場合は値を取り出して優先順位を比較する。
                String stack_item = work_stack.size() > 0 ? work_stack.lastElement():"";
                int stack_item_priority = this.ec.getValuePriority(stack_item);

                if (list_item_priority <= stack_item_priority && !stack_item.equals("(")){
                    polish_str_list.add(work_stack.pop());
                } else if (!list_item.equals(")")){
                    work_stack.push(list_item);
                    break;
                } else {
                    // list_item == ")" -> work_stack.pop() == "("
                    work_stack.pop();
                    break;
                }
            }
        }
    }
    /**
     * 逆ポーランド記法の規則で積まれたスタックの内容を計算する
     * @param
     * @return
     */
    String calcPolishStrList(ArrayList<String> str_list){
        Stack<String> local_stack = new Stack<>();
        int idx = 0;
        while (true) {
            try {
                String st = str_list.get(idx++);
                if (this.ec.isOperator(st)) {
                    String b = local_stack.pop();
                    String a = local_stack.pop();

                    BigDecimal ans = this.sc.calculate(a, b, st);
                    local_stack.push(ans.toString());
                } else {
                    local_stack.push(st);
                }
            } catch (Exception e) {
                break;
            }

        }
        return local_stack.pop();
    }
}
