package com.takeohman.postfixnotaion.formatter;

abstract public class AbstractFormatter implements FormatterInterface<String>{
    protected FormatterInterface<String> formatter;
    public AbstractFormatter(FormatterInterface<String> formatter){
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