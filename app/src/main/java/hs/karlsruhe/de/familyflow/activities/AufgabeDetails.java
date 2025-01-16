package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerAufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;

public class AufgabeDetails extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;
    private Button btnSpeichern, btnLoeschen;
    private AufgabeDao aufgabeDao;
    private String aufgabeId; // ID der Aufgabes
    private Spinner spinnerBenutzer;
    private BenutzerDao benutzerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_details);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();
        benutzerDao = db.benutzerDao();

        // Intent-Daten abrufen
        aufgabeId = getIntent().getStringExtra("aufgabeId");

        // Views initialisieren
        etAufgabenbezeichnung = findViewById(R.id.editTextAufgabenbezeichnung);
        etStatus = findViewById(R.id.editTextStatus);
        etFaelligkeitsdatum = findViewById(R.id.editTextFaelligkeitsdatum);
        etNotiz = findViewById(R.id.editTextNotiz);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);
        btnSpeichern = findViewById(R.id.buttonAufgabeSpeichern);
        btnLoeschen = findViewById(R.id.buttonAufgabeLoeschen);

        // Aufgabe laden und in die Felder einfügen
        loadAufgabeDetails();
        loadBenutzerInSpinner();

        // Speichern-Button: Änderungen speichern
        btnSpeichern.setOnClickListener(v -> saveChanges());

        // Löschen-Button: Aufgabe löschen
        btnLoeschen.setOnClickListener(v -> softDeleteAufgabe());

        // Zurück-Button: Initialisiert den Button der zur  vorherigen Activity leitet
        Button buttonZurueck = findViewById(R.id.buttonZurueck);
        buttonZurueck.setOnClickListener(v -> finish()); // Zurück zur vorherigen Activity
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

            // Benutzerzuweisung abrufen
            BenutzerAufgabeDao benutzerAufgabeDao = DatabaseManager.getDatabase(this).benutzerAufgabenDao();
            BenutzerAufgaben benutzerAufgabe = benutzerAufgabeDao.getBenutzerWithAufgaben(aufgabeId);

            runOnUiThread(() -> {
                etAufgabenbezeichnung.setText(aufgabe.getAufgabenbezeichnung());
                etStatus.setText(aufgabe.getStatus());
                etFaelligkeitsdatum.setText(aufgabe.getFaelligkeitsdatum());
                etNotiz.setText(aufgabe.getNotiz());

                // Benutzer im Spinner auswählen
                if (benutzerAufgabe != null) {
                    for (int i = 0; i < spinnerBenutzer.getAdapter().getCount(); i++) {
                        Benutzer benutzer = (Benutzer) spinnerBenutzer.getItemAtPosition(i);
                        if (benutzer.getBenutzerId().equals(benutzerAufgabe.getBenutzerId())) {
                            spinnerBenutzer.setSelection(i);
                            break;
                        }
                    }
                }
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
                aufgabeDao.softDeleteAufgabe(aufgabeId);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Aufgabe gelöscht!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    private void loadBenutzerInSpinner() {
        new Thread(() -> {
            List<Benutzer> benutzerListe = benutzerDao.getAllActiveBenutzer();
            runOnUiThread(() -> {
                ArrayAdapter<Benutzer> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, benutzerListe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBenutzer.setAdapter(adapter);
            });
        }).start();
    }
}
