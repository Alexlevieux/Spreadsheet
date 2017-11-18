package function;

public class Function implements Token {
    private FunctionType functionType;
    private int args;

    public Function(FunctionType functionType) {
        this.functionType = functionType;
        this.args = 0;
    }

    public void incArgs() throws ParserException {
        ++args;
        if (args > functionType.getMaxArgs())
            throw new ParserException("Too many arguments for function " + functionType.name());
    }

    public void decArgs() {
        --args;
    }

    public int getArgs() {
        return args;
    }

    public void setArgs(int args) {
        this.args = args;
    }

    public FunctionType getFunctionType() {
        return functionType;
    }

    @Override
    public String toString() {
        return getFunctionType().name() + "(" + getArgs() + ")";
    }
}
