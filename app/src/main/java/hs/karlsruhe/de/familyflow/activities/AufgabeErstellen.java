package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;

public class AufgabeErstellen extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;
    private AufgabeDao aufgabeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_erstellen);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();

        // Views initialisieren
        etAufgabenbezeichnung = findViewById(R.id.editTextAufgabenbezeichnung);
        etStatus = findViewById(R.id.editTextStatus);
        etFaelligkeitsdatum = findViewById(R.id.editTextFaelligkeitsdatum);
        etNotiz = findViewById(R.id.editTextNotiz);

        Button btnSpeichern = findViewById(R.id.buttonAufgabeSpeichern);
        btnSpeichern.setOnClickListener(v -> saveAufgabe());
    }

    private void saveAufgabe() {
        String aufgabenbezeichnung = etAufgabenbezeichnung.getText().toString();
        String status = etStatus.getText().toString();
        String faelligkeitsdatum = etFaelligkeitsdatum.getText().toString();
        String notiz = etNotiz.getText().toString();

        // Eingabefelder validieren
        if (aufgabenbezeichnung.isEmpty() || status.isEmpty() || faelligkeitsdatum.isEmpty()) {
            Toast.makeText(AufgabeErstellen.this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            // Neue Aufgabe erstellen
            String aufgabeId = UUID.randomUUID().toString();
            Aufgabe neueAufgabe = new Aufgabe(
                    aufgabeId,
                    aufgabenbezeichnung,
                    status,
                    faelligkeitsdatum,
                    notiz,
                    false // Inaktiv (kann bei Bedarf angepasst werden)
            );

            Log.d("AufgabeErstellen", "Neue Aufgabe erstellt mit ID: " + aufgabeId);

            // Aufgabe in der Datenbank speichern
            new Thread(() -> {
                aufgabeDao.insertAufgabe(neueAufgabe);
                runOnUiThread(() -> {
                    Toast.makeText(AufgabeErstellen.this, "Aufgabe erstellt!", Toast.LENGTH_SHORT).show();
                    finish(); // Activity beenden und zur Aufgabeübersicht zurückkehren
                });
            }).start();
        }
    }
}
