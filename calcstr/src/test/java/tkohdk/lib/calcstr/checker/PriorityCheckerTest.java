package tkohdk.lib.calcstr.checker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PriorityCheckerTest {

    @Test
    public void getValuePriority() {
        PriorityChecker pc = new PriorityChecker(new BigDecimalNumericChecker());
        assertEquals(true, pc.getValuePriority("(") > pc.getValuePriority("!") );
        assertEquals(true, pc.getValuePriority("!") > pc.getValuePriority("1") );
        assertEquals(true, pc.getValuePriority("1") > pc.getValuePriority("^") );
        assertEquals(true, pc.getValuePriority("^") > pc.getValuePriority("*") );
        assertEquals(true, pc.getValuePriority("*") > pc.getValuePriority("+") );
        assertEquals(true, pc.getValuePriority("+") > pc.getValuePriority(")") );
    }
}