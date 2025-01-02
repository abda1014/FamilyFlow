package hs.karlsruhe.de.familyflow.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.entity.Termin;

/**
 * Die Pinnwand:
 * Beinhaltet die Navigation zu den anderen Activities und den Inhalt der Pinnwand
 */
@SuppressLint("SetTextI18n")
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
    }

    /**
     * Initialisiert die Anzeige des nächsten Events, oder wenn keines existiert mit einer Standardanzeige
     */
    public void InitialisiereEventAnzeige(){
        AppDatabase db = DatabaseManager.getDatabase(this);
        Termin termin = db.terminDao().getNextTermin();

        TextView eventTitleView = findViewById(R.id.eventTitle);
        TextView eventTimeView = findViewById(R.id.eventTime);
        ImageView eventImageView = findViewById(R.id.eventParticipants);

        //befülle die Terminansicht auf der Pinnwand
        if (termin != null) {
            //Beschreibung setzen
            eventTitleView.setText(termin.getTerminname());
            eventTimeView.setText(termin.getDatum() + " " + termin.getUhrzeit());

            //Avatarbildchen setzen
            List<String> nutzerIds = db.benutzerTermineDao().getBeteiligteNutzerIds(termin.getTerminId());
            eventImageView.setImageDrawable(createCombinedAvatar(nutzerIds, db));
        } else {
            //Fallback, falls noch keine Termine existieren
            eventTitleView.setText("zurzeit gibt es keine anstehenden Termine");
            eventTimeView.setText(Calendar.getInstance().getTime().toString());
            eventImageView.setImageResource(R.drawable.defaultavatar);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable createCombinedAvatar(List<String> nutzerIds, AppDatabase db) {
        if (nutzerIds != null && !nutzerIds.isEmpty()) {
            return getResources().getDrawable(R.drawable.defaultavatar);
        } else {
            //Fallback wenn keine Avatare vorhanden sind
            return getResources().getDrawable(R.drawable.defaultavatar);
        }
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
