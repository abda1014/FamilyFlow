package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerAufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerTerminDao;
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerTermin;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminDetails extends AppCompatActivity {

    // UI-Komponenten
    private EditText etTerminname, etDatum, etUhrzeit, etWiederholung, etBeschreibung;
    private Button btnSpeichern, btnLoeschen;
    private Spinner spinnerBenutzer;

    // Datenbank-Objekte
    private TerminDao terminDao;
    private BenutzerDao benutzerDao;

    // Termin- und Benutzer-IDs
    private String benutzerId;
    private String terminId; // ID des Termins

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details);

        // Room-Datenbank und DAOs initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();
        benutzerDao = db.benutzerDao();

        // Intent-Daten abrufen (Termin-ID wird übergeben)
        terminId = getIntent().getStringExtra("terminId");

        // Views initialisieren
        etTerminname = findViewById(R.id.editTextTerminname);
        etDatum = findViewById(R.id.editTextDatum);
        etUhrzeit = findViewById(R.id.editTextUhrzeit);
        etWiederholung = findViewById(R.id.editTextWiederholung);
        etBeschreibung = findViewById(R.id.editTextBeschreibung);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);
        btnSpeichern = findViewById(R.id.buttonTerminSpeichern);
        btnLoeschen = findViewById(R.id.buttonTerminLoeschen);

        // Termin-Daten laden und in die Eingabefelder einfügen
        loadTerminDetails();

        // Benutzerliste für den Spinner laden
        loadBenutzerInSpinner();

        // Klick-Listener für den Speichern-Button
        btnSpeichern.setOnClickListener(v -> saveChanges());

        // Klick-Listener für den Löschen-Button
        btnLoeschen.setOnClickListener(v -> softDeleteTermin());

        // Klick-Listener für den Zurück-Button, um zur vorherigen Activity zu navigieren
        Button buttonZurueck = findViewById(R.id.buttonZurueck);
        buttonZurueck.setOnClickListener(v -> finish());
    }

    /**
     * Lädt die Details eines Termins aus der Datenbank und zeigt sie in den Eingabefeldern an.
     */
    private void loadTerminDetails() {
        new Thread(() -> {
            // Termin-Daten aus der Datenbank abrufen
            Termin termin = terminDao.findTerminById(terminId);
            if (termin == null) {
                // Falls der Termin nicht gefunden wurde, eine Fehlermeldung anzeigen und die Activity schließen
                runOnUiThread(() -> {
                    Toast.makeText(this, "Termin nicht gefunden!", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            // Benutzer-Terminzuteilung abrufen
            BenutzerTerminDao benutzerTerminDao = DatabaseManager.getDatabase(this).benutzerTermineDao();
            BenutzerTermin benutzerTermin = benutzerTerminDao.getBenutzerWithTermin(terminId);

            // UI-Elemente mit den geladenen Termindaten füllen
            runOnUiThread(() -> {
                etTerminname.setText(termin.getTerminname());
                etDatum.setText(termin.getDatum());
                etUhrzeit.setText(termin.getUhrzeit());
                etWiederholung.setText(termin.getWiederholung());
                etBeschreibung.setText(termin.getBeschreibung());

                // Falls der Termin einem Benutzer zugeordnet ist, den Benutzer im Spinner auswählen
                if (benutzerTermin != null) {
                    for (int i = 0; i < spinnerBenutzer.getAdapter().getCount(); i++) {
                        Benutzer benutzer = (Benutzer) spinnerBenutzer.getItemAtPosition(i);
                        if (benutzer.getBenutzerId().equals(benutzerTermin.getBenutzerId())) {
                            spinnerBenutzer.setSelection(i);
                            break;
                        }
                    }
                }
            });
        }).start();
    }

    /**
     * Speichert die Änderungen am Termin in der Datenbank.
     */
    private void saveChanges() {
        // Daten aus den Eingabefeldern auslesen
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

        // Änderungen in der Datenbank speichern
        new Thread(() -> {
            // Termin aus der Datenbank laden
            Termin termin = terminDao.findTerminById(terminId);
            if (termin != null) {
                // Termin-Daten aktualisieren
                termin.setTerminname(terminname);
                termin.setDatum(datum);
                termin.setUhrzeit(uhrzeit);
                termin.setWiederholung(wiederholung);
                termin.setBeschreibung(beschreibung);

                // Änderungen speichern
                terminDao.updateTermin(termin);

                // Erfolgsnachricht anzeigen und Activity schließen
                runOnUiThread(() -> {
                    Toast.makeText(this, "Änderungen gespeichert!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    /**
     * Löscht den Termin durch eine weiche Löschung (Markieren als gelöscht) in der Datenbank.
     */
    private void softDeleteTermin() {
        new Thread(() -> {
            // Termin aus der Datenbank abrufen
            Termin termin = terminDao.findTerminById(terminId);
            if (termin != null) {
                // Termin als gelöscht markieren
                terminDao.softDeleteTermin(terminId);

                // Erfolgsnachricht anzeigen und Activity schließen
                runOnUiThread(() -> {
                    Toast.makeText(this, "Termin gelöscht!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    /**
     * Lädt alle aktiven Benutzer aus der Datenbank und füllt die Benutzerliste in den Spinner.
     */
    private void loadBenutzerInSpinner() {
        new Thread(() -> {
            // Liste aller aktiven Benutzer abrufen
            List<Benutzer> benutzerListe = benutzerDao.getAllActiveBenutzer();

            // Benutzerliste in den Spinner einfügen
            runOnUiThread(() -> {
                ArrayAdapter<Benutzer> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, benutzerListe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBenutzer.setAdapter(adapter);
            });
        }).start();
    }
}
