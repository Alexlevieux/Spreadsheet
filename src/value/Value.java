package value;

import function.Token;

public abstract class Value implements Token {
    public abstract Object getValue();
    public abstract void setValue(Object o);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value otherValue = (Value)o;

        return getValue() != null ? getValue().equals(otherValue.getValue()) : otherValue.getValue() == null;
    }
}
