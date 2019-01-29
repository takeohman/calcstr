package tkohdk.lib.calcstr;

import org.junit.Test;

import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/08/15.
 */
public class CalcStrTest {

    @Test
    public void calcInfixStr() throws Exception {
        MathContext mc = new MathContext(12, RoundingMode.HALF_EVEN);

        CalcStr calcStr = new CalcStr(mc);

        TestQuestions.TestQuestionType[] tp = {
                TestQuestions.TestQuestionType.Basic,
                TestQuestions.TestQuestionType.ManyOperator,
                TestQuestions.TestQuestionType.UI,
                TestQuestions.TestQuestionType.Impossible,
                TestQuestions.TestQuestionType.SinCosTan,
                TestQuestions.TestQuestionType.Log,
                TestQuestions.TestQuestionType.ScientificNotation,
                TestQuestions.TestQuestionType.ScientificNotation2
        };

        for (TestQuestions.TestQuestionType type : tp) {
            TestQuestions tq = new TestQuestions(type);
            while (tq.hasNext()) {
                TestQuestions.Question q = tq.next();

                try {
                    String ans = calcStr.calculate(q.getQuestion()).getAns();
                    assertEquals(q.getQuestion() + ":" + q.getQuestion(), q.getAns(), ans);
                } catch (Exception e) {
                    System.out.println("Exception:" + q.getQuestion());
                    System.out.println("         :" + e.toString());

                    for (StackTraceElement _s : e.getStackTrace()) {
                        System.out.println("         " + _s.toString());
                    }
                }
            }
        }
    }
}