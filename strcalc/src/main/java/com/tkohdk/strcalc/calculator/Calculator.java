package com.tkohdk.strcalc.calculator;

/**
 * Created by takeoh on 2018/02/08.
 */

public interface Calculator<T extends Number, N> {
    T add(N strA, N strB);
    T subtract(N strA, N strB);
    T multiply(N strA, N strB);
    T divide(N strA, N strB);
    T involution(N strA, N strB);
    T calculate(String operator, N strA, N strB);
    T calculate(String operator, N str);
    T factorial(N intValStr);
    T sin(N inValStr);
    T cos(N inValStr);
    T tan(N inValStr);
    T log(N inValStr);
    T log10(N inValStr);
}
