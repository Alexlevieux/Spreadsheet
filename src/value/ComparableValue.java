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
}
