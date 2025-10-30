package com.gb.midterm_mehmet_demir;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnInsert;
    private ListView lvTable;

    private ArrayAdapter<String> adapter;
    private final DataRepository repo = DataRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set ActionBar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("timesTable");
        }

        etNumber = findViewById(R.id.etNumber);
        btnInsert = findViewById(R.id.btnInsert);
        lvTable = findViewById(R.id.lvTable);

        List<String> rows = repo.getCurrentRows();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows);
        lvTable.setAdapter(adapter);

        if (repo.getCurrentNumber() != null) {
            etNumber.setText(String.valueOf(repo.getCurrentNumber()));
        }

        btnInsert.setOnClickListener(v -> {
            String input = etNumber.getText() == null ? "" : etNumber.getText().toString().trim();
            Integer n = null;
            try { n = Integer.parseInt(input); } catch (NumberFormatException ignored) {}
            if (n == null) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            repo.generateTable(n);
            adapter.notifyDataSetChanged();
        });

        // Tap a row to delete it (confirm + toast)
        lvTable.setOnItemClickListener((parent, view, position, id) -> {
            String value = repo.getCurrentRows().get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete row?")
                    .setMessage("Remove " + value + " from the list?")
                    .setPositiveButton("Delete", (d, w) -> {
                        repo.getCurrentRows().remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Deleted: " + value, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (id == R.id.action_clear_all) {
            if (repo.getCurrentRows().isEmpty()) {
                Toast.makeText(this, "Nothing to clear.", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Clear all?")
                        .setMessage("Remove all rows from the current list?")
                        .setPositiveButton("Clear", (d, w) -> {
                            repo.clearCurrent();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(this, "All rows cleared.", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
