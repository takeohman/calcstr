package tkohdk.lib.calcstr;

import org.junit.Test;

import java.math.BigDecimal;

import tkohdk.lib.calcstr.checker.BigDecimalNumericChecker;
import tkohdk.lib.calcstr.checker.FunctionChecker;
import tkohdk.lib.calcstr.checker.OperatorChecker;
import tkohdk.lib.calcstr.tokenizer.NumericTokenElement;
import tkohdk.lib.calcstr.tokenizer.TokenElement;
import tkohdk.lib.calcstr.tokenizer.TokenElementList;
import tkohdk.lib.calcstr.tokenizer.TokenElementObject;
import tkohdk.lib.calcstr.tokenizer.TokenValueChecker;

import static org.junit.Assert.assertEquals;

public class CalcStrResultTest {

    @Test
    public void getTokenElementList() {
        TokenValueChecker vc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker());
        TokenElementList elementList = new TokenElementList();
        NumericTokenElement ans = new NumericTokenElement(0, new BigDecimal("3"));
        elementList.add(new TokenElement(vc, "1"));
        elementList.add(new TokenElement(vc, "+"));
        elementList.add(new TokenElement(vc, "2"));
        CalcStrResult csr = new CalcStrResult("1+2",ans, elementList);
        TokenElementList actual = csr.getTokenElementList();
        assertEquals(elementList, actual);
    }

    @Test
    public void getStatus() {
        TokenValueChecker vc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker());
        TokenElementList elementList = new TokenElementList();
        NumericTokenElement ans = new NumericTokenElement(0, new BigDecimal("3"));
        elementList.add(new TokenElement(vc, "1"));
        elementList.add(new TokenElement(vc, "+"));
        elementList.add(new TokenElement(vc, "2"));
        CalcStrResult csr = new CalcStrResult("1+2",ans, elementList);
        assertEquals(CalcStrResult.RESULT_OK, csr.getStatus());
    }
    @Test
    public void getStatus_NG() {
        TokenValueChecker vc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker());
        TokenElementList elementList = new TokenElementList();
        elementList.add(new TokenElement(vc, "1"));
        elementList.add(new TokenElement(vc, "+"));
        elementList.add(new TokenElement(vc, "2"));
        CalcStrResult csr = new CalcStrResult("1+2",null, elementList);
        assertEquals(CalcStrResult.RESULT_EXPECTED_EXCEPTION, csr.getStatus());
    }

    @Test
    public void getAnswerToken() {
        TokenValueChecker vc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker());
        TokenElementList elementList = new TokenElementList();
        NumericTokenElement ans = new NumericTokenElement(0, new BigDecimal("3"));
        elementList.add(new TokenElement(vc, "1"));
        elementList.add(new TokenElement(vc, "+"));
        elementList.add(new TokenElement(vc, "2"));
        CalcStrResult csr = new CalcStrResult("1+2",ans, elementList);
        TokenElementObject actual = csr.getAnswerToken();
        assertEquals(ans.getStr(), actual.getStr());
        assertEquals(ans.getIndex(), actual.getIndex());
        assertEquals(ans.getNumberObject(), actual.getNumberObject());
        assertEquals(ans, actual);
    }

    @Test
    public void getAns() {
        TokenValueChecker vc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker());
        TokenElementList elementList = new TokenElementList();
        NumericTokenElement ans = new NumericTokenElement(0, new BigDecimal("3"));
        elementList.add(new TokenElement(vc, "1"));
        elementList.add(new TokenElement(vc, "+"));
        elementList.add(new TokenElement(vc, "2"));
        CalcStrResult csr = new CalcStrResult("1+2",ans, elementList);
        assertEquals(csr.getAns(), "3");
    }

    @Test
    public void getProblemStr() {
        TokenValueChecker vc = new TokenValueChecker(
                new BigDecimalNumericChecker(),
                new FunctionChecker(),
                new OperatorChecker());
        TokenElementList elementList = new TokenElementList();
        NumericTokenElement ans = new NumericTokenElement(0, new BigDecimal("3"));
        elementList.add(new TokenElement(vc, "1"));
        elementList.add(new TokenElement(vc, "+"));
        elementList.add(new TokenElement(vc, "2"));
        CalcStrResult csr = new CalcStrResult("1+2",ans, elementList);
        assertEquals(csr.getProblemStr(), "1+2");
    }
}