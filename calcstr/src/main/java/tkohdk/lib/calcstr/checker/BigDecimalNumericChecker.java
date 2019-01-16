package tkohdk.lib.calcstr.checker;

import java.math.BigDecimal;

/**
 * Created by takeoh on 2017/12/22.
 */


public class BigDecimalNumericChecker extends NumericChecker{

    @Override
    public boolean isNumeric(String num){
        return this.getNumericValue(num) != null;
    }

    /**
     *
     * @param num 対象の文字列
     * @return String or null
     */
    @Override
    public String getNumericValue(String num){
        try {
            BigDecimal bc = new BigDecimal(num);
            return bc.toString();
        } catch (Exception ex){
            return null;
        }
    }
}