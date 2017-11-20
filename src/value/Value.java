package value;

import function.Token;

public abstract class Value implements Comparable, Token {
    public abstract int getTypeID();
    public abstract Comparable getValue();

    @Override
    public int compareTo(Object o) {
        Value otherValue = (Value)o;
        return getTypeID() != otherValue.getTypeID() ?
                getTypeID() - otherValue.getTypeID() :
                getValue().compareTo(otherValue.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value otherValue = (Value)o;

        return getValue() != null ? getValue().equals(otherValue.getValue()) : otherValue.getValue() == null;
    }
}
