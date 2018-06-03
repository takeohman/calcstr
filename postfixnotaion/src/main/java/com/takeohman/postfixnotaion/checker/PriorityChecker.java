package com.takeohman.postfixnotaion.checker;

import java.util.Arrays;

/**
 * Created by takeoh on 2018/04/25.
 */

public class PriorityChecker {
    private static final int PRIORITY_1 = 5;
    private static final int PRIORITY_2 = 4;
    private static final int PRIORITY_3 = 3;
    private static final int PRIORITY_4 = 2;
    private static final int PRIORITY_5 = 1;
    private static final int PRIORITY_6 = 0;
    private static final int PRIORITY_7 = -1;
    private static final int PRIORITY_8 = -2;

    private NumericCheckerInterface nc;

    public PriorityChecker(NumericCheckerInterface nc){
        this.nc = nc;
    }

    public int getValuePriority(String val){
        String[] p1 = {"("};
        String[] p3 = {"!"};
        String[] p4 = {"^", "sin", "cos", "tan", "log"};
        String[] p5 = {"*", "/"};
        String[] p6 = {"+", "-"};
        String[] p7 = {")"};

        if (Arrays.asList(p1).contains(val)) {          // "("
            return PRIORITY_1;
        }else if (nc.isNumeric(val)){   // 0,1,2....
            return PRIORITY_3;
        } else if (Arrays.asList(p3).contains(val)){    // !
            return PRIORITY_2;
        } else if (Arrays.asList(p4).contains(val)){    // ^
            return PRIORITY_4;
        } else if (Arrays.asList(p5).contains(val)){    // *, /
            return PRIORITY_5;
        } else if (Arrays.asList(p6).contains(val)){    // +, -
            return PRIORITY_6;
        } else if (Arrays.asList(p7).contains(val)){    // ")"
            return PRIORITY_7;
        } else {
            return PRIORITY_8;
        }
    }
}
