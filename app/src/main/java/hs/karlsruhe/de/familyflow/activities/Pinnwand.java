package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import hs.karlsruhe.de.familyflow.R;

/**
 * Die Pinnwand:
 * Beinhaltet die Navigation zu den anderen Activities und den Inhalt der Pinnwand
 */
public class Pinnwand extends AppCompatActivity {

    /**
     * Wird beim Laden der Pinnwand ausgeführt und initialisiert die View
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinnwand);
        InitialisiereClickHandler(findViewById(R.id.Pinnwand));
    }

    /**
     * ClickHandler für die Buttons auf der Pinnwand um zu den anderen Activities zu navigieren
     * @param view Die Pinnwand View, die das Click Event erhalten hat
     */
    public void InitialisiereClickHandler(View view) {
        //initialisiere Aufgaben Button
        Button aufgabenButton = findViewById(R.id.AufgabenButton);
        aufgabenButton.setOnClickListener(v -> ZuAufgabenUebersicht());

        //initialisiere Termine Button
        Button termineButton = findViewById(R.id.TermineButton);
        termineButton.setOnClickListener(v -> ZuTerminUebersicht());
    }

    /**
     * lädt die AufgabeActivity, nachdem der Aufgaben Knopf gedrückt wurde
     */
    private void ZuAufgabenUebersicht() {
        Intent intent = new Intent(Pinnwand.this, AufgabeActivity.class);
        startActivity(intent);
    }

    /**
     * lädt die TerminActivity, nachdem der Aufgaben Knopf gedrückt wurde
     */
    private void ZuTerminUebersicht() {
        Intent intent = new Intent(Pinnwand.this, TerminActivity.class);
        startActivity(intent);
    }
}
