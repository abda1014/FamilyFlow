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
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;

public class AufgabeDetails extends AppCompatActivity {

    private AufgabeDao aufgabeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_details);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();

        String aufgabeId = getIntent().getStringExtra("aufgabeId");



        // Aufgabe aus DB laden
        loadAufgabeDetails(aufgabeId);
    }

    private void loadAufgabeDetails(String aufgabeId) {
        Log.d("AufgabeDetails", "Start loading details for ID: " + aufgabeId);
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe == null) {
                Log.e("AufgabeDetails", "Aufgabe nicht gefunden!");
                runOnUiThread(() -> {
                    Toast.makeText(this, "Aufgabe nicht gefunden!", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }
            runOnUiThread(() -> {
                Log.d("AufgabeDetails", "Aufgabe geladen: " + aufgabe.getAufgabenbezeichnung());
                TextView tvBezeichnung = findViewById(R.id.aufgabenbezeichnung);
                TextView tvStatus = findViewById(R.id.status);
                TextView tvFaelligkeit = findViewById(R.id.faelligkeitsdatum);

                tvBezeichnung.setText(aufgabe.getAufgabenbezeichnung());
                tvStatus.setText(aufgabe.getStatus());
                tvFaelligkeit.setText(aufgabe.getFaelligkeitsdatum());
            });
        }).start();
    }
}



