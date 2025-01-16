package hs.karlsruhe.de.familyflow.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hs.karlsruhe.de.familyflow.R;
import hs.karlsruhe.de.familyflow.data.DatabaseManager;
import hs.karlsruhe.de.familyflow.data.AppDatabase;
import hs.karlsruhe.de.familyflow.data.entity.Benutzer;
import hs.karlsruhe.de.familyflow.data.dao.BenutzerDao;
import hs.karlsruhe.de.familyflow.data.DBDaten;


public class Login extends AppCompatActivity {

    // Initialisierung der UI-Komponenten für E-Mail, Passwort und Fehlermeldung
    private EditText eingabeMail;
    private EditText eingabePasswort;
    private TextView fehleranzeige; // Fehleranzeige

    // onCreate-Methode wird beim Starten der Activity aufgerufen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setzt das Layout für die Login-Activity
        setContentView(R.layout.activity_login);

        // Testdaten initialisieren
        DBDaten.initialisiereDaten(this);

        // Initialisiere Frames als ConstraintLayout
        ConstraintLayout frameInitial = findViewById(R.id.frameInitial);
        ConstraintLayout frameLogin = findViewById(R.id.frameLogin);

        // Initialisiere UI-Elemente
        eingabeMail = findViewById(R.id.editTextMail);
        eingabePasswort = findViewById(R.id.editTextPasswort);
        fehleranzeige = findViewById(R.id.fehlermeldung);

        // Login-Button Initial-Frame
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Zeigt Einloggen-Frame mit Eingabefeldern und versteckt das Initial-Frame
        buttonLogin.setOnClickListener(v -> {
            frameInitial.setVisibility(View.GONE);
            frameLogin.setVisibility(View.VISIBLE);
        });

        // Login-Button Einloggen-Frame initialisieren
        Button buttonNewLogin = findViewById(R.id.buttonNewLogin);

        // Überprüft die Eingaben und navigiert zur Pinnwand-Activity
        buttonNewLogin.setOnClickListener(v -> userLogin());
    }

    // Methode zur Verarbeitung des Benutzer-Logins
    private void userLogin() {
        // Abrufen der eingegebenen E-Mail und des Passworts aus den EditText-Feldern
        String mail = eingabeMail.getText().toString().trim();
        String passwort = eingabePasswort.getText().toString().trim();

        // Berechnen des Hashes des Passworts, um die Sicherheit zu gewährleisten
        String hashedPassword = DBDaten.hashPassword(passwort);

        // Falls die Passwort-Hash-Berechnung fehlgeschlagen ist
        if (hashedPassword == null) {
            // Setzen einer Fehlermeldung in der UI
            runOnUiThread(() -> fehleranzeige.setText("Fehler bei der Passwort-Hash-Berechnung."));
            return; // Rückkehr, falls Fehler beim Hashen auftritt
        }

        // Starte einen neuen Thread, um die Datenbankoperation im Hintergrund auszuführen
        new Thread(() -> {
            // Zugriff auf die App-Datenbank
            AppDatabase db = DatabaseManager.getDatabase(this);
            // BenutzerDao zum Abrufen der Benutzerdaten aus der Datenbank
            BenutzerDao benutzerDao = db.benutzerDao();
            // Suche nach dem Benutzer mit der angegebenen E-Mail und dem gehashten Passwort
            Benutzer benutzer = benutzerDao.findBenutzerByEmailAndPassword(mail, hashedPassword);

            // UI wird im Haupt-Thread aktualisiert, wenn die Datenbankabfrage abgeschlossen ist
            runOnUiThread(() -> {
                if (benutzer != null) {

                    // Wenn der Benutzer gefunden wird, speichere die Benutzer-ID in den SharedPreferences, um sie später wieder abzurufen
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_id", benutzer.getBenutzerId());  // Die ID des Benutzers speichern
                    editor.apply(); // Änderungen anwenden

                    // Weiterleitung zur Pinnwand-Activity (Hauptbildschirm nach Login)
                    Intent intent = new Intent(Login.this, Pinnwand.class);
                    startActivity(intent); // Starten der neuen Activity
                } else {
                    // Falls die Anmeldedaten nicht korrekt sind, eine Fehlermeldung anzeigen
                    fehleranzeige.setVisibility(View.VISIBLE); // Sichtbarmachen der Fehlermeldung
                    fehleranzeige.setText("Die eingegebenen Daten sind falsch."); // Fehlermeldung setzen
                }
            });
        }).start(); // Starten des Hintergrund-Threads
    }

}
