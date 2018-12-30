package tkohdk.lib.calcstr.checker;

import java.util.Arrays;

/**
 * Created by takeoh on 2018/04/18.
 */

public class FunctionChecker implements FunctionCheckerInterface{
    @Override
    public boolean isFunction(String val){
        String[] func = {"sin", "cos", "tan", "log"};
        return Arrays.asList(func).contains(val);
    }
}
