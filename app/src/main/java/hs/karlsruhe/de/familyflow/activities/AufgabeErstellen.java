package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
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
import hs.karlsruhe.de.familyflow.data.dao.BenutzerAufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;

public class AufgabeErstellen extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;
    private AufgabeDao aufgabeDao; // DAO für den Zugriff auf die Aufgaben-Datenbank
    private Spinner spinnerBenutzer; // Dropdown zur Auswahl eines Benutzers
    private BenutzerDao benutzerDao; // DAO für den Zugriff auf die Benutzer-Datenbank

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_erstellen);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();
        benutzerDao = db.benutzerDao();

        // Views aus dem Layout initialisieren
        etAufgabenbezeichnung = findViewById(R.id.editTextAufgabenbezeichnung);
        etStatus = findViewById(R.id.editTextStatus);
        etFaelligkeitsdatum = findViewById(R.id.editTextFaelligkeitsdatum);
        etNotiz = findViewById(R.id.editTextNotiz);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);

        // Button zur Speicherung der Aufgabe initialisieren
        Button btnSpeichern = findViewById(R.id.buttonAufgabeSpeichern);

        // Benutzerliste laden und im Spinner anzeigen
        loadBenutzerInSpinner();

        // Klick-Listener für den Speichern-Button setzen
        btnSpeichern.setOnClickListener(v -> saveAufgabe());

        // Zurück-Button initialisieren, um zur vorherigen Activity zurückzukehren
        Button buttonZurueck = findViewById(R.id.buttonZurueck);
        buttonZurueck.setOnClickListener(v -> finish());
    }

    /**
     * Speichert eine neue Aufgabe in der Datenbank und weist sie einem Benutzer zu.
     */
    private void saveAufgabe() {
        // Werte aus den Eingabefeldern holen
        String aufgabenbezeichnung = etAufgabenbezeichnung.getText().toString();
        String status = etStatus.getText().toString();
        String faelligkeitsdatum = etFaelligkeitsdatum.getText().toString();
        String notiz = etNotiz.getText().toString();

        // Validierung der Pflichtfelder
        if (aufgabenbezeichnung.isEmpty() || status.isEmpty() || faelligkeitsdatum.isEmpty()) {
            Toast.makeText(AufgabeErstellen.this, "Bitte alle Pflichtfelder ausfüllen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ausgewählten Benutzer aus dem Spinner holen
        Benutzer ausgewählterBenutzer = (Benutzer) spinnerBenutzer.getSelectedItem();
        if (ausgewählterBenutzer == null) {
            Toast.makeText(this, "Bitte eine Person auswählen!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generiere eine eindeutige ID für die neue Aufgabe
        String aufgabeId = UUID.randomUUID().toString();

        // Aufgabe erstellen und initialisieren
        Aufgabe neueAufgabe = new Aufgabe(
                aufgabeId,                  // Eindeutige ID der Aufgabe
                aufgabenbezeichnung,        // Bezeichnung der Aufgabe
                status,                     // Status der Aufgabe
                faelligkeitsdatum,          // Fälligkeitsdatum der Aufgabe
                notiz,                      // Zusätzliche Notizen
                false                       // Aufgabe ist nicht gelöscht (Soft-Delete)
        );

        // Aufgabe und Benutzerzuweisung in der Datenbank speichern
        new Thread(() -> {
            // Aufgabe in die Datenbank einfügen
            aufgabeDao.insertAufgabe(neueAufgabe);

            // Benutzer-Aufgabe-Verknüpfung speichern
            BenutzerAufgaben benutzerAufgabe = new BenutzerAufgaben(ausgewählterBenutzer.getBenutzerId(), aufgabeId);
            BenutzerAufgabeDao benutzerAufgabeDao = DatabaseManager.getDatabase(this).benutzerAufgabenDao();
            benutzerAufgabeDao.insertAufgabe(benutzerAufgabe);

            // Rückmeldung an den Benutzer und Beenden der Activity
            runOnUiThread(() -> {
                Toast.makeText(this, "Aufgabe erstellt und Person zugewiesen!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

    /**
     * Lädt die Liste aller aktiven Benutzer in den Spinner (Dropdown-Menü).
     */
    private void loadBenutzerInSpinner() {
        new Thread(() -> {
            // Aktive Benutzer aus der Datenbank abrufen
            List<Benutzer> benutzerListe = benutzerDao.getAllActiveBenutzer();

            // Adapter erstellen, um die Benutzer im Spinner anzuzeigen
            runOnUiThread(() -> {
                ArrayAdapter<Benutzer> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, benutzerListe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBenutzer.setAdapter(adapter);
            });
        }).start();
    }
}
