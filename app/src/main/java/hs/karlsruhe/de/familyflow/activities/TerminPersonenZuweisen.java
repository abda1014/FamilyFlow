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

    // Datenbankzugriff-Objekte
    private BenutzerDao benutzerDao; // DAO für Benutzer
    private TerminDao terminDao;     // DAO für Termine

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personen_zuweisen);

        // Datenbankinstanz abrufen und DAOs initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        benutzerDao = db.benutzerDao(); // Zugriff auf Benutzer-Daten
        terminDao = db.terminDao();     // Zugriff auf Termin-Daten

        // Speichern-Button aus dem Layout abrufen
        Button btnSpeichern = findViewById(R.id.buttonPersonenSpeichern);

        // Klick-Listener für den Speichern-Button setzen
        btnSpeichern.setOnClickListener(v -> {
            // Hier könnte die Logik für die Zuweisung von Personen zu einem Termin implementiert werden
            Toast.makeText(this, "Personen gespeichert", Toast.LENGTH_SHORT).show();
        });
    }
}
