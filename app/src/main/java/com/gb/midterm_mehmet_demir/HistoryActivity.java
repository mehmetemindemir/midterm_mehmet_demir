package com.gb.midterm_mehmet_demir;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private final DataRepository repo = DataRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView historyList = findViewById(R.id.lvHistory);

        ArrayList<String> data = new ArrayList<>();
        for (Integer n : repo.getHistory()) data.add(String.valueOf(n));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        historyList.setAdapter(adapter);

        // Tap a number to reload that table on MainActivity
        historyList.setOnItemClickListener((parent, view, position, id) -> {
            int n = Integer.parseInt(data.get(position));
            repo.generateTable(n);
            finish(); // go back; MainActivity will show selected table
        });
    }
}
