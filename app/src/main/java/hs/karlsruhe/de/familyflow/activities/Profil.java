package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import androidx.room.Room;
import android.net.Uri;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Profil extends AppCompatActivity {

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // ExecutorService zum Hintergrund-Thread ausführen
        executorService = Executors.newSingleThreadExecutor();

        // Datenbankabfrage im Hintergrund ausführen
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(this);
            BenutzerDao benutzerDao = db.benutzerDao();
            Benutzer benutzer = benutzerDao.findBenutzerById("1");


            // Logging, um zu überprüfen, ob der Benutzer gefunden wird
            if (benutzer == null) {
                Log.e("ProfilActivity", "Benutzer nicht gefunden mit ID: 1");
            } else {
                Log.d("ProfilActivity", "Benutzer gefunden: " + benutzer.getVorname());
            }

            // Ergebnis zurück an den Hauptthread (UI-Thread) übergeben
            runOnUiThread(() -> {
                if (benutzer != null) {
                    // Weiterleitung zur Pinnwand
                    String imageProfilUrl = benutzer.getImageProfil();
                    ImageView imageView = findViewById(R.id.imageViewProfil);
                    imageView.setImageURI(Uri.parse(imageProfilUrl));
                } else {
                    // Fehler: Benutzer wurde nicht gefunden
                    Toast.makeText(Profil.this, "Benutzer nicht gefunden", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ExecutorService schließen, wenn die Aktivität zerstört wird
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
