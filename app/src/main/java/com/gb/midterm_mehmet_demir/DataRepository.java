package com.gb.midterm_mehmet_demir;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DataRepository {
    private static final DataRepository INSTANCE = new DataRepository();

    public static DataRepository getInstance() { return INSTANCE; }

    private final Set<Integer> history = new LinkedHashSet<>();
    private Integer currentNumber = null;
    private final ArrayList<String> currentRows = new ArrayList<>();

    private DataRepository() {}

    public Set<Integer> getHistory() { return history; }
    public Integer getCurrentNumber() { return currentNumber; }
    public List<String> getCurrentRows() { return currentRows; }

    public void generateTable(int n) {
        currentNumber = n;
        currentRows.clear();
        for (int i = 1; i <= 10; i++) {
            currentRows.add(n + " × " + i + " = " + (n * i));
        }
        history.add(n);
    }

    public void clearCurrent() {
        currentRows.clear();
    }
}
