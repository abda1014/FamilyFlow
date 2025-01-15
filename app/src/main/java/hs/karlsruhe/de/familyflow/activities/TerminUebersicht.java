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
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminUebersicht extends AppCompatActivity {

    private ArrayList<String> termineListe; // Liste zur Anzeige der Terminbezeichnung
    private ArrayList<String> termineIdListe; // Liste zur Speicherung der Termin-IDs
    private ArrayAdapter<String> adapter;
    private TerminDao terminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_uebersicht);

        // Room-Datenbank und DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();

        // Zurück-Button
        Button buttonZurueckPinnwand = findViewById(R.id.buttonZurueckPinnwand);
        buttonZurueckPinnwand.setOnClickListener(v -> finish()); // Zurück zur vorherigen Activity

        ListView listViewTermine = findViewById(R.id.listViewTermine);

        termineListe = new ArrayList<>();
        termineIdListe = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termineListe);
        listViewTermine.setAdapter(adapter);

        // Termine aus der Datenbank laden
        loadTermine();

        // Item-Klick: Navigiere zu Details
        listViewTermine.setOnItemClickListener((parent, view, position, id) -> {
            String terminId = termineIdListe.get(position); // Die richtige Termin-ID abrufen
            Intent intent = new Intent(this, TerminDetails.class);
            intent.putExtra("terminId", terminId); // Termin-ID übergeben
            startActivity(intent);
        });

        // Button "Neuer Termin" klickbar machen
        Button buttonNeuerTermin = findViewById(R.id.button_neuer_termin);
        buttonNeuerTermin.setOnClickListener(v -> {
            // Navigiere zu TerminErstellen
            Intent intent = new Intent(TerminUebersicht.this, TerminErstellen.class);
            startActivity(intent);
        });

        // Button für Sortierung nach Datum
        Button buttonSortiereDatum = findViewById(R.id.buttonSortiereDatum);
        buttonSortiereDatum.setOnClickListener(v -> sortiereNachDatum());

        // Button für Sortierung nach Alphabet
        Button buttonSortiereAlphabetisch = findViewById(R.id.buttonSortiereAlphabetisch);
        buttonSortiereAlphabetisch.setOnClickListener(v -> sortiereAlphabetisch());
    }

    private void loadTermine() {
        new Thread(() -> {
            List<Termin> termine = terminDao.getAllActiveTermine();
            runOnUiThread(() -> {
                termineListe.clear();
                termineIdListe.clear(); // IDs löschen, bevor sie neu geladen werden

                for (Termin termin : termine) {
                    termineListe.add(termin.getTerminname()); // Namen hinzufügen
                    termineIdListe.add(termin.getTerminId()); // IDs hinzufügen
                }

                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    // Termine nach Datum sortieren
    private void sortiereNachDatum() {
        new Thread(() -> {
            List<Termin> termine = terminDao.getAllActiveTermine();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(termine, Comparator.comparing(Termin::getDatum));
            }
            runOnUiThread(() -> {
                termineListe.clear();
                termineIdListe.clear();

                for (Termin termin : termine) {
                    termineListe.add(termin.getTerminname());
                    termineIdListe.add(termin.getTerminId());
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(TerminUebersicht.this, "Termine nach Datum sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    // Termine alphabetisch sortieren
    private void sortiereAlphabetisch() {
        new Thread(() -> {
            List<Termin> termine = terminDao.getAllActiveTermine();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(termine, Comparator.comparing(Termin::getTerminname));
            }
            runOnUiThread(() -> {
                termineListe.clear();
                termineIdListe.clear();

                for (Termin termin : termine) {
                    termineListe.add(termin.getTerminname());
                    termineIdListe.add(termin.getTerminId());
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(TerminUebersicht.this, "Termine alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
