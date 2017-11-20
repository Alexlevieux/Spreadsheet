package value;

public class BooleanValue extends Value{
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

    public void setValue(Boolean value) {
        this.value = value;
    }
}
