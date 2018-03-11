package com.takeohman.postfixnotaion.splitter;

import com.takeohman.postfixnotaion.TestQuestions;
import com.takeohman.postfixnotaion.TestQuestions.Question;

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

        ArrayList<Question> ql = TestQuestions.getQuestionList();
        for (Question q : ql){
            String qs = q.getQuestion();

            Iterator<String> ite = sp.getIterator(qs);
            ArrayList<String> _tmp = new ArrayList<>();
            while(ite.hasNext()){
                _tmp.add(ite.next());
            }
            assertEquals(q.getExpectedList(), _tmp);
        }
    }
}