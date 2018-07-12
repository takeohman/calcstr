package com.takeohman.postfixnotaion.tokenizer;


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

        int index = 0;
        TokenElement prevElement = null;
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

            if (prevElement != null){
                /*
                1. ...)*(...
                2. ...)*7
                3. ...7*(...
                4. ...!*7...
                5. ...!*(...
                6. ...7*tan...
                7. ...7*.123
                 */
                if ((prevElement.isRightBracket() && matchedElement.isLeftBracket()) ||
                        (prevElement.isRightBracket() && matchedElement.isNumeric()) ||
                        (prevElement.isNumeric() && matchedElement.isLeftBracket()) ||
                        (prevElement.isExclamation() && matchedElement.isNumeric()) ||
                        (prevElement.isExclamation() && matchedElement.isLeftBracket()) ||
                        (prevElement.isNumeric() && matchedElement.isFunction()) ||
                        (prevElement.isNumeric() && matchedElement.isIncompleteDecimal())
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

                // - -
                else if (prevElement.isMinusOperator() && matchedElement.isMinusOperator()){
                    prevElement.setIsValid(false);
                    matchedElement.setStr("+");
                }
                // "(" "+ or * or /" --->Exception
                else if (prevElement.isLeftBracket() &&
                        (matchedElement.isPlusOperator() || matchedElement.isMultiplicationOperator() || matchedElement.isDivisionOperator())){
                    throw new InvalidElementOrderException();
                }
                // + - -> -
                else if (prevElement.isPlusOperator() && matchedElement.isMinusOperator()){
                    prevElement.setStr("-");
                    continue;
                }
                // * * -> ^
                else if (prevElement.isMultiplicationOperator() && matchedElement.isMultiplicationOperator()){
                    prevElement.setStr("^");
                    continue;
                }
                // + + -> Exception
                else if (prevElement.isPlusOperator() && matchedElement.isPlusOperator()){
                    throw new InvalidElementOrderException();
                }
                // - +
                else if (prevElement.isMinusOperator() && matchedElement.isPlusOperator()){
                    throw new InvalidElementOrderException();
                }
                // - *
                else if (prevElement.isMinusOperator() && matchedElement.isMultiplicationOperator()){
                    throw new InvalidElementOrderException();
                }
                // - /
                else if (prevElement.isMinusOperator() && matchedElement.isDivisionOperator()){
                    throw new InvalidElementOrderException();
                }
                // + *
                else if (prevElement.isPlusOperator() && matchedElement.isMultiplicationOperator()){
                    throw new InvalidElementOrderException();
                }
                // + /
                else if (prevElement.isPlusOperator() && matchedElement.isDivisionOperator()){
                    throw new InvalidElementOrderException();
                }
                // ( )
                else if (prevElement.isLeftBracket() && matchedElement.isRightBracket()){
                    throw new InvalidElementOrderException();
                }
                // "*" "+" -> Exception
                else if (prevElement.isMultiplicationOperator() && matchedElement.isPlusOperator()){
                    throw new InvalidElementOrderException();
                }
                // "/" "+" -> Exception
                else if (prevElement.isDivisionOperator() && matchedElement.isPlusOperator()){
                    throw new InvalidElementOrderException();
                }
            }
            prevElement = matchedElement;
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
