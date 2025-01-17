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

    private ArrayList<Termin> termineListe; // Liste für alle geladenen Termine
    private TerminAdapter adapter; // Adapter zur Darstellung der Termine in der ListView
    private TerminDao terminDao; // DAO für den Zugriff auf die Termin-Datenbank

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_uebersicht);

        // Room-Datenbank und Termin-DAO initialisieren
        AppDatabase db = DatabaseManager.getDatabase(this);
        terminDao = db.terminDao();

        // Zurück-Button: Schließt die aktuelle Activity und kehrt zur vorherigen zurück
        Button buttonZurueckPinnwand = findViewById(R.id.buttonZurueckPinnwand);
        buttonZurueckPinnwand.setOnClickListener(v -> finish());

        // ListView für die Anzeige der Termine
        ListView listViewTermine = findViewById(R.id.listViewTermine);

        // Initialisierung der Terminliste und des Adapters
        termineListe = new ArrayList<>();
        adapter = new TerminAdapter(this, termineListe);
        listViewTermine.setAdapter(adapter);

        // Termine aus der Datenbank laden und anzeigen
        loadTermine();

        // Klick auf ein Listenelement: Navigiert zur Termin-Detailseite
        listViewTermine.setOnItemClickListener((parent, view, position, id) -> {
            // Das ausgewählte Terminobjekt abrufen
            Termin termin = termineListe.get(position);
            String terminId = termin.getTerminId(); // Termin-ID für die Detailseite
            Intent intent = new Intent(this, TerminDetails.class);
            intent.putExtra("terminId", terminId); // Termin-ID an die nächste Activity übergeben
            startActivity(intent);
        });

        // Button "Neuer Termin": Öffnet die Seite zur Erstellung eines neuen Termins
        Button buttonNeuerTermin = findViewById(R.id.button_neuer_termin);
        buttonNeuerTermin.setOnClickListener(v -> {
            Intent intent = new Intent(TerminUebersicht.this, TerminErstellen.class);
            startActivity(intent);
        });

        // Button "Sortiere nach Datum": Sortiert die Terminliste nach Datum
        Button buttonSortiereDatum = findViewById(R.id.buttonSortiereDatum);
        buttonSortiereDatum.setOnClickListener(v -> sortiereNachDatum());

        // Button "Sortiere alphabetisch": Sortiert die Terminliste nach Namen
        Button buttonSortiereAlphabetisch = findViewById(R.id.buttonSortiereAlphabetisch);
        buttonSortiereAlphabetisch.setOnClickListener(v -> sortiereAlphabetisch());
    }

    // Lade alle aktiven Termine aus der Datenbank
    private void loadTermine() {
        new Thread(() -> {
            // Termine aus der Datenbank abrufen
            List<Termin> termine = terminDao.getAllActiveTermine();
            runOnUiThread(() -> {
                // Terminliste aktualisieren und die Änderungen im Adapter anzeigen
                termineListe.clear();
                termineListe.addAll(termine);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    // Sortiere die Termine nach Datum (aufsteigend)
    private void sortiereNachDatum() {
        new Thread(() -> {
            // Termine aus der Datenbank abrufen
            List<Termin> termine = terminDao.getAllActiveTermine();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // Liste nach Datum sortieren
                Collections.sort(termine, Comparator.comparing(Termin::getDatum));
            }
            runOnUiThread(() -> {
                // Terminliste aktualisieren und die Änderungen im Adapter anzeigen
                termineListe.clear();
                termineListe.addAll(termine);
                adapter.notifyDataSetChanged();
                // Benutzer informieren, dass die Termine sortiert wurden
                Toast.makeText(TerminUebersicht.this, "Termine nach Datum sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    // Sortiere die Termine alphabetisch nach ihrem Namen
    private void sortiereAlphabetisch() {
        new Thread(() -> {
            // Termine aus der Datenbank abrufen
            List<Termin> termine = terminDao.getAllActiveTermine();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // Liste alphabetisch nach Terminname sortieren
                Collections.sort(termine, Comparator.comparing(Termin::getTerminname));
            }
            runOnUiThread(() -> {
                // Terminliste aktualisieren und die Änderungen im Adapter anzeigen
                termineListe.clear();
                termineListe.addAll(termine);
                adapter.notifyDataSetChanged();
                // Benutzer informieren, dass die Termine sortiert wurden
                Toast.makeText(TerminUebersicht.this, "Termine alphabetisch sortiert!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
