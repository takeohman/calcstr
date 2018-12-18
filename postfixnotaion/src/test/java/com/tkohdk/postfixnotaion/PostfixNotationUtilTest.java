package com.tkohdk.postfixnotaion;

import com.tkohdk.postfixnotaion.calculator.StringCalculator;
import com.tkohdk.postfixnotaion.checker.BigDecimalNumericChecker;
import com.tkohdk.postfixnotaion.checker.FunctionChecker;
import com.tkohdk.postfixnotaion.checker.OperatorChecker;
import com.tkohdk.postfixnotaion.splitter.StringSplitter;
import com.tkohdk.postfixnotaion.tokenizer.StringTokenizer;
import com.tkohdk.postfixnotaion.tokenizer.TokenValueChecker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2017/09/27.
 */
public class PostfixNotationUtilTest {
    @Test
    public void convertInfixToPostfix() throws Exception {
        PostfixNotationUtil pnu = new PostfixNotationUtil(
            new TokenValueChecker(new BigDecimalNumericChecker(), new FunctionChecker(), new OperatorChecker()),
            new StringCalculator()
        );
        TestQuestions.TestQuestionType[] tp = {
            TestQuestions.TestQuestionType.Basic,
            TestQuestions.TestQuestionType.ManyOperator,
            TestQuestions.TestQuestionType.UI,
            TestQuestions.TestQuestionType.Log,
//            TestQuestions.TestQuestionType.SinCosTan,
        };

        for (TestQuestions.TestQuestionType type : tp) {
            StringTokenizer st = new StringTokenizer(new StringSplitter());
            TestQuestions tq = new TestQuestions(type);
            while (tq.hasNext()) {
                TestQuestions.Question q = tq.next();
                if (q.isAmbiguous()) {
                    System.out.println("Ignored question (" + this.getClass().getSimpleName() + ") : " + q.getQuestion() + " = " + q.getAns());
                    continue;
                }
                String _str = q.getQuestion();

                ArrayList<String> _list = pnu.convertInfixToPostfix(st.getList(_str));
                try {
                    String ans = pnu.calcPolishStrList(_list);
                    String _s = "null";
                    if (_list != null) {
                        _s = _list.toString();
                    }
                    assertEquals(q.getQuestion() + ":" + _s, q.getAns(), ans);
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
//    @Test
//    public void convertInfixToPostfix2() throws Exception {
//        PostfixNotationUtil pnu = new PostfixNotationUtil(
//                new TokenValueChecker(),
//                new BigDecimalCalculator()
//        );
//        // 1 + 2
//        StringfiableList<String> pbm = new StringfiableList<>();
//        pbm.add("1");
//        pbm.add("+");
//        pbm.add("2");
//
//        StringfiableList<String> expected = new StringfiableList<>();
//        expected.add("1");
//        expected.add("2");
//        expected.add("+");
//
//        ArrayList<String> pbm1 = new ArrayList<>();
//        pbm1.add("1");
//        pbm1.add("+");
//        pbm1.add("2");
//        ArrayList<String> expected1 = new ArrayList<>();
//        expected1.add("1");
//        expected1.add("2");
//        expected1.add("+");
//        ArrayList<String> result1 = pnu.convertInfixToPostfix(pbm1);
//        assertEquals(result1, expected1);
//        String ans = pnu.calcPolishStrList(result1);
//        assertEquals("3", ans);
//
//
//        // 1 + 2 * 3
//        ArrayList<String> pbm2 = new ArrayList<>();
//        pbm2.add("1");
//        pbm2.add("+");
//        pbm2.add("2");
//        pbm2.add("*");
//        pbm2.add("3");
//        ArrayList<String> expected2 = new ArrayList<>();
//        expected2.add("1");
//        expected2.add("2");
//        expected2.add("3");
//        expected2.add("*");
//        expected2.add("+");
//        ArrayList<String> result2 = pnu.convertInfixToPostfix(pbm2);
//        assertEquals(result2, expected2);
//        ans = pnu.calcPolishStrList(result2);
//        assertEquals("7", ans);
//
//
//        // 計算ができないパターン
//        // 1 2 * 3
//        ArrayList<String> pbm3 = new ArrayList<>();
//        pbm3.add("1");
//        pbm3.add("2");
//        pbm3.add("*");
//        pbm3.add("3");
//        ArrayList<String> expected3 = new ArrayList<>();
//        expected3.add("1");
//        expected3.add("2");
//        expected3.add("3");
//        expected3.add("*");
//        ArrayList<String> result3 = pnu.convertInfixToPostfix(pbm3);
//        assertEquals(result3, expected3);
//        ans = pnu.calcPolishStrList(result3);
//        // TODO: 演算子が１つ足りない場合はできるところまで計算でいいの？
//        assertEquals("6", ans);
//    }
//    @Test
//    public void convertInfixToPostfix3() throws Exception {
//        PostfixNotationUtil pnu = new PostfixNotationUtil(
//                new TokenValueChecker(),
//                new BigDecimalCalculator()
//        );
//        ArrayList<TestQuestions.Question> tq = TestQuestions.getQuestionList();
//
//        for (TestQuestions.Question q : tq){
//            System.out.println(q.getQuestion());
//
//        }
//    }
}