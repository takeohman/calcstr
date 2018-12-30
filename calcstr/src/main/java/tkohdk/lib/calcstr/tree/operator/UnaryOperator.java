package tkohdk.lib.calcstr.tree.operator;

public enum UnaryOperator {

    Sin("sin"),
    Cos("cos"),
    Tan("tan"),
    Log("log"),
    Log10("log10"),
    Log1p("log1p"),
    Factorial("!");

    String operator;
    UnaryOperator(String ope){
        this.operator = ope;
    }
    public String getOperator(){
        return this.operator;
    }

    @Override
    public String toString(){return operator;}

    static public UnaryOperator getOperator(String str){
        switch (str){
            case "sin":
                return UnaryOperator.Sin;
            case "cos":
                return UnaryOperator.Cos;
            case "tan":
                return UnaryOperator.Tan;
            case "log":
                return UnaryOperator.Log;
            case "log10":
                return UnaryOperator.Log10;
            case "log1p":
                return UnaryOperator.Log1p;
            case "!":
                return UnaryOperator.Factorial;
        }
        return null;
    }
}
