package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hs.karlsruhe.de.familyflow.R;

public class TerminPersonenZuweisen extends AppCompatActivity {

    private Spinner spPersonen;
    private ArrayList<String> zugewiesenePersonen;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personen_zuweisen);

        // Views initialisieren
        spPersonen = findViewById(R.id.spPersonen);
        Button btnHinzufuegen = findViewById(R.id.btnHinzufuegen);
        Button btnSpeichern = findViewById(R.id.btnSpeichern);
        ListView lvPersonenListe = findViewById(R.id.personen_liste);

        // Daten initialisieren
        zugewiesenePersonen = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, zugewiesenePersonen);
        lvPersonenListe.setAdapter(adapter);

        // Spinner-Daten (Beispielwerte)
        ArrayList<String> personenListe = new ArrayList<>();
        personenListe.add("Person 1");
        personenListe.add("Person 2");
        personenListe.add("Person 3");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, personenListe);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPersonen.setAdapter(spinnerAdapter);

        // Klick-Listener für Hinzufügen-Button
        btnHinzufuegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ausgewaehltePerson = spPersonen.getSelectedItem().toString();
                if (!zugewiesenePersonen.contains(ausgewaehltePerson)) {
                    zugewiesenePersonen.add(ausgewaehltePerson);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(TerminPersonenZuweisen.this, "Person hinzugefügt", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TerminPersonenZuweisen.this, "Person bereits zugewiesen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Klick-Listener für Speichern-Button
        btnSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speichern der zugewiesenen Personen (Platzhalter)
                Toast.makeText(TerminPersonenZuweisen.this, "Personen erfolgreich zugewiesen", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
