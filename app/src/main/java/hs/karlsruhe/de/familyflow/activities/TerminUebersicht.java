package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

import java.util.ArrayList;
import java.util.Collections;

public class TerminUebersicht extends AppCompatActivity {

    private ArrayList<String> termineListe; // Beispiel-Liste
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_uebersicht);

        EditText searchBar = findViewById(R.id.search_bar);
        ListView listView = findViewById(R.id.termine_liste);

        // Beispiel-Termineliste
        termineListe = new ArrayList<>();
        termineListe.add("Termin 1");
        termineListe.add("Termin 2");
        termineListe.add("Termin 3");

        // Adapter initialisieren
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termineListe);
        listView.setAdapter(adapter);

        // Sortierfunktion hinzufügen
        findViewById(R.id.sort_by_date).setOnClickListener(v -> sortByDate());
        findViewById(R.id.sort_alphabetically).setOnClickListener(v -> sortAlphabetically());

        // Item-Klick: Navigiere zu Details
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(this, TerminDetails.class);
            intent.putExtra("termin", termineListe.get(position));
            startActivity(intent);
        });

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
        Collections.sort(termineListe);
        adapter.notifyDataSetChanged();
    }

    private void sortAlphabetically() {
        Collections.sort(termineListe);
        adapter.notifyDataSetChanged();
    }
}
