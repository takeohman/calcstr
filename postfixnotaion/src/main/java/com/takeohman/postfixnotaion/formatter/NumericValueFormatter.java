package com.takeohman.postfixnotaion.formatter;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by takeoh on 2018/01/08.
 */

//public class NumericValueFormatter implements FormatterInterface<String>{
public class NumericValueFormatter extends AbstractFormatter{
    public NumericValueFormatter(FormatterInterface<String> formatter){
        super(formatter);
    }

    public NumericValueFormatter(){
        super();
    }

    private String formatAsNumber(Object val){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(val);
    }

    @Override
    public String myFormat(String val){
        String _val = val;
        if (this.formatter != null){
            _val = this.formatter.format(val);
        }
        BigDecimal bd = new BigDecimal(_val);
        return this.formatAsNumber(bd);
    }
}
