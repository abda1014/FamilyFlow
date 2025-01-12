package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminDetails extends AppCompatActivity {

    private TerminDao terminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();

        String terminId = getIntent().getStringExtra("terminId");



        // Termin aus DB laden
        loadTerminDetails(terminId);
    }

    private void loadTerminDetails(String terminId) {
        Log.d("TerminDetails", "Start loading details for ID: " + terminId);
        new Thread(() -> {
            Termin termin = terminDao.findTerminById(terminId);
            if (termin == null) {
                Log.e("TerminDetails", "Termin nicht gefunden!");
                runOnUiThread(() -> {
                    Toast.makeText(this, "Termin nicht gefunden!", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }
            runOnUiThread(() -> {
                Log.d("TerminDetails", "Termin geladen: " + termin.getTerminname());
                TextView tvTerminame = findViewById(R.id.terminname);
                TextView tvBeschreibung = findViewById(R.id.beschreibung);
                TextView tvDatum = findViewById(R.id.datum);

                tvTerminame.setText(termin.getTerminname());
                tvBeschreibung.setText(termin.getBeschreibung());
                tvDatum.setText(termin.getDatum());
            });
        }).start();
    }
}



