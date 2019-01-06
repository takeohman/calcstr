package tkohdk.lib.calcstr;

import tkohdk.lib.calcstr.tokenizer.TokenElementList;
import tkohdk.lib.calcstr.tokenizer.TokenElementObject;

public interface TokenCalculator {
    TokenElementObject calculateTokenElementList(TokenElementList pbmTokenObjList);
}
