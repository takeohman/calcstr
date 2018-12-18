package com.tkohdk.postfixnotaion.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by takeoh on 2018/01/08.
 */

public class NumericValueFormatter extends AbstractFormatter{
    // the format pattern string for decimal places.
    private static String format_pattern_dec = ".################";
    // the format pattern string for a full numeric value.
    private static String format_pattern_full = "#,###.################";
    private Pattern patStartPeriod;
    private static DecimalFormat formatter_dec = new DecimalFormat(format_pattern_dec);
    private static DecimalFormat formatter_full = new DecimalFormat(format_pattern_full);



    public NumericValueFormatter(FormatterInterface<String, String> formatter){
        super(formatter);
        this.patStartPeriod = Pattern.compile("^\\.[0-9]+$");
    }

    public NumericValueFormatter(){
        super();
        this.patStartPeriod = Pattern.compile("^\\.[0-9]+$");
    }

    private DecimalFormat getDecimalFormat(String val){
        Matcher mat =  this.patStartPeriod.matcher(val);
        if (mat.find()){
            return formatter_dec;
        }
        return formatter_full;
    }


    @Override
    public String myFormat(String val){
        String _val = val;
        if (this.formatter != null){
            _val = this.formatter.format(val);
        }
        BigDecimal bd = new BigDecimal(_val);
        return getDecimalFormat(_val).format(bd);
    }
}