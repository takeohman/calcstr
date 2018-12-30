package tkohdk.lib.calcstr.checker;

/**
 * Created by takeoh on 2018/04/19.
 */

public interface OperatorCheckerInterface {
    boolean isOperator(String val);
    //TODO: 単項演算子、二項演算子の判定メソッドを追加
    boolean isBinaryOperator(String val);
    boolean isUnaryOperator(String val);
}
