package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

import java.util.ArrayList;
import java.util.Collections;

public class AufgabeUebersicht extends AppCompatActivity {

    private ArrayList<String> aufgabenListe; // Beispiel-Liste
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_uebersicht);

        EditText searchBar = findViewById(R.id.search_bar);
        ListView listView = findViewById(R.id.aufgaben_liste);

        // Beispiel-Aufgabenliste
        aufgabenListe = new ArrayList<>();
        aufgabenListe.add("Aufgabe 1");
        aufgabenListe.add("Aufgabe 2");
        aufgabenListe.add("Aufgabe 3");

        // Adapter initialisieren
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aufgabenListe);
        listView.setAdapter(adapter);

        // Sortierfunktion hinzufügen
        findViewById(R.id.sort_by_date).setOnClickListener(v -> sortByDate());
        findViewById(R.id.sort_alphabetically).setOnClickListener(v -> sortAlphabetically());

        // Item-Klick: Navigiere zu Details
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(this, AufgabeDetails.class);
            intent.putExtra("aufgabe", aufgabenListe.get(position));
            startActivity(intent);
        });

        InitialisiereClickHandler(findViewById(R.id.AufgabeUebersicht));

        // Suche nach Text in der Liste
        searchBar.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void sortByDate() {
        // Beispiel: Sortieren nach Datum (derzeit nur alphabetisch simuliert)
        Collections.sort(aufgabenListe);
        adapter.notifyDataSetChanged();
    }

    private void sortAlphabetically() {
        Collections.sort(aufgabenListe);
        adapter.notifyDataSetChanged();
    }

    /**
     * ClickHandler für die Buttons auf der Aufgabenübersicht um zu den anderen Activities zu navigieren
     * @param view Die Aufgabenübersicht View, die das Click Event erhalten hat
     */
    public void InitialisiereClickHandler(View view) {
        //initialisiere Aufgaben Button
        Button zurueckPinnwand = findViewById(R.id.buttonZurueckPinnwand);
        zurueckPinnwand.setOnClickListener(v -> ZuPinnwand());

        //initialisiere Termine Button
        Button aufgabeErstellen = findViewById(R.id.buttonAufgabeErstellen);
        aufgabeErstellen.setOnClickListener(v -> ZuAufgabeErstellen());
    }

    private void ZuPinnwand() {
        Intent intent = new Intent(AufgabeUebersicht.this, Pinnwand.class);
        startActivity(intent);
    }

    /**
     * Lädt die AufgabeErstellen Activity, nachdem der Aufgabe erstellen Knopf gedrückt wurde
     */
    private void ZuAufgabeErstellen() {
        Intent intent = new Intent(AufgabeUebersicht.this, AufgabeErstellen.class);
        startActivity(intent);
    }
}
