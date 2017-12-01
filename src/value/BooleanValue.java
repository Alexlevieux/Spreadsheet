package value;

public class BooleanValue extends ComparableValue {
    private Boolean value;

    public BooleanValue(Boolean value) {
        setValue(value);
    }

    @Override
    public int getTypeID() {
        return 2;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Comparable value) {
        this.value = (Boolean) value;
    }

    @Override
    public String toString() {
        return value ? "TRUE" : "FALSE";
    }
}
