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

    // UI-Komponenten (Eingabefelder und Spinner für Benutzer)
    private EditText etTerminname, etDatum, etUhrzeit, etWiederholung, etBeschreibung;
    private Spinner spinnerBenutzer;

    // Datenbankzugriffe über DAOs
    private TerminDao terminDao;
    private BenutzerDao benutzerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_erstellen);

        // Datenbank und DAOs initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao(); // Zugriff auf die Termin-Tabelle
        benutzerDao = db.benutzerDao(); // Zugriff auf die Benutzer-Tabelle

        // UI-Elemente initialisieren
        etTerminname = findViewById(R.id.editTextTerminname);
        etDatum = findViewById(R.id.editTextDatum);
        etUhrzeit = findViewById(R.id.editTextUhrzeit);
        etWiederholung = findViewById(R.id.editTextWiederholung);
        etBeschreibung = findViewById(R.id.editTextBeschreibung);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);

        // "Speichern"-Button konfigurieren
        Button btnSpeichern = findViewById(R.id.buttonTerminSpeichern);
        btnSpeichern.setOnClickListener(v -> saveTermin()); // Beim Klick Termin speichern

        // Benutzer in den Spinner laden
        loadBenutzerInSpinner();

        // "Zurück"-Button konfigurieren, um zur vorherigen Activity zu wechseln
        Button buttonZurueck = findViewById(R.id.buttonZurueck);
        buttonZurueck.setOnClickListener(v -> finish()); // Activity schließen
    }

    /**
     * Speichert einen neuen Termin und weist ihn einem Benutzer zu.
     */
    private void saveTermin() {
        // Eingabewerte aus den EditText-Feldern auslesen
        String terminname = etTerminname.getText().toString();
        String datum = etDatum.getText().toString();
        String uhrzeit = etUhrzeit.getText().toString();
        String wiederholung = etWiederholung.getText().toString();
        String beschreibung = etBeschreibung.getText().toString();

        // Validierung: Überprüfen, ob Pflichtfelder ausgefüllt sind
        if (terminname.isEmpty() || beschreibung.isEmpty() || datum.isEmpty()) {
            Toast.makeText(TerminErstellen.this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
            return; // Speichern abbrechen, falls etwas fehlt
        }

        // Überprüfen, ob ein Benutzer ausgewählt wurde
        Benutzer ausgewählterBenutzer = (Benutzer) spinnerBenutzer.getSelectedItem();
        if (ausgewählterBenutzer == null) {
            Toast.makeText(this, "Bitte eine Person auswählen!", Toast.LENGTH_SHORT).show();
            return; // Speichern abbrechen, falls kein Benutzer ausgewählt wurde
        }

        // Einen neuen Termin mit einer eindeutigen ID erstellen
        String terminId = UUID.randomUUID().toString(); // Zufällige UUID für den Termin
        Termin neuerTermin = new Termin(
                terminId,
                terminname,
                datum,
                uhrzeit,
                wiederholung,
                beschreibung,
                false // Standardmäßig ist der Termin nicht gelöscht
        );

        // Datenbankoperationen im Hintergrund-Thread ausführen
        new Thread(() -> {
            // Termin in die Datenbank einfügen
            terminDao.insertTermin(neuerTermin);

            // Verknüpfung zwischen Benutzer und Termin speichern
            BenutzerTermin benutzerTermin = new BenutzerTermin(ausgewählterBenutzer.getBenutzerId(), terminId);
            BenutzerTerminDao benutzerTerminDao = DatabaseManager.getDatabase(this).benutzerTermineDao();
            benutzerTerminDao.insertTermin(benutzerTermin);

            // UI-Feedback nach erfolgreicher Speicherung
            runOnUiThread(() -> {
                Toast.makeText(this, "Termin erstellt und Person zugewiesen!", Toast.LENGTH_SHORT).show();
                finish(); // Activity schließen und zur vorherigen Ansicht zurückkehren
            });
        }).start();
    }

    /**
     * Lädt alle aktiven Benutzer aus der Datenbank und fügt sie in den Spinner ein.
     */
    private void loadBenutzerInSpinner() {
        // Datenbankzugriff im Hintergrund-Thread
        new Thread(() -> {
            // Liste aller aktiven Benutzer aus der Datenbank abrufen
            List<Benutzer> benutzerListe = benutzerDao.getAllActiveBenutzer();

            // Benutzerliste im UI-Thread in den Spinner einfügen
            runOnUiThread(() -> {
                // ArrayAdapter für die Benutzerliste erstellen
                ArrayAdapter<Benutzer> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, benutzerListe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBenutzer.setAdapter(adapter); // Adapter dem Spinner zuweisen
            });
        }).start();
    }
}
