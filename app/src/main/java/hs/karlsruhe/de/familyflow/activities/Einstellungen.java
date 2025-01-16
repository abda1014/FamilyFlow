package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import hs.karlsruhe.de.familyflow.R;

/**
 * Die Einstellungen-Activity:
 * Ermöglicht dem Benutzer zwischen Logout und Profilansicht zu navigieren.
 */

public class Einstellungen extends AppCompatActivity {

    /**
     * Wird beim Laden der Einstellungen-Activity ausgeführt und initialisiert die View.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellung);

        // Logout-Button: Initialisiert den Button der zum Logout führt
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });

        // Profil-Button: Initialisiert den Button der zur Profil-Activity leitet
        Button buttonProfil = findViewById(R.id.buttonProfil);
        buttonProfil.setOnClickListener(v -> {
            // Startet die Profil-Activity
            Intent intent = new Intent(Einstellungen.this, Profil.class);
            startActivity(intent);
        });

        // Zurück-Button: Initialisiert den Button der zur vorherigen Activity leitet
        Button buttonZurueckPinnwand = findViewById(R.id.buttonZurueckPinnwand);
        // Zurück zur vorherigen Activity
        buttonZurueckPinnwand.setOnClickListener(v -> finish());
    }

    /**
     * Zeigt einen Bestätigungsdialog, wenn der Benutzer sich abmelden möchte.
     */
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Abmelden?")
                .setMessage("Möchtest du dich wirklich abmelden?")
                .setPositiveButton("Nein", (dialog, which) -> {
                    // Wenn der Benutzer "Nein" klickt, Dialog schließen
                    dialog.dismiss();
                })
                .setNegativeButton("Ja", (dialog, which) -> {
                    // Wenn der Benutzer "Ja" klickt, dann logout
                    logoutUser();
                })
                .create()
                .show();
    }

    /**
     * Führt die Logout-Logik aus, leitet zur Login-Activity weiter und zeigt eine Bestätigungsmeldung an.
     */
    private void logoutUser() {
        // Öffnet die Login-Activity
        Intent intent = new Intent(Einstellungen.this, Login.class);
        startActivity(intent);
        finish();

        //  Zeigt eine kurze Bestätigungsmeldung
        Toast.makeText(this, "Erfolgreich abgemeldet", Toast.LENGTH_SHORT).show();
    }
}
