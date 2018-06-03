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

    @Override
    public String toString(){return ope;}

    static public BinaryOperator getOperator(String str){

        switch(str){
            case "+":
                return BinaryOperator.Plus;
            case "-":
                return BinaryOperator.Minus;
            case "*":
            case "×":
                return BinaryOperator.Multiplication;
            case "/":
            case "÷":
                return BinaryOperator.Division;
            case "^":
                return BinaryOperator.Pow;

        }
        return null;
    }
}
