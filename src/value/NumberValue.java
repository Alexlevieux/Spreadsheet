package value;

public class NumberValue extends ComparableValue {
    private Double value;

    public NumberValue(Double value) {
        setValue(value);
    }
    public NumberValue(Integer value){
        this(value.doubleValue());
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Comparable c) {
        this.value = (Double) c;
    }

    @Override
    public int getTypeID() {
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(Math.floor(value) - value < 1e-9 ? value.intValue() : value);
    }
}
