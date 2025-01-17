package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import hs.karlsruhe.de.familyflow.data.TerminAdapter;

public class TerminUebersicht extends AppCompatActivity {

    private ArrayList<Termin> termineListe; // Liste für Terminobjekte
    private TerminAdapter adapter;
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
        adapter = new TerminAdapter(this, termineListe);
        listViewTermine.setAdapter(adapter);

        // Termine aus der Datenbank laden
        loadTermine();

        // Item-Klick: Navigiere zu Details
        listViewTermine.setOnItemClickListener((parent, view, position, id) -> {
            Termin termin = termineListe.get(position); // Das Terminobjekt abrufen
            String terminId = termin.getTerminId(); // Termin-ID abrufen
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
                termineListe.addAll(termine); // Alle Termine zur Liste hinzufügen
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
                termineListe.addAll(termine); // Alle sortierten Termine zur Liste hinzufügen
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
                termineListe.addAll(termine); // Alle alphabetisch sortierten Termine zur Liste hinzufügen
                adapter.notifyDataSetChanged();
                Toast.makeText(TerminUebersicht.this, "Termine alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
