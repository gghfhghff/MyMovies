package sg.edu.rp.c346.id21016412.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class EditActivity extends AppCompatActivity {
    EditText etId, etTitle, etGenre, etYear;
    Spinner spnRating;
    Button btnUpdate, btnDelete, btnCancel;
    Movies data;
    ArrayList<String> alRating;
    ArrayAdapter<String> aaRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etId = findViewById(R.id.editTextId);
        etTitle = findViewById(R.id.editTextTitle);
        etGenre = findViewById(R.id.editTextGenre);
        etYear = findViewById(R.id.editTextYear);
        spnRating = findViewById(R.id.spnRating);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent i = getIntent();
        data = (Movies) i.getSerializableExtra("movieData");

        Intent iBack = new Intent(EditActivity.this,
                ListActivity.class);

        alRating = new ArrayList<>(Arrays.asList("G", "PG", "PG13", "NC16", "M18", "R21"));
        aaRating = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alRating);

        spnRating.setAdapter(aaRating);

        etId.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));

        for (int j = 0; j < alRating.size(); j++){
            if (alRating.get(j).equals(data.getRating())){
                spnRating.setSelection(j);
            }
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setRating(spnRating.getSelectedItem().toString());

                dbh.updateMovie(data);
                dbh.close();
                startActivity(iBack);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie '"+data.getTitle()+"'");
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbh = new DBHelper(EditActivity.this);
                        dbh.deleteMovie(data.getId());
                        startActivity(iBack);
                    }
                });

                myBuilder.setPositiveButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes");
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(iBack);
                    }
                });

                myBuilder.setPositiveButton("Do not discard", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });
    }
}