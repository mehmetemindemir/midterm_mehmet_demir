package com.gb.midterm_mehmet_demir;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private final DataRepository repo = DataRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        ListView historyList = findViewById(R.id.lvHistory);

        ArrayList<String> data = new ArrayList<>();
        for (Integer n : repo.getHistory()) data.add(String.valueOf(n));

        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        historyList.setAdapter(adapter);

        historyList.setOnItemClickListener((parent, view, position, id) -> {
            int n = Integer.parseInt(data.get(position));
            repo.generateTable(n);
            finish();
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
