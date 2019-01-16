package tkohdk.lib.calcstr;

import java.math.MathContext;

import tkohdk.lib.calcstr.checker.BDNumericCheckerEx;
import tkohdk.lib.calcstr.checker.FunctionChecker;
import tkohdk.lib.calcstr.checker.OperatorChecker;
import tkohdk.lib.calcstr.rpn.RPNCalculator;
import tkohdk.lib.calcstr.splitter.StringSplitter;
import tkohdk.lib.calcstr.tokenizer.StringListTokenizer;
import tkohdk.lib.calcstr.tokenizer.StringTokenizer;
import tkohdk.lib.calcstr.tokenizer.TokenElementList;
import tkohdk.lib.calcstr.tokenizer.TokenElementObject;
import tkohdk.lib.calcstr.tokenizer.TokenValueChecker;


public class CalcStr {
    private TokenCalculator tokenCalculator;
    private StringListTokenizer stringListTokenizer;


    /**
     *
     * @param mc MathContext
     */
    public CalcStr(MathContext mc){
        this.tokenCalculator = new RPNCalculator(mc);
        this.stringListTokenizer = new StringListTokenizer(
                new StringTokenizer(new StringSplitter()),
                new TokenValueChecker(new BDNumericCheckerEx(), new FunctionChecker(), new OperatorChecker())
        );
    }

    /**
     *
     * @param problemStr String
     * @return CalcStrResult
     */
    public CalcStrResult calculate(String problemStr){
        TokenElementObject ansElm = null;
        TokenElementList pbmTokenObjList = null;
        Exception _ex = null;
        try {
            pbmTokenObjList = this.stringListTokenizer.getList(problemStr);
            ansElm = this.tokenCalculator.calculateTokenElementList(pbmTokenObjList);
        } catch (Exception ex) {
            _ex = ex;
        }
        return new CalcStrResult(problemStr, ansElm, pbmTokenObjList, _ex);
    }
}
