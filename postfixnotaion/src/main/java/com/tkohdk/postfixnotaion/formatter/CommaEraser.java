package com.tkohdk.postfixnotaion.formatter;

public class CommaEraser extends AbstractFormatter{
    @Override
    protected String myFormat(String val) {
        return val.replace(",","");
    }
}
