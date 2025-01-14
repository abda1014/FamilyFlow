package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;

public class AufgabeDetails extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;
    private Button btnSpeichern, btnLoeschen;
    private AufgabeDao aufgabeDao;
    private String aufgabeId; // ID der Aufgabes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_details);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();

        // Intent-Daten abrufen
        aufgabeId = getIntent().getStringExtra("aufgabeId");

        // Views initialisieren
        etAufgabenbezeichnung = findViewById(R.id.editTextAufgabenbezeichnung);
        etStatus = findViewById(R.id.editTextStatus);
        etFaelligkeitsdatum = findViewById(R.id.editTextFaelligkeitsdatum);
        etNotiz = findViewById(R.id.editTextNotiz);
        btnSpeichern = findViewById(R.id.buttonAufgabeSpeichern);
        btnLoeschen = findViewById(R.id.buttonAufgabeLoeschen);

        // Aufgabe laden und in die Felder einfügen
        loadAufgabeDetails();

        // Speichern-Button: Änderungen speichern
        btnSpeichern.setOnClickListener(v -> saveChanges());

        // Löschen-Button: Aufgabe löschen
        btnLoeschen.setOnClickListener(v -> softDeleteAufgabe());
    }

    private void loadAufgabeDetails() {
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe == null) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Aufgabe nicht gefunden!", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            // Aufgabe-Daten in die Felder einfügen
            runOnUiThread(() -> {
                etAufgabenbezeichnung.setText(aufgabe.getAufgabenbezeichnung());
                etStatus.setText(aufgabe.getStatus());
                etFaelligkeitsdatum.setText(aufgabe.getFaelligkeitsdatum());
                etNotiz.setText(aufgabe.getNotiz());
            });
        }).start();
    }

    private void saveChanges() {
        String aufgabenbezeichnung = etAufgabenbezeichnung.getText().toString();
        String status = etStatus.getText().toString();
        String faelligkeitsdatum = etFaelligkeitsdatum.getText().toString();
        String notiz = etNotiz.getText().toString();

        // Eingabefelder validieren
        if (aufgabenbezeichnung.isEmpty() || status.isEmpty() || faelligkeitsdatum.isEmpty()) {
            Toast.makeText(this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Änderungen speichern
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe != null) {
                aufgabe.setAufgabenbezeichnung(aufgabenbezeichnung);
                aufgabe.setStatus(status);
                aufgabe.setFaelligkeitsdatum(faelligkeitsdatum);
                aufgabe.setNotiz(notiz);

                aufgabeDao.updateAufgabe(aufgabe);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Änderungen gespeichert!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    private void softDeleteAufgabe() {
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe != null) {
                aufgabeDao.softDeleteAufgabe(String.valueOf(aufgabe));

                runOnUiThread(() -> {
                    Toast.makeText(this, "Aufgabe gelöscht!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }
}
