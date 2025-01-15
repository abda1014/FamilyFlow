package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import hs.karlsruhe.de.familyflow.data.dao.BenutzerAufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerTerminDao;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerTermin;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminErstellen extends AppCompatActivity {

    private EditText etTerminname, etDatum, etUhrzeit, etWiederholung, etBeschreibung;
    private TerminDao terminDao;
    private Spinner spinnerBenutzer;
    private BenutzerDao benutzerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_erstellen);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();
        benutzerDao = db.benutzerDao();

        // Views initialisieren
        etTerminname = findViewById(R.id.editTextTerminname);
        etDatum = findViewById(R.id.editTextDatum);
        etUhrzeit = findViewById(R.id.editTextUhrzeit);
        etWiederholung = findViewById(R.id.editTextWiederholung);
        etBeschreibung = findViewById(R.id.editTextBeschreibung);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);

        Button btnSpeichern = findViewById(R.id.buttonTerminSpeichern);
        loadBenutzerInSpinner();
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
        }

        // Hole den ausgewählten Benutzer
        Benutzer ausgewählterBenutzer = (Benutzer) spinnerBenutzer.getSelectedItem();
        if (ausgewählterBenutzer == null) {
            Toast.makeText(this, "Bitte eine Person auswählen!", Toast.LENGTH_SHORT).show();
            return;
        }

            // Neuen Termin erstellen
            String terminId = UUID.randomUUID().toString();
            Termin neuerTermin = new Termin(
                    terminId,
                    terminname,
                    datum,
                    uhrzeit,
                    wiederholung,
                    beschreibung,
                    false
            );

            // Termin in der Datenbank speichern
            new Thread(() -> {
                terminDao.insertTermin(neuerTermin);

                // Zuweisung zwischen Benutzer und Termin speichern
                BenutzerTermin benutzerTermin = new BenutzerTermin(ausgewählterBenutzer.getBenutzerId(), terminId);
                BenutzerTerminDao benutzerTerminDao = DatabaseManager.getDatabase(this).benutzerTermineDao();
                benutzerTerminDao.insertTermin(benutzerTermin);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Termin erstellt und Person zugewiesen!", Toast.LENGTH_SHORT).show();
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
