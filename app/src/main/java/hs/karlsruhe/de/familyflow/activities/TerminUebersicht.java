package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import hs.karlsruhe.de.familyflow.data.dao.TerminDao;
import hs.karlsruhe.de.familyflow.data.entity.Aufgabe;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

public class TerminUebersicht extends AppCompatActivity {

    private ArrayList<String> termineListe; // Liste zur Anzeige der Terminbezeichnung
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termineListe);
        listViewTermine.setAdapter(adapter);

        // Aufgaben aus der Datenbank laden
        loadTermine();

        // Item-Klick: Navigiere zu Details
        listViewTermine.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, TerminDetails.class);
            intent.putExtra("terminId", termineListe.get(position)); // Terminnummer übergeben
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
                for (Termin termin : termine) {
                    termineListe.add(termin.getTerminname());
                }
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    // Termine nach Datum sortieren
    private void sortiereNachDatum() {
        new Thread(() -> {
            List<Termin> termine = terminDao.getAllActiveTermine();
            Collections.sort(termine, new Comparator<Termin>() {
                @Override
                public int compare(Termin o1, Termin o2) {
                    // Beispiel: Datum in lexikographischer Reihenfolge vergleichen
                    return o1.getDatum().compareTo(o2.getDatum());
                }
            });
            runOnUiThread(() -> {
                termineListe.clear();
                for (Termin termin : termine) {
                    termineListe.add(termin.getTerminname());
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
            Collections.sort(termine, new Comparator<Termin>() {
                @Override
                public int compare(Termin o1, Termin o2) {
                    // Alphabetische Reihenfolge nach Terminname
                    return o1.getTerminname().compareTo(o2.getTerminname());
                }
            });
            runOnUiThread(() -> {
                termineListe.clear();
                for (Termin termin : termine) {
                    termineListe.add(termin.getTerminname());
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(TerminUebersicht.this, "Termine alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
