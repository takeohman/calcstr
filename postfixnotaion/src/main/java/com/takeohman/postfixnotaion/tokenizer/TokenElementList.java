package com.takeohman.postfixnotaion.tokenizer;

import com.takeohman.postfixnotaion.core.StringfiableList;

/**
 * Created by takeoh on 2018/02/10.
 */

public class TokenElementList extends StringfiableList<TokenElement> {
    private int numeric_item_cnt = 0;
    private int function_item_cnt = 0;


    protected void countItem(TokenElement tokenElement){
        if (tokenElement.isNumeric()){
            this.numeric_item_cnt += 1;
        } else if (tokenElement.isFunction()){
            this.function_item_cnt += 1;
        }
    }

    @Override
    public boolean add(TokenElement tokenElement) {
        this.countItem(tokenElement);
        return super.add(tokenElement);
    }

    public int getNumericItemCount(){
        return this.numeric_item_cnt;
    }

    public int getFunctionItemCount(){
        return this.function_item_cnt;
    }
}
