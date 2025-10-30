package com.gb.midterm_mehmet_demir;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText numberInput;
    private Button generateBtn;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private final DataRepository repo = DataRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.etNumber);
        generateBtn = findViewById(R.id.btnGenerate);
        listView = findViewById(R.id.lvResults);

        List<String> rows = repo.getCurrentRows();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows);
        listView.setAdapter(adapter);

        if (repo.getCurrentNumber() != null) {
            numberInput.setText(String.valueOf(repo.getCurrentNumber()));
        }

        generateBtn.setOnClickListener(v -> {
            String text = numberInput.getText() == null ? "" : numberInput.getText().toString().trim();
            Integer n = null;
            try { n = Integer.parseInt(text); } catch (NumberFormatException ignored) {}
            if (n == null) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                return;
            }
            repo.generateTable(n);
            adapter.notifyDataSetChanged();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String row = repo.getCurrentRows().get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete row?")
                    .setMessage("Do you want to delete:\n\n" + row)
                    .setPositiveButton("Delete", (d, w) -> {
                        repo.getCurrentRows().remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Deleted: " + row, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu); // History + Clear All
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