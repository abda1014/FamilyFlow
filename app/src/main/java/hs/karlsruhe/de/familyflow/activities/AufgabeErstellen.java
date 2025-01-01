package hs.karlsruhe.de.familyflow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.de.familyflow.R;

public class AufgabeErstellen extends AppCompatActivity {

    private EditText etAufgabenbezeichnung, etStatus, etFaelligkeitsdatum, etNotiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_erstellen);

        // Views initialisieren
        etAufgabenbezeichnung = findViewById(R.id.aufgabenbezeichnung);
        etStatus = findViewById(R.id.status);
        etFaelligkeitsdatum = findViewById(R.id.faelligkeitsdatum);
        etNotiz = findViewById(R.id.notiz);
        Button btnSpeichern = findViewById(R.id.save_button);

        // Klick-Listener für den Speichern-Button
        btnSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aufgabenbezeichnung = etAufgabenbezeichnung.getText().toString();
                String status = etStatus.getText().toString();
                String faelligkeitsdatum = etFaelligkeitsdatum.getText().toString();
                String notiz = etNotiz.getText().toString();

                if (aufgabenbezeichnung.isEmpty() || status.isEmpty() || faelligkeitsdatum.isEmpty()) {
                    Toast.makeText(AufgabeErstellen.this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                } else {
                    // Speichern der Aufgabe (hier Platzhalter)
                    Toast.makeText(AufgabeErstellen.this, "Aufgabe erstellt!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
