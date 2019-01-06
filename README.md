# calcstr

---

**calcstr** is a Java library to calculate string expressions mainly for Android. 

Each parts are written simply so you can replace them easily.


### Usage

---

```
MathContext mc = new MathContext(12, RoundingMode.HALF_EVEN);

CalcStr calcStr = new CalcStr(mc);
  
CalcStrResult result = calcStr.calculate("1+2+3");
  
System.out.println(result.getAns());
```
