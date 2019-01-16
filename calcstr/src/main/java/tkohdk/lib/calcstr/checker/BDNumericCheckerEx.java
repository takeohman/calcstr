package tkohdk.lib.calcstr.checker;

public class BDNumericCheckerEx extends BigDecimalNumericChecker{

    /**
     * Constructor
     */
    public BDNumericCheckerEx(){
        super();
    }

    /**
     *
     * @param num String
     * @return String
     *
     * [example]
     *  Input : ---1,000
     *  Output: 1000
     */
    @Override
    public String getNumericValue(String num){
            return super.getNumericValue(num.replaceAll("^(--)+|,", ""));
    }
}
