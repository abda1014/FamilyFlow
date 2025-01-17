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

    private ListView listViewAufgaben;
    private AufgabeDao aufgabeDao;
    private List<Aufgabe> aufgabenListe;
    private AufgabeAdapter aufgabeAdapter;

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

        // Zurück-Button
        buttonZurueckPinnwand.setOnClickListener(v -> finish());

        // Button "Neuer Aufgabe" klickbar machen
        buttonNeueAufgabe.setOnClickListener(v -> {
            Intent intent = new Intent(AufgabeUebersicht.this, AufgabeErstellen.class);
            startActivity(intent);
        });

        // Button für Sortierung nach Datum
        buttonSortiereDatum.setOnClickListener(v -> sortiereNachFaelligkeitsdatum());

        // Button für Sortierung nach Alphabet
        buttonSortiereAlphabetisch.setOnClickListener(v -> sortiereAlphabetisch());

        // Aufgaben aus der Datenbank laden
        loadAufgaben();

        // Item-Klick: Navigiere zu Details
        listViewAufgaben.setOnItemClickListener((parent, view, position, id) -> {
            Aufgabe aufgabe = aufgabenListe.get(position);
            Intent intent = new Intent(this, AufgabeDetails.class);
            intent.putExtra("aufgabeId", aufgabe.getAufgabeId());
            startActivity(intent);
        });
    }

    private void loadAufgaben() {
        new Thread(() -> {
            aufgabenListe = aufgabeDao.getAllActiveAufgaben();
            runOnUiThread(() -> {
                aufgabeAdapter = new AufgabeAdapter(this, aufgabenListe);
                listViewAufgaben.setAdapter(aufgabeAdapter);
            });
        }).start();
    }

    private void sortiereNachFaelligkeitsdatum() {
        if (aufgabenListe == null) return;

        new Thread(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(aufgabenListe, Comparator.comparing(Aufgabe::getFaelligkeitsdatum));
            }
            runOnUiThread(() -> {
                aufgabeAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Aufgaben nach Datum sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    private void sortiereAlphabetisch() {
        if (aufgabenListe == null) return;

        new Thread(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(aufgabenListe, Comparator.comparing(Aufgabe::getAufgabenbezeichnung));
            }
            runOnUiThread(() -> {
                aufgabeAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Aufgaben alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
