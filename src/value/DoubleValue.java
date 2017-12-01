package value;

public class DoubleValue extends ComparableValue {
    private Double value;

    public DoubleValue(Double value) {
        setValue(value);
    }
    public DoubleValue(Integer value){
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
}
