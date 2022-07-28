package sg.edu.rp.c346.id21016412.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText etTitle, etGenre, etYear;
    Spinner spnRating;
    Button btnInsert, btnShowList;
    ArrayList <String> alRating;
    ArrayAdapter <String> aaRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.editTextTitle);
        etGenre = findViewById(R.id.editTextGenre);
        etYear = findViewById(R.id.editTextYear);
        spnRating = findViewById(R.id.spnRating);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        alRating = new ArrayList<>(Arrays.asList("Select movie rating","G", "PG", "PG13", "NC16", "M18", "R21"));
        aaRating = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alRating);

        spnRating.setAdapter(aaRating);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, String.valueOf(spnRating.getSelectedItemPosition()),
                        Toast.LENGTH_SHORT).show();

                if (spnRating.getSelectedItemPosition() != 0) {
                    String title = etTitle.getText().toString();
                    String genre = etGenre.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    String rating = spnRating.getSelectedItem().toString();

                    DBHelper dbh = new DBHelper(MainActivity.this);
                    long inserted_id = dbh.insertMovie(title, genre, year, rating);
                    if (inserted_id != -1) {
                        Toast.makeText(MainActivity.this, "Insert successful",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Insert unsuccessful",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);

            }
        });



    }
}