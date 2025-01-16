package hs.karlsruhe.de.familyflow.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

/**
 * Die Pinnwand:
 * Beinhaltet die Navigation zu den anderen Activities und den Inhalt der Pinnwand
 */
@SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "SimpleDateFormat"})
public class Pinnwand extends AppCompatActivity {

    /**
     * Wird beim Laden der Pinnwand ausgeführt und initialisiert die View
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinnwand);
        InitialisiereClickHandler(findViewById(R.id.Pinnwand));
        InitialisiereEventAnzeige();
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

        //initialisiere Einstellungen Button
        ImageButton settingsButton = findViewById(R.id.einstellungen);
        settingsButton.setOnClickListener(v -> ZuEinstellungen());
    }

    /**
     * Initialisiert die Anzeige des nächsten Events, oder wenn keines existiert mit einer Standardanzeige
     */
    public void InitialisiereEventAnzeige() {
        // Asynchronen Task starten, um die Datenbank im Hintergrund zu laden
        new Thread(() -> {
            AppDatabase db = DatabaseManager.getDatabase(this);
            Termin termin = db.terminDao().getNextTermin();
            Benutzer benutzer = null;

            // Benutzerprofilbild laden
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String userId = sharedPreferences.getString("user_id", null); // Benutzer-ID abrufen
            if (userId != null) {
                benutzer = db.benutzerDao().findBenutzerById(userId);
            }

            Benutzer finalBenutzer = benutzer;

            runOnUiThread(() -> {
                TextView eventTitleView = findViewById(R.id.eventTitle);
                TextView eventTimeView = findViewById(R.id.eventTime);
                ImageView eventImageView = findViewById(R.id.eventParticipants);

                //befülle die Terminansicht auf der Pinnwand
                if (termin != null) {
                    //Beschreibung setzen
                    eventTitleView.setText(termin.getTerminname());
                    eventTimeView.setText(termin.getDatum() + " " + termin.getUhrzeit());

                    //Avatarbildchen setzen
                    //eventImageView.setImageDrawable(getResources().getDrawable(R.drawable.defaultavatar));
                } else {
                    //Fallback, falls noch keine Termine existieren
                    eventTitleView.setText("zurzeit gibt es keine anstehenden Termine");
                    //Uhrzeit und Datum setzen
                    SimpleDateFormat datumsFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    eventTimeView.setText(datumsFormat.format(Calendar.getInstance().getTime()));
                }

                // Profilbild setzen
                if (finalBenutzer != null && finalBenutzer.getImageProfil() != null) {
                    Glide.with(this)
                            .load(finalBenutzer.getImageProfil())
                            .placeholder(R.drawable.defaultavatar) // Platzhalter-Bild
                            .into(eventImageView);
                } else {
                    eventImageView.setImageDrawable(getResources().getDrawable(R.drawable.defaultavatar));
                }
            });
        }).start(); // Startet den Thread
    }


    /**
     * lädt die AufgabeActivity, nachdem der Aufgaben Knopf gedrückt wurde
     */
    private void ZuAufgabenUebersicht() {
        Intent intent = new Intent(Pinnwand.this, AufgabeUebersicht.class);
        startActivity(intent);
    }

    /**
     * lädt die TerminActivity, nachdem der Aufgaben Knopf gedrückt wurde
     */
    private void ZuTerminUebersicht() {
        Intent intent = new Intent(Pinnwand.this, TerminUebersicht.class);
        startActivity(intent);
    }

    /**
     * lädt die EinstellungenActivity, nachdem der Einstellungen Knopf gedrückt wurde
     */
    private void ZuEinstellungen() {
        Intent intent = new Intent(Pinnwand.this, Einstellungen.class);
        startActivity(intent);
    }
}
