package tkohdk.lib.calcstr.splitter;

import java.util.Iterator;

/**
 * Created by takeoh on 2018/01/30.
 */

public interface Splitter {
    Iterator<String> getIterator(String problemStr);
}
