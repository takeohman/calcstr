# calcstr


**calcstr** is a Java library to calculate string expressions mainly for Android. 

Each parts are written simply so you can replace them easily.


### Usage

#### Basic

```
MathContext mc = new MathContext(12, RoundingMode.HALF_EVEN);

CalcStr calcStr = new CalcStr(mc);
  
CalcStrResult result = calcStr.calculate("1+2+3");
  
System.out.println(result.getAns());
```

#### Allowed Input

Here's basic patterns of valid expressions.
You can find more patterns in the test code.

|type           |input   |example    |explanation  |
|---------------|--------|-----------|-------------|
|addition       |\+      |"1+2"      |             |
|subtraction    |\-      |"1-2"      |             |
|multiplication |\*      |"1*2"      |             |
|               |\.      |"2.2.2"    |2.2 * 2      |
|               |N(N)    |"2(3)"     |2\*3         |
|division       |/       |"9/3"      |             |
|involution     |\^      |"2^10"     |             |
|               |\*\*    |"2\*\*10"  |             |
|factorial      |!       |"4!"       |             |




