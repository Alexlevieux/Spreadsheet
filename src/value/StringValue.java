package value;

public class StringValue extends Value {
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

    public void setValue(String value) {
        this.value = value;
    }
}
