package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.AufgabeAdapter;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;

public class AufgabeUebersicht extends AppCompatActivity {

    private ListView listViewAufgaben; // ListView für die Anzeige der Aufgaben
    private AufgabeDao aufgabeDao; // DAO für den Zugriff auf Aufgaben in der Datenbank
    private List<Aufgabe> aufgabenListe; // Liste der geladenen Aufgaben
    private AufgabeAdapter aufgabeAdapter; // Adapter zur Anzeige der Aufgaben in der ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_uebersicht);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();

        // UI-Elemente initialisieren
        listViewAufgaben = findViewById(R.id.listViewAufgaben);
        Button buttonZurueckPinnwand = findViewById(R.id.buttonZurueckPinnwand);
        Button buttonNeueAufgabe = findViewById(R.id.button_neue_aufgabe);
        Button buttonSortiereDatum = findViewById(R.id.buttonSortiereDatum);
        Button buttonSortiereAlphabetisch = findViewById(R.id.buttonSortiereAlphabetisch);

        // Zurück-Button: Beendet die aktuelle Activity
        buttonZurueckPinnwand.setOnClickListener(v -> finish());

        // "Neue Aufgabe"-Button: Öffnet die Activity zum Erstellen einer neuen Aufgabe
        buttonNeueAufgabe.setOnClickListener(v -> {
            Intent intent = new Intent(AufgabeUebersicht.this, AufgabeErstellen.class);
            startActivity(intent);
        });

        // Sortier-Button für das Fälligkeitsdatum
        buttonSortiereDatum.setOnClickListener(v -> sortiereNachFaelligkeitsdatum());

        // Sortier-Button für die alphabetische Reihenfolge
        buttonSortiereAlphabetisch.setOnClickListener(v -> sortiereAlphabetisch());

        // Aufgaben aus der Datenbank laden und anzeigen
        loadAufgaben();

        // Klick auf eine Aufgabe: Öffnet die Detailansicht der Aufgabe
        listViewAufgaben.setOnItemClickListener((parent, view, position, id) -> {
            Aufgabe aufgabe = aufgabenListe.get(position);
            Intent intent = new Intent(this, AufgabeDetails.class);
            intent.putExtra("aufgabeId", aufgabe.getAufgabeId()); // Aufgabe-ID an die nächste Activity übergeben
            startActivity(intent);
        });
    }

    /**
     * Lädt alle aktiven Aufgaben aus der Datenbank und zeigt sie in der ListView an.
     */
    private void loadAufgaben() {
        new Thread(() -> {
            // Lade alle aktiven Aufgaben aus der Datenbank
            aufgabenListe = aufgabeDao.getAllActiveAufgaben();

            // Aktualisiere die UI in der Haupt-Thread
            runOnUiThread(() -> {
                aufgabeAdapter = new AufgabeAdapter(this, aufgabenListe); // Adapter initialisieren
                listViewAufgaben.setAdapter(aufgabeAdapter); // Adapter an die ListView setzen
            });
        }).start();
    }

    /**
     * Sortiert die Aufgaben nach dem Fälligkeitsdatum.
     */
    private void sortiereNachFaelligkeitsdatum() {
        if (aufgabenListe == null) return;

        new Thread(() -> {
            // Aufgabenliste nach Fälligkeitsdatum sortieren (ab API 24)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(aufgabenListe, Comparator.comparing(Aufgabe::getFaelligkeitsdatum));
            }

            // UI aktualisieren
            runOnUiThread(() -> {
                aufgabeAdapter.notifyDataSetChanged(); // Änderungen am Adapter melden
                Toast.makeText(this, "Aufgaben nach Datum sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    /**
     * Sortiert die Aufgaben alphabetisch nach der Aufgabenbezeichnung.
     */
    private void sortiereAlphabetisch() {
        if (aufgabenListe == null) return;

        new Thread(() -> {
            // Aufgabenliste alphabetisch sortieren (ab API 24)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(aufgabenListe, Comparator.comparing(Aufgabe::getAufgabenbezeichnung));
            }

            // UI aktualisieren
            runOnUiThread(() -> {
                aufgabeAdapter.notifyDataSetChanged(); // Änderungen am Adapter melden
                Toast.makeText(this, "Aufgaben alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
