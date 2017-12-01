package value;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ListValue extends IncomparableValue {
    private ArrayList<ComparableValue> list;

    @Override
    public ArrayList<ComparableValue> getValue() {
        return list;
    }

    @Override
    public void setValue(Object o) {
        //noinspection unchecked
        this.list = (ArrayList<ComparableValue>) o;
    }

    public Value get(int index) {
        return list.get(index);
    }

    public void add(ComparableValue value) {
        list.add(value);
    }

    public void addAll(List<ComparableValue> values){
        list.addAll(values);
    }

    public void set(int index, ComparableValue value) {
        list.set(index, value);
    }

    public int size(){
        return list.size();
    }
}
