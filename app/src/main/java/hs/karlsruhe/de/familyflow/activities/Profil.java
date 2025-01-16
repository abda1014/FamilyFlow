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

public class Profil extends AppCompatActivity {

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // ExecutorService für Hintergrundaufgaben initialisieren
        executorService = Executors.newSingleThreadExecutor();

        Button buttonZurueckEinstellungen = findViewById(R.id.buttonZurueckEinstellungen);
        buttonZurueckEinstellungen.setOnClickListener(v -> finish()); // Zurück zur vorherigen Activity

        // Benutzerprofil laden
        ladeBenutzerProfil();
    }

    private void ladeBenutzerProfil() {
        // Benutzer-ID aus den SharedPreferences abrufen
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);  // Benutzer-ID abrufen

            executorService.execute(() -> {
                // Datenbankzugriff initialisieren
                AppDatabase db = DatabaseManager.getDatabase(this);
                BenutzerDao benutzerDao = db.benutzerDao();
                Benutzer benutzer = benutzerDao.findBenutzerById(userId);  // Benutzer anhand der gespeicherten ID finden

                // Logging, um zu überprüfen, ob der Benutzer gefunden wird
                if (benutzer == null) {
                    Log.e("ProfilActivity", "Benutzer nicht gefunden mit ID: " + userId);
                } else {
                    Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname() +"und" + userId);
                }

                // Ergebnis zurück an den Hauptthread übergeben
                runOnUiThread(() -> {
                    if (benutzer != null) {
                        Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname());
                        // Profilbild-URL abrufen
                        String imageProfilUrl = benutzer.getImageProfil();

                        // Überprüfen, ob die URL gültig ist
                        if (imageProfilUrl != null && !imageProfilUrl.isEmpty()) {
                            // Verwenden von Glide, um das Bild zu laden
                            ImageView imageView = findViewById(R.id.imageViewProfil);
                            Glide.with(this)  // Context angeben
                                    .load(imageProfilUrl)  // Bild-URL
                                    .into(imageView);  // Das ImageView, in das das Bild geladen wird
                        } else {
                            // Fehler, wenn keine Bild-URL vorhanden ist
                            ImageView imageView = findViewById(R.id.imageViewProfil);
                            Glide.with(this)
                                    .load(R.drawable.defaultavatar) // Standardbild laden
                                    .into(imageView);
                        }


                        // Vorname, Nachname und E-Mail setzen
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
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ExecutorService ordnungsgemäß herunterfahren
        executorService.shutdown();
    }
}
