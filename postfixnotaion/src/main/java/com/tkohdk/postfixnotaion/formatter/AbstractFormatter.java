package com.tkohdk.postfixnotaion.formatter;

abstract public class AbstractFormatter implements FormatterInterface<String, String>{
    protected FormatterInterface<String, String> formatter;
    public AbstractFormatter(FormatterInterface<String, String> formatter){
        this.formatter = formatter;
    }
    public AbstractFormatter(){this.formatter=null;}

    abstract protected String myFormat(String val);
    @Override
    public String format(String val){
        String _val = val;
        if (this.formatter != null){
            _val = this.formatter.format(val);
        }
        return this.myFormat(_val);
    }
}
