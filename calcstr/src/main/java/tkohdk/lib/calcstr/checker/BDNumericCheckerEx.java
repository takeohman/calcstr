package tkohdk.lib.calcstr.checker;

public class BDNumericCheckerEx extends BigDecimalNumericChecker{
    public BDNumericCheckerEx(){
        super();
    }
    @Override
    public String getNumericValue(String num){
            return super.getNumericValue(num.replaceAll("^(--)+|,", ""));
    }
}
