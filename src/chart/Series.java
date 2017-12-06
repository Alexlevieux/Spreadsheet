package chart;

import function.CellRange;

public class Series {
    private String name;
    private CellRange values;

    public Series(String name, CellRange values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    CellRange getValues() {
        return values;
    }

    public void setValues(CellRange values) {
        this.values = values;
    }

}
