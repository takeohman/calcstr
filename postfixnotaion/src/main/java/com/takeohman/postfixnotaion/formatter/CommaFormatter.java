package com.takeohman.postfixnotaion.formatter;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by takeoh on 2018/01/08.
 */

public class CommaFormatter {


    String formatAsNumber(Object val){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(val);
    }

//    String formatAsPercentage(Object val){
//        NumberFormat nf = NumberFormat.getPercentInstance();
//        return nf.format(val);
//    }

    String format(String val){
        BigDecimal bd = new BigDecimal(val);
        return this.formatAsNumber(bd);
    }
}
