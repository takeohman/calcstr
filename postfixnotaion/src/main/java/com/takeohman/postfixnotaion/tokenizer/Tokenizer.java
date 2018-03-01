package com.takeohman.postfixnotaion.tokenizer;

import java.util.ArrayList;

/**
 * Created by takeoh on 2017/11/13.
 */

interface Tokenizer<E extends ArrayList, F>{
    E getList(F pbm);
}
