package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

public class TerminDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details);

        // Beispiel: Hole den Termin aus dem Intent
        String termin = getIntent().getStringExtra("termin");

        TextView textView = findViewById(R.id.details_text);
        textView.setText(termin); // Zeige die Details
        InitialisiereClickHandler(findViewById(R.id.TerminDetails));
    }
    /**
     * ClickHandler für die Buttons von AufgabeDetails um zu den anderen Activities zu navigieren
     * @param view Die AufgabeDetails View, die das Click Event erhalten hat
     */
    public void InitialisiereClickHandler(View view) {
        //initialisiere Aufgaben Button
        Button zurueckTerminUebersicht = findViewById(R.id.buttonZurueckTerminUebersicht);
        zurueckTerminUebersicht.setOnClickListener(v -> ZuTerminUebersicht());

        //initialisiere Termine Button
        Button terminBearbeiten = findViewById(R.id.buttonTerminBearbeiten);
        terminBearbeiten.setOnClickListener(v -> ZuTerminBearbeiten());
    }

    private void ZuTerminUebersicht() {
        Intent intent = new Intent(TerminDetails.this, TerminUebersicht.class);
        startActivity(intent);
    }

    /**
     * lädt die TerminActivity, nachdem der Aufgaben Knopf gedrückt wurde
     */
    private void ZuTerminBearbeiten() {
        Intent intent = new Intent(TerminDetails.this, TerminErstellen.class);
        startActivity(intent);
    }
}
