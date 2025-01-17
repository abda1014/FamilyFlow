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
 * Die Pinnwand-Aktivität zeigt die Übersicht von Ereignissen und ermöglicht die Navigation zu verschiedenen Bereichen der App.
 * Beinhaltet die Navigation zu anderen Aktivitäten (z. B. Aufgaben, Termine, Einstellungen)
 * und zeigt Informationen zum nächsten anstehenden Event oder eine Standardanzeige.
 */
@SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "SimpleDateFormat"})
public class Pinnwand extends AppCompatActivity {

    /**
     * Wird beim Laden der Pinnwand-Aktivität ausgeführt. Initialisiert die View und ruft Methoden zum Setup der Interaktivität auf.
     * @param savedInstanceState Falls die Aktivität nach einer vorherigen Schließung neu initialisiert wird,
     *                             enthält dieser Bundle die zuletzt gespeicherten Daten aus {@link #onSaveInstanceState}. Andernfalls ist dieser Wert null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinnwand);

        // Initialisiert die Click-Handler für die Navigationsbuttons
        InitialisiereClickHandler(findViewById(R.id.Pinnwand));

        // Initialisiert die Anzeige des nächsten Events
        InitialisiereEventAnzeige();
    }

    /**
     * Initialisiert die Click-Handler für die Buttons auf der Pinnwand,
     * um zur entsprechenden Activity zu navigieren.
     * @param view Die Pinnwand View, die das Click Event erhalten hat.
     */
    public void InitialisiereClickHandler(View view) {
        // Initialisiere den Button für die Aufgabenübersicht und setze den OnClickListener
        Button aufgabenButton = findViewById(R.id.AufgabenButton);
        aufgabenButton.setOnClickListener(v -> ZuAufgabenUebersicht());

        // Initialisiere den Button für die Termineübersicht und setze den OnClickListener
        Button termineButton = findViewById(R.id.TermineButton);
        termineButton.setOnClickListener(v -> ZuTerminUebersicht());

        // Initialisiere den Button für die Einstellungen und setze den OnClickListener
        ImageButton settingsButton = findViewById(R.id.einstellungen);
        settingsButton.setOnClickListener(v -> ZuEinstellungen());
    }

    /**
     * Initialisiert die Anzeige des nächsten anstehenden Events. Falls kein Termin vorhanden ist,
     * wird eine Standardanzeige angezeigt.
     * Die Daten werden asynchron aus der Datenbank geladen.
     */
    public void InitialisiereEventAnzeige() {
        // Starte einen asynchronen Task, um Daten aus der Datenbank im Hintergrund zu laden
        new Thread(() -> {
            AppDatabase db = DatabaseManager.getDatabase(this);
            Termin termin = db.terminDao().getNextTermin();  // Holen des nächsten Termins
            Benutzer benutzer = null;

            // Lade das Benutzerprofilbild, falls der Benutzer angemeldet ist
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String userId = sharedPreferences.getString("user_id", null);  // Abrufen der Benutzer-ID
            if (userId != null) {
                benutzer = db.benutzerDao().findBenutzerById(userId);  // Suche nach dem Benutzer in der DB
            }

            Benutzer finalBenutzer = benutzer;

            // Update der UI im Hauptthread
            runOnUiThread(() -> {
                TextView eventTitleView = findViewById(R.id.eventTitle);
                TextView eventTimeView = findViewById(R.id.eventTime);
                ImageView eventImageView = findViewById(R.id.eventParticipants);

                // Wenn ein Termin vorhanden ist, zeige die Details an
                if (termin != null) {
                    eventTitleView.setText(termin.getTerminname());  // Setze den Titel des Events
                    eventTimeView.setText(termin.getDatum() + " " + termin.getUhrzeit());  // Setze das Datum und die Uhrzeit des Events
                } else {
                    // Wenn kein Termin vorhanden ist, zeige eine Standardnachricht an
                    eventTitleView.setText("Zurzeit gibt es keine anstehenden Termine");

                    // Setze das aktuelle Datum und die Uhrzeit
                    SimpleDateFormat datumsFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    eventTimeView.setText(datumsFormat.format(Calendar.getInstance().getTime()));
                }

                // Setze das Profilbild des Benutzers, falls vorhanden
                if (finalBenutzer != null && finalBenutzer.getImageProfil() != null) {
                    Glide.with(this)
                            .load(finalBenutzer.getImageProfil())  // Lade das Profilbild des Benutzers
                            .placeholder(R.drawable.defaultavatar)  // Zeige ein Platzhalterbild, falls kein Bild vorhanden ist
                            .into(eventImageView);  // Setze das Bild in die ImageView
                } else {
                    // Fallback auf das Standard-Avatar-Bild, falls kein Profilbild vorhanden ist
                    eventImageView.setImageDrawable(getResources().getDrawable(R.drawable.defaultavatar));
                }
            });
        }).start(); // Startet den Hintergrund-Thread zum Laden der Daten
    }

    /**
     * Navigiert zur Aufgabenübersicht Activity, wenn der Aufgaben-Button gedrückt wird.
     */
    private void ZuAufgabenUebersicht() {
        Intent intent = new Intent(Pinnwand.this, AufgabeUebersicht.class);
        startActivity(intent);  // Startet die Aufgabenübersicht-Aktivität
    }

    /**
     * Navigiert zur Terminübersicht Activity, wenn der Termine-Button gedrückt wird.
     */
    private void ZuTerminUebersicht() {
        Intent intent = new Intent(Pinnwand.this, TerminUebersicht.class);
        startActivity(intent);  // Startet die Terminübersicht-Aktivität
    }

    /**
     * Navigiert zur Einstellungen Activity, wenn der Einstellungen-Button gedrückt wird.
     */
    private void ZuEinstellungen() {
        Intent intent = new Intent(Pinnwand.this, Einstellungen.class);
        startActivity(intent);  // Startet die Einstellungen-Aktivität
    }
}
