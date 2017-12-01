package value;

public abstract class IncomparableValue extends Value {
    @Override
    public abstract Object getValue();

    @Override
    public abstract void setValue(Object o);
}
