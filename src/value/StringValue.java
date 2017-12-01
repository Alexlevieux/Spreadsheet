package value;

public class StringValue extends ComparableValue {
    private String value;

    public StringValue(String value) {
        setValue(value);
    }

    @Override
    public int getTypeID() {
        return 1;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(Comparable value) {
        this.value = (String)value;
    }

    @Override
    public String toString() {
        return value;
    }
}
