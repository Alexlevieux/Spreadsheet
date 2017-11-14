package function;

public class StringValue implements Value, DataType {
    private String value;
    public StringValue(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
