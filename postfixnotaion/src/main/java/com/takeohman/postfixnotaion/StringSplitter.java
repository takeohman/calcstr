package com.takeohman.postfixnotaion;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by takeoh on 2017/11/13.
 */

class StringSplitter {

    private final String splitPattern =
            "(^[-][0-9]+[.]?[0-9]+|^[-][0-9]+|(?<=\\()[-][0-9]+|[0-9]+[.]?[0-9]+|[0-9]+|!|sin|cos|tan|log|[^()0-9 ]+?|\\(|\\))";

    StringSplitter(){}

    class InvalidElementOrderException extends RuntimeException{}
    /**
     * 正規表現で分割した結果をMatcherとして返す
     * @param problemStr 計算問題の文字列
     * @return Matcher
     */
    private Matcher getMatcher(String problemStr){
        String trimmedStr = problemStr.replace(" ", "");

        Pattern pat = Pattern.compile(this.splitPattern);
        return pat.matcher(trimmedStr);
    }
    /**
     * 中置記法の計算式文字列から数字、演算子、カッコのリストにして返す
     *
     * 1. ")(", "N(", ")N" などは '*'の省略と判断し、計算しやすいように'*'を追加する。
     * 2. "+", "*"のみの場合はそれぞれ"0", "1"を追加する。
     *
     * @param problemStr 中置き記法の計算式
     * @return ArrayList<ProblemStrElement>
     */
    ArrayList<ProblemStrElement> getProblemStrObjListFromStr(String problemStr){

        ArrayList<ProblemStrElement> problemStrElementObjList = new ArrayList<>();
        Matcher mat = this.getMatcher(problemStr);

        int index = 0;
        ProblemStrElement prevElement = null;
        while(mat.find()){
            String matchedString = mat.group(1);
            ProblemStrElement matchedElement = new ProblemStrElement(matchedString);

            if (prevElement != null){
                /*
                1. ...)*(...
                2. ...)*7
                3. ...7*(...
                4. ...!*7...
                5. ...!*(...
                6. ...7*tan...
                 */
                if ((prevElement.isRightBracket() && matchedElement.isLeftBracket()) ||
                        (prevElement.isRightBracket() && matchedElement.isNumber()) ||
                        (prevElement.isNumber() && matchedElement.isLeftBracket()) ||
                        (prevElement.isExclamation() && matchedElement.isNumber()) ||
                        (prevElement.isExclamation() && matchedElement.isLeftBracket()) ||
                        (prevElement.isNumber() && matchedElement.isFunction())
                        )
                {
                    problemStrElementObjList.add(new ProblemStrElement(index, "*"));
                    index++;
                }
                /*
                   | prev | matched | replace |
                   |  -   |    -    |    +    |
                   |  +   |    -    |    -    |
                   |  +   |    +    |    +    |
                   |  -   |    +    |Exception|

                 */
                else if (prevElement.isMinusOperator() && matchedElement.isMinusOperator() ||
                        prevElement.isPlusOperator() && matchedElement.isPlusOperator()) {
                    prevElement.str = "+";
                    continue;
                }
                else if (prevElement.isPlusOperator() && matchedElement.isMinusOperator()){
                    prevElement.str = "-";
                    continue;
                }
                else if (prevElement.isMinusOperator() && matchedElement.isPlusOperator()){
                    throw new InvalidElementOrderException();
                }
            }
            prevElement = matchedElement;
            matchedElement.setIndex(index);
            problemStrElementObjList.add(matchedElement);
            index++;
        }
        return this.postCheck(problemStrElementObjList);
    }

    /**
     *
     * @param elementList
     * @return
     */
    private ArrayList<ProblemStrElement> postCheck(ArrayList<ProblemStrElement> elementList){
       /*
        演算子のみの場合
         */
        if (elementList.size() == 1){
            ProblemStrElement tmp = elementList.get(0);
            if (tmp.isPlusOperator()){
                elementList.add(new ProblemStrElement(tmp.index + 1, "0"));
            } else if (tmp.isMultiplicationOperator()){
                elementList.add(new ProblemStrElement(tmp.index + 1, "1"));
            } else if (!tmp.isNumber()){
                // 上記以外の演算子は計算不可能としてリストを空にする
                elementList.remove(0);
            }
        }
        return elementList;
    }
}
