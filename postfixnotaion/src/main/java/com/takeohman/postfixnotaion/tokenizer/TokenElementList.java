package com.takeohman.postfixnotaion.tokenizer;

import com.takeohman.postfixnotaion.core.StringfiableList;

/**
 * Created by takeoh on 2018/02/10.
 */

public class TokenElementList extends StringfiableList<TokenElement> {
    private int numeric_item_cnt = 0;
    private int function_item_cnt = 0;
    private int binary_operator_cnt = 0;
    private int unary_operator_cnt = 0;


    protected void countItem(TokenElement tokenElement, Boolean isAdding){
        int num_to_add = -1;

        if (isAdding){
            num_to_add = 1;
        }

        if (tokenElement.isNumeric()){
            this.numeric_item_cnt += num_to_add;
        } else if (tokenElement.isFunction()){
            this.function_item_cnt += num_to_add;
        } else if (tokenElement.isBinaryOperator()){
            this.binary_operator_cnt += num_to_add;
        } else if (tokenElement.isUnaryOperator()){
            this.unary_operator_cnt += num_to_add;
        }
    }

    @Override
    public boolean add(TokenElement tokenElement) {
        this.countItem(tokenElement, true);
        return super.add(tokenElement);
    }

    @Override
    public void add(int index, TokenElement element) {
        this.countItem(element, true);
        super.add(index, element);
    }

    @Override
    public TokenElement remove(int index) {
        TokenElement element = super.remove(index);
        this.countItem(element, false);
        return element;
    }

    public int getNumericItemCount(){
        return this.numeric_item_cnt;
    }

    public int getFunctionItemCount(){
        return this.function_item_cnt;
    }

    public int getBinaryOperatorCount(){
        return this.binary_operator_cnt;
    }

    public int getUnaryOperatorCount(){
        return this.unary_operator_cnt;
    }
}
