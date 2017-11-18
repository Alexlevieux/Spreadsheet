package function;

public enum FunctionType {
    SUM, AVERAGE, MIN, MAX, COUNT, COUNTBLANK, VAR, STDDEV;
    private int maxArgs;
    FunctionType(int maxArgs){
        setMaxArgs(maxArgs);
    }
    FunctionType(){
        this(255);
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }
    public static boolean contains(String value){
        for(FunctionType ft : values()){
            if(ft.name().equals(value)) return true;
        }
        return false;
    }
}
