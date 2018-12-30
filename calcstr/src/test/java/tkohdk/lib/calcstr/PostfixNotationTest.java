package tkohdk.lib.calcstr;

import org.junit.Test;

import java.math.MathContext;
import java.math.RoundingMode;

import tkohdk.lib.calcstr.splitter.StringSplitter;
import tkohdk.lib.calcstr.tokenizer.StringTokenizer;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/08/15.
 */
public class PostfixNotationTest {

    @Test
    public void calcInfixStr() throws Exception {
        MathContext mc = new MathContext(12, RoundingMode.HALF_EVEN);
        PostfixNotation pn = new PostfixNotation(mc);


        TestQuestions.TestQuestionType[] tp = {
                TestQuestions.TestQuestionType.Basic,
                TestQuestions.TestQuestionType.ManyOperator,
                TestQuestions.TestQuestionType.UI,
                TestQuestions.TestQuestionType.Impossible,
                TestQuestions.TestQuestionType.SinCosTan,
                TestQuestions.TestQuestionType.Log
        };

        for (TestQuestions.TestQuestionType type : tp) {
            StringTokenizer st = new StringTokenizer(new StringSplitter());
            TestQuestions tq = new TestQuestions(type);
            while (tq.hasNext()) {
                TestQuestions.Question q = tq.next();
//                if (q.isAmbiguous()) {
//                    System.out.println("Ignored question (" + this.getClass().getSimpleName() + ") : " + q.getQuestion() + " = " + q.getAns());
//                    continue;
//                }

                try {
                    String ans = pn.calcInfixStr(q.getQuestion());
//                    System.out.println(q.getQuestion());
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