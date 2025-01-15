package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerAufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;

public class PersonenZuweisen extends AppCompatActivity {

    private BenutzerDao benutzerDao;
    private AufgabeDao aufgabeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personen_zuweisen);

        AppDatabase db = DatabaseManager.getDatabase(this);
        benutzerDao = db.benutzerDao();
        aufgabeDao = db.aufgabeDao();

        Button btnSpeichern = findViewById(R.id.buttonPersonenSpeichern);
        btnSpeichern.setOnClickListener(v -> {
            // Eventuell Daten verarbeiten (Zuweisung noch implementieren)
            Toast.makeText(this, "Personen gespeichert", Toast.LENGTH_SHORT).show();
        });
    }
}



