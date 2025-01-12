package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

public class AufgabeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_details);

        // Beispiel: Hole die Aufgabe aus dem Intent
        String aufgabe = getIntent().getStringExtra("aufgabe");

        TextView textView = findViewById(R.id.details_text);
        textView.setText(aufgabe); // Zeige die Details
        InitialisiereClickHandler(findViewById(R.id.AufgabeDetails));
    }
    /**
     * ClickHandler für die Buttons von AufgabeDetails um zu den anderen Activities zu navigieren
     * @param view Die AufgabeDetails View, die das Click Event erhalten hat
     */
    public void InitialisiereClickHandler(View view) {
        //initialisiere Aufgaben Button
        Button zurueckAufgabeUebersicht = findViewById(R.id.buttonZurueckAufgabeUebersicht);
        zurueckAufgabeUebersicht.setOnClickListener(v -> ZuAufgabeUebersicht());

        //initialisiere Termine Button
        Button aufgabeBearbeiten = findViewById(R.id.buttonAufgabeBearbeiten);
        aufgabeBearbeiten.setOnClickListener(v -> ZuAufgabeBearbeiten());
    }

    private void ZuAufgabeUebersicht() {
        Intent intent = new Intent(AufgabeDetails.this, AufgabeUebersicht.class);
        startActivity(intent);
    }

    /**
     * lädt die TerminActivity, nachdem der Aufgaben Knopf gedrückt wurde
     */
    private void ZuAufgabeBearbeiten() {
        Intent intent = new Intent(AufgabeDetails.this, AufgabeErstellen.class);
        startActivity(intent);
    }
}
