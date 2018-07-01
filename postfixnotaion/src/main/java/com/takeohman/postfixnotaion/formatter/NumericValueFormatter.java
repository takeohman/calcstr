package com.takeohman.postfixnotaion.formatter;

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


    public NumericValueFormatter(FormatterInterface<String> formatter){
        super(formatter);
        this.patStartPeriod = Pattern.compile("^\\.[0-9]+$");
    }

    public NumericValueFormatter(){
        super();
        this.patStartPeriod = Pattern.compile("^\\.[0-9]+$");
    }

    private String getFormatStr(String val){
        Matcher mat =  this.patStartPeriod.matcher(val);
        String format_str = format_pattern_full;
        if (mat.find()){
            format_str = format_pattern_dec;
        }
        return format_str;
    }


    @Override
    public String myFormat(String val){
        String _val = val;
        if (this.formatter != null){
            _val = this.formatter.format(val);
        }
        String format_str = this.getFormatStr(_val);
        DecimalFormat dc = new DecimalFormat(format_str);

        BigDecimal bd = new BigDecimal(_val);
        return dc.format(bd);
    }
}
