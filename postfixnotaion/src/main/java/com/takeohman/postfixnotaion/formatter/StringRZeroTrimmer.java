package com.takeohman.postfixnotaion.formatter;

public class StringRZeroTrimmer extends AbstractFormatter{
    public StringRZeroTrimmer(FormatterInterface<String> formatter){
        super(formatter);
    }

    public StringRZeroTrimmer(){
        super();
    }
    @Override
    public String myFormat(String val) {
        return val.replaceAll("([.]+0+)|(?<=\\.[0-9]?)0+$", "");
    }
}
