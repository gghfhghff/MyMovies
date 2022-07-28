package sg.edu.rp.c346.id21016412.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ListActivity extends AppCompatActivity {

    Button btnFilterRating;
    Spinner spnRating;
    ListView lv;
    ArrayList<Movies> al, alFilter;
    ArrayList<String> alRating;
    ArrayAdapter<String> aaRating;
    CustomAdapter caRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnFilterRating = findViewById(R.id.btnFilterRating);
        spnRating = findViewById(R.id.spnRating);
        lv = findViewById(R.id.listView);

        alRating = new ArrayList<>(Arrays.asList("Filter by rating", "G", "PG", "PG13", "NC16", "M18", "R21"));
        aaRating = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alRating);

        spnRating.setAdapter(aaRating);

        al = new ArrayList<Movies>();
        alFilter = new ArrayList<>();
        caRating = new CustomAdapter(this,
                R.layout.row, al);
        lv.setAdapter(caRating);

        DBHelper dbh = new DBHelper(ListActivity.this);
        al.addAll(dbh.getAllMovies());

        btnFilterRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                al.addAll(dbh.getAllMovies());

                String selectedRating = spnRating.getSelectedItem().toString();
                if (selectedRating.equals("Filter by rating")) {
                    Toast.makeText(ListActivity.this, "Please select a rating",
                            Toast.LENGTH_SHORT).show();
                } else if (selectedRating.equals("View all Movies")) {
                    al.clear();
                    al.addAll(dbh.getAllMovies());
                    alRating.set(0, "Filter by rating");

                } else {
                    alRating.set(0, "View all Movies");
                    aaRating.notifyDataSetChanged();
                    for (int i = 0; i < al.size(); i++) {
                        if (!al.get(i).getRating().equals(selectedRating)) {
                            alFilter.add(al.get(i));
                        }
                    }
                    al.removeAll(alFilter);
                }
                caRating.notifyDataSetChanged();
                aaRating.notifyDataSetChanged();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movies data = al.get(i);
                Intent intent = new Intent(ListActivity.this, EditActivity.class);
                intent.putExtra("movieData", data);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ListActivity.this);
        al.clear();
        al.addAll(dbh.getAllMovies());
        caRating.notifyDataSetChanged();

        alRating.set(0, "Filter by rating");
        aaRating.notifyDataSetChanged();
    }
}