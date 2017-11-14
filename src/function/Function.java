package function;

public abstract class Function<ReturnType extends DataType> implements Value {
    private ReturnType returnType;
    private int MAX_ARGS;
    public Function(int maxArgs){
        MAX_ARGS = maxArgs;
    }
    public abstract ReturnType compute(Value... values) throws Exception;
}
