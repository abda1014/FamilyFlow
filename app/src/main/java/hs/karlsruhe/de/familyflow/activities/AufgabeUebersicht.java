package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.dao.AufgabeDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;

public class AufgabeUebersicht extends AppCompatActivity {

    private ArrayList<String> aufgabenListe; // Liste zur Anzeige der Aufgabebezeichnung
    private ArrayList<String> aufgabenIdListe; // Liste zur Speicherung der Aufgabe-IDs
    private ArrayAdapter<String> adapter;
    private AufgabeDao aufgabeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_uebersicht);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        aufgabeDao = db.aufgabeDao();

        // Zurück-Button
        Button buttonZurueckPinnwand = findViewById(R.id.buttonZurueckPinnwand);
        buttonZurueckPinnwand.setOnClickListener(v -> finish()); // Zurück zur vorherigen Activity

        ListView listViewAufgaben = findViewById(R.id.listViewAufgaben);

        aufgabenListe = new ArrayList<>();
        aufgabenIdListe = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aufgabenListe);
        listViewAufgaben.setAdapter(adapter);

        // Aufgaben aus der Datenbank laden
        loadAufgaben();

        // Item-Klick: Navigiere zu Details
        listViewAufgaben.setOnItemClickListener((parent, view, position, id) -> {
            String aufgabeId = aufgabenIdListe.get(position); // Die richtige Aufgabe-ID abrufen
            Intent intent = new Intent(this, AufgabeDetails.class);
            intent.putExtra("aufgabeId", aufgabeId); // Aufgabe-ID übergeben
            startActivity(intent);
        });

        // Button "Neuer Aufgabe" klickbar machen
        Button buttonNeueAufgabe = findViewById(R.id.button_neue_aufgabe);
        buttonNeueAufgabe.setOnClickListener(v -> {
            // Navigiere zu AufgabeErstellen
            Intent intent = new Intent(AufgabeUebersicht.this, AufgabeErstellen.class);
            startActivity(intent);
        });

        // Button für Sortierung nach Datum
        Button buttonSortiereDatum = findViewById(R.id.buttonSortiereDatum);
        buttonSortiereDatum.setOnClickListener(v -> sortiereNachFaelligkeitsdatum());

        // Button für Sortierung nach Alphabet
        Button buttonSortiereAlphabetisch = findViewById(R.id.buttonSortiereAlphabetisch);
        buttonSortiereAlphabetisch.setOnClickListener(v -> sortiereAlphabetisch());
    }

    private void loadAufgaben() {
        new Thread(() -> {
            List<Aufgabe> aufgaben = aufgabeDao.getAllActiveAufgaben();
            runOnUiThread(() -> {
                aufgabenListe.clear();
                aufgabenIdListe.clear(); // IDs löschen, bevor sie neu geladen werden

                for (Aufgabe aufgabe : aufgaben) {
                    aufgabenListe.add(aufgabe.getAufgabenbezeichnung()); // Namen hinzufügen
                    aufgabenIdListe.add(aufgabe.getAufgabeId()); // IDs hinzufügen
                }

                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    // Aufgaben nach Datum sortieren
    private void sortiereNachFaelligkeitsdatum() {
        new Thread(() -> {
            List<Aufgabe> aufgaben = aufgabeDao.getAllActiveAufgaben();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(aufgaben, Comparator.comparing(Aufgabe::getFaelligkeitsdatum));
            }
            runOnUiThread(() -> {
                aufgabenListe.clear();
                aufgabenIdListe.clear();

                for (Aufgabe aufgabe : aufgaben) {
                    aufgabenListe.add(aufgabe.getAufgabenbezeichnung());
                    aufgabenIdListe.add(aufgabe.getAufgabeId());
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(AufgabeUebersicht.this, "Aufgaben nach Datum sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    // Aufgaben alphabetisch sortieren
    private void sortiereAlphabetisch() {
        new Thread(() -> {
            List<Aufgabe> aufgaben = aufgabeDao.getAllActiveAufgaben();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(aufgaben, Comparator.comparing(Aufgabe::getAufgabenbezeichnung));
            }
            runOnUiThread(() -> {
                aufgabenListe.clear();
                aufgabenIdListe.clear();

                for (Aufgabe aufgabe : aufgaben) {
                    aufgabenListe.add(aufgabe.getAufgabenbezeichnung());
                    aufgabenIdListe.add(aufgabe.getAufgabeId());
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(AufgabeUebersicht.this, "Aufgaben alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
