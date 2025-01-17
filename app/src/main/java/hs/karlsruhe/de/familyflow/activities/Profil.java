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
 * Diese Activity zeigt die Profildaten des aktuell angemeldeten Benutzers an.
 * Sie lädt Benutzerinformationen aus der Datenbank und stellt diese dar.
 */
public class Profil extends AppCompatActivity {

    // ExecutorService für asynchrone Hintergrundaufgaben, die außerhalb des Haupt-Threads laufen
    private ExecutorService executorService;

    /**
     * Wird beim Starten der Profil-Activity ausgeführt und initialisiert die View.
     * @param savedInstanceState Enthält die zuletzt gespeicherten Daten, falls die Activity nach einer Zerstörung wiederhergestellt wird.
     *                            Falls keine Daten vorhanden sind, ist dieser Wert null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // ExecutorService für Hintergrundaufgaben initialisieren, mit einem einzigen Thread
        executorService = Executors.newSingleThreadExecutor();

        // Button für den Zurück-Button initialisieren, der den Benutzer zur vorherigen Activity zurückführt
        Button buttonZurueckEinstellungen = findViewById(R.id.buttonZurueckEinstellungen);
        buttonZurueckEinstellungen.setOnClickListener(v -> finish()); // Beendet die aktuelle Activity und geht zurück

        // Lädt die Profildaten des Benutzers aus der Datenbank
        ladeBenutzerProfil();
    }

    /**
     * Lädt die Benutzerdaten aus der Datenbank, basierend auf der Benutzer-ID, die in den SharedPreferences gespeichert ist.
     * Diese Methode wird im Hintergrund ausgeführt, um die UI nicht zu blockieren.
     */
    private void ladeBenutzerProfil() {
        // Benutzer-ID aus den SharedPreferences abrufen
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        // Überprüft, ob eine Benutzer-ID vorhanden ist, bevor der Datenbankzugriff erfolgt
        if (userId != null) {
            executorService.execute(() -> {
                // Zugriff auf die Datenbank und das Benutzer-Datenbankobjekt
                AppDatabase db = DatabaseManager.getDatabase(this);
                BenutzerDao benutzerDao = db.benutzerDao();
                Benutzer benutzer = benutzerDao.findBenutzerById(userId);   // Benutzer anhand der ID finden

                // Logging, um zu überprüfen, ob der Benutzer gefunden wird
                if (benutzer == null) {
                    Log.e("ProfilActivity", "Benutzer nicht gefunden mit ID: " + userId);
                } else {
                    Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname() + " und ID: " + userId);
                }

                // UI wird im Haupt-Thread aktualisiert, um UI-Elemente zu bearbeiten
                runOnUiThread(() -> {
                    if (benutzer != null) {
                        // Logging für erfolgreiche Benutzerabfrage
                        Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname());

                        // Profilbild-URL des Benutzers abrufen und anzeigen
                        String imageProfilUrl = benutzer.getImageProfil();

                        // Lädt das Profilbild oder ein Standardbild, falls keine URL vorhanden ist
                        ImageView imageView = findViewById(R.id.imageViewProfil);
                        if (imageProfilUrl != null && !imageProfilUrl.isEmpty()) {
                            Glide.with(this)
                                    .load(imageProfilUrl)  // URL für Profilbild laden
                                    .into(imageView);
                        } else {
                            Glide.with(this)
                                    .load(R.drawable.defaultavatar) // Standardbild verwenden, falls keine URL vorhanden ist
                                    .into(imageView);
                        }

                        // Benutzerinformationen in die entsprechenden TextViews einfügen
                        TextView vornameTextView = findViewById(R.id.textViewVorname);
                        TextView nachnameTextView = findViewById(R.id.textViewNachname);
                        TextView emailTextView = findViewById(R.id.textViewEmail);
                        TextView altersdatumTextView = findViewById(R.id.textViewAltersdatum);

                        // Die Textfelder werden mit den Profilinformationen des Benutzers gefüllt
                        vornameTextView.setText(benutzer.getVorname());
                        nachnameTextView.setText(benutzer.getNachname());
                        emailTextView.setText(benutzer.getEmail());
                        altersdatumTextView.setText(benutzer.getAlterDatum());
                    } else {
                        // Fehlerfall: Kein Benutzer gefunden, daher wird eine Nachricht angezeigt und der Benutzer wird zum Login weitergeleitet
                        Toast.makeText(this, "Kein Benutzer angemeldet. Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();

                        // Wechselt zur Login-Activity und schließt die aktuelle Activity
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
            });
        } else {
            // Falls keine Benutzer-ID in den SharedPreferences vorhanden ist, wird der Benutzer ebenfalls zum Login weitergeleitet
            Toast.makeText(this, "Benutzer-ID nicht gefunden. Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Wird aufgerufen, wenn die Activity zerstört wird.
     * Diese Methode stellt sicher, dass der ExecutorService ordnungsgemäß heruntergefahren wird, um unnötige Ressourcen zu vermeiden.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();  // Beendet den ExecutorService, um den Thread sauber zu schließen
    }
}
