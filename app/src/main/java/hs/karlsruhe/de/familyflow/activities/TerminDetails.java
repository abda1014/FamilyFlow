package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminDetails extends AppCompatActivity {

    private EditText etTerminname, etDatum, etUhrzeit, etWiederholung, etBeschreibung;
    private Button btnSpeichern, btnLoeschen;
    private TerminDao terminDao;
    private String terminId; // ID des Termins

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();

        // Intent-Daten abrufen
        terminId = getIntent().getStringExtra("terminId");

        // Views initialisieren
        etTerminname = findViewById(R.id.editTextTerminname);
        etDatum = findViewById(R.id.editTextDatum);
        etUhrzeit = findViewById(R.id.editTextUhrzeit);
        etWiederholung = findViewById(R.id.editTextWiederholung);
        etBeschreibung = findViewById(R.id.editTextBeschreibung);
        btnSpeichern = findViewById(R.id.buttonTerminSpeichern);
        btnLoeschen = findViewById(R.id.buttonTerminLoeschen);

        // Termin laden und in die Felder einfügen
        loadTerminDetails();

        // Speichern-Button: Änderungen speichern
        btnSpeichern.setOnClickListener(v -> saveChanges());

        // Löschen-Button: Termin löschen
        btnLoeschen.setOnClickListener(v -> softDeleteTermin());
    }

    private void loadTerminDetails() {
        new Thread(() -> {
            Termin termin = terminDao.findTerminById(terminId);
            if (termin == null) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Termin nicht gefunden!", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            // Termin-Daten in die Felder einfügen
            runOnUiThread(() -> {
                etTerminname.setText(termin.getTerminname());
                etDatum.setText(termin.getDatum());
                etUhrzeit.setText(termin.getUhrzeit());
                etWiederholung.setText(termin.getWiederholung());
                etBeschreibung.setText(termin.getBeschreibung());
            });
        }).start();
    }

    private void saveChanges() {
        String terminname = etTerminname.getText().toString();
        String datum = etDatum.getText().toString();
        String uhrzeit = etUhrzeit.getText().toString();
        String wiederholung = etWiederholung.getText().toString();
        String beschreibung = etBeschreibung.getText().toString();

        // Eingabefelder validieren
        if (terminname.isEmpty() || datum.isEmpty() || beschreibung.isEmpty()) {
            Toast.makeText(this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Änderungen speichern
        new Thread(() -> {
            Termin termin = terminDao.findTerminById(terminId);
            if (termin != null) {
                termin.setTerminname(terminname);
                termin.setDatum(datum);
                termin.setUhrzeit(uhrzeit);
                termin.setWiederholung(wiederholung);
                termin.setBeschreibung(beschreibung);

                terminDao.updateTermin(termin);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Änderungen gespeichert!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    private void softDeleteTermin() {
        new Thread(() -> {
            Termin termin = terminDao.findTerminById(terminId);
            if (termin != null) {
                terminDao.softDeleteTermin(String.valueOf(termin));

                runOnUiThread(() -> {
                    Toast.makeText(this, "Termin gelöscht!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }
}
