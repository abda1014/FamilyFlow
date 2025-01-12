package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
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

        InitialisiereClickHandler(findViewById(R.id.AufgabeErstellen));

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
    /**
     * ClickHandler für die Buttons von AufgabeErstellen um zu den anderen Activities zu navigieren
     * @param view Die AufgabeErstellen View, die das Click Event erhalten hat
     */
    public void InitialisiereClickHandler(View view) {
        //initialisiere Aufgaben Button
        Button abbruchAufgabeUebersicht = findViewById(R.id.buttonAbbruchAufgabeUebersicht);
        abbruchAufgabeUebersicht.setOnClickListener(v -> ZuAufgabeUebersicht());

        //initialisiere Termine Button
        Button aufgabeDetails = findViewById(R.id.buttonAufgabeDetails);
        aufgabeDetails.setOnClickListener(v -> ZuAufgabeDetails());

        //initialisiere Termine Button
        Button aufgabePersonenZuweisen = findViewById(R.id.buttonAufgabePersonenZuweisen);
        aufgabePersonenZuweisen.setOnClickListener(v -> ZuAufgabePersonenZuweisen());
    }

    private void ZuAufgabeUebersicht() {
        Intent intent = new Intent(AufgabeErstellen.this, AufgabeUebersicht.class);
        startActivity(intent);
    }

    /**
     * Lädt die AufgabeErstellen Activity, nachdem der Aufgabe erstellen Knopf gedrückt wurde
     */
    private void ZuAufgabeDetails() {
        Intent intent = new Intent(AufgabeErstellen.this, AufgabeDetails.class);
        startActivity(intent);
    }

    private void ZuAufgabePersonenZuweisen() {
        Intent intent = new Intent(AufgabeErstellen.this, PersonenZuweisen.class);
        startActivity(intent);
    }
}
