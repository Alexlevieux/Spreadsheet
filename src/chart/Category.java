package chart;

import function.CellRange;

public class Category {
    private String name;
    private CellRange values;

    public Category(String name, CellRange values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CellRange getValues() {
        return values;
    }

    public void setValues(CellRange values) {
        this.values = values;
    }
}
