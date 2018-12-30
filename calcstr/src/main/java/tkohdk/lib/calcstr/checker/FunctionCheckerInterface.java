package tkohdk.lib.calcstr.checker;

/**
 * Created by takeoh on 2018/04/18.
 */

public interface FunctionCheckerInterface {
    /**
     * 与えられた文字列がxxx(value)形式の関数名に一致する場合にtrueを返す。
     * @param val (ex. sin, cos, tan)
     * @return true or false
     */
    boolean isFunction(String val);
}
