package function;

public class NumberValue implements Value, DataType {
    private Double value;
    public NumberValue(Double value){
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}

