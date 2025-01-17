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

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerAufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.BenutzerAufgaben;

public class AufgabeDetails extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;
    private Button btnSpeichern, btnLoeschen;
    private AufgabeDao aufgabeDao; // Data Access Object für Aufgaben
    private String aufgabeId; // ID der Aufgabe, die bearbeitet wird
    private Spinner spinnerBenutzer; // Dropdown-Menü zur Auswahl eines Benutzers
    private BenutzerDao benutzerDao; // Data Access Object für Benutzer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_details);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();
        benutzerDao = db.benutzerDao();

        // Intent-Daten abrufen (Aufgaben-ID von der vorherigen Activity)
        aufgabeId = getIntent().getStringExtra("aufgabeId");

        // Views aus dem Layout initialisieren
        etAufgabenbezeichnung = findViewById(R.id.editTextAufgabenbezeichnung);
        etStatus = findViewById(R.id.editTextStatus);
        etFaelligkeitsdatum = findViewById(R.id.editTextFaelligkeitsdatum);
        etNotiz = findViewById(R.id.editTextNotiz);
        spinnerBenutzer = findViewById(R.id.spinnerBenutzer);
        btnSpeichern = findViewById(R.id.buttonAufgabeSpeichern);
        btnLoeschen = findViewById(R.id.buttonAufgabeLoeschen);

        // Aufgabe laden und die Details in die Eingabefelder füllen
        loadAufgabeDetails();

        // Benutzerliste in den Spinner laden
        loadBenutzerInSpinner();

        // Speichern-Button: Speichert Änderungen an der Aufgabe
        btnSpeichern.setOnClickListener(v -> saveChanges());

        // Löschen-Button: Führt eine weiche Löschung (Soft-Delete) der Aufgabe durch
        btnLoeschen.setOnClickListener(v -> softDeleteAufgabe());

        // Zurück-Button: Beendet die aktuelle Activity und kehrt zur vorherigen zurück
        Button buttonZurueck = findViewById(R.id.buttonZurueck);
        buttonZurueck.setOnClickListener(v -> finish());
    }

    /**
     * Lädt die Details der aktuellen Aufgabe basierend auf der übergebenen ID.
     * Die Daten werden aus der Datenbank abgerufen und in die Eingabefelder eingefügt.
     */
    private void loadAufgabeDetails() {
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe == null) {
                // Falls die Aufgabe nicht gefunden wurde, wird eine Fehlermeldung angezeigt und die Activity geschlossen
                runOnUiThread(() -> {
                    Toast.makeText(this, "Aufgabe nicht gefunden!", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            // Benutzer-Aufgabe-Verknüpfung aus der Datenbank abrufen
            BenutzerAufgabeDao benutzerAufgabeDao = DatabaseManager.getDatabase(this).benutzerAufgabenDao();
            BenutzerAufgaben benutzerAufgabe = benutzerAufgabeDao.getBenutzerWithAufgaben(aufgabeId);

            // Eingabefelder mit den Daten der Aufgabe und Benutzerverknüpfung befüllen
            runOnUiThread(() -> {
                etAufgabenbezeichnung.setText(aufgabe.getAufgabenbezeichnung());
                etStatus.setText(aufgabe.getStatus());
                etFaelligkeitsdatum.setText(aufgabe.getFaelligkeitsdatum());
                etNotiz.setText(aufgabe.getNotiz());

                // Falls ein Benutzer mit der Aufgabe verknüpft ist, diesen im Spinner auswählen
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

    /**
     * Speichert die Änderungen der aktuellen Aufgabe in der Datenbank.
     * Die eingegebenen Werte aus den Eingabefeldern werden validiert und aktualisiert.
     */
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

        // Änderungen in der Datenbank speichern
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe != null) {
                // Aufgabe mit neuen Werten aktualisieren
                aufgabe.setAufgabenbezeichnung(aufgabenbezeichnung);
                aufgabe.setStatus(status);
                aufgabe.setFaelligkeitsdatum(faelligkeitsdatum);
                aufgabe.setNotiz(notiz);
                aufgabeDao.updateAufgabe(aufgabe);

                // Benutzer-Aufgabe-Verknüpfung aktualisieren (optional, falls Spinner geändert wird)

                runOnUiThread(() -> {
                    Toast.makeText(this, "Änderungen gespeichert!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    /**
     * Markiert die Aufgabe in der Datenbank als "gelöscht" (Soft-Delete).
     * Die Aufgabe bleibt in der Datenbank, ist jedoch für Benutzer unsichtbar.
     */
    private void softDeleteAufgabe() {
        new Thread(() -> {
            Aufgabe aufgabe = aufgabeDao.findAufgabeById(aufgabeId);
            if (aufgabe != null) {
                aufgabeDao.softDeleteAufgabe(aufgabeId);

                // Benutzer über den erfolgreichen Löschvorgang informieren
                runOnUiThread(() -> {
                    Toast.makeText(this, "Aufgabe gelöscht!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    /**
     * Lädt die Liste der Benutzer in den Spinner (Dropdown-Menü).
     * Nur aktive Benutzer werden angezeigt.
     */
    private void loadBenutzerInSpinner() {
        new Thread(() -> {
            // Liste der aktiven Benutzer aus der Datenbank abrufen
            List<Benutzer> benutzerListe = benutzerDao.getAllActiveBenutzer();

            // Benutzerliste im Spinner anzeigen
            runOnUiThread(() -> {
                ArrayAdapter<Benutzer> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, benutzerListe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBenutzer.setAdapter(adapter);
            });
        }).start();
    }
}
