package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;

public class TerminPersonenZuweisen extends AppCompatActivity {

    private BenutzerDao benutzerDao;
    private TerminDao terminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personen_zuweisen);

        AppDatabase db = DatabaseManager.getDatabase(this);
        benutzerDao = db.benutzerDao();
        terminDao = db.terminDao();

        Button btnSpeichern = findViewById(R.id.buttonPersonenSpeichern);
        btnSpeichern.setOnClickListener(v -> {
            // Eventuell Daten verarbeiten (Zuweisung noch implementieren)
            Toast.makeText(this, "Personen gespeichert", Toast.LENGTH_SHORT).show();
        });
    }
}
