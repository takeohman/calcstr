package com.takeohman.postfixnotaion.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by takeoh on 2018/01/08.
 */

//public class NumericValueFormatter implements FormatterInterface<String>{
public class NumericValueFormatter extends AbstractFormatter{
    private String format_pattern = "#,###.################";
    private DecimalFormat decimal_format;

    public NumericValueFormatter(DecimalFormat dc){
        super();
        this.decimal_format = dc;
    }

    public NumericValueFormatter(FormatterInterface<String> formatter, DecimalFormat dc){
        super(formatter);
        this.decimal_format = dc;
    }

    public NumericValueFormatter(FormatterInterface<String> formatter){
        super(formatter);
        this.decimal_format = new DecimalFormat(this.format_pattern);
    }

    public NumericValueFormatter(){
        super();
        this.decimal_format = new DecimalFormat(this.format_pattern);
    }

    private String formatAsNumber(Object val){
        return this.decimal_format.format(val);
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
