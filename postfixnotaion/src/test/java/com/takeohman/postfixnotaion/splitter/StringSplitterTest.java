package com.takeohman.postfixnotaion.splitter;

import com.takeohman.postfixnotaion.TestQuestions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by takeoh on 2018/02/27.
 */
public class StringSplitterTest {
    @Test
    public void getIterator1() throws Exception {
        StringSplitter sp = new StringSplitter();

        TestQuestions.TestQuestionType[] tp = {
            TestQuestions.TestQuestionType.Basic,
            TestQuestions.TestQuestionType.ManyOperator,
            TestQuestions.TestQuestionType.UI,
            TestQuestions.TestQuestionType.Comma,
            TestQuestions.TestQuestionType.Impossible
        };

        for (TestQuestions.TestQuestionType type : tp) {
            TestQuestions tq = new TestQuestions(type);
            while (tq.hasNext()) {
                TestQuestions.Question q = tq.next();
                String qs = q.getQuestion();
                Iterator<String> ite = sp.getIterator(qs);
                ArrayList<String> _tmp = new ArrayList<>();
                while (ite.hasNext()) {
                    _tmp.add(ite.next());
                }
                assertEquals(q.getExpectedList(), _tmp);
            }
        }
    }
}