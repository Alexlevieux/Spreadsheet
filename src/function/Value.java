package function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Value<Type extends Comparable> implements Token, Comparable {
    private Type value;

    public Value(Type value) {
        setValue(value);
    }

    public void setValue(Type value) {
        this.value = value;
    }

    public Type getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "val(" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value<?> value1 = (Value<?>) o;

        return value != null ? value.equals(value1.value) : value1.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;

        Value<?> val = (Value<?>) o;
//        System.out.println(value + " compare " + val.getValue());
        if (value.equals(val.getValue())) {
            return 0;
        } else {
            ArrayList<String> types = new ArrayList<>();
            types.add("java.lang.Double");
            types.add("java.lang.String");
            types.add("java.lang.Boolean");
            if (value.getClass().equals(val.getValue().getClass())) {
                return value.compareTo(val.getValue());
            } else {
//                System.out.println(value.getClass().getName());
                int thisTypeID = types.indexOf(value.getClass().getName());
//                System.out.println(val.getValue().getClass().getName());
                int otherTypeID = types.indexOf(val.getValue().getClass().getName());
//                System.out.println(thisTypeID + ", " + otherTypeID);
                return thisTypeID - otherTypeID;
            }
        }
    }
}
