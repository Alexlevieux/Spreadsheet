package value;

public class DoubleValue extends Value {
    private Double value;

    public DoubleValue(Double value) {
        setValue(value);
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public int getTypeID() {
        return 0;
    }
}
