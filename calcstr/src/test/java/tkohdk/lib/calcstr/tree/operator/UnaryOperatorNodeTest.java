package tkohdk.lib.calcstr.tree.operator;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by takeoh on 2018/03/11.
 */
public class UnaryOperatorNodeTest {
    @Test
    public void setNode() throws Exception {
        UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Log10);
        assertNull(un.getNode());
        un.setNode(new NumericValue("1000"));
        assertNotNull(un.getNode());
    }

    @Test
    public void getNode() throws Exception {
        UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Log10);
        assertNull(un.getNode());
        un.setNode(new NumericValue("1000"));
        assertEquals("1000",un.getNode().getValue());
    }

    @Test
    public void isLeafNode() throws Exception {
        UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Log10);
        assertFalse(un.isLeafNode());
    }

    @Test
    public void getValue() throws Exception {
        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Log10);
            assertNull(un.getNode());
            un.setNode(new NumericValue("1000"));
            assertEquals("3", un.getValue());
        }
        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Log);
            assertNull(un.getNode());
            un.setNode(new NumericValue("1000"));
            assertEquals("6.907755278982136815102421678602695465087890625", un.getValue());
        }
        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Log1p);
            assertNull(un.getNode());
            un.setNode(new NumericValue("1000"));
            assertEquals("6.908754779315220417856835410930216312408447265625", un.getValue());
        }

        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Sin);
            assertNull(un.getNode());
            un.setNode(new NumericValue("3"));
            assertEquals("0.1411200080598672135234750157906091772019863128662109375", un.getValue());
        }

        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Cos);
            assertNull(un.getNode());
            un.setNode(new NumericValue("3"));
            assertEquals("-0.9899924966004454152113112286315299570560455322265625", un.getValue());
        }

        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Tan);
            assertNull(un.getNode());
            un.setNode(new NumericValue("3"));
            assertEquals("-0.1425465430742778039086005037461291067302227020263671875", un.getValue());
        }

        {
            UnaryOperatorNode un = new UnaryOperatorNode(UnaryOperator.Factorial);
            assertNull(un.getNode());
            un.setNode(new NumericValue("10"));
            assertEquals("3628800", un.getValue());
        }
    }

}