package com.takeohman.postfixnotaion.calculator;

/**
 * Created by takeoh on 2018/02/08.
 */

public interface Calculator<T extends Number> {
    T add(String strA, String strB);
    T subtract(String strA, String strB);
    T multiply(String strA, String strB);
    T divide(String strA, String strB);
    T involution(String strA, String strB);
    T calculate(String strA, String strB, String operator);
    T factorial(String intValStr);
    T sin(String inValStr);
    T cos(String inValStr);
    T tan(String inValStr);
    T log(String inValStr);
    T log10(String inValStr);
}
