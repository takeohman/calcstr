package com.takeohman.postfixnotaion.tree.operator;

public enum UnaryOperator {

    Sin("sin"),
    Cos("cos"),
    Tan("tan"),
    Log("log"),
    Log10("log10"),
    Log1p("log1p"),
    Factorial("!");

    String operator;
    UnaryOperator(String ope){
        this.operator = ope;
    }
    public String getOperator(){
        return this.operator;
    }
}
