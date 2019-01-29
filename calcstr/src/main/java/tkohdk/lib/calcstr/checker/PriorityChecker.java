package tkohdk.lib.calcstr.checker;

import java.util.Arrays;
import java.util.List;

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

    private static String[] p1 = {"("};
    private static String[] p3 = {"!"};
    private static String[] p4 = {"^", "sin", "cos", "tan", "log", "E", "e"};
    private static String[] p5 = {"*", "/"};
    private static String[] p6 = {"+", "-"};
    private static String[] p7 = {")"};
    private List<String> p1List;
    private List<String> p3List;
    private List<String> p4List;
    private List<String> p5List;
    private List<String> p6List;
    private List<String> p7List;

    public PriorityChecker(NumericCheckerInterface nc){
        this.nc = nc;
        this.p1List = Arrays.asList(p1);
        this.p3List = Arrays.asList(p3);
        this.p4List = Arrays.asList(p4);
        this.p5List = Arrays.asList(p5);
        this.p6List = Arrays.asList(p6);
        this.p7List = Arrays.asList(p7);
    }

    public int getValuePriority(String val){

        if (this.p1List.contains(val)) {          // "("
            return PRIORITY_1;
        }else if (nc.isNumeric(val)){   // 0,1,2....
            return PRIORITY_3;
        } else if (this.p3List.contains(val)){    // !
            return PRIORITY_2;
        } else if (this.p4List.contains(val)){    // ^
            return PRIORITY_4;
        } else if (this.p5List.contains(val)){    // *, /
            return PRIORITY_5;
        } else if (this.p6List.contains(val)){    // +, -
            return PRIORITY_6;
        } else if (this.p7List.contains(val)){    // ")"
            return PRIORITY_7;
        } else {
            return PRIORITY_8;
        }
    }
}
