package value;

import exception.ErrorType;

public class ErrorValue extends ComparableValue {
    ErrorType type;

    public ErrorValue(ErrorType type){
        this.type = type;
    }

    @Override
    public int getTypeID() {
        return 1000;
    }

    @Override
    public void setValue(Comparable c) {

    }

    @Override
    public ErrorType getValue() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
            case NUM:
                return "#NUM!";
            case REF:
                return "#REF!";
            case ARGS:
                return "#ARGS!";
            case NAME:
                return "#NAME?";
            case VALUE:
                return "#VALUE!";
            case DIVZERO:
                return "#DIV/0!";
            case ERROR:
            default:
                return "#ERROR!";
        }
    }
}
