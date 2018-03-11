package com.takeohman.postfixnotaion.tree.operator;

public enum BinaryOperator {
    Plus("+"),
    Minus("-"),
    Multiplication("*"),
    Division("/"),
    Pow("^");

    String ope;
    BinaryOperator(String ope){
        this.ope = ope;
    }
}
