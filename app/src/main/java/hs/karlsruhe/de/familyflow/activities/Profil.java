package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;

/**
 * Die Profil-Activity:
 * Zeigt die Profildaten des aktuell angemeldeten Benutzers an.
 */
public class Profil extends AppCompatActivity {

    // ExecutorService wird verwendet, um Hintergrundaufgaben asynchron auszuführen
    private ExecutorService executorService;

    /**
     * Wird beim Starten der Profil-Activity ausgeführt und initialisiert die View.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // ExecutorService für Hintergrundaufgaben initialisieren
        executorService = Executors.newSingleThreadExecutor();

        // Zurück-Button: Initialisiert den Button der zur  vorherigen Activity leitet
        Button buttonZurueckEinstellungen = findViewById(R.id.buttonZurueckEinstellungen);
        buttonZurueckEinstellungen.setOnClickListener(v -> finish()); // Zurück zur vorherigen Activity

        // Lädt die Profildaten des Benutzers aus der Datenbank
        ladeBenutzerProfil();
    }

    /**
     * Lädt die Benutzerdaten aus der Datenbank.
     */
    private void ladeBenutzerProfil() {
        // Benutzer-ID aus den SharedPreferences abrufen
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        // Überprüft ob eine Benutzer-ID vorhanden ist
            executorService.execute(() -> {
                // Datenbankzugriff initialisieren
                AppDatabase db = DatabaseManager.getDatabase(this);
                BenutzerDao benutzerDao = db.benutzerDao();
                Benutzer benutzer = benutzerDao.findBenutzerById(userId);   // Benutzer anhand der  ID finden

                // Logging, um zu überprüfen, ob der Benutzer gefunden wird
                if (benutzer == null) {
                    Log.e("ProfilActivity", "Benutzer nicht gefunden mit ID: " + userId);
                } else {
                    Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname() +"und" + userId);
                }

                // Aktualisiert die Benutzeroberfläche im Hauptthread
                runOnUiThread(() -> {
                    if (benutzer != null) {
                        // Logging für erfolgreiche Benutzerabfrage
                        Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname());

                        // Profilbild-URL abrufen und anzeigen
                        String imageProfilUrl = benutzer.getImageProfil();

                        // Lädt das Profilbild oder ein Standardbild, wenn keine URL vorhanden ist
                        if (imageProfilUrl != null && !imageProfilUrl.isEmpty()) {
                            ImageView imageView = findViewById(R.id.imageViewProfil);
                            Glide.with(this)
                                    .load(imageProfilUrl)
                                    .into(imageView);
                        } else {
                            ImageView imageView = findViewById(R.id.imageViewProfil);
                            Glide.with(this)
                                    .load(R.drawable.defaultavatar) // Standardbild laden
                                    .into(imageView);
                        }

                        // Benutzerinformationen in die entsprechenden TextViews einfügen
                        TextView vornameTextView = findViewById(R.id.textViewVorname);
                        TextView nachnameTextView = findViewById(R.id.textViewNachname);
                        TextView emailTextView = findViewById(R.id.textViewEmail);
                        TextView altersdatumTextView = findViewById(R.id.textViewAltersdatum);

                        vornameTextView.setText(benutzer.getVorname());
                        nachnameTextView.setText(benutzer.getNachname());
                        emailTextView.setText(benutzer.getEmail());
                        altersdatumTextView.setText(benutzer.getAlterDatum());

                    } else {
                        // Fehler: Benutzer wurde nicht gefunden
                        Toast.makeText(this, "Kein Benutzer angemeldet. Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();

                        // Leitet zur Login-Activity weiter und schließt die aktuelle Activity
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
            });
    }

    /**
     * Wird aufgerufen, wenn die Activity zerstört wird.
     * Stellt sicher, dass der ExecutorService bzw. der Thread ordnungsgemäß heruntergefahren wird.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
