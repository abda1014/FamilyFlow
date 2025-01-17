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

    // DAO-Objekte für den Zugriff auf die Datenbank
    private BenutzerDao benutzerDao;
    private AufgabeDao aufgabeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personen_zuweisen);

        // Initialisiere die Datenbank und DAO-Objekte
        AppDatabase db = DatabaseManager.getDatabase(this);
        benutzerDao = db.benutzerDao(); // Zugriff auf Benutzer-Tabellenoperationen
        aufgabeDao = db.aufgabeDao();   // Zugriff auf Aufgaben-Tabellenoperationen

        // Speicher-Button initialisieren
        Button btnSpeichern = findViewById(R.id.buttonPersonenSpeichern);

        // Click-Listener für den Button
        btnSpeichern.setOnClickListener(v -> {
            // Eventuell: Logik für das Speichern der Zuweisung hinzufügen
            Toast.makeText(this, "Personen gespeichert", Toast.LENGTH_SHORT).show();
        });
    }
}
