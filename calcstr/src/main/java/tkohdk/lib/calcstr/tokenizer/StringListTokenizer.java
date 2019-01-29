package tkohdk.lib.calcstr.tokenizer;


public class StringListTokenizer implements Tokenizer<TokenElementList, String>{
    private StringTokenizer tokenizer;
    private TokenCheckerInterface ec;

    /**
     *
     * @param sp:
     * @param ec:
     */
    public StringListTokenizer(StringTokenizer sp, TokenCheckerInterface ec){
        this.tokenizer = sp;
        this.ec = ec;
    }

    public class InvalidElementOrderException extends RuntimeException{}
    public class InvalidBracketCountException extends RuntimeException{}
    public class LeftBracketOnlyException extends RuntimeException{}

    /**
     * 中置記法の計算式文字列から数字、演算子、カッコのリストにして返す
     *
     * 1. ")(", "N(", ")N" などは '*'の省略と判断し、計算しやすいように'*'を追加する。
     * 2. "+", "*"のみの場合はそれぞれ"0", "1"を追加する。
     *
     * @param problemStr 中置き記法の計算式
     * @return ArrayList<TokenElement>
     */
    @Override
    public TokenElementList getList(String problemStr){
        TokenElementList tokenElementObjList = new TokenElementList();
        /*
         prev3          : 100
         prev2          : E
         prev1          : +
         matched        : 12
         */
        int index = 0;
        TokenElement prev3Elem = null;
        TokenElement prev2Elem = null;
        TokenElement prev1Elem = null;

        int bracket_count_l = 0;
        int bracket_count_r = 0;

        for(String matchedString:this.tokenizer.getList(problemStr)){
            
            TokenElement matchedElement = new TokenElement(this.ec, matchedString);

            //
            if (matchedElement.isLeftBracket()){
                bracket_count_l++;
            } else if (matchedElement.isRightBracket()){
                bracket_count_r++;
            }
            /*
                       : pat1    | pat2    |
            prev3Elem  : 123     |         |
            prev2Elem  : E       | 123     |
            prev1Elem  : - or +  | E       |
            matched    : 100     | 100     |
             */
            boolean isNeedECheck = prev2Elem != null && matchedElement.isNumeric();
            if (isNeedECheck && prev2Elem.isEMark() && prev3Elem != null ){
                // pat1
                if (!prev1Elem.isMinusOperator() && !prev1Elem.isPlusOperator()){
                    throw new InvalidElementOrderException();
                } else {
                    matchedElement.setStr(
                            prev3Elem.getRawStr()+"E"+prev1Elem.getRawStr() + matchedElement.getRawStr());
                    prev3Elem.setIsValid(false); // 123
                    prev2Elem.setIsValid(false); // E
                    prev1Elem.setIsValid(false); // + or -
                }
            }
            else if (isNeedECheck && prev1Elem.isEMark() ){
                // pat2
                if (prev2Elem.isNumeric()){
                    matchedElement.setStr(prev2Elem.getRawStr()+"E"+matchedElement.getRawStr());
                    prev2Elem.setIsValid(false); // 123
                    prev1Elem.setIsValid(false); // E
                } else {
                    throw new InvalidElementOrderException();
                }
            }
            else if (prev1Elem != null){
                /*
                1. ...)*(...
                2. ...)*7
                3. ...7*(...
                4. ...7*tan...
                5. ...7*.123
                6. ...!*7...
                7. ...!*(...
                 */
                if ((prev1Elem.isRightBracket() && (matchedElement.isLeftBracket() || matchedElement.isNumeric())) ||
                    (prev1Elem.isNumeric() && (matchedElement.isLeftBracket()||matchedElement.isFunction() || matchedElement.isIncompleteDecimal())) ||
                    (prev1Elem.isExclamation() && (matchedElement.isNumeric() || matchedElement.isLeftBracket()))
                    )
                {
                    tokenElementObjList.add(new TokenElement(this.ec, index, "*"));
                    index++;
                }
                /*
                   | prev | matched | replace |
                   |  -   |    -    |    +    |
                   |  +   |    -    |    -    |
                   |  +   |    +    |Exception|
                   |  -   |    +    |Exception|
                   |  -   |    *    |Exception|
                   |  -   |    /    |Exception|
                   |  +   |    *    |Exception|
                   |  +   |    /    |Exception|
                   |  (   |    )    |Exception|

                 */

                else if (prev1Elem.getRawStr().equals(matchedElement.getRawStr())){
                    //
                    if (matchedElement.isMinusOperator()){
                        prev1Elem.setIsValid(false);
                        matchedElement.setStr("+");
                    }
                    // * * -> ^
                    else if (matchedElement.isMultiplicationOperator()){
                        prev1Elem.setStr("^");
                        continue;
                    }
                    // + + -> Exception
                    else if (matchedElement.isPlusOperator()){
                        throw new InvalidElementOrderException();
                    }
                    // "/" "/" -> Exception
                    else if (matchedElement.isDivisionOperator()){
                        throw new InvalidElementOrderException();
                    }
                }

                else if (prev1Elem.isMinusOperator()){
                    // - +
                    if (matchedElement.isPlusOperator()){
                        throw new InvalidElementOrderException();
                    }
                    // - *
                    else if (matchedElement.isMultiplicationOperator()){
                        throw new InvalidElementOrderException();
                    }
                    // - /
                    else if (matchedElement.isDivisionOperator()){
                        throw new InvalidElementOrderException();
                    }
                }
                else if (prev1Elem.isPlusOperator()){
                    // + - -> -
                    if (matchedElement.isMinusOperator()){
                        prev1Elem.setStr("-");
                        continue;
                    }
                    // + *
                    else if (matchedElement.isMultiplicationOperator()){
                        throw new InvalidElementOrderException();
                    }
                    // + /
                    else if (matchedElement.isDivisionOperator()){
                        throw new InvalidElementOrderException();
                    }
                }
                else if ((prev1Elem.isMultiplicationOperator() && matchedElement.isDivisionOperator())||
                        prev1Elem.isDivisionOperator() && matchedElement.isMultiplicationOperator()){
                    throw new InvalidElementOrderException();
                }
                else if (prev1Elem.isInvolutionOperator() && (!matchedElement.isNumeric() && !matchedElement.isLeftBracket() && !matchedElement.isMinusOperator())){
                    throw new InvalidElementOrderException();
                }
                // "(" and "+ or * or / or )" --->Exception
                else if (prev1Elem.isLeftBracket() &&
                        (matchedElement.isPlusOperator() ||
                            matchedElement.isMultiplicationOperator() ||
                            matchedElement.isDivisionOperator() ||
                            matchedElement.isRightBracket())){
                    throw new InvalidElementOrderException();
                }
                else if (matchedElement.isPlusOperator()){
                    // "*" "+" -> Exception
                    // "/" "+" -> Exception
                    if (prev1Elem.isMultiplicationOperator() || prev1Elem.isDivisionOperator()){
                        throw new InvalidElementOrderException();
                    }
                }
            }
            prev3Elem = prev2Elem;
            prev2Elem = prev1Elem;
            prev1Elem = matchedElement;
            matchedElement.setIndex(index);
            tokenElementObjList.add(matchedElement);
            index++;
        }
        if (bracket_count_l < bracket_count_r){
            throw new InvalidBracketCountException();
        } else if (bracket_count_l == tokenElementObjList.size()){
            throw new LeftBracketOnlyException();
        }

        return this.postCheck(tokenElementObjList);
    }

    /**
     *
     * @param elementList
     * @return
     */
    private TokenElementList postCheck(TokenElementList elementList){
       /*
        演算子のみの場合
         */
        if (elementList.size() == 1){
            TokenElement tmp = elementList.get(0);
            if (tmp.isPlusOperator()){
                elementList.add(new TokenElement(this.ec, tmp.getIndex() + 1, "0"));
            } else if (tmp.isMultiplicationOperator()){
                elementList.add(new TokenElement(this.ec, tmp.getIndex() + 1, "1"));
            } else if (!tmp.isNumeric()){
                // 上記以外の演算子は計算不可能としてリストを空にする
                elementList.remove(0);
            }
        }
        return elementList;
    }
}
