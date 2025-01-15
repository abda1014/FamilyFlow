package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

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

public class AufgabeErstellen extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;
    private AufgabeDao aufgabeDao;
    private Spinner spinnerBenutzer;
    private BenutzerDao benutzerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_erstellen);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();
        benutzerDao = db.benutzerDao();

        // Views initialisieren
        etAufgabenbezeichnung = findViewById(R.id.editTextAufgabenbezeichnung);
        etStatus = findViewById(R.id.editTextStatus);
        etFaelligkeitsdatum = findViewById(R.id.editTextFaelligkeitsdatum);
        etNotiz = findViewById(R.id.editTextNotiz);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);

        Button btnSpeichern = findViewById(R.id.buttonAufgabeSpeichern);
        // Benutzerliste laden und im Spinner anzeigen
        loadBenutzerInSpinner();
        btnSpeichern.setOnClickListener(v -> saveAufgabe());
    }

    private void saveAufgabe() {
        String aufgabenbezeichnung = etAufgabenbezeichnung.getText().toString();
        String status = etStatus.getText().toString();
        String faelligkeitsdatum = etFaelligkeitsdatum.getText().toString();
        String notiz = etNotiz.getText().toString();

        if (aufgabenbezeichnung.isEmpty() || status.isEmpty() || faelligkeitsdatum.isEmpty()) {
            Toast.makeText(AufgabeErstellen.this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hole den ausgewählten Benutzer
        Benutzer ausgewählterBenutzer = (Benutzer) spinnerBenutzer.getSelectedItem();
        if (ausgewählterBenutzer == null) {
            Toast.makeText(this, "Bitte eine Person auswählen!", Toast.LENGTH_SHORT).show();
            return;
        }

        String aufgabeId = UUID.randomUUID().toString();
        Aufgabe neueAufgabe = new Aufgabe(
                aufgabeId,
                aufgabenbezeichnung,
                status,
                faelligkeitsdatum,
                notiz,
                false
        );

        new Thread(() -> {
            aufgabeDao.insertAufgabe(neueAufgabe);

            // Zuweisung zwischen Benutzer und Aufgabe speichern
            BenutzerAufgaben benutzerAufgabe = new BenutzerAufgaben(ausgewählterBenutzer.getBenutzerId(), aufgabeId);
            BenutzerAufgabeDao benutzerAufgabeDao = DatabaseManager.getDatabase(this).benutzerAufgabenDao();
            benutzerAufgabeDao.insertAufgabe(benutzerAufgabe);

            runOnUiThread(() -> {
                Toast.makeText(this, "Aufgabe erstellt und Person zugewiesen!", Toast.LENGTH_SHORT).show();
                finish();
            });
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
