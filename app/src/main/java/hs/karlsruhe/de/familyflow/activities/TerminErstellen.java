package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminErstellen extends AppCompatActivity {

    private EditText etTerminname, etDatum, etUhrzeit, etWiederholung, etBeschreibung;
    private TerminDao terminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_erstellen);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();

        // Views initialisieren
        etTerminname = findViewById(R.id.editTextTerminname);
        etDatum = findViewById(R.id.editTextDatum);
        etUhrzeit = findViewById(R.id.editTextUhrzeit);
        etWiederholung = findViewById(R.id.editTextWiederholung);
        etBeschreibung = findViewById(R.id.editTextBeschreibung);

        Button btnSpeichern = findViewById(R.id.buttonTerminSpeichern);
        btnSpeichern.setOnClickListener(v -> saveTermin());
    }

    private void saveTermin() {
        String terminname = etTerminname.getText().toString();
        String datum = etDatum.getText().toString();
        String uhrzeit = etUhrzeit.getText().toString();
        String wiederholung = etWiederholung.getText().toString();
        String beschreibung = etBeschreibung.getText().toString();

        // Eingabefelder validieren
        if (terminname.isEmpty() || beschreibung.isEmpty() || datum.isEmpty()) {
            Toast.makeText(TerminErstellen.this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            // Neuen Termin erstellen
            String terminId = UUID.randomUUID().toString();
            Termin neuerTermin = new Termin(
                    terminId,
                    terminname,
                    datum,
                    uhrzeit,
                    wiederholung,
                    beschreibung,
                    false // Inaktiv (kann bei Bedarf angepasst werden)
            );

            Log.d("TerminErstellen", "Neuer Termin erstellt mit ID: " + terminId);

            // Termin in der Datenbank speichern
            new Thread(() -> {
                terminDao.insertTermin(neuerTermin);
                runOnUiThread(() -> {
                    Toast.makeText(TerminErstellen.this, "Termin erstellt!", Toast.LENGTH_SHORT).show();
                    finish(); // Activity beenden und zur Terminübersicht zurückkehren
                });
            }).start();
        }
    }
}
