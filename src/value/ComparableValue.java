package value;

public abstract class ComparableValue extends Value implements Comparable {
    public abstract int getTypeID();

    public abstract void setValue(Comparable c);

    @Override
    public abstract Comparable getValue();

    @Override
    public void setValue(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTo(Object o) {
        ComparableValue otherValue = (ComparableValue) o;
        //noinspection unchecked
        return getTypeID() != otherValue.getTypeID() ?
                getTypeID() - otherValue.getTypeID() :
                getValue().compareTo(otherValue.getValue());
    }

    @Override
    public abstract String toString();

    public static ComparableValue valueOf(String expression) {
        if (expression.toUpperCase().equals("TRUE"))
            return new BooleanValue(true);
        else if (expression.toUpperCase().equals("FALSE"))
            return new BooleanValue(false);
        else try {
                Double d = Double.valueOf(expression);
                return new NumberValue(d);
            } catch (NumberFormatException e) {
                return new StringValue(expression);
            }
    }
}
